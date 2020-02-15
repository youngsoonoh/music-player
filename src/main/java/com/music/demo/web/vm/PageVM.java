package com.music.demo.web.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageVM {
  Long total;
  Integer size;
  String first;
  String prev;
  String last;
  String next;
}
