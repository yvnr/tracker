package com.jobboard.tracker.models;

import java.util.Date;

import com.jobboard.tracker.enums.JobStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication { 
	
	private long id;
	private String user;
	private String school;
	private String company;
	private String position;
	private JobStatusEnum status;
	private String jobId;
	private String location;
	private Date time;
}
