package dev.mongmeo.side_project_team_matching.adapter.out.persistence.repository;

import dev.mongmeo.side_project_team_matching.domain.port.out.repository.verification.VerificationMetaSaveRepository;
import dev.mongmeo.side_project_team_matching.entity.verification.VerificationMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVerificationMetaRepository extends JpaRepository<VerificationMetaEntity, Long>,
    VerificationMetaSaveRepository {

}
