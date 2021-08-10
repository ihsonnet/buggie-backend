package com.testingtool.buggie.repository;

import com.testingtool.buggie.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,String> {

}
