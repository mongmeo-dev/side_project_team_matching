package dev.mongmeo.side_project_team_matching.adapter.out.persistence.converter;

import dev.mongmeo.side_project_team_matching.exception.ServerException;
import dev.mongmeo.side_project_team_matching.model.user.UserRole;
import java.util.Arrays;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {

  @Override
  public Integer convertToDatabaseColumn(UserRole attribute) {
    return attribute.getDbCode();
  }

  @Override
  public UserRole convertToEntityAttribute(Integer dbData) {
    return Arrays.stream(UserRole.values())
        .filter(userRole -> dbData.equals(userRole.getDbCode()))
        .findFirst()
        .orElseThrow(() -> new ServerException("Invalid User Role"));
  }
}