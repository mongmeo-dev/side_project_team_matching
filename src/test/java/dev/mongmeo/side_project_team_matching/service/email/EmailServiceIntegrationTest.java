package dev.mongmeo.side_project_team_matching.service.email;

import dev.mongmeo.side_project_team_matching.domain.model.email.SendEmailModel;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.NotThrownAssert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Disabled("테스트시 이메일이 발송되어 비활성화함")
@SpringBootTest
class EmailServiceIntegrationTest {

  @Autowired
  TemplateEngine templateEngine;

  @Autowired
  private EmailService emailService;

  @Value("${test.mail.to}")
  private String toAddress;

  @Test
  void sendPlainEmailTest() {
    SendEmailModel testMail = SendEmailModel.createNoReplyMail(toAddress, "test", "testContent");
    emailService.sendPlainEmail(testMail);
    NotThrownAssert result = Assertions.assertThatNoException();
  }

  @Test
  void sendHtmlEmailTest() {
    String content = templateEngine.process("email_test_template", new Context());
    SendEmailModel testMail = SendEmailModel.createNoReplyMail(toAddress, "html mail test", content);
    emailService.sendHtmlEmail(testMail);
    NotThrownAssert result = Assertions.assertThatNoException();
  }
}