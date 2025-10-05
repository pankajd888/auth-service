package com.digi.auth.adapter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digi.auth.domain.model.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	Optional<CustomerEntity> findByUsername(String username);

	List<CustomerEntity> findAllByUsername(String username);

	Optional<CustomerEntity> findByIdAndUsername(Long id, String username);

	@Query("""
	 select new com.digi.auth.domain.model.dto.CustomerResponseDto(
	   c.username, c.fullName, c.email, c.phone
	 )
	 from com.digi.auth.domain.model.entity.CustomerEntity c
	 where c.username = :username
	""")
	Optional<com.digi.auth.domain.model.dto.CustomerResponseDto> fetchDtoByUsername(@Param("username") String username);
}


