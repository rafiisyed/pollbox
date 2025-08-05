package com.poll.restapi_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poll.restapi_spring_boot.models.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

}