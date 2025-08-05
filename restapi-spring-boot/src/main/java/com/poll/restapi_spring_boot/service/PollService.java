package com.poll.restapi_spring_boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.poll.restapi_spring_boot.models.OptionVote;
import com.poll.restapi_spring_boot.models.Poll;
import com.poll.restapi_spring_boot.repository.PollRepository;

@Service
public class PollService {

    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(Poll poll) {
        poll.setId(null);
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    // This method will handle voting logic
    // It will fetch the poll by ID, increment the vote count for the selected
    // option,
    public void vote(Long pollId, int optionIndex) {
        Optional<Poll> poll = getPollById(pollId); // Fetch the poll by ID
        if (poll.isPresent()) {
            List<OptionVote> options = poll.get().getOptions();
            if (optionIndex >= 0 && optionIndex < options.size()) {
                OptionVote selectedOption = options.get(optionIndex);
                selectedOption.setVoteCount(selectedOption.getVoteCount() + 1); // Increment the vote count for the
                                                                                // selected option
                pollRepository.save(poll.get()); // Save the updated poll
            } else {
                throw new IllegalArgumentException("Invalid option index");
            }
        } else {
            throw new IllegalArgumentException("Poll not found");
        }
    }

    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
}