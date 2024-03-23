package edu.java.scheduler;

import edu.java.scrapper.service.LinkUpdater;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
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
