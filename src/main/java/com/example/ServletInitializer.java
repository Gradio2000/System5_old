package com.example;

import com.example.system5.util.Version;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ServletInitializer extends SpringBootServletInitializer {
    public static final String VERSION = Version.getVersion();
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(System5Application.class);
    }
}
