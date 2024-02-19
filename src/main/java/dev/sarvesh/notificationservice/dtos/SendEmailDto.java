package dev.sarvesh.notificationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SendEmailDto {

    private  String from;
    private String to;
    private String subject;
    private String body;

}
