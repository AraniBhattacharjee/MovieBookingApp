package com.cts.userauthservice.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection="secretQuestions")
public class SecretQuestion {
	@Id
	@Field("_id")
	private Long id;

	@Field("question")
	private String question;
}
