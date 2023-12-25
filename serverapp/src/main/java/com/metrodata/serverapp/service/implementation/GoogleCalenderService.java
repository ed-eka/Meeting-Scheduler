package com.metrodata.serverapp.service.implementation;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Person;
import com.metrodata.serverapp.model.request.Email;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Method;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GoogleCalenderService {

    public String createCalender(Email email) {
        ICalendar iCalendar = new ICalendar();
        iCalendar.addProperty(new Method(Method.REQUEST));

        VEvent event = new VEvent();
        event.setUrl(email.getLocation());
        event.setSummary(email.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Date dateTimeStart = getDateTime(LocalDateTime.parse(email.getDateStart()));
        // Date dateTimeEnd = getDateTime(LocalDateTime.parse(email.getDateEnd()));
        event.setDateStart(getDateTime(LocalDateTime.parse(email.getDateStart())));
        event.setDateEnd(getDateTime(LocalDateTime.parse(email.getDateEnd())));
        event.setOrganizer(email.getInitiator());
        addAttendees(event, email.getParticipants());

        iCalendar.addEvent(event);
        return Biweekly.write(iCalendar).go();
    }

    private void addAttendees(VEvent event, List<Person> participants) {
        for (Person participant : participants) {
            event.addAttendee(participant.getEmail());
        }
    }

    public Date getDateTime(LocalDateTime evenDateTime) {
        Instant instant = evenDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
