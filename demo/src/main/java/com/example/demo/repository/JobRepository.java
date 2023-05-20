package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Job;

public interface JobRepository extends MongoRepository<Job, String>{

}
