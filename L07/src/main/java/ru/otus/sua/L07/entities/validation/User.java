package ru.otus.sua.L07.entities.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// TODO refacto to replace this by CredentialEntity

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(prefix = "")
public class User {

    @NotNull
    @Size(max = 8, min = 3)
    private String login;

    @NotNull
    @Size(max = 8, min = 3)
    private String password;
}
