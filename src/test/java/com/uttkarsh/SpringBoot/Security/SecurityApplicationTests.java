package com.uttkarsh.SpringBoot.Security;

import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import com.uttkarsh.SpringBoot.Security.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		UserEntity userEntity = new UserEntity(2L, "ut@gmail.com", "pass", "Uttkarsh");
		String token = jwtService.generateAccessToken(userEntity);
		System.out.println(token);

		long id = jwtService.getUserIdWithJwtToken(token);
		System.out.println(id);

	}

}
