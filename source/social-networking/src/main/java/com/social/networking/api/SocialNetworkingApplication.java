package com.social.networking.api;

import com.social.networking.api.model.audit.AuditorAwareImpl;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
public class SocialNetworkingApplication {
    /**
     * This method is used to get the current auditor of the application.
     *
     * @return AuditorAware<String>
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    /**
     * This method is used to set the default timezone of the application.
     */
    @PostConstruct
    public void init() {
        // It will set Asia/Ho_Chi_Minh timezone
        String timezone = "Asia/Ho_Chi_Minh";
        TimeZone.setDefault(TimeZone.getTimeZone(timezone));
        // It will print Asia/Ho_Chi_Minh timezone
        System.out.println("Spring boot application running in " + timezone + " timezone : " + new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkingApplication.class, args);
    }

    /**
     * RabbitMQ configuration
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
