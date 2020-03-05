package com.tomoya06.loiserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ApplicationConfiguration {

  @Autowired
  private MongoDbFactory mongoDbFactory;

  @Autowired
  private MongoMappingContext mongoMappingContext;

  @Bean
  public MappingMongoConverter mappingMongoConverter() {

    DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
    MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));

    return converter;
  }

  @Bean
  public CorsFilter corsFilter() {
    //1.添加CORS配置信息
    CorsConfiguration config = new CorsConfiguration();
    //放行哪些原始域
    config.addAllowedOrigin("*");
    //是否发送Cookie信息
    config.setAllowCredentials(true);
    //放行哪些原始域(请求方式)
    config.addAllowedMethod("*");
    //放行哪些原始域(头部信息)
    config.addAllowedHeader("*");

    //2.添加映射路径
    UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
    configSource.registerCorsConfiguration("/**", config);

    //3.返回新的CorsFilter.
    return new CorsFilter(configSource);
  }
}
