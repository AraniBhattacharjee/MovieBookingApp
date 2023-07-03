package com.cts.adminservice.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document
public class SecretQuestion {
	@Id
	private Long id;

	@Field("question")
	private String question;
}
