package com.example.task.repository;

import com.example.task.model.ChatRoomEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ChatRoomEntryRepository extends PagingAndSortingRepository <ChatRoomEntry, Long> {
}
