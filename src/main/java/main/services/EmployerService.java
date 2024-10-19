package main.services;

import jakarta.transaction.Transactional;
import main.exceptions.ResourceNotFoundException;
import main.exceptions.UserNotFoundException;
import main.entities.Address;
import main.entities.Job;
import main.entities.JobDetailsDto;
import main.entities.User;
import main.repository.AddressRepository;
import main.repository.JobRepository;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {
    UserRepository userRepository;
    JobRepository jobRepository;
    AddressRepository addressRepository;

    EmployerService(
            UserRepository userRepository,
            JobRepository jobRepository,
            AddressRepository addressRepository
    ) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.addressRepository = addressRepository;
    }

    public Job addJob(JobDetailsDto jobDetails, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        Optional<Address> optionalAddress = addressRepository.findById(jobDetails.getAddressId());

        if (optionalAddress.isEmpty()) {
            throw new IllegalArgumentException("Address not found in db");
        }

        Job job = new Job();
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setJobPoster(user);
        job.setAddress(optionalAddress.get());

        return jobRepository.save(job);
    }

    public List<Job> getJobs(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserNotFoundException("User not found in db!");
        }
        return jobRepository.findByJobPoster(user);
    }

    @Transactional
    public Job updateJob(int jobId, JobDetailsDto jobDetails) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        Optional<Address> optionalAddress = addressRepository.findById(jobDetails.getAddressId());

        if(optionalJob.isEmpty()) {
            throw new ResourceNotFoundException("Job not found with id: "+jobId);
        }
        Job job = optionalJob.get();

        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setAddress(optionalAddress.get());

        return jobRepository.save(job);
    }

    @Transactional
    public void deleteJob(int jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);

        if(optionalJob.isEmpty()) {
            throw new ResourceNotFoundException("Job not found!");
        }

        jobRepository.delete(optionalJob.get());
    }
}
