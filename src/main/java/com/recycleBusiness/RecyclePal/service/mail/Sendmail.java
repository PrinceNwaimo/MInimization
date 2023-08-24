package com.recycleBusiness.RecyclePal.service.mail;

import com.recycleBusiness.RecyclePal.dto.request.EmailNotificationRequest;
import com.recycleBusiness.RecyclePal.dto.request.SendMailRequest;
import com.recycleBusiness.RecyclePal.dto.response.SendMailResponse;

public interface Sendmail {
	void sendMail(SendMailRequest sendMailRequest);



}
