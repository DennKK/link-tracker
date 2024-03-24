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
    private final String linkIdString = "linkId";
    private final String chatIdString = "chatId";

    @Override
    public Iterable<LinkDto> findAll() {
        return jdbcTemplate.query("select * from links", rowMapper);
    }

    @Override
    @Transactional
    public void add(LinkDto link) {
        jdbcTemplate.update(
            "insert into links(url, updated_at) values(:url, :updatedAt)",
            new BeanPropertySqlParameterSource(link)
        );
    }

    @Override
    @Transactional
    public int remove(LinkDto link) {
        return jdbcTemplate.update(
            "delete from links where link_id = :linkId",
            new MapSqlParameterSource()
                .addValue(linkIdString, link.getLinkId())
        );
    }

    @Override
    @Transactional
    public void map(LinkDto link, ChatDto chat) {
        jdbcTemplate.update(
            "insert into links_to_chats(chat_id, link_id) values(:chatId, :linkId)",
            new MapSqlParameterSource()
                .addValue(linkIdString, link.getLinkId())
                .addValue(chatIdString, chat.getChatId())
        );
    }

    @Override
    @Transactional
    public void unmap(Long linkId, Long chatId) {
        jdbcTemplate.update(
            "delete from links_to_chats where link_id = :linkId and chat_id = :chatId",
            new MapSqlParameterSource()
                .addValue(linkIdString, linkId)
                .addValue(chatIdString, chatId)
        );
    }

    @Override
    @Transactional
    public LinkDto getByUrl(String url) {
        List<LinkDto> links = jdbcTemplate.query(
            "select * from links where link = :link",
            new MapSqlParameterSource().addValue("link", url),
            rowMapper
        );
        return !links.isEmpty() ? links.getFirst() : null;
    }

    @Override
    @Transactional
    public Collection<LinkDto> findAllByChat(ChatDto chat) {
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

    @Override
    public Collection<LinkDto> findOlderThan(int minutes) {
        return jdbcTemplate.query(
            "select *, now() - updatedAt from links where (now() - updatedAt) > (:interval * interval '1 minute')",
            new MapSqlParameterSource()
                .addValue("interval", minutes),
            rowMapper
        );
    }

    @Override
    @Transactional
    public Collection<ChatDto> getChats(LinkDto link) {
        return jdbcTemplate.query(
            "select * from chats where chat_id in (select chat_id from links_to_chats where link_id = :linkId)",
            new BeanPropertySqlParameterSource(link),
            new DataClassRowMapper<>(ChatDto.class)
        );
    }

    @Override
    public void update(LinkDto link) {
        jdbcTemplate.update(
            "update links set updated_at = :updatedAt where link_id = :linkId",
            new MapSqlParameterSource()
                .addValue("updatedAt", link.getUpdatedAt())
                .addValue(linkIdString, link.getLinkId())
        );
    }
}
