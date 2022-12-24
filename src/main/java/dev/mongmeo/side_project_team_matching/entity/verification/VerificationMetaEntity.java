package dev.mongmeo.side_project_team_matching.entity.verification;

import dev.mongmeo.side_project_team_matching.adapter.out.persistence.converter.VerifyCodeSendMethodConverter;
import dev.mongmeo.side_project_team_matching.exception.ErrorCode;
import dev.mongmeo.side_project_team_matching.exception.RequestException;
import java.time.Clock;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "verification_meta")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class VerificationMetaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "char(6)")
  private String code;

  @Column(nullable = false)
  @Convert(converter = VerifyCodeSendMethodConverter.class)
  private VerifyCodeSendMethod sendMethod;

  @Column(nullable = false)
  private boolean isVerified;

  @Column
  private String email;

  @Column(length = 11)
  private String phoneNumber;

  @Column
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime expireAt;

  public static VerificationMetaEntity createEmailVerificationMeta(String email, String code, Clock clock) {
    LocalDateTime expireDateTime = LocalDateTime.now(clock).plusMinutes(30);
    return new VerificationMetaEntity(null, code, VerifyCodeSendMethod.EMAIL, false, email, null, null, expireDateTime);
  }

  public void verify(Clock clock) {
    if (!isValidMeta(clock)) {
      throw new RequestException(ErrorCode.INVALID_VERIFICATION_META);
    }
    this.isVerified = true;
  }

  private boolean isValidMeta(Clock clock) {
    return !isVerified && LocalDateTime.now(clock).isBefore(expireAt);
  }
}
