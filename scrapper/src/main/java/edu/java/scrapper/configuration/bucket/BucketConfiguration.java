package edu.java.scrapper.configuration.bucket;

import edu.java.scrapper.ratelimiting.RateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BucketConfiguration implements WebMvcConfigurer {
    private final RateLimitInterceptor interceptor;

    public BucketConfiguration(RateLimitInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
            .addPathPatterns("/updates")
            .addPathPatterns("/links")
            .addPathPatterns("/tg-chat/{id}");
    }

}
