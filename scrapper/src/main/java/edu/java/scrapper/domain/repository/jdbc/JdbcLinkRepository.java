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
    private final String linkIdTemplate = "LINK_ID";
    private final String chatIdTemplate = "CHAT_ID";

    @Override
    public Iterable<LinkDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM \"LINKS\"", rowMapper);
    }

    @Override
    @Transactional
    public void add(LinkDto link) {
        jdbcTemplate.update(
            "INSERT INTO \"LINKS\"(\"URL\", \"UPDATED_AT\") VALUES(:url, :updatedAt)",
            new BeanPropertySqlParameterSource(link)
        );
    }

    @Override
    @Transactional
    public int remove(LinkDto link) {
        return jdbcTemplate.update(
            "DELETE FROM \"LINKS\" WHERE \"LINK_ID\" = :LINK_ID",
            new MapSqlParameterSource().addValue(linkIdTemplate, link.getLinkId())
        );
    }

    @Override
    @Transactional
    public void map(LinkDto link, ChatDto chat) {
        jdbcTemplate.update(
            "INSERT INTO \"LINKS_TO_CHATS\"(\"CHAT_ID\", \"LINK_ID\") VALUES(:CHAT_ID, :LINK_ID)",
            new MapSqlParameterSource()
                .addValue(linkIdTemplate, link.getLinkId())
                .addValue(chatIdTemplate, chat.getChatId())
        );
    }

    @Override
    @Transactional
    public void unmap(Long linkId, Long chatId) {
        jdbcTemplate.update(
            "DELETE FROM \"LINKS_TO_CHATS\" WHERE \"LINK_ID\" = :LINK_ID AND \"CHAT_ID\" = :CHAT_ID",
            new MapSqlParameterSource()
                .addValue(linkIdTemplate, linkId)
                .addValue(chatIdTemplate, chatId)
        );
    }

    @Override
    @Transactional
    public LinkDto getByUrl(String url) {
        List<LinkDto> links = jdbcTemplate.query(
            "SELECT * FROM \"LINKS\" WHERE \"URL\" = :url",
            new MapSqlParameterSource().addValue("url", url),
            rowMapper
        );
        return !links.isEmpty() ? links.get(0) : null;
    }

    @Override
    @Transactional
    public Collection<LinkDto> findAllByChat(ChatDto chat) {
        return jdbcTemplate.query(
            """
                SELECT L.* FROM \"LINKS_TO_CHATS\" LT
                JOIN \"LINKS\" L ON L.\"LINK_ID\" = LT.\"LINK_ID\"
                WHERE LT.\"CHAT_ID\" = :CHAT_ID
                """,
            new MapSqlParameterSource().addValue(chatIdTemplate, chat.getChatId()),
            rowMapper
        );
    }

    @Override
    public Collection<LinkDto> findOlderThan(int minutes) {
        return jdbcTemplate.query(
            "SELECT * FROM \"LINKS\" WHERE (NOW() - \"UPDATED_AT\") > (:interval * INTERVAL '1 minute')",
            new MapSqlParameterSource().addValue("interval", minutes),
            rowMapper
        );
    }

    @Override
    public void update(LinkDto link) {
        jdbcTemplate.update(
            "UPDATE \"LINKS\" SET \"UPDATED_AT\" = :updatedAt WHERE \"LINK_ID\" = :LINK_ID",
            new MapSqlParameterSource()
                .addValue("updatedAt", link.getUpdatedAt())
                .addValue(linkIdTemplate, link.getLinkId())
        );
    }

    @Override
    @Transactional
    public Collection<ChatDto> getChats(LinkDto link) {
        return jdbcTemplate.query(
            "SELECT * FROM \"CHATS\" "
                + "WHERE \"CHAT_ID\" IN "
                + "(SELECT \"CHAT_ID\" FROM \"LINKS_TO_CHATS\" WHERE \"LINK_ID\" = :LINK_ID)",
            new MapSqlParameterSource().addValue(linkIdTemplate, link.getLinkId()),
            new DataClassRowMapper<>(ChatDto.class)
        );
    }
}
