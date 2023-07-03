package com.cts.userauthservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cts.userauthservice.model.SecretQuestion;

public interface SecretQuestionRepository extends MongoRepository<SecretQuestion, Long> {

}
