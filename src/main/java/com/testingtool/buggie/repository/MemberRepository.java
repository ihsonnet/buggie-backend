package com.testingtool.buggie.repository;

import com.testingtool.buggie.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long > {
}
