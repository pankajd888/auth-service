package com.digi.auth.adapter.api.service.impl;

import org.springframework.stereotype.Service;

import com.digi.auth.adapter.api.service.CustomerService;
import com.digi.auth.adapter.repository.CustomerRepository;
import com.digi.auth.domain.model.dto.CustomerCreateDto;
import com.digi.auth.domain.model.dto.CustomerResponseDto;
import com.digi.auth.domain.model.dto.CustomerUpdateDto;
import com.digi.auth.domain.model.entity.CustomerEntity;
import com.digi.auth.exception.NotFoundException;
import com.digi.auth.exception.UnauthorizedException;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDto getByUsername(String username) {
        CustomerEntity entity = customerRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + username));

        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setUsername(entity.getUsername());
        dto.setFullName(null);
        dto.setEmail(null);
        dto.setPhone(null);
        return dto;
    }

    @Override
    public CustomerResponseDto create(CustomerCreateDto dto, String actor) {
        // Only allow creating rows for self; ignore body.username if present
        CustomerEntity e = new CustomerEntity();
        e.setUsername(actor);
        e.setFullName(dto.getFullName());
        e.setEmail(dto.getEmail());
        e.setPhone(dto.getPhone());
        // audit: createdBy must be set; createdAt handled by @PrePersist; status default 'ACT'
        e.setStatus("ACT");
        e.setCreatedBy(actor);
        var saved = customerRepository.save(e);
        return new CustomerResponseDto(saved.getUsername(), saved.getFullName(), saved.getEmail(), saved.getPhone());
    }

    @Override
    public CustomerResponseDto update(Long id, CustomerUpdateDto dto, String actor) {
        var e = customerRepository.findByIdAndUsername(id, actor)
            .orElseThrow(() -> new NotFoundException("Customer not found or not owned: " + id));
        if (dto.getFullName() != null) e.setFullName(dto.getFullName());
        if (dto.getEmail() != null)    e.setEmail(dto.getEmail());
        if (dto.getPhone() != null)    e.setPhone(dto.getPhone());
        // audit: modifiedBy set here; modifiedAt via @PreUpdate
        e.setModifiedBy(actor);
        var saved = customerRepository.save(e);
        return new CustomerResponseDto(saved.getUsername(), saved.getFullName(), saved.getEmail(), saved.getPhone());
    }

    @Override
    public void softDelete(Long id, String actor) {
        var e = customerRepository.findByIdAndUsername(id, actor)
            .orElseThrow(() -> new NotFoundException("Customer not found or not owned: " + id));
        // audit: soft delete helper on entity
        e.setStatus("DEL");
        e.setDeletedBy(actor);
        e.setModifiedBy(actor); // modifiedAt via @PreUpdate
        customerRepository.save(e);
    }

    @Override
    public List<CustomerResponseDto> getCurrentUserCustomers(String actor) {
        return customerRepository.findAllByUsername(actor).stream()
            .filter(c -> !"DEL".equalsIgnoreCase(c.getStatus()))
            .map(c -> new CustomerResponseDto(c.getUsername(), c.getFullName(), c.getEmail(), c.getPhone()))
            .toList();
    }

    @Override
    public void softDeleteCustomer(Long id, String actor) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found: id=" + id));

        if (!actor.equals(entity.getUsername())) {
            throw new UnauthorizedException("Forbidden: actor does not own the resource");
        }

        entity.softDelete(actor);
        customerRepository.save(entity);
    }
}


