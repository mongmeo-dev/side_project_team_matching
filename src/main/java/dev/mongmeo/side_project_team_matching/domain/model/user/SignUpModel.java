package dev.mongmeo.side_project_team_matching.domain.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpModel {

  @Email
  @NotBlank
  private String email;

  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&])[A-Za-z\\d@$!%*?&]{8,}")
  private String password;

  @Length(max = 10)
  private String name;

  @Length(max = 30)
  private String nickname;

  public void updatePasswordToEncodedPassword(String encoded) {
    this.password = encoded;
  }
}
