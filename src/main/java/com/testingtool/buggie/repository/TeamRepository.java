package com.testingtool.buggie.repository;

import com.testingtool.buggie.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team,String> {
    Optional<Team> findByCreatedBy(String id);
}
