package dev.mongmeo.side_project_team_matching.adapter.out.persistence.converter;

import dev.mongmeo.side_project_team_matching.entity.verification.VerifyCodeSendMethod;
import dev.mongmeo.side_project_team_matching.exception.ErrorCode;
import dev.mongmeo.side_project_team_matching.exception.ServerException;
import java.util.Arrays;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class VerifyCodeSendMethodConverter implements AttributeConverter<VerifyCodeSendMethod, Integer> {

  @Override
  public Integer convertToDatabaseColumn(VerifyCodeSendMethod attribute) {
    return attribute.getDbCode();
  }

  @Override
  public VerifyCodeSendMethod convertToEntityAttribute(Integer dbData) {
    return Arrays.stream(VerifyCodeSendMethod.values())
        .filter(userRole -> dbData.equals(userRole.getDbCode()))
        .findFirst()
        .orElseThrow(() -> new ServerException(ErrorCode.INVALID_USER_ROLE));
  }
}