package dev.mongmeo.side_project_team_matching.adapter.out.persistence.repository.verificatioin;

import static dev.mongmeo.side_project_team_matching.entity.verification.QVerificationMetaEntity.verificationMetaEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.mongmeo.side_project_team_matching.domain.port.out.repository.verification.VerificationMetaQueryRepository;
import dev.mongmeo.side_project_team_matching.entity.verification.VerificationMetaEntity;
import dev.mongmeo.side_project_team_matching.entity.verification.VerifyCodeSendMethod;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QueryDslVerificationMetaQueryRepository implements VerificationMetaQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<VerificationMetaEntity> findLastEmailVerificationMeta(String email) {
    return Optional.ofNullable(queryFactory.selectFrom(verificationMetaEntity)
        .where(verificationMetaEntity.sendMethod.eq(VerifyCodeSendMethod.EMAIL))
        .where(verificationMetaEntity.email.eq(email))
        .orderBy(verificationMetaEntity.createdAt.desc())
        .fetchFirst());
  }

  @Override
  public Optional<VerificationMetaEntity> findLastPhoneVerificationMeta(String phoneNumber) {
    return Optional.ofNullable(queryFactory.selectFrom(verificationMetaEntity)
        .where(verificationMetaEntity.sendMethod.eq(VerifyCodeSendMethod.PHONE))
        .where(verificationMetaEntity.phoneNumber.eq(phoneNumber))
        .orderBy(verificationMetaEntity.createdAt.desc())
        .fetchFirst());
  }
}
