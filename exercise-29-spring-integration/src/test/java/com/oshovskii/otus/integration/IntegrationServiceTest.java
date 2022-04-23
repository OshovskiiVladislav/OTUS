package com.oshovskii.otus.integration;

import com.oshovskii.otus.config.AppConfig;
import org.awaitility.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(AppConfig.class)
@MockBeans(value ={ @MockBean(WorkGateway.class)})
class IntegrationServiceTest {

    @SpyBean
    private IntegrationService integrationService;

    @Test
    @DisplayName("Check the number of times that the scheduled method is called in the period of five seconds test")
    void start_voidInput_shouldCheckNumberOfTimesThatTheScheduledMethodCalledInTheExpectedPeriod() {
        await()
                .atMost(Duration.FIVE_SECONDS)
                .untilAsserted(() -> verify(integrationService, atLeast(5)).start());
    }
}
