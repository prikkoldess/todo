package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		// СТАВИМ ЭТО ПЕРВЫМ ДЕЛОМ:
		// Это принудительно меняет пояс для всего процесса ДО запуска базы
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		SpringApplication.run(TodoApplication.class, args);
	}
}