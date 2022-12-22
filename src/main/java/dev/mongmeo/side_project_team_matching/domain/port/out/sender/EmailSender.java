package dev.mongmeo.side_project_team_matching.domain.port.out.sender;

import dev.mongmeo.side_project_team_matching.domain.model.email.SendEmailModel;

public interface EmailSender {

  void sendPlainEmail(SendEmailModel model);

  void sendHtmlEmail(SendEmailModel model);
}
