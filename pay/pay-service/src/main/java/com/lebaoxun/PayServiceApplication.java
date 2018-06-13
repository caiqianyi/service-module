package com.lebaoxun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

//springboot启动时会自动注入数据源和配置jpa
//排除其注入
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PayServiceApplication{
	
	 public static void main(String[] args) {
         SpringApplication.run(PayServiceApplication.class, args);
     }
	 
}
 