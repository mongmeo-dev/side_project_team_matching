package dev.mongmeo.side_project_team_matching.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
  ROLE_ADMIN(1), ROLE_MANAGER(2), ROLE_USER(3);

  private final int dbCode;
}
