package com.metrodata.serverapp.model.request;

import java.time.Duration;
import java.util.List;

import com.metrodata.serverapp.entity.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String to;
    private String subject;
    private String body;
    private String attachment;

    private String name;
    private String linkToken;

    private String initName;
    private String initEmail;
    private String initPhone;

    private String title;
    private String dateStart;
    private String dateEnd;
    private String location;
    private String linkMeeting;
    private String initiator;
    private List<Person> participants;

    private String linkConfirm;
    private String linkReject;
}