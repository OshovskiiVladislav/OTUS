package com.oshovskii.otus.integration;

import com.oshovskii.otus.models.Work;
import com.oshovskii.otus.models.Cat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;

@RequiredArgsConstructor
@IntegrationComponentScan
@Service
@Slf4j
public class IntegrationService {

    private final WorkGateway workGateway;

    @Async
    @Scheduled(fixedRate = 1000)
    public void start() {

        Collection<Cat> cats = generateInspections();

        log.warn("---------New Cat: " + cats.stream()
                .map(Cat::toString)
                .collect(joining("\n")));

        Collection<Work> works = workGateway.process(cats);

        log.warn("---------Work: " + works.stream()
                .map(Work::toString)
                .collect(joining("\n")));
    }

    private static Collection<Cat> generateInspections() {
        return IntStream
                .range(0, nextInt(1, 5))
                .mapToObj(i -> new Cat(nextLong()))
                .collect(toList());
    }
}
