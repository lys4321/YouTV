package com.example.demo;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/thumbnail/**").addResourceLocations("file:///YTVrepo/thumbnail/");
		registry.addResourceHandler("/video/**").addResourceLocations("file:///YTVrepo/video/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
}
