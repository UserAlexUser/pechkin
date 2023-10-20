package com.pechkin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @Column(name = "text")
    private String text;
    @CreatedDate
    @Column(name = "sending_time")
    private LocalDateTime sendingTime;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Message(User sender, String text, Chat chat) {
        this.sender = sender;
        this.text = text;
        this.chat = chat;
        this.sendingTime = LocalDateTime.now();
    }
}
