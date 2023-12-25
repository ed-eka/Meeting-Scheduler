package com.metrodata.clientapp.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingResponse {
    private long id;
    private String title;
    private String description;
    private String dateTimeStart;
    private String dateTimeEnd;
    private String isOnline;
    private Object venue;
    private String link;
    private Object status;
    private Object initiator;
    private List<Object> invitations;
}