package dev.mongmeo.side_project_team_matching.adapter.out.persistence.repository;

import dev.mongmeo.side_project_team_matching.config.JpaConfiguration;
import dev.mongmeo.side_project_team_matching.entity.user.JoinType;
import dev.mongmeo.side_project_team_matching.entity.user.UserEntity;
import dev.mongmeo.side_project_team_matching.entity.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Import(JpaConfiguration.class)
@DataJpaTest
@Transactional
class JpaUserRepositoryTest {

  @Autowired
  private JpaUserRepository userRepository;

  @Test
  @DisplayName("유저 정보가 저장되면서 생성시간과 업데이트시간이 자동으로 삽입된다")
  void userSaveTest() {
    // given
    UserEntity entity = new UserEntity(
        null,
        "test@test.com",
        "password",
        "tester",
        "tester",
        UserRole.ROLE_USER,
        JoinType.EMAIL,
        null,
        null,
        null
    );

    // when
    UserEntity actual = userRepository.save(entity);

    // then
    Assertions.assertThat(actual.getId()).isNotNull();
    Assertions.assertThat(actual.getCreatedAt()).isNotNull();
    Assertions.assertThat(actual.getLastModifiedAt()).isNotNull();
  }

  @Test
  @DisplayName("유저 엔티티 생성시 role을 설정하지 않으면 기본값이 부여된다.")
  void userRoleDefaultValueTest() {
    // given
    UserEntity entity = new UserEntity(
        null,
        "test@test.com",
        "password",
        "tester",
        "tester",
        null,
        JoinType.EMAIL,
        null,
        null,
        null
    );

    // when
    UserEntity actual = userRepository.save(entity);

    // then
    Assertions.assertThat(actual.getRole()).isEqualTo(UserRole.ROLE_USER);
  }

  @Test
  @DisplayName("유저 엔티티 생성시 joinType을 설정하지 않으면 기본값이 부여된다.")
  void userJoinTypeDefaultValueTest() {
    // given
    UserEntity entity = new UserEntity(
        null,
        "test@test.com",
        "password",
        "tester",
        "tester",
        UserRole.ROLE_USER,
        null,
        null,
        null,
        null
    );

    // when
    UserEntity actual = userRepository.save(entity);

    // then
    Assertions.assertThat(actual.getJoinType()).isEqualTo(JoinType.EMAIL);
  }
}