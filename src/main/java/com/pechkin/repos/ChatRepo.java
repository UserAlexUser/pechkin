package com.pechkin.repos;

import  com.pechkin.model.Chat;
import  com.pechkin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepo extends JpaRepository<Chat, String> {
    Chat findChatBySenderInAndRecipientIn(User senders, User recipients);
}
