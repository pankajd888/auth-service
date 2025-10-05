package com.digi.auth.adapter.api.service;

import java.util.List;
import com.digi.auth.domain.model.dto.CustomerCreateDto;
import com.digi.auth.domain.model.dto.CustomerUpdateDto;
import com.digi.auth.domain.model.dto.CustomerResponseDto;

public interface CustomerService {

    CustomerResponseDto getByUsername(String username);

    CustomerResponseDto create(CustomerCreateDto dto, String actorUsername);
    
    CustomerResponseDto update(Long id, CustomerUpdateDto dto, String actorUsername);
    
    void softDelete(Long id, String actorUsername);
    
    List<CustomerResponseDto> getCurrentUserCustomers(String actorUsername);

    void softDeleteCustomer(Long id, String actor);
}


