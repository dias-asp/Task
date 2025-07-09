package com.example.task.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
