package dev.mongmeo.side_project_team_matching.domain.port.in.usecase.email;

import dev.mongmeo.side_project_team_matching.domain.model.email.SendEmailModel;

public interface SendEmailUseCase {

  void sendPlainEmail(SendEmailModel model);

  void sendHtmlEmail(SendEmailModel model);
}
