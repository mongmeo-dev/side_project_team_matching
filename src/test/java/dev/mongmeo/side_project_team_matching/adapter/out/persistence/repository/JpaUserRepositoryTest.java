package dev.mongmeo.side_project_team_matching.adapter.out.persistence.repository;

import dev.mongmeo.side_project_team_matching.config.JpaConfiguration;
import dev.mongmeo.side_project_team_matching.domain.model.user.SignUpModel;
import dev.mongmeo.side_project_team_matching.entity.user.UserEntity;
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
    SignUpModel model = new SignUpModel("test@test.com", "password", "tester", "tester");
    UserEntity entity = UserEntity.joinByEmail(model);
    // when
    UserEntity actual = userRepository.save(entity);

    // then
    Assertions.assertThat(actual.getId()).isNotNull();
    Assertions.assertThat(actual.getCreatedAt()).isNotNull();
    Assertions.assertThat(actual.getLastModifiedAt()).isNotNull();
  }
}