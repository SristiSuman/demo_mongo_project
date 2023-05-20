package com.example.demo.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Job;
import com.example.demo.repository.JobRepository;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobRepository jobRepository;
    private final MongoOperations mongoOperations;

    @Autowired
    public JobController(JobRepository jobRepository, MongoOperations mongoOperations) {
        this.jobRepository = jobRepository;
        this.mongoOperations = mongoOperations;
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable String id) {
        return jobRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable String id, @RequestBody Job updatedJob) {
        updatedJob.setId(id);
        return jobRepository.save(updatedJob);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable String id) {
        jobRepository.deleteById(id);
    }

    @PutMapping("/{id}/decrement")
    public Job decrementCounter(@PathVariable String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().inc("counter", -1);
        return mongoOperations.findAndModify(query, update, Job.class);
    }
    
    @PutMapping("/{id}/increment-parallel")
    public void incrementCounterParallel(@PathVariable String id, @RequestParam int numThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> incrementCounter(id));
        }

        executorService.shutdown();
    }

    private void incrementCounter(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().inc("counter", 1);
        mongoOperations.findAndModify(query, update, Job.class);
    }
}