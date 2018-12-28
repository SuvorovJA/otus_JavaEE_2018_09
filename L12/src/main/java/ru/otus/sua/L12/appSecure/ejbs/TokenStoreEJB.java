package ru.otus.sua.L12.appSecure.ejbs;

import ru.otus.sua.L12.appSecure.entities.Account;
import ru.otus.sua.L12.appSecure.entities.Token;
import ru.otus.sua.L12.appSecure.entities.TokenType;
import ru.otus.sua.L12.appSecure.exception.InvalidUsernameException;
import ru.otus.sua.L12.appSecure.hashing.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.UUID.randomUUID;

@Stateless
@Named
public class TokenStoreEJB {

    @PersistenceContext
    EntityManager em;

    @Inject
    @HashServiceType(HashType.SHA)
    @Sha(algorithm = AlgorithmSHA.SHA256)
    HashGenerator hash;

    @Inject
    AccountStoreEJB accountStore;

    public String generate(final String username,
                           final String ipAddress,
                           final String description,
                           final TokenType tokenType) {
        String rawToken = randomUUID().toString();
        Instant expiration = now().plus(14, DAYS);
        save(rawToken, username, ipAddress, description, tokenType, Date.from(expiration));
        return rawToken;
    }

    public void save(final String rawToken,
                     final String username,
                     final String ipAddress,
                     final String description,
                     final TokenType tokenType,
                     final Date expiration) {

        Account account = this.accountStore.getByUsername(username).orElseThrow(InvalidUsernameException::new);
        Token token = new Token();
        token.setTokenHash(this.hash.getHashedText(rawToken));
        token.setExpiration(expiration);
        token.setDescription(description);
        token.setTokenType(tokenType);
        token.setIpAddress(ipAddress);
        account.addToken(token);
        this.em.merge(account);
    }

    public void remove(String token) {
        this.em.createNamedQuery(Token.REMOVE_TOKEN, Token.class).setParameter("tokenHash", token).executeUpdate();
    }

    public void removeExpired() {
        this.em.createNamedQuery(Token.REMOVE_EXPIRED_TOKEN, Token.class).executeUpdate();
    }

    public List<Token> findAllTokens() {
        TypedQuery<Token> query = em.createNamedQuery("Token.findAllTokens", Token.class);
        return query.getResultList();
    }
}
