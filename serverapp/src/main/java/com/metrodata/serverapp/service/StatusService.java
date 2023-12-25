package com.metrodata.serverapp.service;

import java.util.List;

import com.metrodata.serverapp.model.response.StatusResponse;

public interface StatusService {

    List<StatusResponse> getAll();

    StatusResponse getById(long id);
}