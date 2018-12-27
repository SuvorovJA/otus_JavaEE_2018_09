package ru.otus.sua.L12.appSecure.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "ACCOUNTS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "email"})
})
@NamedQueries({
        @NamedQuery(name = Account.FIND_ALL, query = "SELECT a FROM Account a"),
        @NamedQuery(name = Account.FIND_BY_USERNAME, query = "select a from Account a where a.username = :username"),
        @NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email"),
        @NamedQuery(name = Account.FIND_BY_TOKEN, query = "select a from Account a inner join a.tokens t where t.tokenHash = :tokenHash and t.tokenType = :tokenType and t.expiration > CURRENT_TIMESTAMP")
})
@Data
@NoArgsConstructor
public class Account {

    public static final String FIND_BY_USERNAME = "Account.findByUsername";
    public static final String FIND_BY_EMAIL = "Account.findByEmail";
    public static final String FIND_BY_TOKEN = "Account.findByToken";
    public static final String FIND_ALL = "Account.findAllAccounts";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Basic
    private String username;

    @Basic
    @NotNull
    private String password;

    @Basic
    @NotNull
    @Size(min = 1, max = 100)
    private String email;

    @Basic
    @Column(name = "active")
    @NotNull
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Account(String username, String password, String email, String rolename) {
        this.username = username;
        this.password = password;
        this.email = email;
        Role role = new Role();
        role.setRole(rolename);
        addRole(role);
    }

    public void addToken(Token token) {
        this.tokens.add(token);
        token.setAccount(this);
    }

    public void removeToken(Token token) {
        this.tokens.remove(token);
        token.setAccount(this);
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.setAccount(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.setAccount(this);
    }

    public Set<String> getRolesAsStrings(){
        return this.roles.stream().map(Role::getRole).collect(Collectors.toSet());
    }

    public Set<Role> getRoles(){
        return this.roles;
    }

    @Override
    public String toString() {
        return "Account: " + this.username;
    }
}
