package com.guibedan.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@ApplicationScoped
public class SesClientProducer {

    @ConfigProperty(name = "quarkus.ses.aws.credentials.static-provider.access-key-id")
    String accessKey;

    @ConfigProperty(name = "quarkus.ses.aws.credentials.static-provider.secret-access-key")
    String secretKey;

    @ConfigProperty(name = "quarkus.ses.aws.region")
    String region;

    @Produces
    @ApplicationScoped
    @Named("sesClient")
    public SesClient createSesClient() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        return SesClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(region))
                .build();
    }

}
