package com.poll.restapi_spring_boot.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;

    @ElementCollection // it indicates that the field is a collection of basic types or embeddable
                       // objects
    private List<OptionVote> options = new ArrayList<>();

    // @ElementCollection
    // private List<String> votes = new ArrayList<>();
}