package dev.mongmeo.side_project_team_matching.entity.verification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerifyCodeSendMethod {
  EMAIL(1), PHONE(2);

  private final int dbCode;
}
