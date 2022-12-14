package dev.mongmeo.side_project_team_matching.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException {

  private final HttpStatus responseStatus;
  private final ErrorCode errorCode;

  public RequestException(HttpStatus responseStatus, ErrorCode errorCode) {
    super();
    this.responseStatus = responseStatus;
    this.errorCode = errorCode;
  }

  public RequestException(ErrorCode errorCode) {
    super();
    this.responseStatus = HttpStatus.BAD_REQUEST;
    this.errorCode = errorCode;
  }
}
