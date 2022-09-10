package com.example;

import com.example.system5.util.Version;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class System5Application {
    public static final String VERSION = Version.getVersion();

    public static void main(String[] args) {
        SpringApplication.run(System5Application.class, args);
    }
}
