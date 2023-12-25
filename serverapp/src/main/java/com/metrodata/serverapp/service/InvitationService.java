package com.metrodata.serverapp.service;

import java.util.List;

import com.metrodata.serverapp.model.request.InvitationRequest;
import com.metrodata.serverapp.model.response.InvitationResponse;

public interface InvitationService {

    List<InvitationResponse> getAll();

    InvitationResponse getById(long id);

    InvitationResponse create(InvitationRequest invitationRequest);

    InvitationResponse update(long id, InvitationRequest invitationRequest);

    InvitationResponse delete(long id);

    String confirmInvite(String token);

    String rejectInvite(String token);


    //  Using Map<K,V>
    //  To Use: Comment out this one and comment in all of the code above
    //  Then, comment out and in the respective service class
//    List<Map<String, Object>> getAll2();
//
//    Map<String, Object> getById2(long id);
}