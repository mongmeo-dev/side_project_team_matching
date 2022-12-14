package dev.mongmeo.side_project_team_matching.domain.repository.user;

import dev.mongmeo.side_project_team_matching.model.user.UserEntity;

public interface UserUpsertRepository {

  UserEntity save(UserEntity entity);
}
