package dev.mongmeo.side_project_team_matching.domain.port.out.repository.verification;

import dev.mongmeo.side_project_team_matching.entity.verification.VerificationMetaEntity;
import java.util.Optional;

public interface VerificationMetaQueryRepository {

  Optional<VerificationMetaEntity> findLastEmailVerificationMeta(String email);
  Optional<VerificationMetaEntity> findLastPhoneVerificationMeta(String phoneNumber);
}
