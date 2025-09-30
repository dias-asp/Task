package com.example.task.controller;

import com.example.task.model.ChatRoom;
import com.example.task.service.ChatRoomEntryService;
import com.example.task.service.ChatRoomService;
import com.example.task.service.P2PRoomService;
import com.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRoomEntryService chatRoomEntryService;
    @Autowired
    private P2PRoomService p2PRoomService;

    @PostMapping("/chat")
    public void createChatRoom(@RequestBody ChatRoom chatRoom) {
        Long newChatRoomId = chatRoomService.createChatRoom(chatRoom.getName());
        chatRoomEntryService.createChatRoomEntry(userService.getCurrentUser().getId(), newChatRoomId);
    }

    @PostMapping("/chat/enter")
    public void enterChatRoom(@RequestBody ChatRoom chatRoom) {
        if (chatRoom.getId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        if (!chatRoomService.existsChatRoom(chatRoom.getId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        chatRoomEntryService.createChatRoomEntry(userService.getCurrentUser().getId(), chatRoom.getId());
    }

    @GetMapping("/chat")
    public Iterable<ChatRoom> findMyChats() {
        return chatRoomService.findMyChats(userService.getCurrentUser().getId());
    }
}
