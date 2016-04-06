package org.rabix.engine.rest.api.impl;

import java.util.Collections;

import javax.ws.rs.core.Response;

import org.rabix.bindings.model.Job;
import org.rabix.engine.rest.api.JobHTTPService;
import org.rabix.engine.rest.service.JobServiceException;
import org.rabix.engine.rest.service.JobService;

import com.google.inject.Inject;

import ch.qos.logback.core.status.Status;

public class JobHTTPServiceImpl implements JobHTTPService {

  private final JobService jobService;

  @Inject
  public JobHTTPServiceImpl(JobService jobService) {
    this.jobService = jobService;
  }
  
  @Override
  public Response create(Job job) {
    try {
      return ok(jobService.create(job));
    } catch (Exception e) {
      return error();
    }
  }
  
  @Override
  public Response get() {
    return ok(jobService.get());
  }
  
  @Override
  public Response get(String id) {
    Job job = jobService.get(id);
    if (job == null) {
      return error();
    }
    return ok(job);
  }
  
  @Override
  public Response save(String id, Job job) {
    try {
      jobService.update(job);
    } catch (JobServiceException e) {
      return error();
    }
    return ok();
  }
  
  private Response error() {
    return Response.status(Status.ERROR).build();
  }
  
  private Response ok() {
    return Response.ok(Collections.emptyMap()).build();
  }
  
  private Response ok(Object items) {
    if (items == null) {
      return ok();
    }
    return Response.ok().entity(items).build();
  }
}
