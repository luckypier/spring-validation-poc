package com.poc.library;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RestController
@Validated
@Slf4j
public class BookController {

    @PostMapping(value = "/control/{age}/mark/{mark}")
    public Object control(
            @PathVariable("age")
            @Min(5) Integer age,

            @PathVariable("mark")
            @Max(5) Integer mark,

            @Min(3) @RequestParam(name = "conclusion") Integer conclusion,

            @Positive @RequestParam(name = "infect") Integer infect,

            @Valid
            @RequestBody Book book
    ) {

      return new Book();
    }
}
