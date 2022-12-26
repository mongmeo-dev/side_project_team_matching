package dev.mongmeo.side_project_team_matching.service.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

import dev.mongmeo.side_project_team_matching.domain.model.user.SignUpModel;
import dev.mongmeo.side_project_team_matching.domain.port.in.usecase.verification.SendVerificationEmailUseCase;
import dev.mongmeo.side_project_team_matching.domain.port.out.repository.user.UserQueryRepository;
import dev.mongmeo.side_project_team_matching.domain.port.out.repository.user.UserUpsertRepository;
import dev.mongmeo.side_project_team_matching.entity.user.UserEntity;
import dev.mongmeo.side_project_team_matching.exception.RequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private SendVerificationEmailUseCase sendVerificationEmailUseCase;
  @Mock
  private UserUpsertRepository userUpsertRepository;
  @Mock
  private UserQueryRepository userQueryRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @InjectMocks
  private UserService userService;

  @Test
  @DisplayName("SignUpModel 객체를 넘기면 UserEntity 객체를 생성하는데, 비밀번호는 해싱되어 저장한다.")
  void signUpByEmailTest() {
    // given
    String encodedPassword = "encodedPassword";
    SignUpModel model = new SignUpModel("test@test.com", "password", "tester", "tester");
    BDDMockito.given(passwordEncoder.encode(model.getPassword())).willReturn(encodedPassword);
    BDDMockito.given(userQueryRepository.existsByEmail(model.getEmail())).willReturn(false);
    BDDMockito.given(userQueryRepository.existsByNickname(model.getNickname())).willReturn(false);
    UserEntity mockSavedEntity = Mockito.mock(UserEntity.class);
    BDDMockito.given(userUpsertRepository.save(any())).willReturn(mockSavedEntity);
    BDDMockito.given(mockSavedEntity.getId()).willReturn(1L);

    // when
    long actual = userService.signUpByEmail(model);

    // then
    Assertions.assertThat(actual).isEqualTo(1L);
    Mockito.verify(userUpsertRepository, Mockito.times(1))
        .save(argThat(entity -> entity.getEmail().equals(model.getEmail())
            && entity.getPassword().equals(encodedPassword) && entity.getName().equals(model.getName())
            && entity.getNickname().equals(model.getNickname())));
    Mockito.verify(sendVerificationEmailUseCase, Mockito.times(1)).sendVerificationEmail(mockSavedEntity);
  }

  @Test
  @DisplayName("이미 사용중인 이메일로 회원가입을 시도하면 RequestException이 발생한다.")
  void signUpDuplicatedEmailTest() {
    // given
    String email = "test@test.com";
    SignUpModel model = new SignUpModel(email, "password", "tester", "tester");
    BDDMockito.given(userQueryRepository.existsByEmail(email)).willReturn(true);

    // when
    // then
    Assertions.assertThatThrownBy(() -> userService.signUpByEmail(model)).isInstanceOf(RequestException.class);
  }

  @Test
  @DisplayName("이미 사용중인 닉네임으로 회원가입을 시도하면 RequestException이 발생한다.")
  void SignUpDuplicatedNicknameTest() {
    // given
    String nickname = "tester";
    SignUpModel model = new SignUpModel("test@test.com", "password", "tester", nickname);
    BDDMockito.given(userQueryRepository.existsByNickname(nickname)).willReturn(true);

    // when
    // then
    Assertions.assertThatThrownBy(() -> userService.signUpByEmail(model)).isInstanceOf(RequestException.class);
  }
}