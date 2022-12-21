package dev.mongmeo.side_project_team_matching.domain.port.out.repository.user;

public interface UserQueryRepository {

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);
}
