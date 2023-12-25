package com.metrodata.clientapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequest {
    private String title;
    private String description;
    private String dateTimeStart;
    private String dateTimeEnd;
    private long venue_id;
    private String link;
    private long status = 1;
    private long initiator_id;

    private List<Long> employeeId;
}