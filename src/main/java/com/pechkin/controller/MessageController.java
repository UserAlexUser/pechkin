package com.pechkin.controller;

import com.pechkin.dto.ChatHistoryDto;
import com.pechkin.dto.MessageHistoryDto;
import com.pechkin.dto.MessageRequestDto;
import com.pechkin.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping(value = "/{username}")
    public ResponseEntity<?> send(
            @PathVariable("username")
            String recipientUsername,
            HttpServletRequest request,
            @RequestBody MessageRequestDto messageRequest
    ) {
        messageService.newMessage(request, recipientUsername, messageRequest);
        return ResponseEntity.ok("Message sent");
    }

    @GetMapping("/{username}")
    public ChatHistoryDto getChatHistory(
            @PathVariable("username")
            String recipientUsername,
            HttpServletRequest request
    ) {
        return messageService.getChatMessages(request, recipientUsername);
    }
}