package com.music.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
				info = @Info(
								title = "FLO DEMO",
								version = "0.0",
								description = "FLO DEMO API",
								license = @License(name = "NONE", url = "http://foo.bar"),
								contact = @Contact(url = "http://demo.com", name = "노영수", email = "guitaryc33@gmail.com")
				),
				servers = {
								@Server(description = "prod",url = "http://ec2-15-164-251-108.ap-northeast-2.compute.amazonaws.com:8080"),
								@Server(description = "dev",url = "http://localhost:8080")
				}
)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
