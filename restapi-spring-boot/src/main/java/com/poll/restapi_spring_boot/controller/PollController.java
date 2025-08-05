package com.poll.restapi_spring_boot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poll.restapi_spring_boot.models.Poll;
import com.poll.restapi_spring_boot.request.Vote;
import com.poll.restapi_spring_boot.service.PollService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/polls")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        return pollService.createPoll(poll);
    }

    @GetMapping()
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {
        return pollService.getPollById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vote")
    public void vote(@RequestBody Vote vote) {
        pollService.vote(vote.getPollId(), vote.getOptionIndex());
        // The vote method in PollService handles the logic of incrementing the vote
        // count for the selected option
    }

    @DeleteMapping("/delete/{id}")
    public void deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
    }
}