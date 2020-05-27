package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import service.DataInitializer;

@SpringBootApplication
@EnableTransactionManagement
public class Main {
    public static void main(String[] args) {
        DataInitializer.clear();
        DataInitializer.run();
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return new MyTomcatWebServerCustomizer();
    }

    private static class MyTomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
        @Override
        public void customize(TomcatServletWebServerFactory factory) {
            factory.addConnectorCustomizers(connector -> {
                connector.setProperty("relaxedQueryChars", "<>[\\]^`{}|");
                connector.setProperty("relaxedPathChars", "<>[\\]^`{}|");
            });
        }
    }
}
