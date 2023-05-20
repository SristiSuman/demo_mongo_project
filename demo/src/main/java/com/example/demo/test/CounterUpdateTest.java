package com.example.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.controller.JobController;

@Component
public class CounterUpdateTest implements CommandLineRunner {
    private final JobController jobController;

    @Autowired
    public CounterUpdateTest(JobController jobController) {
        this.jobController = jobController;
    }

    @Override
    public void run(String... args) {
        // Perform parallel counter updates
        int numThreads = 10; // Set the number of threads
        String jobId = "6467c1180ba7f86fa935c590"; // Specify the job ID to update its counter

        jobController.incrementCounterParallel(jobId, numThreads);

        try {
            Thread.sleep(5000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(jobController.getJobById(jobId));
    }
}
