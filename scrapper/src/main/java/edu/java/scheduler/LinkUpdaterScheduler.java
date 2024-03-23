package edu.java.scheduler;

import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;

    private static final Logger LOGGER = Logger.getLogger(LinkUpdaterScheduler.class.getName());

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        LOGGER.info("Running link update task...");
        linkUpdater.update();
    }
}
