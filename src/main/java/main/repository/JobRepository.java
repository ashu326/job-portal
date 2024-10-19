package main.repository;

import main.entities.Job;
import main.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByJobPoster(User user);

    @Query("Select j FROM Job j WHERE j.address.id = :addressId")
    List<Job> findByLocation(@Param("addressId") int addressId);
}
