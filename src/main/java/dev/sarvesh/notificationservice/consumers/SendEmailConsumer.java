package dev.sarvesh.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sarvesh.notificationservice.dtos.SendEmailDto;
import dev.sarvesh.notificationservice.utilities.EmailUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
@AllArgsConstructor
public class SendEmailConsumer {

    private ObjectMapper objectMapper;

    @KafkaListener(topics = "sendEmail",groupId = "emailService")
    public void sendEmail(String message) throws JsonProcessingException {
        SendEmailDto dto = objectMapper.readValue(message, SendEmailDto.class);
//        System.out.println(dto.toString());
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(dto.getFrom(), "<gmail app password>");
            }
        };
        Session session = Session.getInstance(props, auth);
        EmailUtil.sendEmail(session,dto.getTo(),dto.getSubject(),dto.getBody());
    }
}
