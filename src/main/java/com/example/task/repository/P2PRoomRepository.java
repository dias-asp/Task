package com.example.task.repository;

import com.example.task.model.P2PRoom;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface P2PRoomRepository extends PagingAndSortingRepository<P2PRoom, Long> {
}
