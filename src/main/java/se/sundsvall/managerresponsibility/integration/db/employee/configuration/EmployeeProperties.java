package se.sundsvall.managerresponsibility.integration.db.employee.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "integration.employee")
public record EmployeeProperties(
	@DefaultValue("5") int connectTimeout,
	@DefaultValue("15") int readTimeout) {}
