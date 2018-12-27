package ru.otus.sua.L12.appSecure;

import ru.otus.sua.L12.appSecure.entities.Account;
import ru.otus.sua.L12.appSecure.entities.AppRole;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Named
public class AppRoleStoreEJB implements Serializable {

    @PersistenceContext
    EntityManager em;

    public void addAppRole(final String rolename) {
        AppRole role = new AppRole();
        role.setRole(rolename);
        this.em.persist(role);
    }

    public List<AppRole> findAllAppRoles() {
        TypedQuery<AppRole> query = em.createNamedQuery("AppRole.findAllAppRoles", AppRole.class);
        return query.getResultList();
    }

    public List<String> findAllAppRolesAsStrings() {
        return findAllAppRoles().stream().map(AppRole::getRole).collect(Collectors.toList());
    }

}
