package com.example.task.repository;

import com.example.task.model.ChatRoom;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChatRoomRepository extends PagingAndSortingRepository<ChatRoom, Long> {
}
