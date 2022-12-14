package dev.mongmeo.side_project_team_matching.adapter.out.persistence.repository;

import dev.mongmeo.side_project_team_matching.domain.repository.user.UserQueryRepository;
import dev.mongmeo.side_project_team_matching.domain.repository.user.UserUpsertRepository;
import dev.mongmeo.side_project_team_matching.model.user.UserEntity;
import org.springframework.data.repository.Repository;

public interface JpaUserRepository extends Repository<UserEntity, Long>, UserQueryRepository, UserUpsertRepository {

}
