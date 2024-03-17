package edu.java.scrapper.domain.dto;

import java.sql.Timestamp;
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
    private Timestamp updatedAt;
}
