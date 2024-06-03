package com.ideage.ams.config;

import com.ideage.ams.common.Constants;
import com.ideage.ams.config.handler.TokenToUserMethodArgumentResolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description コンフィグレーション
 * @author zhen.cheng
 */
@Configuration
public class AmsWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
    private TokenToUserMethodArgumentResolver tokenUserMethodArgumentResolver;

    /**
     * TokenToUser
     *
     * @param argumentResolvers
     */
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenUserMethodArgumentResolver);
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:"+ Constants.FILE_UPLOAD_PATH);
    }
}