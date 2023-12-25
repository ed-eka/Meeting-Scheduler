package com.metrodata.serverapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationRequest {

    private long participantId;

    private long meetingId;

    private long statusId;
}