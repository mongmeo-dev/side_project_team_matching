package dev.mongmeo.side_project_team_matching.domain.usecase.user;

import dev.mongmeo.side_project_team_matching.domain.model.user.SignUpModel;

public interface SignUpUseCase {

  long signUpByEmail(SignUpModel model);
}
