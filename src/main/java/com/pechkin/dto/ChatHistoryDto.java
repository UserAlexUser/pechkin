package com.pechkin.dto;

import com.pechkin.model.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChatHistoryDto {
    private Long id;
    private List<MessageHistoryDto> messages;

    public ChatHistoryDto(List<Message> messages) {
        this.messages = new ArrayList<>();
        for (Message message : messages) {
            this.messages.add(
                    new MessageHistoryDto(
                            message.getSender(),
                            message.getId(),
                            message.getText(),
                            message.getSendingTime()
                    )
            );
        }
    }
}
