package com.digi.auth.adapter.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digi.auth.adapter.repository.CustomerRepository;
import com.digi.auth.domain.model.dto.CustomerResponseDto;
import com.digi.auth.domain.model.entity.CustomerEntity;
import com.digi.auth.exception.NotFoundException;
import com.digi.auth.exception.UnauthorizedException;
import com.digi.auth.security.InMemoryTokenStore;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private InMemoryTokenStore tokenStore;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void getByUsername_success_mapsDto() {
        CustomerEntity entity = new CustomerEntity();
        entity.setUsername("jane");
        when(customerRepository.findByUsername("jane")).thenReturn(Optional.of(entity));

        CustomerResponseDto dto = customerService.getByUsername("jane");

        assertEquals("jane", dto.getUsername());
    }

    @Test
    void getByUsername_notFound_throwsException() {
        when(customerRepository.findByUsername("missing")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.getByUsername("missing"));
    }

    @Test
    void getCurrentUserCustomers_success_returnsList() {
        when(tokenStore.resolveUsername("token123")).thenReturn(Optional.of("john"));

        CustomerEntity e1 = new CustomerEntity();
        e1.setUsername("john");
        e1.setFullName("John A");
        e1.setEmail("john@example.com");
        e1.setPhone("123");

        CustomerEntity e2 = new CustomerEntity();
        e2.setUsername("john");
        e2.setFullName("John B");
        e2.setEmail("john.b@example.com");
        e2.setPhone("456");

        when(customerRepository.findAllByUsername("john")).thenReturn(Arrays.asList(e1, e2));

        List<CustomerResponseDto> list = customerService.getCurrentUserCustomers("token123");

        assertEquals(2, list.size());
        assertEquals("john", list.get(0).getUsername());
        assertEquals("John A", list.get(0).getFullName());
        assertEquals("john.b@example.com", list.get(1).getEmail());
    }

    @Test
    void getCurrentUserCustomers_badToken_throwsUnauthorized() {
        when(tokenStore.resolveUsername("bad")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> customerService.getCurrentUserCustomers("bad"));
    }

    @Test
    void softDeleteCustomer_happyPath_savesDeleted() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(10L);
        entity.setUsername("john");
        when(customerRepository.findById(10L)).thenReturn(Optional.of(entity));

        customerService.softDeleteCustomer(10L, "john");

        verify(customerRepository).save(entity);
        assertEquals("DEL", entity.getStatus());
        assertEquals("john", entity.getDeletedBy());
    }

    @Test
    void softDeleteCustomer_wrongActor_throwsUnauthorized() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(11L);
        entity.setUsername("alice");
        when(customerRepository.findById(11L)).thenReturn(Optional.of(entity));

        assertThrows(UnauthorizedException.class, () -> customerService.softDeleteCustomer(11L, "bob"));
    }

    @Test
    void softDeleteCustomer_notFound_throwsNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.softDeleteCustomer(99L, "actor"));
    }
}


