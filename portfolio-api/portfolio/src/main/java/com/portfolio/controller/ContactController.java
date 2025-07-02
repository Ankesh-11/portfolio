package com.portfolio.controller;

import com.portfolio.dto.ContactMessageDto;
import com.portfolio.model.ContactMessage;
import com.portfolio.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping("/query")
    public String submitContactForm(@RequestBody ContactMessageDto dto) {
        service.saveMessage(dto);
        return "Message submitted successfully!";
    }

    @GetMapping("/all")
    public List<ContactMessage> getAllMessages() {
        return service.getAllMessages();
    }
}
