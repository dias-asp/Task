package com.example.task.service;

import com.example.task.model.Message;
import com.example.task.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Vector;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;

    
    public void createMessage(String text, Long senderId, Long chatRoomId){
        Message message = new Message();
        message.setText(text);
        message.setChatRoom(chatRoomId);
        message.setUser(senderId);
        message.setDate(new java.sql.Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);
    }
    public void createMessage(String text, Long senderId, Long chatRoomId, java.sql.Timestamp date){
        Message message = new Message();
        message.setText(text);
        message.setChatRoom(chatRoomId);
        message.setUser(senderId);
        message.setDate(date);
        messageRepository.save(message);
    }

    public Iterable<Message> getMessages(Long chatRoomId){
        Iterable<Message> messages = messageRepository.findAll();
        Vector< Message > vector = new Vector < > ();
        for (Message message : messages) {
            if (message.getChatRoom() == chatRoomId && message.getDate().before(new java.sql.Timestamp(System.currentTimeMillis()))) {
                vector.add(message);
            }
        }
        return vector;
    }
}
