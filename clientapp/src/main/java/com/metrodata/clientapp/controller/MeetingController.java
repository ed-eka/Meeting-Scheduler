package com.metrodata.clientapp.controller;

import com.metrodata.clientapp.service.MeetingService;
import com.metrodata.clientapp.model.request.MeetingRequest;
import com.metrodata.clientapp.model.response.MeetingResponse;
import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.metrodata.clientapp.service.AuthService;
import com.metrodata.clientapp.service.PersonService;
import com.metrodata.clientapp.service.RoomService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/meeting")
@AllArgsConstructor
public class MeetingController {

    private MeetingService meetingService;
    private PersonService personService;
    private RoomService roomService;
    private AuthService authService;

    @GetMapping
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("meetings", meetingService.getAll());
        model.addAttribute("title","Meeting");
        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            model.addAttribute("role", "ADMIN");
        } else {
            model.addAttribute("role", "USER");
        }
        return "meeting/index";
    }

    @GetMapping("/meeting-list")
    public String meetingList(Model model){
        model.addAttribute("meetingResponse", meetingService.getAll());
        model.addAttribute("title", "Meeting List");
        return "meeting/meeting-list";
    }

    @GetMapping("/create")
    public String createView(MeetingRequest meetingRequest, Model model){
        model.addAttribute("rooms", roomService.getAll());
        model.addAttribute("employees", personService.getByVisibility());
        model.addAttribute("title", "Create Meeting");
        return "meeting/create-form";
    }

    @PostMapping
    public String create(MeetingRequest meetingRequest){
        meetingRequest.setInitiator_id(authService.getId());
        System.out.println("Meeting Request: "+meetingRequest);

        meetingService.create(meetingRequest);
        return"redirect:/meeting";
    }

    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable long id){
        meetingService.updateStatus(id);
        return"redirect:/meeting-list";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable long id, Model model, MeetingRequest meetingRequest){
        model.addAttribute("title", "Update Meeting");
        model.addAttribute("meetingResponse", meetingService.getById(id));
        return "meeting/update-form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id,  @ModelAttribute("meetingRequest") MeetingRequest meetingRequest){
        meetingService.update(id,meetingRequest);
        return "redirect:/meeting";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id){
        meetingService.delete(id);
        return "redirect:/meeting";
    }

    //Ambil detail meeting
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable long id, Model model){
        MeetingResponse meetingResponse = meetingService.getById(id);
        model.addAttribute("title", "Meeting Detail");
        model.addAttribute("meetingResponse", meetingResponse);
        return "meeting/detail";
    }

}
