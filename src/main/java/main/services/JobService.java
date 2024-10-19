package main.services;

import main.entities.Job;
import main.exceptions.ResourceNotFoundException;
import main.repository.AddressRepository;
import main.repository.JobRepository;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    UserRepository userRepository;
    JobRepository jobRepository;
    AddressRepository addressRepository;

    JobService(
            UserRepository userRepository,
            JobRepository jobRepository,
            AddressRepository addressRepository
    ) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.addressRepository = addressRepository;
    }

    public Job getJobDetails(int jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);

        if(optionalJob.isEmpty()) {
            throw new ResourceNotFoundException("Job not found for id: "+jobId);
        }

        return optionalJob.get();
    }

    public List<Job> getJobs(Integer addressId) {
        if(addressId == null) {
            return jobRepository.findAll();
        } else {
            return jobRepository.findByLocation(addressId);
        }
    }
}
