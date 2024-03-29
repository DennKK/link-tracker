package edu.java.scrapper.domain.repository.jpa;

import edu.java.scrapper.domain.entity.LinkEntity;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByUrl(String url);

    @Query("SELECT le FROM LinkEntity le WHERE le.checkedAt < :cutoff")
    Collection<LinkEntity> findLinksNotCheckedSince(@Param("cutoff") OffsetDateTime cutoff);
}
