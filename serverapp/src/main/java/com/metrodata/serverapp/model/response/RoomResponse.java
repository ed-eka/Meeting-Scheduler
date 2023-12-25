package com.metrodata.serverapp.model.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private long id;
    private String name;
    private List<Map<String, Object>> meeting;
}
