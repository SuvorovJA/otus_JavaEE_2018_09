package ru.otus.sua.helpers;

import ru.otus.sua.entityes.AppointmentEntity;
import ru.otus.sua.entityes.CredentialEntity;
import ru.otus.sua.entityes.DepartmentEntity;
import ru.otus.sua.entityes.EmployeEntity;

import static ru.otus.sua.helpers.JpaHelper6.saveAppointment;
import static ru.otus.sua.helpers.JpaHelper6.saveDepartment;

public class EntytiesHelper6 {

    public static AppointmentEntity createAppointmentEntity(String appointment){
        AppointmentEntity entity = new AppointmentEntity();
        entity.setName(appointment);
        saveAppointment(entity);
        return entity;
    }

    public static DepartmentEntity createDepartmentEntity(String department){
        DepartmentEntity entity = new DepartmentEntity();
        entity.setName(department);
        saveDepartment(entity);
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

        return employeEntity;
    }

}
