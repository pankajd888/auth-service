package com.digi.auth.domain.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "deleted_by")
	private String deletedBy;

	public CustomerEntity() {}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

	public String getCreatedBy() { return createdBy; }
	public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

	public LocalDateTime getModifiedAt() { return modifiedAt; }
	public void setModifiedAt(LocalDateTime modifiedAt) { this.modifiedAt = modifiedAt; }

	public String getModifiedBy() { return modifiedBy; }
	public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

	public String getDeletedBy() { return deletedBy; }
	public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }

	@PrePersist
	public void prePersist() {
		if (this.status == null) this.status = "ACT";
		if (this.createdAt == null) this.createdAt = java.time.LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.modifiedAt = java.time.LocalDateTime.now();
	}

	public void softDelete(String actor) {
		this.status = "DEL";
		this.deletedBy = actor;
		this.modifiedAt = LocalDateTime.now();
	}
}

