package com.pechkin.dto;

import com.pechkin.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageHistoryDto {
    private User sender;
    private Long messageId;
    private String content;
    private LocalDateTime sendingTime;
}
