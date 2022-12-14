package dev.mongmeo.side_project_team_matching.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServerException extends RuntimeException {

  private final HttpStatus responseStatus;
  private final ErrorCode errorCode;

  public ServerException(HttpStatus responseStatus, ErrorCode errorCode) {
    super();
    this.responseStatus = responseStatus;
    this.errorCode = errorCode;
  }

  public ServerException(ErrorCode errorCode) {
    super();
    this.responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    this.errorCode = errorCode;
  }
}
