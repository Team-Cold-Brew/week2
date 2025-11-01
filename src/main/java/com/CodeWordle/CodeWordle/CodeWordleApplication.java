package com.CodeWordle.CodeWordle; // Asegúrate de que el paquete sea el correcto

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CodeWordleApplication extends SpringBootServletInitializer { // <-- 1. Extiende esta clase

    public static void main(String[] args) {
        SpringApplication.run(CodeWordleApplication.class, args);
    }

    // <-- 2. Añade este método
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CodeWordleApplication.class);
    }
}
