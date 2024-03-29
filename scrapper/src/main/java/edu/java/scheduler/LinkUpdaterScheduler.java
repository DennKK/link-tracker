package edu.java.scheduler;

import edu.java.client.tgbot.BotClient;
import edu.java.processor.LinkUpdateProcessor;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.service.LinkService;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler implements LinkUpdater {
    private final List<LinkUpdateProcessor> processors;
    @Value("${app.update-frequency}")
    protected int updateFrequency;
    private LinkService linkService;
    private BotClient botClient;

    @Override
    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Running link update task...");

        Collection<LinkDto> oldLinks = linkService.getOlderThan(updateFrequency);

        oldLinks.forEach(link -> {
            try {
                LinkUpdateProcessor processor = processors.stream()
                    .filter(p -> p.supports(link.getUrl()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No processor found for link: " + link.getUrl()));

                processor.processLinkUpdate(link);
            } catch (Exception e) {
                log.error("Error processing link {}: {}", link.getUrl(), e.getMessage());
            }
        });
    }
}
