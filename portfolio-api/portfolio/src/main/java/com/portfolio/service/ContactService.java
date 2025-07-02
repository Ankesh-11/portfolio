package com.portfolio.service;

import com.portfolio.dto.ContactMessageDto;
import com.portfolio.model.ContactMessage;

import java.util.List;

public interface ContactService {
    void saveMessage(ContactMessageDto dto);
    List<ContactMessage> getAllMessages(); // for admin side
}
