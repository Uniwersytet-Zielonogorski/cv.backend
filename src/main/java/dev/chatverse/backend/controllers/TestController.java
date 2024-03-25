package dev.chatverse.backend.controllers;

import dev.chatverse.backend.documents.User.User;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.chatverse.backend.services.UserService;

@RestController
public class TestController {

    private UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    String hello() {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = oAuth2User.getAttribute("email");
        User user = userService.loadUserByEmail(email);
        String pictureUrl = user.getPicture();
        System.out.println(pictureUrl);
        String familyName = user.getFamilyName();

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <img src=\"" + pictureUrl + "\" alt=\"User Picture\"/>\n" +
                "    <h1>" + familyName + "</h1>\n" +
                "</body>\n" +

                "</html>";
    }
}