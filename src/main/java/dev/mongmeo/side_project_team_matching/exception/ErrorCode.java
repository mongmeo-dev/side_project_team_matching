package dev.mongmeo.side_project_team_matching.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  INVALID_USER_ROLE("invalid.user.role"), INVALID_JOIN_TYPE("invalid.user.join-type"),
  INVALID_EMAIL_FORMAT("invalid.format.user.email"), INVALID_PASSWORD_FORMAT("invalid.format.user.nickname"),
  INVALID_NICKNAME_FORMAT("invalid.nickname.user.format"), INVALID_NAME_FORMAT("invalid.format.user.name"),
  INVALID_VERIFICATION_META("invalid.verification.meta"), INVALID_INPUT("invalid.input"),
  DUPLICATED_EMAIL("duplicated.user.email"), DUPLICATED_NICKNAME("duplicated.user.nickname"),
  NOT_SUPPORTED_VERIFICATION_METHOD("notSupported.verification.method");

  private final String messageCode;
}
