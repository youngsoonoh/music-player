package com.flo.demo.web.rest.erros;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class ExceptionHandler implements ProblemHandling {


    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVM> fieldErrors = result.getFieldErrors().stream()
                .map(x -> new FieldErrorVM(x.getObjectName(), x.getField(), x.getCode()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("/constraint-violation"))
                .withTitle("필드 유효성 검증 오류")
                .withStatus(defaultConstraintViolationStatus())
                .with("message", "error.validation")
                .with("fieldErrors", fieldErrors)
                .build();
        return create(ex, problem, request);
    }
}
