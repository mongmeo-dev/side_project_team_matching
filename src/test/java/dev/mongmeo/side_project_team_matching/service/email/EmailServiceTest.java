package dev.mongmeo.side_project_team_matching.service.email;

import dev.mongmeo.side_project_team_matching.domain.model.email.SendEmailModel;
import dev.mongmeo.side_project_team_matching.domain.port.out.sender.EmailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

  @Mock
  private SendEmailModel sendEmailModel;
  @Mock
  private EmailSender emailSender;
  @InjectMocks
  private EmailService emailService;

  @Test
  @DisplayName("plain email 발송 성공")
  void sendPlainEmailTest() {
    // when
    emailService.sendPlainEmail(sendEmailModel);
    // then
    Mockito.verify(emailSender, Mockito.times(1)).sendPlainEmail(sendEmailModel);
  }

  @Test
  @DisplayName("html email 발송 성공")
  void sendHtmlEmailTest() {
    // when
    emailService.sendHtmlEmail(sendEmailModel);
    // then
    Mockito.verify(emailSender, Mockito.times(1)).sendHtmlEmail(sendEmailModel);
  }
}