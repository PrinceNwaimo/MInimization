package com.recycleBusiness.RecyclePal.service.mail;

import com.recycleBusiness.RecyclePal.dto.request.EmailNotificationRequest;
import com.recycleBusiness.RecyclePal.dto.request.SendMailRequest;
import com.recycleBusiness.RecyclePal.dto.response.SendMailResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.API_KEY_VALUE;
import static com.recycleBusiness.RecyclePal.utils.AppUtils.EMAIL_URL;

@Service
@AllArgsConstructor
public class SendMailImpl implements Sendmail {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(SendMailRequest sendMailRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(sendMailRequest.getRecipients());
        simpleMailMessage.setFrom(sendMailRequest.getEmailSender());
        simpleMailMessage.setSubject(simpleMailMessage.getSubject());
        simpleMailMessage.setText(sendMailRequest.getSetContent());
        javaMailSender.send(simpleMailMessage);
    }
}
