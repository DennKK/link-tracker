package edu.java.client.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubResponse(
    // hw5 bonus task

    long id,
    String name,
    @JsonProperty("updated_at") String updatedAt,
    @JsonProperty("pushed_at") String pushedAt
) {
}
