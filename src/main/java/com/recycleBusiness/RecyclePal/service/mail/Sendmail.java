package com.recycleBusiness.RecyclePal.service.mail;

import com.recycleBusiness.RecyclePal.dto.request.EmailNotificationRequest;
import com.recycleBusiness.RecyclePal.dto.responce.SendMailResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;

public interface Sendmail {
	SendMailResponse sendMail(EmailNotificationRequest emailNotificationRequest);



}
