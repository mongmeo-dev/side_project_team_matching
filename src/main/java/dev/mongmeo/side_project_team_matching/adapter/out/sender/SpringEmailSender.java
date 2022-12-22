package dev.mongmeo.side_project_team_matching.adapter.out.sender;

import dev.mongmeo.side_project_team_matching.domain.model.email.SendEmailModel;
import dev.mongmeo.side_project_team_matching.domain.port.out.sender.EmailSender;
import dev.mongmeo.side_project_team_matching.exception.ErrorCode;
import dev.mongmeo.side_project_team_matching.exception.ServerException;
import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SpringEmailSender implements EmailSender {

  private final JavaMailSender mailSender;

  @Override
  public void sendPlainEmail(SendEmailModel model) {
    mailSender.send(createSimpleMailMessageFormModel(model));
  }

  @Override
  public void sendHtmlEmail(SendEmailModel model) {
    try {
      mailSender.send(createHtmlMessageFromModel(model));
    } catch (MessagingException e) {
      throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INVALID_INPUT);
    }
  }

  private SimpleMailMessage createSimpleMailMessageFormModel(SendEmailModel model) {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setFrom(model.getFrom());
    mail.setTo(model.getTo());
    mail.setSubject(model.getSubject());
    mail.setText(model.getContent());
    return mail;
  }

  private MimeMessage createHtmlMessageFromModel(SendEmailModel model) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
    messageHelper.setFrom(model.getFrom());
    messageHelper.setTo(model.getTo());
    messageHelper.setSubject(model.getSubject());
    messageHelper.setText(model.getContent(), true);
    return mimeMessage;
  }
}
