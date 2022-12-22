package dev.mongmeo.side_project_team_matching.service.email;

import dev.mongmeo.side_project_team_matching.domain.model.email.SendEmailModel;
import dev.mongmeo.side_project_team_matching.domain.port.in.usecase.email.SendEmailUseCase;
import dev.mongmeo.side_project_team_matching.domain.port.out.sender.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService implements SendEmailUseCase {

  private final EmailSender emailSender;

  @Override
  public void sendPlainEmail(SendEmailModel model) {
    emailSender.sendPlainEmail(model);
  }

  @Override
  public void sendHtmlEmail(SendEmailModel model) {
    emailSender.sendHtmlEmail(model);
  }
}
