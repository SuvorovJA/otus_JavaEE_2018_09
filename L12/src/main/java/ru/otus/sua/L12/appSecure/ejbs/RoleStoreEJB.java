package ru.otus.sua.L12.appSecure.ejbs;

import ru.otus.sua.L12.appSecure.entities.Role;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@Named
public class RoleStoreEJB {

    @PersistenceContext
    EntityManager em;

    public void addRole(final String rolename) {
        Role role = new Role();
        role.setRole(rolename);
        this.em.persist(role);
    }


    public List<Role> findAllRoles() {
        TypedQuery<Role> query = em.createNamedQuery("Role.findAllRoles", Role.class);
        return query.getResultList();
    }
}
