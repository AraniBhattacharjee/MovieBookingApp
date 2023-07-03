package com.cts.adminservice.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user")
public class User {
	@Id
	private String userId;
	@NotNull(message="First name should not be null")
	private String firstName;
	@NotNull(message="Last name should not be null")
	private String lastName;
	@NotNull(message="Email should not be null")
	private String email;
	@NotNull(message="Password should not be null")
	private String password;
	private Role role;
	@NotNull(message="Secret Question Id should not be null")
	private SecretQuestion secretQuestion;
	@NotNull(message="Anwer to secret question should not be null")
	private String answerToSecretQuestion;

}