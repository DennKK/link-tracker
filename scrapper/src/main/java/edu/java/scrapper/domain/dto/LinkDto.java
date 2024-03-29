package edu.java.scrapper.domain.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkDto {
    private Long linkId;
    private String url;
    private OffsetDateTime checkedAt;
    private OffsetDateTime updatedAt;
}
