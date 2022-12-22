package dev.mongmeo.side_project_team_matching.domain.port.out.repository.verification;

import dev.mongmeo.side_project_team_matching.entity.verification.VerificationMetaEntity;

public interface VerificationMetaSaveRepository {

  VerificationMetaEntity save(VerificationMetaEntity entity);
}
