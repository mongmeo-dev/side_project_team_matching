package dev.mongmeo.side_project_team_matching.domain.model.email;

import dev.mongmeo.side_project_team_matching.exception.RequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SendEmailModelTest {

  @Test
  @DisplayName("올바르지 않은 형식의 이메일 주소를 넣으면 생성에 실패한다.")
  void createModelFailCauseByInvalidEmail() {
    // given
    String invalidAddress = "mongmeo";

    // when
    // then
    Assertions.assertThatThrownBy(() -> SendEmailModel.createNoReplyMail(invalidAddress, "test", "test"))
        .isInstanceOf(RequestException.class);
  }

  @Test
  @DisplayName("올바른 형식의 이메일 주소를 넣으면 생성에 성공한다.")
  void createModelSuccessTest() {
    // given
    String validAddress = "mongmeo.dev@gmail.com";

    // when
    SendEmailModel actual = SendEmailModel.createNoReplyMail(validAddress, "test", "test");

    // then
    Assertions.assertThat(actual.getTo()).isEqualTo(validAddress);
  }
}