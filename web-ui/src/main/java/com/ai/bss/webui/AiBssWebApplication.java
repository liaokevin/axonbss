package com.ai.bss.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.ai.bss.mutitanent.TenantContext;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages={"com.ai.bss.query"})
@SpringBootApplication(scanBasePackages={"com.ai.bss"})
@EntityScan({"com.ai.bss"})
@EnableDiscoveryClient
public class AiBssWebApplication {
	
	public static void main(String[] args) throws Exception {
		TenantContext.setTenant("asiainfo-nj");
		SpringApplication.run(AiBssWebApplication.class, args);
	}
	
	/**
     * LoadBalanced 注解表明restTemplate使用LoadBalancerClient执行请求
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template.getRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return template;
    }

}