package com.oshovskii.otus.config;


import com.oshovskii.otus.services.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import static org.springframework.integration.scheduling.PollerMetadata.DEFAULT_POLLER;

@RequiredArgsConstructor
@IntegrationComponentScan
@Configuration
@EnableIntegration
public class IntegrationConfig {

    private final WorkService workService;

    @Bean
    public QueueChannel processChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel workChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    /**
     * .from(processChannel()) - из какого канала взять все входяшие сообщения
     * .handle(workService, "generateWork") - какой handle вызвать
     * .channel(workChannel()) - какой результат вернуть
     */
    @Bean
    public IntegrationFlow workFlow() {
        return IntegrationFlows
                .from(processChannel())
                .split()
                .handle(workService, "generateWork")
                .aggregate()
                .channel(workChannel())
                .get();
    }
}
