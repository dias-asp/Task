package com.example.task.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "p2p_rooms")
public class P2PRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user1_id")
    private long user1;

    @Column(name = "user2_id")
    private long user2;

    @Column(name = "entry_id")
    private long entryId;
}
