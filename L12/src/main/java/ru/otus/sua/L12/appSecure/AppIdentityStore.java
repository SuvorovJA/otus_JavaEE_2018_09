package ru.otus.sua.L12.appSecure;


import ru.otus.sua.L12.appSecure.entities.Account;
import ru.otus.sua.L12.appSecure.exception.AccountNotVerifiedException;
import ru.otus.sua.L12.appSecure.exception.InvalidCredentialException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;


@ApplicationScoped
@Default
public class AppIdentityStore implements IdentityStore {

    @Inject
    AccountStoreEJB accountStore;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        try {
            if (credential instanceof UsernamePasswordCredential) {
                String username = ((UsernamePasswordCredential) credential).getCaller();
                String password = ((UsernamePasswordCredential) credential).getPasswordAsString();
                return validate(this.accountStore.getByUsernameAndPassword(username, password));
            }
            if (credential instanceof CallerOnlyCredential) {
                String username = ((CallerOnlyCredential) credential).getCaller();
                return validate(this.accountStore.getByUsername(username).orElseThrow(InvalidCredentialException::new));
            }
        } catch (InvalidCredentialException e) {
            return INVALID_RESULT;
        }
        return NOT_VALIDATED_RESULT;
    }

    // before return the CredentialValidationResult, check if the account is active or not
    private CredentialValidationResult validate(Account account) {
        if (!account.isActive()) throw new AccountNotVerifiedException();
        return new CredentialValidationResult(account.getUsername(),account.getRoles());
    }
}
