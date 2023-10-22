package com.pechkin.service;

import com.pechkin.dto.ChatHistoryDto;
import com.pechkin.dto.MessageRequestDto;
import com.pechkin.model.Chat;
import com.pechkin.model.Message;
import com.pechkin.model.User;
import com.pechkin.repos.ChatRepo;
import com.pechkin.repos.MessageRepo;
import com.pechkin.repos.UserRepo;
import com.pechkin.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepo userRepo;
    private final ChatRepo chatRepo;
    private final MessageRepo messageRepo;

    @Transactional
    public void newMessage(HttpServletRequest request, String recipientUsername, MessageRequestDto messageRequest) {
        String token = jwtTokenProvider.resolveToken(request);
        User sender = userRepo.findByUsername(jwtTokenProvider.getUsername(token));
        User recipient = userRepo.findByUsername(recipientUsername);
        if (recipient == null) {
            throw new UsernameNotFoundException("User: " + recipientUsername + " not found");
        }

        Chat chat = chatRepo.findChatBySenderInAndRecipientIn(sender, recipient);
        if (chat == null) {
            chat = new Chat(sender, recipient);
        }

        Message message = messageRepo.save(new Message(sender, messageRequest.getText(), chat));
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        chat.setMessages(messages);
        chatRepo.save(chat);
    }

    public ChatHistoryDto getChatMessages(HttpServletRequest request, String recipientUsername) {
        String token = jwtTokenProvider.resolveToken(request);
        User sender = userRepo.findByUsername(jwtTokenProvider.getUsername(token));
        User recipient = userRepo.findByUsername(recipientUsername);
        if (recipient == null) {
            throw new UsernameNotFoundException("User: " + recipientUsername + " not found");
        }
        try {
            Chat chat = chatRepo.findChatBySenderInAndRecipientIn(sender, recipient);
            List<Message> messages = messageRepo.findAllByChatOrderBySendingTime(chat);
            ChatHistoryDto chatHistoryDto = new ChatHistoryDto(messages);
            chatHistoryDto.setId(chat.getId());
            return chatHistoryDto;
        } catch (Exception e) {
            throw new RuntimeException("chat: sender" + sender.getUsername() + "recipient" + recipientUsername + " not found");
        }
    }

}
