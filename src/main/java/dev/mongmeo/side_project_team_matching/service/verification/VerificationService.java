package dev.mongmeo.side_project_team_matching.service.verification;

import dev.mongmeo.side_project_team_matching.domain.model.email.SendEmailModel;
import dev.mongmeo.side_project_team_matching.domain.port.in.usecase.email.SendEmailUseCase;
import dev.mongmeo.side_project_team_matching.domain.port.in.usecase.verification.SendVerificationEmailUseCase;
import dev.mongmeo.side_project_team_matching.domain.port.out.repository.verification.VerificationMetaSaveRepository;
import dev.mongmeo.side_project_team_matching.entity.user.UserEntity;
import dev.mongmeo.side_project_team_matching.entity.verification.VerificationMetaEntity;
import dev.mongmeo.side_project_team_matching.entity.verification.VerifyCodeSendMethod;
import java.time.Clock;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class VerificationService implements SendVerificationEmailUseCase {

  @Value("${server.domain}")
  private final String domain;
  private final SendEmailUseCase sendEmailUseCase;
  private final VerificationMetaSaveRepository saveRepository;
  private final Clock clock;
  private final ITemplateEngine templateEngine;

  public VerificationService(
      @Value("${server.domain}") String domain,
      SendEmailUseCase sendEmailUseCase,
      VerificationMetaSaveRepository saveRepository,
      Clock clock,
      ITemplateEngine templateEngine
  ) {
    this.domain = domain;
    this.sendEmailUseCase = sendEmailUseCase;
    this.saveRepository = saveRepository;
    this.clock = clock;
    this.templateEngine = templateEngine;
  }

  @Override
  @Transactional
  public void sendVerificationEmail(UserEntity userEntity) {
    VerificationMetaEntity verificationMeta = createVerificationMeta(VerifyCodeSendMethod.EMAIL);
    String content = generateVerificationEmailContent(userEntity, verificationMeta);
    SendEmailModel verificationMail = SendEmailModel.createNoReplyMail(
        userEntity.getEmail(),
        "이메일 인증을 완료해주세요",
        content
    );
    sendEmailUseCase.sendHtmlEmail(verificationMail);
  }

  private VerificationMetaEntity createVerificationMeta(VerifyCodeSendMethod method) {
    VerificationMetaEntity entity = VerificationMetaEntity.create(generateVerifyCode(), method, clock);
    return saveRepository.save(entity);
  }

  private String generateVerifyCode() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  private String generateVerificationEmailContent(UserEntity userEntity, VerificationMetaEntity verificationMeta) {
    Context context = new Context();
    context.setVariable("name", userEntity.getName());
    context.setVariable("domain", domain);
    context.setVariable("code", verificationMeta.getCode());
    return templateEngine.process("/email/verification_email", context);
  }
}
