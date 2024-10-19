package main.controllers;

import jakarta.servlet.http.HttpServletRequest;
import main.entities.Job;
import main.entities.JobDetailsDto;
import main.services.EmployerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {
    EmployerService employerService;

    EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @PostMapping("/jobs")
    public Job addNewJob(@RequestBody JobDetailsDto jobDetails, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        return employerService.addJob(jobDetails, userDetails.getUsername());
    }

    @GetMapping("/jobs")
    public List<Job> getJobs(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");

        return employerService.getJobs(userDetails.getUsername());
    }

    @PutMapping("/jobs/{jobId}")
    public Job updateJobDetails(@PathVariable int jobId, @RequestBody JobDetailsDto jobDetails) {
        return employerService.updateJob(jobId, jobDetails);
    }

    @DeleteMapping("/jobs/{jobId}")
    public Map<String, Object> deleteJob(@PathVariable int jobId) {
        employerService.deleteJob(jobId);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Successfully deleted the job!");

        return  res;
    }
}
