package dev.mongmeo.side_project_team_matching.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JoinType {
  EMAIL(1), THIRD_PARTY(2);

  private final int dbCode;

}
