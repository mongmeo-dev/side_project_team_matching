package dev.mongmeo.side_project_team_matching.entity.user;

import dev.mongmeo.side_project_team_matching.adapter.out.persistence.converter.JoinTypeConverter;
import dev.mongmeo.side_project_team_matching.adapter.out.persistence.converter.UserRoleConverter;
import dev.mongmeo.side_project_team_matching.domain.model.user.SignUpModel;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private boolean emailVerified;

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

  public static UserEntity joinByEmail(SignUpModel model) {
    return new UserEntity(
        null,
        model.getEmail(),
        false,
        model.getPassword(),
        model.getName(),
        model.getNickname(),
        UserRole.ROLE_USER,
        JoinType.EMAIL,
        null,
        null,
        null
    );
  }
}
