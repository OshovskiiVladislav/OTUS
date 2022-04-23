package com.oshovskii.otus.integration;

import com.oshovskii.otus.models.Work;
import com.oshovskii.otus.models.Cat;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import java.util.Collection;

@MessagingGateway
public interface WorkGateway {
    @Gateway(requestChannel = "processChannel", replyChannel = "workChannel")
    Collection<Work> process(Collection<Cat> cats);
}
