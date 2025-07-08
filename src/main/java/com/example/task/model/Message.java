package com.example.task.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    @Column(name = "room_id")
    private long chatRoom;
    @Column(name = "sender_id")
    private long user;
    @Column(name = "date")
    private Timestamp date;

}
