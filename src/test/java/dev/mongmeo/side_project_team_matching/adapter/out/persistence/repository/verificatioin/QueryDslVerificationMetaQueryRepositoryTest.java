package dev.mongmeo.side_project_team_matching.adapter.out.persistence.repository.verificatioin;

import dev.mongmeo.side_project_team_matching.entity.verification.VerificationMetaEntity;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class QueryDslVerificationMetaQueryRepositoryTest {

  @Autowired
  private QueryDslVerificationMetaQueryRepository repository;

  @PersistenceContext
  private EntityManager entityManager;

  @BeforeEach
  void setUp() {
    for (int i = 0; i < 10; i++) {
      String time = "2022-12-26T09:20:" + (10 + i) + ".00Z";
      String email = "test" + i + "@test.com";
      String code = UUID.randomUUID().toString().substring(0, 8);
      Clock clock = Clock.fixed(Instant.parse(time), ZoneId.of("UTC"));
      VerificationMetaEntity entity = VerificationMetaEntity.createEmailVerificationMeta(email, code, clock);
      if (i != 9) {
        entity.verify(clock);
      }
      entityManager.persist(entity);
    }
    for (int i = 10; i < 20; i++) {
      String time = "2022-12-26T09:20:" + (10 + i) + ".00Z";
      String phoneNumber = "010123456" + i;
      String code = UUID.randomUUID().toString().substring(0, 8);
      Clock clock = Clock.fixed(Instant.parse(time), ZoneId.of("UTC"));
      VerificationMetaEntity entity = VerificationMetaEntity.createPhoneVerificationMeta(phoneNumber, code, clock);
      if (i != 19) {
        entity.verify(clock);
      }
      entityManager.persist(entity);
    }
    entityManager.flush();
  }

  @Test
  @DisplayName("마지막으로 저장된 이메일 인증정보를 가져온다")
  void findLastEmailVerificationMetaTest() {
    // given
    String userEmail = "test9@test.com";

    // when
    Optional<VerificationMetaEntity> actual = repository.findLastEmailVerificationMeta(userEmail);

    // then
    Assertions.assertThat(actual).isNotEmpty();
    Assertions.assertThat(actual.get().getEmail()).isEqualTo(userEmail);
  }

  @Test
  @DisplayName("마지막으로 저장된 휴대폰 인증정보를 가져온다")
  void findLastPhoneVerificationMetaTest() {
    // given
    String userPhone = "01012345619";

    // when
    Optional<VerificationMetaEntity> actual = repository.findLastPhoneVerificationMeta(userPhone);

    // then
    Assertions.assertThat(actual).isNotEmpty();
    Assertions.assertThat(actual.get().getPhoneNumber()).isEqualTo(userPhone);
  }
}