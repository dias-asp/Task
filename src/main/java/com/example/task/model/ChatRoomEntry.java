package com.example.task.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "room_entries")
public class ChatRoomEntry {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "room_id")
    private long chatRoom;
    @Column(name = "user_id")
    private long user;
}
