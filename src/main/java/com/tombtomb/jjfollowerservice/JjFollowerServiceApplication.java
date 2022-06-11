package com.tombtomb.jjfollowerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.metrics.export.datadog.EnableDatadogMetrics;

@SpringBootApplication
@EnableDatadogMetrics
public class JjFollowerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JjFollowerServiceApplication.class, args);
	}

}
