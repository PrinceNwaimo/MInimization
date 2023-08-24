package com.recycleBusiness.RecyclePal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.*;

@Setter
@Getter
@NoArgsConstructor

public class SendMailRequest {

    private String  emailSender;
    private String  recipients;
    private String subject;
    private String setContent;
}
