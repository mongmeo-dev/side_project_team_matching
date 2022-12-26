package dev.mongmeo.side_project_team_matching.service.verification;

import dev.mongmeo.side_project_team_matching.domain.port.in.usecase.email.SendEmailUseCase;
import dev.mongmeo.side_project_team_matching.domain.port.out.repository.verification.VerificationMetaQueryRepository;
import dev.mongmeo.side_project_team_matching.domain.port.out.repository.verification.VerificationMetaSaveRepository;
import dev.mongmeo.side_project_team_matching.entity.user.UserEntity;
import dev.mongmeo.side_project_team_matching.entity.verification.VerificationMetaEntity;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@ExtendWith(MockitoExtension.class)
class VerificationServiceTest {

  @Mock
  private SendEmailUseCase sendEmailUseCase;
  @Mock
  private VerificationMetaSaveRepository saveRepository;
  @Mock
  private VerificationMetaQueryRepository queryRepository;
  @Mock
  private ITemplateEngine templateEngine;
  private final Clock clock = Clock.fixed(Instant.parse("2022-12-22T20:00:00.00Z"), ZoneId.of("UTC"));
  private VerificationService verificationService;

  @BeforeEach
  void setUp() {
    String domain = "http://localhost:8080";
    verificationService = new VerificationService(
        domain,
        sendEmailUseCase,
        saveRepository,
        queryRepository,
        clock,
        templateEngine
    );
  }

  @Test
  @DisplayName("UserEntity를 받아 마지막 이메일 인증 정보를 만료시키고 VerificationMetaEntity객체를 생성하고 인증 이메일을 보낸다")
  void sendVerificationEmailTest() {
    // given
    UserEntity userEntity = Mockito.mock(UserEntity.class);
    VerificationMetaEntity lastVerificationMetaEntity = Mockito.mock(VerificationMetaEntity.class);
    VerificationMetaEntity verificationMetaEntity = Mockito.mock(VerificationMetaEntity.class);
    String email = "test@test.com";
    Mockito.when(userEntity.getEmail()).thenReturn(email);
    Mockito.when(saveRepository.save(Mockito.any())).thenReturn(verificationMetaEntity);
    Mockito.when(queryRepository.findLastEmailVerificationMeta(email))
        .thenReturn(Optional.of(lastVerificationMetaEntity));

    // when
    verificationService.sendVerificationEmail(userEntity);

    // then
    Mockito.verify(lastVerificationMetaEntity, Mockito.times(1)).expire();
    Mockito.verify(saveRepository, Mockito.times(1)).save(Mockito.argThat(arg -> StringUtils.hasLength(arg.getCode())));
    Mockito.verify(templateEngine, Mockito.times(1))
        .process(Mockito.eq("/email/verification_email"), Mockito.any(Context.class));
    Mockito.verify(sendEmailUseCase, Mockito.times(1)).sendHtmlEmail(Mockito.argThat(arg -> arg.getTo().equals(email)));
  }
}