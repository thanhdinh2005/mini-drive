package com.spring.backend.entity;

import com.spring.backend.entity.enums.PermissionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
  name = "permissions",
  uniqueConstraints = @UniqueConstraint(columnNames = "public_token")
)
@Getter
@Setter
@NoArgsConstructor
public class Permission extends BaseEntity{

  @ManyToOne
  @JoinColumn(name = "folder_id")
  private Folder folder;

  @ManyToOne
  @JoinColumn(name = "shared_with_user_id")
  private User sharedWithUser;

  private PermissionType permissionType;

  private String publicToken;

  @ManyToOne
  @JoinColumn(name = "granted_by_id")
  private User grantedBy;

}
