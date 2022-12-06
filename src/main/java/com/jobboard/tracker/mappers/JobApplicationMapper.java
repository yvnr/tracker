package com.jobboard.tracker.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jobboard.tracker.entities.JobApplicationEntity;
import com.jobboard.tracker.models.JobApplication;

@Mapper
public interface JobApplicationMapper {

	@Insert("insert into job_applications_table (user_name, school, company, position, status, job_id, location, time) values (#{user}, #{school}, #{company}, #{position}, #{status}, #{jobId}, #{location}, #{time})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") 
	public long addNewApplication(JobApplication jobApplication);
	
	
	@Select("select * from job_applications_table where id = #{id}")
	public JobApplication findApplicationById(long id);
	
	
	@Update("update job_applications_table set company = #{company}, position = #{position}, status = #{status}, job_id = #{jobId}, location = #{location}, time = #{time} where id = #{id}")
	public long updateJobApplication(JobApplication jobApplication);
	
	@Delete("delete from job_applications_table where id = #{id}")
	public void deleteJobApplicationWithId(long id);
	
	@Select("select * from job_applications_table where id >= #{startId} order by id limit #{numberOfRecords}")
	public ArrayList<JobApplication> fetchJobApplications(long startId, long numberOfRecords);
	
	@Select("select count(*) from job_applications_table")
	public long fetchJobApplicationsCount();
}
