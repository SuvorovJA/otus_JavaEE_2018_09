package ru.otus.sua.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.entityes.AppointmentEntity;
import ru.otus.sua.entityes.CredentialEntity;
import ru.otus.sua.entityes.DepartmentEntity;
import ru.otus.sua.entityes.EmployeEntity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static java.lang.Long.parseLong;
import static ru.otus.sua.helpers.JpaHelper6.*;

public class EntytiesHelper6 {

    private static Logger log = LoggerFactory.getLogger(EntytiesHelper6.class);

    public static AppointmentEntity createAppointmentEntity(String appointment) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setName(appointment);
        if (containsAppointmentEntityByName(entity.getName())) {
            log.info("Reuse AppointmentEntity: {}", entity.toString());
            entity = readAppointmentEntityByName(entity.getName());
        } else {
            log.info("Create AppointmentEntity: {}", entity.toString());
            saveAppointmentEntity(entity);
        }
        return entity;
    }

    public static DepartmentEntity createDepartmentEntity(String department) {
        DepartmentEntity entity = new DepartmentEntity();
        entity.setName(department);
        if (containsDepartmentEntityByName(entity.getName())) {
            log.info("Reuse DepartmentEntity: {}", entity.toString());
            entity = readDepartmentEntityByName(entity.getName());
        } else {
            log.info("Create DepartmentEntity: {}", entity.toString());
            saveDepartmentEntity(entity);
        }
        return entity;
    }

    @SuppressWarnings("Duplicates")
    public static EmployeEntity createEmployeEntity(
            String fullName,
            String city,
            long salary,
            String login,
            String passhash,
            AppointmentEntity appointmentEntity,
            DepartmentEntity departmentEntity) {

        CredentialEntity credentialEntity = new CredentialEntity();
        credentialEntity.setLogin(login);
        credentialEntity.setPasshash(passhash);

        EmployeEntity employeEntity = new EmployeEntity();
        employeEntity.setFullname(fullName);
        employeEntity.setCity(city);
        employeEntity.setSalary(salary);
        employeEntity.setDepartment(departmentEntity);
        employeEntity.setAppointment(appointmentEntity);
        employeEntity.setCredentials(credentialEntity);

        credentialEntity.setEmploye(employeEntity);

        log.info("Create EmployeEntity obj: {}", employeEntity);

        return employeEntity;
    }


    // WANTED json EXAMPLE
    // {"employeId":"7",          "departmentId":"6",
    // "appointmentId":"5",       "credentialsId":"8",
    // "fullName":"USER",       "city":"TOMSK",
    // "department":"Users",    "appointment":"User",
    // "salary":"1000",         "login":"user",
    // "passhash":"user"}
    public static EmployeEntity createEmployeEntityFromJsonWithIds(String json) {
        JsonReader jsonReader = Json.createReader(new StringReader(json));
        JsonObject jsonObject = jsonReader.readObject();

        CredentialEntity credentialEntity = new CredentialEntity();
        if (!jsonObject.getString("credentialsId").equals("")) {
            credentialEntity.setId(parseLong(jsonObject.getString("credentialsId")));
        }
        credentialEntity.setLogin(jsonObject.getString("login"));
        credentialEntity.setPasshash(jsonObject.getString("passhash"));

        EmployeEntity employeEntity = createEmployeEntity(
                jsonObject.getString("fullName"),
                jsonObject.getString("city"),
                parseLong(jsonObject.getString("salary")),
                jsonObject.getString("login"),
                jsonObject.getString("passhash"),
                createAppointmentEntity(jsonObject.getString("appointment")),
                createDepartmentEntity(jsonObject.getString("department"))
        );
        if (!jsonObject.getString("employeId").equals("")) {
            employeEntity.setId(parseLong(jsonObject.getString("employeId")));
        }
        employeEntity.setCredentials(credentialEntity);
        credentialEntity.setEmploye(employeEntity);

        log.info("From json create EmployeEntity obj: {}", employeEntity);

        return employeEntity;
    }

    public static void cloneEmployeEntity(EmployeEntity entityExternal, EmployeEntity entityPersisted) {
        log.info("Start Copy obj from {}.hash={} to obj {}.hash={}",
                entityExternal, entityExternal.hashCode(),
                entityPersisted, entityPersisted.hashCode());

        entityPersisted.setCredentials(entityExternal.getCredentials());
        entityPersisted.setAppointment(entityExternal.getAppointment());
        entityPersisted.setDepartment(entityExternal.getDepartment());
        entityPersisted.setCity(entityExternal.getCity());
        entityPersisted.setFullname(entityExternal.getFullname());
        entityPersisted.setSalary(entityExternal.getSalary());

        log.info("After Copy obj state {}.hash={}",
                entityPersisted, entityPersisted.hashCode());
    }

}
