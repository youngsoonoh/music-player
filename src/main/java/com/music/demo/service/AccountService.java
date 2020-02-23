package com.music.demo.service;

import com.music.demo.domain.Authority;
import com.music.demo.domain.User;
import com.music.demo.repository.AuthorityRepository;
import com.music.demo.repository.UserRepository;
import com.music.demo.web.rest.erros.LoginAlreadyUsedException;
import com.music.demo.web.vm.RegisterVM;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthorityRepository authorityRepository;

  public void register(RegisterVM registerVM) {

    userRepository.findOneByLogin(registerVM.getLogin())
            .ifPresent(x -> {
              throw new LoginAlreadyUsedException();
            });
    User user = new User();
    user.setLogin(registerVM.getLogin());
    Set<Authority> authorities = new HashSet<>();
    authorityRepository.findById("ROLE_USER").ifPresent(authorities::add);
    user.setAuthorities(authorities);
    user.setPassword(passwordEncoder.encode(registerVM.getPassword()));
    userRepository.save(user);
  }
}
