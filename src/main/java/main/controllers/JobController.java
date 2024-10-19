package main.controllers;

import main.entities.Job;
import main.services.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobController {
    JobService jobService;

    JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public List<Job> getJobs(@RequestParam(required = false) int addressId) {
        return jobService.getJobs(addressId);
    }

    @GetMapping("/jobs/{jobId}")
    public Job getJobDetails(@PathVariable int jobId) {
        return jobService.getJobDetails(jobId);
    }
}
