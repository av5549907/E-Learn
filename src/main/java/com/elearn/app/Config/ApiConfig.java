package com.elearn.app.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URL;
@Configuration
public class ApiConfig {
   @Bean
   public ModelMapper modelMapper() {
      return new ModelMapper();
   }
}
