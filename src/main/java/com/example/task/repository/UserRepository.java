package com.example.task.repository;

import com.example.task.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
