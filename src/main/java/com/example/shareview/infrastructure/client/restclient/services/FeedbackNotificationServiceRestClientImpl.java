package com.example.shareview.infrastructure.client.restclient.services;

import com.example.shareview.datasources.FeedbackNotificationDataSource;
import com.example.shareview.exceptions.BadRequestException;
import com.example.shareview.infrastructure.exceptions.UnauthorizedException;
import dtos.requests.CreateBadFeedbackNotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Profile("restclient")
public class FeedbackNotificationServiceRestClientImpl implements FeedbackNotificationDataSource {

    @Value("${notification-service.base-url}")
    private String baseUrl;
    private final RestClient client;

    public FeedbackNotificationServiceRestClientImpl(RestClient client) {
        this.client = client;
    }

    @Override
    public void sendBadFeedbackNotification(String serviceToken, CreateBadFeedbackNotificationRequest createBadFeedbackNotificationRequest) {
        client.post()
                .uri(baseUrl + "/api/v1/notificacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + serviceToken)
                .body(createBadFeedbackNotificationRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) ->
                        {
                            if (res.getStatusCode().equals(HttpStatus.BAD_REQUEST))
                                throw new BadRequestException(res.getStatusText());
                            if (res.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
                                throw new UnauthorizedException();
                        }
                )
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) ->
                        {
                            throw new RuntimeException("Falha no serviço de notificações (notification-service).");
                        }
                )
                .toBodilessEntity();

    }
}
