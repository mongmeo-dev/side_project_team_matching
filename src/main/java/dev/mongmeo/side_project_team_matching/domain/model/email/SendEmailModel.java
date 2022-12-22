package dev.mongmeo.side_project_team_matching.domain.model.email;

import dev.mongmeo.side_project_team_matching.exception.ErrorCode;
import dev.mongmeo.side_project_team_matching.exception.RequestException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class SendEmailModel {

  private final String from;
  private final String to;
  private final String subject;
  private final String content;

  private SendEmailModel(String from, String to, String subject, String content) {
    validateMailAddress(to);

    this.from = from;
    this.to = to;
    this.subject = subject;
    this.content = content;
  }

  public static SendEmailModel createNoReplyMail(String to, String subject, String content) {
    return new SendEmailModel("noreply@mongmeo.dev", to, subject, content);
  }

  private void validateMailAddress(String address) {
    Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    Matcher matcher = emailPattern.matcher(address);

    if (!matcher.matches()) {
      throw new RequestException(ErrorCode.INVALID_EMAIL_FORMAT);
    }
  }
}
