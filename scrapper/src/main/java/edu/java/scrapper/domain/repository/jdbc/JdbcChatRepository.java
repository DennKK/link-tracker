package edu.java.scrapper.domain.repository.jdbc;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository implements ChatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<ChatDto> rowMapper = new DataClassRowMapper<>(ChatDto.class);

    @Override
    public Iterable<ChatDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM chats", rowMapper);
    }

    @Override
    @Transactional
    public void add(ChatDto chat) {
        jdbcTemplate.update(
            "INSERT INTO chats(registered_at) VALUES(:registeredAt)",
            new BeanPropertySqlParameterSource(chat)
        );
    }

    @Override
    @Transactional
    public int remove(ChatDto chat) {
        return jdbcTemplate.update(
            "DELETE FROM chats WHERE chat_id = :chatId",
            new MapSqlParameterSource().addValue("chatId", chat.getChatId())
        );
    }
}
