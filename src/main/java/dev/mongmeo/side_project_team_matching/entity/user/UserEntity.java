package dev.mongmeo.side_project_team_matching.entity.user;

import dev.mongmeo.side_project_team_matching.adapter.out.persistence.converter.JoinTypeConverter;
import dev.mongmeo.side_project_team_matching.adapter.out.persistence.converter.UserRoleConverter;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column
  private String password;

  @Column(nullable = false, length = 10)
  private String name;

  @Column(nullable = false, length = 30)
  private String nickname;

  @Convert(converter = UserRoleConverter.class)
  @Column(nullable = false)
  private UserRole role;

  @Convert(converter = JoinTypeConverter.class)
  @Column(nullable = false)
  private JoinType joinType;

  @CreatedDate
  @Column
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column
  private LocalDateTime lastModifiedAt;

  @Column
  private LocalDateTime deletedAt;

  @PrePersist
  private void setDefaultValue() {
    if (this.joinType == null) {
      this.joinType = JoinType.EMAIL;
    }
    if (this.role == null) {
      this.role = UserRole.ROLE_USER;
    }
  }
}
