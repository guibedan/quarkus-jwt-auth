package com.guibedan.service.strategy.impl;

import com.guibedan.service.strategy.EmailStrategy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@ApplicationScoped
@Named("SesEmailStrategy")
public class SesEmailStrategy implements EmailStrategy {

    @Inject
    @ConfigProperty(name = "ses.source")
    String source;

    @Inject
    @Named("sesClient")
    SesClient sesClient;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = SendEmailRequest.builder()
                .source(source)
                .destination(Destination.builder().toAddresses(to).build())
                .message(Message.builder()
                        .subject(Content.builder().data(subject).build())
                        .body(Body.builder().html(Content.builder().data(body).build()).build())
                        .build()
                )
                .build();

        sesClient.sendEmail(request);
    }

}
