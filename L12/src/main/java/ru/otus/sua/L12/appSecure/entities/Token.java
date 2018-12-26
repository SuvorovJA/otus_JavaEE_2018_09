package ru.otus.sua.L12.appSecure.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.MONTHS;

@Entity
@Table(name = "TOKENS", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"token_hash"})
})
@NamedQueries({@NamedQuery(name = Token.REMOVE_FIND_ALL, query = "SELECT t FROM Token t"),
        @NamedQuery(name = Token.REMOVE_TOKEN, query = "delete from Token t where t.tokenHash = :tokenHash"),
        @NamedQuery(name = Token.REMOVE_EXPIRED_TOKEN, query = "delete from Token t where t.expiration < CURRENT_TIMESTAMP")
})
@Data
public class Token {

    public static final String REMOVE_FIND_ALL = "Token.findAllTokens";
    public static final String REMOVE_TOKEN = "Token.removeToken";
    public static final String REMOVE_EXPIRED_TOKEN = "Token.removeExpiredToken";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Basic
    @NotNull
    @Column(name = "token_hash")
    private String tokenHash;

    @Basic
    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Basic
    @Column(name = "ip_address")
    @Size(min = 1, max = 45)
    private String ipAddress;

    @Basic
    private String description;

    @Basic
//    @Temporal(value = TemporalType.TIMESTAMP)
    private Instant created;

    @Basic
//    @Temporal(value = TemporalType.TIMESTAMP)
    private Instant expiration;

    @PrePersist
    public void generateInformation() {
        this.created = Instant.now();
        if (this.expiration == null) {
            this.expiration = this.created.plus(1, MONTHS);
        }
    }

    @Override
    public String toString() {
        return "Token{ id " + id + '}';
    }
}
