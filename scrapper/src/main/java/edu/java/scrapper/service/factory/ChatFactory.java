package edu.java.scrapper.service.factory;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.entity.ChatEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatFactory {
    public static ChatDto entityToDto(ChatEntity chatEntity) {
        ChatDto chatDto = new ChatDto();
        chatDto.setChatId(chatEntity.getChatId());
        chatDto.setTgChatId(chatEntity.getTgChatId());
        chatDto.setRegisteredAt(chatEntity.getRegisteredAt());
        return chatDto;
    }
}
