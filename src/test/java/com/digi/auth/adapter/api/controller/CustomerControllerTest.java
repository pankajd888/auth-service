package com.digi.auth.adapter.api.controller;

import com.digi.auth.adapter.api.service.CustomerService;
import com.digi.auth.domain.model.dto.CustomerResponseDto;
import com.digi.auth.security.InMemoryTokenStore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private InMemoryTokenStore tokenStore;

    @Test
    void myCustomers_returnsListForValidToken() throws Exception {
        List<CustomerResponseDto> dtoList = Arrays.asList(
                new CustomerResponseDto("alice", "Alice Doe", "alice@example.com", "111-1111"),
                new CustomerResponseDto("alice", "Alice Doe", "alice.work@example.com", "222-2222")
        );
        Mockito.when(customerService.getCurrentUserCustomers(anyString())).thenReturn(dtoList);

        mockMvc.perform(get("/api/customers").header("X-Auth-Token", "token-123"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void myCustomers_missingHeader_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCustomer_missingHeader_returnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCustomer_validHeader_returnsNoContent() throws Exception {
        Mockito.doNothing().when(customerService).softDeleteCustomer(1L, "alice");
        Mockito.when(tokenStore.resolveUsername("token-alice")).thenReturn(Optional.of("alice"));

        mockMvc.perform(delete("/api/customers/1").header("X-Auth-Token", "token-alice"))
                .andExpect(status().isNoContent());
    }
}


