package dev.mongmeo.side_project_team_matching.domain.port.out.repository.user;

import dev.mongmeo.side_project_team_matching.entity.user.UserEntity;

public interface UserUpsertRepository {

  UserEntity save(UserEntity entity);
}
