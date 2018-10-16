package ru.otus.sua.L05.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(prefix = "")
public class User {

    @NotNull
    @Size(max = 8, message = "Login must contain at max 8 characters.")
    @Size(min = 3, message = "Login must contain at least 3 characters.")
    private String login;

    @NotNull
    @Size(max = 8, message = "Password must contain at max 8 characters.")
    @Size(min = 3, message = "Password must contain at least 3 characters.")
    private String password;
}
