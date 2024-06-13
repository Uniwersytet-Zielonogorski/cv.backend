package dev.chatverse.backend.documents.User;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public User(User user) {
        this.id = user.getId();
        this.givenName = user.getGivenName();
        this.familyName = user.getFamilyName();
        this.picture = user.getPicture();
        this.locale = user.getLocale();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.messageCount = user.getMessageCount();
        this.toxicPercentage = user.getToxicPercentage();
        this.isBanned = user.getIsBanned();
        this.banDate = user.getBanDate();
        this.toxicMessages = user.getToxicMessages();
    }

    @Id
    @Nonnull
    private String id;

    @Nonnull
    private String givenName;

    @Nonnull
    private String familyName;

    private String picture;

    private String locale;

    @Nonnull
    private String userName;

    @Nonnull
    @Email
    private String email;

    @Nonnull
    private Set<Role> roles;

    @Nonnull
    private Integer messageCount = 0;

    @Nonnull
    private Float toxicPercentage = 0.0f;

    @Nonnull
    private Boolean isBanned = false;

    private String banDate;

    private List<String> toxicMessages = new ArrayList<>();

}