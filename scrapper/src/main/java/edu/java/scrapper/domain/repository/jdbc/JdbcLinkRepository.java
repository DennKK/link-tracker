package edu.java.scrapper.domain.repository.jdbc;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import java.util.List;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<LinkDto> rowMapper = new DataClassRowMapper<>(LinkDto.class);
    private final String linkId = "linkId";
    private final String chatId = "chatId";

    public JdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Iterable<LinkDto> findAll() {
        return jdbcTemplate.query("select * from links", rowMapper);
    }

    @Transactional
    public void add(LinkDto link) {
        jdbcTemplate.update(
            "insert into links(url, updated_at) values(:url, :updatedAt)",
            new BeanPropertySqlParameterSource(link)
        );
    }

    @Transactional
    public int remove(LinkDto link) {
        return jdbcTemplate.update(
            "delete from links where link_id = :linkId",
            new MapSqlParameterSource()
                .addValue(linkId, link.getLinkId())
        );
    }

    @Transactional
    public void map(LinkDto link, ChatDto chat) {
        jdbcTemplate.update(
            "insert into links_to_chats(chat_id, link_id) values(:chatId, :linkId)",
            new MapSqlParameterSource()
                .addValue(linkId, link.getLinkId())
                .addValue(chatId, chat.getChatId())
        );
    }

    @Transactional
    public void unmap(LinkDto link, ChatDto chat) {
        jdbcTemplate.update(
            "delete from links_to_chats where link_id = :linkId and chat_id = :chatId",
            new MapSqlParameterSource()
                .addValue(linkId, link.getLinkId())
                .addValue(chatId, chat.getChatId())
        );
    }

    @Transactional
    public LinkDto getByUrl(String url) {
        List<LinkDto> links = jdbcTemplate.query(
            "select * from links where link = :link",
            new MapSqlParameterSource().addValue("link", url),
            rowMapper
        );
        return !links.isEmpty() ? links.getFirst() : null;
    }

    @Transactional
    public List<LinkDto> findAllByChat(ChatDto chat) {
        return jdbcTemplate.query(
            """
                    SELECT l.* FROM links_to_chats
                    JOIN links l ON l.link_id = links_to_chats.link_id
                    WHERE chat_id = :id
                    """,
            new BeanPropertySqlParameterSource(chat),
            rowMapper
        );
    }
}
