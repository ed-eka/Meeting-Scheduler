package com.metrodata.serverapp.model.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequest {
    private String title;
    private String description;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private Long venueId;
    private String link;
    private long statusId = 1;
    private long initiatorId;

    // private long invitation;
    // private long name;
    // private long email;
    // private long phone;
}