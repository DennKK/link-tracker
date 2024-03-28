package edu.java.scrapper.domain.repository.jdbc;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.repository.LinkRepository;
import java.util.Collection;
import java.util.List;
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
public class JdbcLinkRepository implements LinkRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<LinkDto> rowMapper = new DataClassRowMapper<>(LinkDto.class);
    private final String linkIdTemplate = "link_id";
    private final String chatIdTemplate = "chat_id";

    @Override
    public Iterable<LinkDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM links", rowMapper);
    }

    @Override
    @Transactional
    public void add(LinkDto link) {
        jdbcTemplate.update(
            "INSERT INTO links(url, updated_at) VALUES(:url, :updatedAt)",
            new BeanPropertySqlParameterSource(link)
        );
    }

    @Override
    @Transactional
    public int remove(LinkDto link) {
        return jdbcTemplate.update(
            "DELETE FROM links WHERE link_id = :link_id",
            new MapSqlParameterSource().addValue(linkIdTemplate, link.getLinkId())
        );
    }

    @Override
    @Transactional
    public void map(Long linkId, Long chatId) {
        jdbcTemplate.update(
            "INSERT INTO links_to_chats(chat_id, link_id) VALUES(:chat_id, :link_id)",
            new MapSqlParameterSource()
                .addValue(linkIdTemplate, linkId)
                .addValue(chatIdTemplate, chatId)
        );
    }

    @Override
    @Transactional
    public void unmap(Long linkId, Long chatId) {
        jdbcTemplate.update(
            "DELETE FROM links_to_chats WHERE link_id = :link_id AND chat_id = :chat_id",
            new MapSqlParameterSource()
                .addValue(linkIdTemplate, linkId)
                .addValue(chatIdTemplate, chatId)
        );
    }

    @Override
    @Transactional
    public LinkDto getByUrl(String url) {
        List<LinkDto> links = jdbcTemplate.query(
            "SELECT * FROM links WHERE url = :url",
            new MapSqlParameterSource().addValue("url", url),
            rowMapper
        );
        return links.isEmpty() ? null : links.get(0);
    }

    @Override
    @Transactional
    public Collection<LinkDto> findAllByChat(ChatDto chat) {
        return jdbcTemplate.query(
            """
                SELECT l.* FROM links_to_chats lt
                JOIN links l ON l.link_id = lt.link_id
                WHERE lt.chat_id = :chat_id
                """,
            new MapSqlParameterSource().addValue(chatIdTemplate, chat.getChatId()),
            rowMapper
        );
    }

    @Override
    public Collection<LinkDto> findOlderThan(int minutes) {
        return jdbcTemplate.query(
            "SELECT * FROM links WHERE (NOW() - updated_at) > (:interval * INTERVAL '1 minute')",
            new MapSqlParameterSource().addValue("interval", minutes),
            rowMapper
        );
    }

    @Override
    public void update(LinkDto link) {
        jdbcTemplate.update(
            "UPDATE links SET updated_at = :updatedAt WHERE link_id = :link_id",
            new MapSqlParameterSource()
                .addValue("updatedAt", link.getUpdatedAt())
                .addValue(linkIdTemplate, link.getLinkId())
        );
    }

    @Override
    @Transactional
    public Collection<ChatDto> getChats(LinkDto link) {
        return jdbcTemplate.query(
            "SELECT * FROM chats "
                + "WHERE chat_id IN "
                + "(SELECT chat_id FROM links_to_chats WHERE link_id = :link_id)",
            new MapSqlParameterSource().addValue(linkIdTemplate, link.getLinkId()),
            new DataClassRowMapper<>(ChatDto.class)
        );
    }
}
