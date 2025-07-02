package com.portfolio.service.Impl;

import com.portfolio.dto.ContactMessageDto;
import com.portfolio.model.ContactMessage;
import com.portfolio.repository.ContactMessageRepository;
import com.portfolio.service.ContactService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactMessageRepository repository;

    public ContactServiceImpl(ContactMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveMessage(ContactMessageDto dto) {
        ContactMessage msg = new ContactMessage();
        msg.setName(dto.getName());
        msg.setEmail(dto.getEmail());
        msg.setMessage(dto.getMessage());
        repository.save(msg);
    }

    @Override
    public List<ContactMessage> getAllMessages() {
        return repository.findAll();
    }
}
