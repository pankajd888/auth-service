package com.digi.auth.adapter.api.controller;

import com.digi.auth.adapter.api.service.CustomerService;
import com.digi.auth.domain.model.dto.CustomerCreateDto;
import com.digi.auth.domain.model.dto.CustomerResponseDto;
import com.digi.auth.domain.model.dto.CustomerUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@RequestHeader("X-Actor") String actor,
                                      @Valid @RequestBody CustomerCreateDto dto) {
        return customerService.create(dto, actor);
    }

    @PutMapping("/{id}")
    public CustomerResponseDto update(@RequestHeader("X-Actor") String actor,
                                      @PathVariable Long id,
                                      @Valid @RequestBody CustomerUpdateDto dto) {
        return customerService.update(id, dto, actor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader("X-Actor") String actor,
                       @PathVariable Long id) {
        customerService.softDelete(id, actor);
    }

    /**
     * List only the current user's rows (not deleted).
     */
    @GetMapping
    public java.util.List<CustomerResponseDto> myCustomers(@RequestHeader("X-Actor") String actor) {
        return customerService.getCurrentUserCustomers(actor);
    }


    @GetMapping("/{username}")
    public ResponseEntity<CustomerResponseDto> getByUsername(@PathVariable("username") String username) {
        CustomerResponseDto response = customerService.getByUsername(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


