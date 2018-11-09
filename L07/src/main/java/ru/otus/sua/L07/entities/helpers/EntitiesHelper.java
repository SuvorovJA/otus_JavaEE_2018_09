package ru.otus.sua.L07.entities.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.L07.entities.AppointmentEntity;
import ru.otus.sua.L07.entities.CredentialEntity;
import ru.otus.sua.L07.entities.DepartamentEntity;
import ru.otus.sua.L07.entities.EmployeEntity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Long.parseLong;

@SuppressWarnings("Duplicates")
public class EntitiesHelper {

    private static Logger log = LoggerFactory.getLogger(EntitiesHelper.class);

    public static AppointmentEntity createAppointmentEntity(String appointment) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setName(appointment);
        if (JpaDtoForAppointmentEntity.containsAppointmentEntityByName(entity.getName())) {
            log.info("Reuse AppointmentEntity: {}", entity.toString());
            entity = JpaDtoForAppointmentEntity.readAppointmentEntityByName(entity.getName());
        } else {
            log.info("Create AppointmentEntity: {}", entity.toString());
            JpaDtoForAppointmentEntity.saveAppointmentEntity(entity);
        }
        return entity;
    }

    public static DepartamentEntity createDepartmentEntity(String department) {
        DepartamentEntity entity = new DepartamentEntity();
        entity.setName(department);
        if (JpaDtoForDepartamentEntity.containsDepartmentEntityByName(entity.getName())) {
            log.info("Reuse DepartamentEntity: {}", entity.toString());
            entity = JpaDtoForDepartamentEntity.readDepartmentEntityByName(entity.getName());
        } else {
            log.info("Create DepartamentEntity: {}", entity.toString());
            JpaDtoForDepartamentEntity.saveDepartmentEntity(entity);
        }
        return entity;
    }

    @SuppressWarnings("Duplicates")
    public static EmployeEntity createEmployeEntity(
            String fullName,
            Date dateOfBirth,
            String city,
            long salary,
            String login,
            String passhash,
            AppointmentEntity appointmentEntity,
            DepartamentEntity departamentEntity) {

        CredentialEntity credentialEntity = new CredentialEntity();
        credentialEntity.setLogin(login);
        credentialEntity.setPasshash(passhash);

        EmployeEntity employeEntity = new EmployeEntity();
        employeEntity.setFullName(fullName);
        employeEntity.setDateOfBirth(dateOfBirth);
        employeEntity.setCity(city);
        employeEntity.setSalary(salary);
        employeEntity.setDepartament(departamentEntity);
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
    // TODO replace Date Stub
    public static EmployeEntity createEmployeEntityFromJsonWithIds(String json) {
        JsonReader jsonReader = Json.createReader(new StringReader(json));
        JsonObject jsonObject = jsonReader.readObject();

        CredentialEntity credentialEntity = new CredentialEntity();
        if (!jsonObject.getString("credentialsId").equals("")) {
            credentialEntity.setId(parseLong(jsonObject.getString("credentialsId")));
        }
        credentialEntity.setLogin(jsonObject.getString("login"));
        credentialEntity.setPasshash(jsonObject.getString("passhash"));

        EmployeEntity employeEntity = null;

        try {
            employeEntity = createEmployeEntity(
                    jsonObject.getString("fullName"),
                    new SimpleDateFormat("dd.MM.yyyy").parse("01.01.0001"),
                    jsonObject.getString("city"),
                    parseLong(jsonObject.getString("salary")),
                    jsonObject.getString("login"),
                    jsonObject.getString("passhash"),
                    createAppointmentEntity(jsonObject.getString("appointment")),
                    createDepartmentEntity(jsonObject.getString("department"))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        entityPersisted.setDepartament(entityExternal.getDepartament());
        entityPersisted.setCity(entityExternal.getCity());
        entityPersisted.setFullName(entityExternal.getFullName());
        entityPersisted.setDateOfBirth(entityExternal.getDateOfBirth());
        entityPersisted.setSalary(entityExternal.getSalary());

        log.info("After Copy obj state {}.hash={}",
                entityPersisted, entityPersisted.hashCode());
    }

}
