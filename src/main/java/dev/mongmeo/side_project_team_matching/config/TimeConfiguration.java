package dev.mongmeo.side_project_team_matching.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfiguration {

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }
}
