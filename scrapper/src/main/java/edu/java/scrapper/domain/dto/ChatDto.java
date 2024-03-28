package edu.java.scrapper.domain.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private Long chatId;
    private Long tgChatId;
    private OffsetDateTime registeredAt;
}
