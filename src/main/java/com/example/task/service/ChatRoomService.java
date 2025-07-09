package com.example.task.service;

import com.example.task.model.ChatRoom;
import com.example.task.model.P2PRoom;
import com.example.task.repository.ChatRoomEntryRepository;
import com.example.task.repository.ChatRoomRepository;
import com.example.task.repository.P2PRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Vector;

@RestController
@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private P2PRoomRepository p2PRoomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRoomEntryRepository chatRoomEntryRepository;
    @Autowired
    private P2PRoomService p2PRoomService;
    @Autowired
    private ChatRoomEntryService chatRoomEntryService;

    public static void swapId(Long id1, Long id2) {
        id1 += id2;
        id2 = id1 - id2;
        id1 -= id2;
    }

    public Iterable<ChatRoom> findMyChats(Long id)
    {
        Iterable<ChatRoom> rooms = chatRoomRepository.findAll();
        Vector<ChatRoom> vector = new Vector < > ();
        for (ChatRoom room : rooms) {
            if (chatRoomEntryService.existsChatRoomEntry(id, room.getId())) {
                vector.add(room);
            }
        }
        return vector;
    }

    public Long createChatRoom(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoomRepository.save(chatRoom);
        return chatRoom.getId();
    }

    public Long getPrivateChat(Long id1, Long id2) {
        if (id1 > id2) {
            id1 += id2;
            id2 = id1 - id2;
            id1 -= id2;
        }
        System.out.println("id1: " + id1 + " id2: " + id2);
        if (!p2PRoomService.existsP2PRoom(id1, id2)) {createP2PRoom(id1, id2);}
        return p2PRoomService.getP2PRoom(id1, id2);
    }

    public boolean existsChatRoom(String name) {
        return getChatRoomByName(name) != null;
    }

    public boolean existsChatRoom(Long id) {
        return getChatRoomById(id) != null;
    }

    public ChatRoom getChatRoomByName(String name) {
        Iterable<ChatRoom> chatRooms = chatRoomRepository.findAll();
        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoom.getName().equals(name)) {
                return chatRoom;
            }
        }
        return null;
    }

    public ChatRoom getChatRoomById(Long id) {
        return chatRoomRepository.findById(id).orElse(null);
    }

    public void createP2PRoom(Long id1, Long id2) {
        if (id1 > id2) {
            id1 += id2;
            id2 = id1 - id2;
            id1 -= id2;}
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName("Private Chat of " + userService.getUserById(id1).getLogin() + " and " + userService.getUserById(id2).getLogin());
        chatRoomRepository.save(chatRoom);

        p2PRoomService.createP2PRoom(id1, id2, chatRoom.getId());

        chatRoomEntryService.createChatRoomEntry(id1, chatRoom.getId());
        chatRoomEntryService.createChatRoomEntry(id2, chatRoom.getId());

    }

}
