package dev.mongmeo.side_project_team_matching.domain.port.in.usecase.verification;

import dev.mongmeo.side_project_team_matching.entity.user.UserEntity;

public interface SendVerificationEmailUseCase {

  void sendVerificationEmail(UserEntity user);
}
