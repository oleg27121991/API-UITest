package by.veremei.api.models.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record SuccessUserLogin(String userId,
                               String username,
                               String password,
                               String token,
                               String expires,
                               @JsonProperty("created_date") Date createdDate,
                               Boolean isActive) {
}
