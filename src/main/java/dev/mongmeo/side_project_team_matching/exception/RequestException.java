package dev.mongmeo.side_project_team_matching.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException {

  private final HttpStatus responseStatus;

  public RequestException(String message, HttpStatus responseStatus) {
    super(message);
    this.responseStatus = responseStatus;
  }

  public RequestException(String message) {
    super(message);
    this.responseStatus = HttpStatus.BAD_REQUEST;
  }
}
