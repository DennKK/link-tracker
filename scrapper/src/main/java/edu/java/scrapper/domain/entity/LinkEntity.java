package edu.java.scrapper.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"LINKS\"")
@AllArgsConstructor
@NoArgsConstructor
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"LINK_ID\"")
    private Long linkId;

    @Column(name = "\"URL\"")
    private String url;

    @Column(name = "\"UPDATED_AT\"")
    private OffsetDateTime updatedAt;

    @ManyToMany(mappedBy = "links", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ChatEntity> chats = new HashSet<>();
}
