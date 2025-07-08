package com.example.task.controller;

import com.example.task.model.Message;
import com.example.task.service.ChatRoomEntryService;
import com.example.task.service.ChatRoomService;
import com.example.task.service.MessageService;
import com.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatRoomEntryService chatRoomEntryService;

    public String messageToString(Message message) {
        return message.getDate() + " " + userService.getUserById(message.getUser()).getLogin() + ": " + message.getText();
    }

    @GetMapping("/chat/{name}")
    public Iterable<String> getMessages(@PathVariable String name) {
        if (!chatRoomService.existsChatRoom(name)) return null;
        if (!chatRoomEntryService.existsChatRoomEntry(
                userService.getCurrentUser().getId(),
                chatRoomService.getChatRoomByName(name).getId()))
            return null;
        Iterable <Message> messages = messageService.getMessages(chatRoomService.getChatRoomByName(name).getId());
        Vector< String> vector = new Vector < > ();
        for (Message message : messages) {
            vector.add(messageToString(message));
        }
        return vector;
    }

    @GetMapping("/user/{login}")
    public Iterable<String> getPrivateMessages(@PathVariable String login) {
        Long chatRoom = chatRoomService.getPrivateChat(userService.getUserByLogin(login), userService.getCurrentUser().getId());
        Iterable<Message> messages = messageService.getMessages(chatRoom);
        Vector< String> vector = new Vector < > ();
        for (Message message : messages) {
            vector.add(messageToString(message));
        }
        return vector;
    }

    @PostMapping("/chat/{name}")
    public void writeMessage(@PathVariable String name, @RequestBody String text) {
        if (!chatRoomService.existsChatRoom(name)) return;
        messageService.createMessage(text, userService.getCurrentUser().getId(),
                chatRoomService.getChatRoomByName(name).getId());
    }

    @PostMapping("/chat/{name}/{date}")
    public void writeMessage(@PathVariable String name, @RequestBody String text, @PathVariable String date){
        if (!chatRoomService.existsChatRoom(name)) return;
        try {
            Timestamp timestamp = Timestamp.valueOf(date);

            messageService.createMessage(text, userService.getCurrentUser().getId(),
                    chatRoomService.getChatRoomByName(name).getId(), timestamp);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date format is invalid. Use 'yyyy-MM-dd HH:mm:ss'", e);
        }

//        messageService.createMessage(text, userService.getCurrentUser().getId(),
//                chatRoomService.getChatRoomByName(name).getId(), date);
    }

    @PostMapping("/user/{login}")
    public void writePrivateMessage(@PathVariable String login, @RequestBody String text) {
        Long chatRoom = chatRoomService.getPrivateChat(userService.getUserByLogin(login), userService.getCurrentUser().getId());
        messageService.createMessage(text, userService.getCurrentUser().getId(), chatRoom);
    }

    @PostMapping("/user/{login}/{date}")
    public void writePrivateMessage(@PathVariable String login, @RequestBody String text, @PathVariable String date) {
        Long chatRoom = chatRoomService.getPrivateChat(userService.getUserByLogin(login), userService.getCurrentUser().getId());
        try {
            Timestamp timestamp = Timestamp.valueOf(date);

            messageService.createMessage(text, userService.getCurrentUser().getId(), chatRoom, timestamp);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date format is invalid. Use 'yyyy-MM-dd HH:mm:ss'", e);
        }
    }
}
