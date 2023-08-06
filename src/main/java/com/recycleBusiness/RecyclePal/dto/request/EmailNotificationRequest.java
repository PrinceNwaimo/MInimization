package com.recycleBusiness.RecyclePal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.swing.text.html.HTML;
import java.util.Set;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailNotificationRequest {
    @JsonProperty(SENDER)
    private Sender emailSender;
    @JsonProperty(TO)
    private Set<Recipient> recipients;
    @JsonProperty(SUBJECT)
    private String subject;
    @JsonProperty("htmlContent")
    private String setContent;


}
