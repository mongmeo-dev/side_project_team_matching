package dev.mongmeo.side_project_team_matching.adapter.out.persistence.converter;

import dev.mongmeo.side_project_team_matching.exception.ServerException;
import dev.mongmeo.side_project_team_matching.model.user.JoinType;
import java.util.Arrays;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class JoinTypeConverter implements AttributeConverter<JoinType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(JoinType attribute) {
    return attribute.getDbCode();
  }

  @Override
  public JoinType convertToEntityAttribute(Integer dbData) {
    return Arrays.stream(JoinType.values())
        .filter(joinType -> dbData.equals(joinType.getDbCode()))
        .findFirst()
        .orElseThrow(() -> new ServerException("Invalid Join Type"));
  }
}