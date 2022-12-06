package com.jobboard.tracker.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.jobboard.tracker.enums.JobStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "job_applications_table")
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueApplicationConstraint", columnNames = {"user_name", "school", "company", "job_id", "status"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationEntity {


	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	 
	@Column(name = "user_name", length = 100, nullable = false, unique = false)
	private String userName;
	
	@Column(name = "school", length = 100, nullable = false, unique = false)
	private String school;
	
	@Column(name = "company", length = 100, nullable = false, unique = false)
	private String company;
	
	@Column(name = "position", length = 100, nullable = false, unique = false)
	private String position;
	
	@Column(name = "status", nullable = false, unique = false)
	private String status; 
	
	@Column(name = "job_id", length = 100, nullable = false, unique = false)
	private String jobId;
	
	@Column(name = "location", length = 100, nullable = false, unique = false)
	private String location;
	
	@Column(name = "time", nullable = false, unique = false)
	private Date time;
	
	
}
