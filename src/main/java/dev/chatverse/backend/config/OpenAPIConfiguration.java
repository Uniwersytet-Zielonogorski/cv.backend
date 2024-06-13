package dev.chatverse.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("ChatVerse Docs");

        Contact myContact = new Contact();
        myContact.setName("University of Zielona Góra");
        myContact.setUrl("https://github.com/Uniwersytet-Zielonogorski");
        myContact.setUrl("https://cv.bykowski.dev");

        Info information = new Info()
                .title("ChatVerse API")
                .version("0.0.1")
                .description("ChatVerse API Documentation")
                .contact(myContact);

        return new OpenAPI().info(information).servers(List.of(server));
    }
}
