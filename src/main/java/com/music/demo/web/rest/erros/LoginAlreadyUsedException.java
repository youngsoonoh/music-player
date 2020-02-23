package com.music.demo.web.rest.erros;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class LoginAlreadyUsedException extends AbstractThrowableProblem {

  public LoginAlreadyUsedException() {
    super(URI.create("/todo-uri"), "이미 사용중인 아이디 입니다.", Status.BAD_REQUEST, null, null, null, getParameter());
  }

   static Map<String, Object> getParameter() {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("message", "error.loginused");
    parameters.put("params", "user");
    return parameters;
  }
}
