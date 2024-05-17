package edu.java.scrapper.stackoverflow;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.scrapper.client.stackoverflow.StackoverflowClient;
import edu.java.scrapper.client.stackoverflow.StackoverflowQuestion;
import edu.java.scrapper.client.stackoverflow.StackoverflowResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackoverflowClientTest {

    private final WireMockServer wireMockServer = new WireMockServer();

    @BeforeEach
    public void setUp() {
        wireMockServer.start();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetStackoverflowResponse() {
        long questionId = 12345678;
        String wireMockBaseUrl = "http://localhost:8080";

        stubFor(get(urlEqualTo("/questions/" + questionId + "?site=stackoverflow"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"items\": [{ \"last_activity_date\": \"2024-02-20T12:00Z\" }]}")));

        StackoverflowClient stackoverflowClient =
            new StackoverflowClient(WebClient.builder().baseUrl(wireMockBaseUrl).build());
        StackoverflowResponse stackoverflowResponse =
            stackoverflowClient.getStackoverflowResponse(String.valueOf(questionId));
        StackoverflowQuestion stackoverflowQuestion = stackoverflowResponse.items().getFirst();

        assertEquals("2024-02-20T12:00Z", stackoverflowQuestion.lastActivityDate().toString());
    }
}
