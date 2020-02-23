package com.music.demo.web.rest;

import com.music.demo.security.jwt.JWTFilter;
import com.music.demo.security.jwt.TokenProvider;
import com.music.demo.service.AccountService;
import com.music.demo.service.dto.UserDTO;
import com.music.demo.web.vm.LoginVM;
import com.music.demo.web.vm.RegisterVM;
import com.music.demo.web.vm.TokenVM;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountResource {

  private final TokenProvider tokenProvider;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final AccountService accountService;

  @PostMapping("/login")
  public ResponseEntity<TokenVM> login(@Valid @RequestBody LoginVM loginVM) {

    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    boolean rememberMe = (loginVM.getRememberMe() == null) ? false : loginVM.getRememberMe();
    String jwt = tokenProvider.createToken(authentication, rememberMe);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

    return new ResponseEntity<>(new TokenVM(jwt), httpHeaders, HttpStatus.OK);
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody RegisterVM registerVMO) {
    accountService.register(registerVMO);
  }
}
