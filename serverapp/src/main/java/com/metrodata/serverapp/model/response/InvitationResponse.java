package com.metrodata.serverapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationResponse {

    private long id;

    private PersonResponse participant;
    private MeetingResponse meeting;
    private StatusResponse status;
}