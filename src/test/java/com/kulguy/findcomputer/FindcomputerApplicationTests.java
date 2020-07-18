package com.kulguy.findcomputer;

import com.kulguy.findcomputer.controller.AuthController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FindcomputerApplicationTests {

  @Autowired
  private AuthController controller;

  @Test
	void contextLoads() {
    assertThat(controller).isNotNull();
	}

}
