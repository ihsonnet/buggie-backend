package com.testingtool.buggie.repository;

import com.testingtool.buggie.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug,String > {

    List<Bug> findByCreatedBy(String id);
    List<Bug> findByAssignedTo(String id);
    List<Bug> findByTeamId(String id);
}
