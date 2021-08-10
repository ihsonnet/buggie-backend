package com.testingtool.buggie.repository;

import com.testingtool.buggie.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug,String > {
}
