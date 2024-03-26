package edu.java.scrapper.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CHATS")
public class ChatEntity {
    @Id
    @Column(name = "CHAT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    @Column(name = "REGISTERED_AT")
    private OffsetDateTime registeredAt;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "LINKS_TO_CHATS",
               joinColumns = @JoinColumn(name = "CHAT_ID"),
               inverseJoinColumns = @JoinColumn(name = "LINK_ID"))
    private Set<LinkEntity> links = new HashSet<>();
}
