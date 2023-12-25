package com.metrodata.serverapp.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Status;
import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.response.StatusResponse;
import com.metrodata.serverapp.repository.StatusRepository;
import com.metrodata.serverapp.service.StatusService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {

    private StatusRepository statusRepository;

    @Override
    public List<StatusResponse> getAll() {
        return statusRepository.findAll()
                .stream()
                .map(status -> {
                    return mappingStatusToStatusResponse(status);
                })
                .collect(Collectors.toList());
    }

    @Override
    public StatusResponse getById(long id) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Status with given id " + id + " not found",
                        "STATUS_NOT_FOUND",
                        404
                ));
        return mappingStatusToStatusResponse(status);
    }

    public StatusResponse mappingStatusToStatusResponse(Status status){
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setId(status.getId());
        statusResponse.setName(status.getName());
        return statusResponse;
    }
}