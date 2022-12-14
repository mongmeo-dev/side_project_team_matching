package dev.mongmeo.side_project_team_matching.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServerException extends RuntimeException {

  private final HttpStatus responseStatus;

  public ServerException(String message, HttpStatus responseStatus) {
    super(message);
    this.responseStatus = responseStatus;
  }

  public ServerException(String message) {
    super(message);
    this.responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
