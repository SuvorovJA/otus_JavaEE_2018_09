package ru.otus.sua.L12.appSecure;

import ru.otus.sua.L12.appSecure.entities.Account;
import ru.otus.sua.L12.appSecure.entities.TokenType;
import ru.otus.sua.L12.appSecure.exception.InvalidPasswordException;
import ru.otus.sua.L12.appSecure.exception.InvalidUsernameException;
import ru.otus.sua.L12.appSecure.hashing.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static org.omnifaces.util.Messages.addGlobalError;

@Stateless
@Named
public class AccountStoreEJB {

    @PersistenceContext
    EntityManager em;

    @Inject
    @HashServiceType(HashType.SHA)
    @Sha(algorithm = AlgorithmSHA.SHA256)
    HashGenerator tokenHash;

    @Inject
    @HashServiceType(HashType.SHA)
    @Sha(algorithm = AlgorithmSHA.SHA512)
    HashGenerator passwordHash;

    @Inject
    TokenStoreEJB tokenStoreEJB;

    public void registerAccount(final String username, final String email, final String password) {
        String securedPassword = this.passwordHash.getHashedText(password);
        Account account = new Account(username, securedPassword, email);
        // TODO Account should not activated by default. But email activation not implemented.
        account.setActive(true);
        this.em.persist(account);
    }

    public Optional<Account> getByUsername(final String username) {
        try {
            return Optional.of(this.em.createNamedQuery(Account.FIND_BY_USERNAME, Account.class)
                    .setParameter("username", username).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Account> getByEmail(final String email) {
        try {
            return Optional.of(this.em.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
                    .setParameter("email", email).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Account> getByLoginToken(String loginToken, TokenType tokenType) {
        try {
            return Optional.of(this.em.createNamedQuery(Account.FIND_BY_TOKEN, Account.class)
                    .setParameter("tokenHash", this.tokenHash.getHashedText(loginToken))
                    .setParameter("tokenType", tokenType)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Account getByUsernameAndPassword(String username, String password) {
        Account managedAccount = getByUsername(username).orElseThrow(InvalidUsernameException::new);
        if (!this.passwordHash.isHashedTextMatch(password, managedAccount.getPassword())) {
            addGlobalError("Invalid Password");
            throw new InvalidPasswordException();
        }
        return managedAccount;
    }

    public List<Account> findAllAccounts() {
        TypedQuery<Account> query = em.createNamedQuery("Account.findAllAccounts", Account.class);
        return query.getResultList();
    }
}
