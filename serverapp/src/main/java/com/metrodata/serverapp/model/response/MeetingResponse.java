package com.metrodata.serverapp.model.response;

import java.util.List;
import java.util.Map;

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
    private Map<String, Object> venue;
    private String link;
    private StatusResponse status;
    private Map<String, Object> initiator;
    private List<Map<String, Object>> invitations;
}
