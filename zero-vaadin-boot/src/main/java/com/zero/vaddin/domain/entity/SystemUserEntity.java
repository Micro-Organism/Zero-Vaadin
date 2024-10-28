package com.zero.vaddin.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;

	public SystemUserEntity(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

}