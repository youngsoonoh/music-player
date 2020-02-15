package com.flo.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="flo_user")
public class User extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 1, max = 30)
  @Column(length = 30, unique = true, nullable = false)
  private String login;

  @JsonIgnore
  @NotNull
  @Size(min = 60, max = 60)
  @Column(name = "password_hash", length = 60, nullable = false)
  private String password;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
          name = "flo_user_authority",
          joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
  @BatchSize(size = 20)
  private Set<Authority> authorities = new HashSet<>();
}
