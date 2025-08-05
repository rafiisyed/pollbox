package com.poll.restapi_spring_boot.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Vote {
    private Long pollId;
    private int optionIndex;
}