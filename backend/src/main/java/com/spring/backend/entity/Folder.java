package com.spring.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "folders")
@Getter
@Setter
@NoArgsConstructor
public class Folder extends BaseEntity{
  private String name;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @ManyToOne
  @JoinColumn(name = "parent_folder_id")
  private Folder parentFolder;

}
