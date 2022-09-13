package com.poc.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@Order(-2)
@AllArgsConstructor
public class ErrorHandling implements WebExceptionHandler {

  private ObjectMapper objectMapper;

  @SneakyThrows
  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    if (ex instanceof WebExchangeBindException) {

      WebExchangeBindException cvex = (WebExchangeBindException) ex;
      Map<String, String> errors = new HashMap<>();
      cvex.getFieldErrors()
          .forEach(ev -> errors.put(ev.getField(), ev.getDefaultMessage()));
      DataBuffer db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(errors));
      exchange.getResponse().setStatusCode(BAD_REQUEST);
      return exchange.getResponse().writeWith(Mono.just(db));
    }
    if (ex instanceof ConstraintViolationException) {
      ConstraintViolationException cvex = (ConstraintViolationException) ex;
      Map<String, String> errors = new HashMap<>();
      cvex.getConstraintViolations().stream()
          .forEach(cv -> errors.put(cv.getPropertyPath().toString(), cv.getMessage()));
      DataBuffer db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(errors));
      exchange.getResponse().setStatusCode(BAD_REQUEST);
      return exchange.getResponse().writeWith(Mono.just(db));
    }
    return Mono.error(ex);
  }
}