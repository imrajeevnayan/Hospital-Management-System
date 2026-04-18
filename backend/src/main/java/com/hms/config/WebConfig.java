package com.hms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Link the /hms path to the external frontend directory
        registry.addResourceHandler("/hms/**")
                .addResourceLocations("file:d:/HMS/frontend/public/hms/")
                .setCachePeriod(0);
        
        // Link root index to the frontend root index
        registry.addResourceHandler("/index.html")
                .addResourceLocations("file:d:/HMS/frontend/index.html")
                .setCachePeriod(0);
    }
}
