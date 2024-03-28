package edu.java.scrapper.domain.repository.jpa;

import edu.java.scrapper.domain.entity.ChatEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {
    Optional<ChatEntity> findByTgChatId(Long tgChatId);
}
