package com.example.introduction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.introduction.gen.mapper")
public class AppConfig  {
}
