package dev.mongmeo.side_project_team_matching.service.user;

import dev.mongmeo.side_project_team_matching.domain.model.user.SignUpModel;
import dev.mongmeo.side_project_team_matching.domain.port.in.usecase.verification.SendVerificationEmailUseCase;
import dev.mongmeo.side_project_team_matching.domain.port.out.repository.user.UserQueryRepository;
import dev.mongmeo.side_project_team_matching.domain.port.out.repository.user.UserUpsertRepository;
import dev.mongmeo.side_project_team_matching.domain.port.in.usecase.user.SignUpUseCase;
import dev.mongmeo.side_project_team_matching.entity.user.UserEntity;
import dev.mongmeo.side_project_team_matching.exception.ErrorCode;
import dev.mongmeo.side_project_team_matching.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService implements SignUpUseCase {

  private final UserUpsertRepository userUpsertRepository;
  private final UserQueryRepository userQueryRepository;
  private final SendVerificationEmailUseCase sendVerificationEmailUseCase;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public long signUpByEmail(SignUpModel model) {
    checkEmailAndNicknameIsAlreadyUsed(model);

    model.updatePasswordToEncodedPassword(passwordEncoder.encode(model.getPassword()));

    UserEntity savedUser = userUpsertRepository.save(UserEntity.joinByEmail(model));

    sendVerificationEmailUseCase.sendVerificationEmail(savedUser);

    return savedUser.getId();
  }

  private void checkEmailAndNicknameIsAlreadyUsed(SignUpModel model) {
    checkEmailAlreadyUse(model.getEmail());
    checkNicknameAlreadyUser(model.getNickname());
  }

  private void checkEmailAlreadyUse(String email) {
    boolean result = userQueryRepository.existsByEmail(email);
    if (result) {
      throw new RequestException(ErrorCode.DUPLICATED_EMAIL);
    }
  }

  private void checkNicknameAlreadyUser(String nickname) {
    boolean result = userQueryRepository.existsByNickname(nickname);
    if (result) {
      throw new RequestException(ErrorCode.DUPLICATED_NICKNAME);
    }
  }
}
