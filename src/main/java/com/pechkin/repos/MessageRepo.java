package com.pechkin.repos;

import com.pechkin.model.Chat;
import com.pechkin.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, String> {
    List<Message> findAllByChatOrderBySendingTime(Chat chat);
}
