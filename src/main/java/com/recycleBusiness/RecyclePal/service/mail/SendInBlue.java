package com.recycleBusiness.RecyclePal.service.mail;

import com.recycleBusiness.RecyclePal.dto.request.EmailNotificationRequest;
import com.recycleBusiness.RecyclePal.dto.response.SendMailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.API_KEY_VALUE;
import static com.recycleBusiness.RecyclePal.utils.AppUtils.EMAIL_URL;

@RequiredArgsConstructor
@Service
public class SendInBlue {
    @Value("${sendinblue.api.key}")
    private String mailApiKey;
    public SendMailResponse sendMail(EmailNotificationRequest emailNotificationRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(API_KEY_VALUE,mailApiKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<EmailNotificationRequest> entity =
                new RequestEntity<>(emailNotificationRequest,httpHeaders, HttpMethod.POST, URI.create(EMAIL_URL));
        ResponseEntity<SendMailResponse> response =
                restTemplate.postForEntity(EMAIL_URL,entity,SendMailResponse.class);
        return response.getBody();
    }
}
