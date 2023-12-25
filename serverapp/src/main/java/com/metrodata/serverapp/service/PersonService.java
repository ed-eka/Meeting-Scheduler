package com.metrodata.serverapp.service;

import java.util.List;

import com.metrodata.serverapp.model.request.PersonRequest;
import com.metrodata.serverapp.model.response.PersonResponse;

public interface PersonService {
    List<PersonResponse> getAll();
    PersonResponse getById(long id);
    List<PersonResponse> getByVisibility();
    PersonResponse create(PersonRequest personRequest);
    PersonResponse update(long id, PersonRequest personRequest);
    PersonResponse delete(long id);
}