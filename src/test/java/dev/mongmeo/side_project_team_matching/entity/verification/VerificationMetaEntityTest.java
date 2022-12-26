package dev.mongmeo.side_project_team_matching.entity.verification;

import dev.mongmeo.side_project_team_matching.exception.RequestException;
import java.lang.reflect.Field;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.NotThrownAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VerificationMetaEntityTest {

  private final Clock clock = Clock.fixed(Instant.parse("2022-12-22T20:00:00.00Z"), ZoneId.of("UTC"));

  @Test
  @DisplayName("새로운 entity를 생성하면 유효기간이 30분인 entity가 생성된다.")
  void createTest() {
    // when
    VerificationMetaEntity emailActual = VerificationMetaEntity.createEmailVerificationMeta("test@test.com", "12345", clock);
    VerificationMetaEntity phoneActual = VerificationMetaEntity.createPhoneVerificationMeta("01012345678", "12345", clock);

    // then
    Assertions.assertThat(emailActual.getExpireAt().toInstant(ZoneOffset.UTC))
        .isEqualTo(clock.instant().plus(30, ChronoUnit.MINUTES));
    Assertions.assertThat(phoneActual.getExpireAt().toInstant(ZoneOffset.UTC))
        .isEqualTo(clock.instant().plus(30, ChronoUnit.MINUTES));
  }

  @Test
  @DisplayName("만료 시간이 지나지 않았고, 이미 인증된 정보가 아니라면 예외가 발생하지 않는다..")
  void verifySuccessTest() {
    // given
    Clock now = Clock.fixed(Instant.parse("2022-12-22T20:29:59.99Z"), ZoneId.of("UTC"));
    VerificationMetaEntity actual = VerificationMetaEntity.createEmailVerificationMeta("test@test.com", "12345", clock);

    // when
    actual.verify(now);

    // then
    NotThrownAssert result = Assertions.assertThatNoException();
  }

  @Test
  @DisplayName("만료 시간이 지났다면 예외를 던진다.")
  void verifyFailCauseByExpiredTest() {
    // given
    Clock now = Clock.fixed(Instant.parse("2022-12-22T20:30:00.01Z"), ZoneId.of("UTC"));
    VerificationMetaEntity actual = VerificationMetaEntity.createEmailVerificationMeta("test@test.com", "12345", clock);

    // when
    // then
    Assertions.assertThatThrownBy(() -> actual.verify(now)).isInstanceOf(RequestException.class);
  }


  @Test
  @DisplayName("이미 인증된 정보라면 예외를 던진다.")
  void verifyFailCauseByAlreadyUsedTest() throws NoSuchFieldException, IllegalAccessException {
    // given
    Clock now = Clock.fixed(Instant.parse("2022-12-22T20:29:59.99Z"), ZoneId.of("UTC"));
    VerificationMetaEntity actual = VerificationMetaEntity.createEmailVerificationMeta("test@test.com", "12345", clock);

    Field isVerifiedField = VerificationMetaEntity.class.getDeclaredField("isVerified");
    isVerifiedField.setAccessible(true);
    isVerifiedField.set(actual, true);

    // when
    // then
    Assertions.assertThatThrownBy(() -> actual.verify(now)).isInstanceOf(RequestException.class);
  }
}