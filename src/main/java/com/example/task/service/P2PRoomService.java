package com.example.task.service;

import com.example.task.model.ChatRoom;
import com.example.task.model.P2PRoom;
import com.example.task.repository.P2PRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.example.task.service.ChatRoomService.swapId;

@Service
public class P2PRoomService {

    @Autowired
    private P2PRoomRepository p2PRoomRepository;


    public Long getP2PRoom(Long id1, Long id2) {
        if (id1 > id2) {
            id1 += id2;
            id2 = id1 - id2;
            id1 -= id2;
        }
        Iterable<P2PRoom> chatRooms = p2PRoomRepository.findAll();
        for (P2PRoom chatRoom : chatRooms) {
            if (chatRoom.getUser1() == id1 && chatRoom.getUser2() == id2) {
                return chatRoom.getEntryId();
            }
        }
        return null;
    }
    public boolean existsP2PRoom(Long id1, Long id2) {
        if (id1 > id2) {
            id1 += id2;
            id2 = id1 - id2;
            id1 -= id2;
        }
        Iterable<P2PRoom> chatRooms = p2PRoomRepository.findAll();
        for (P2PRoom chatRoom : chatRooms) {
            if (chatRoom.getUser1() == id1 && chatRoom.getUser2() == id2) {
                return true;
            }
        }
        return false;
    }

    public void createP2PRoom(Long id1, Long id2, Long id) {
        P2PRoom p2PRoom = new P2PRoom();
        if (id1 > id2) {id1 += id2;
            id2 = id1 - id2;
            id1 -= id2;
        }
        p2PRoom.setUser1(id1);
        p2PRoom.setUser2(id2);
        p2PRoom.setEntryId(id);
        p2PRoomRepository.save(p2PRoom);


    }
}
