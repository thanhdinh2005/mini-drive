package com.spring.backend.entity;

import com.spring.backend.entity.enums.Plan;
import com.spring.backend.entity.enums.Role;
import com.spring.backend.entity.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{
  private String email;
  private String passwordHash;
  private String displayName;
  private Role role;
  private Status status;
  private Plan plan;
  private Long storageUsed;
  private Long storageLimit;
}
