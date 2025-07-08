package com.example.task.service;

import com.example.task.model.ChatRoomEntry;
import com.example.task.repository.ChatRoomEntryRepository;
import com.example.task.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class ChatRoomEntryService {

    @Autowired
    private ChatRoomEntryRepository chatRoomEntryRepository;

    public void createChatRoomEntry(Long userId, Long chatRoomId) {
        ChatRoomEntry chatRoomEntry = new ChatRoomEntry();
        chatRoomEntry.setChatRoom(chatRoomId);
        chatRoomEntry.setUser(userId);
        chatRoomEntryRepository.save(chatRoomEntry);
    }

    public boolean existsChatRoomEntry(Long userId, Long chatRoomId) {
        Iterable<ChatRoomEntry> chatRoomEntries = chatRoomEntryRepository.findAll();
        for (ChatRoomEntry chatRoomEntry : chatRoomEntries) {
            if (chatRoomEntry.getUser() == userId && chatRoomEntry.getChatRoom() == chatRoomId) {
                return true;
            }
        }
        return false;
    }
}
