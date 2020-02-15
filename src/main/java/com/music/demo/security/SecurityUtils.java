package com.music.demo.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@NoArgsConstructor
public final class SecurityUtils {

  public static Optional<String> getCurrentUser() {

      SecurityContext securityContext = SecurityContextHolder.getContext();
      return Optional.ofNullable(securityContext.getAuthentication())
              .map(authentication -> {
                  if (authentication.getPrincipal() instanceof UserDetails) {
                      UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                      return springSecurityUser.getUsername();
                  } else if (authentication.getPrincipal() instanceof String) {
                      return (String) authentication.getPrincipal();
                  }
                  return null;
              });
  }

}
