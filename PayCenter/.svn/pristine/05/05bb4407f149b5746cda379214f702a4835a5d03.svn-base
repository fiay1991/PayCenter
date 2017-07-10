package com.park.paycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAspectJAutoProxy
@Import({DynamicDataSourceRegister.class})
public class PayCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayCenterApplication.class, args);
	}
}
