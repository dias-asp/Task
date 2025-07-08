package com.example.task.controller;

import com.example.task.model.ChatRoom;
import com.example.task.service.ChatRoomEntryService;
import com.example.task.service.ChatRoomService;
import com.example.task.service.P2PRoomService;
import com.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void createChatRoom(@RequestBody String name) {
        if (chatRoomService.existsChatRoom(name)) return;
        chatRoomService.createChatRoom(name);
        chatRoomEntryService.createChatRoomEntry(userService.getCurrentUser().getId(), chatRoomService.getChatRoomByName(name).getId());
    }

    @PostMapping("/chat/{name}/enter")
    public void enterChatRoom(@PathVariable String name) {
        if (!chatRoomService.existsChatRoom(name)) return;
        chatRoomEntryService.createChatRoomEntry(userService.getCurrentUser().getId(), chatRoomService.getChatRoomByName(name).getId());
    }

    @GetMapping("/chat")
    public Iterable<ChatRoom> findMyChats() {
        return chatRoomService.findMyChats(userService.getCurrentUser().getId());
    }
}
