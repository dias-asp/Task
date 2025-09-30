package com.example.task.controller;

import com.example.task.model.ChatRoom;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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



    private Iterable<Message> filterMessages(Iterable<Message> messages) {
        Vector< Message > vector = new Vector < > ();
        for (Message message : messages) {
            if (message.getDate().before(new Timestamp(System.currentTimeMillis()))) vector.add(message);
        }
        return vector;
    }

    @GetMapping("/chat/messages")
    public Iterable<Message> getMessages(@RequestParam Long chatRoomId) {
        if (chatRoomId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        if (!chatRoomService.existsChatRoom(chatRoomId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        if (!chatRoomEntryService.existsChatRoomEntry(
                userService.getCurrentUser().getId(),
                chatRoomId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        return filterMessages(messageService.getMessages(chatRoomId));
    }

    @GetMapping("/user/messages")
    public Iterable<Message> getPrivateMessages(@RequestParam Long userId) {
        Long chatRoom = chatRoomService.getPrivateChat(userId, userService.getCurrentUser().getId());
        return filterMessages(messageService.getMessages(chatRoom));
    }

    @PostMapping("/chat/message")
    public void writeMessage(@RequestBody Message message) {
        if (!chatRoomService.existsChatRoom(message.getChatRoom())) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        if (message.getDate() == null) {
            messageService.createMessage(message.getText(), userService.getCurrentUser().getId(),
                    message.getChatRoom());
        }
        else {
            Timestamp time = message.getDate();
            Instant adjustedInstant = time.toInstant().minus(5, ChronoUnit.HOURS);
            Timestamp newTime = Timestamp.from(adjustedInstant);
            messageService.createMessage(message.getText(), userService.getCurrentUser().getId(),
                    message.getChatRoom(), newTime);
        }
    }
/*
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
    }*/

    @PostMapping("/user/message")
    public void writePrivateMessage(@RequestBody Message message) {
        if (!userService.existsUserById(message.getUser())) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        Long chatRoom = chatRoomService.getPrivateChat(message.getUser(), userService.getCurrentUser().getId());
        if (message.getDate() == null) {
            messageService.createMessage(message.getText(), userService.getCurrentUser().getId(), chatRoom);
        }
        else {
            Timestamp time = message.getDate();
            Instant adjustedInstant = time.toInstant().minus(5, ChronoUnit.HOURS);
            Timestamp newTime = Timestamp.from(adjustedInstant);
            messageService.createMessage(message.getText(), userService.getCurrentUser().getId(), chatRoom, newTime);
        }
    }
/*
    @PostMapping("/user/{login}/{date}")
    public void writePrivateMessage(@PathVariable String login, @RequestBody String text, @PathVariable String date) {
        Long chatRoom = chatRoomService.getPrivateChat(userService.getUserByLogin(login), userService.getCurrentUser().getId());
        try {
            Timestamp timestamp = Timestamp.valueOf(date);

            messageService.createMessage(text, userService.getCurrentUser().getId(), chatRoom, timestamp);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date format is invalid. Use 'yyyy-MM-dd HH:mm:ss'", e);
        }
    }*/
}
