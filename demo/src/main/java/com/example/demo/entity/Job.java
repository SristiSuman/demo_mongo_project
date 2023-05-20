package com.example.demo.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")
public class Job {
    @Id
    private String id;
    private String assetId;
    private String description;
    private List<String> subJob;
    private int counter;

    public Job() {
    }

    public Job(String assetId, String description, List<String> subJob, int counter) {
        this.assetId = assetId;
        this.description = description;
        this.subJob = subJob;
        this.counter = counter;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getSubJob() {
		return subJob;
	}

	public void setSubJob(List<String> subJob) {
		this.subJob = subJob;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
    
    
}
