package ru.otus.ee.L03.helpers;

import ru.otus.ee.L03.entityes.AppointmentEntity;
import ru.otus.ee.L03.entityes.CredentialEntity;
import ru.otus.ee.L03.entityes.DepartmentEntity;
import ru.otus.ee.L03.entityes.EmployeEntity;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;

public class JpaHelper {

    public static String CreateAndSaveBigBoss(EntityManager em) throws ServletException {

        StringBuilder out = new StringBuilder();
        try {
            out.append("start " + getMethodName() + ". <br>");
            em.getTransaction().begin();

            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setName("BOSS");

            DepartmentEntity departmentEntity = new DepartmentEntity();
            departmentEntity.setName("TOP MANAGEMENT");

            EmployeEntity employeEntity = createEmployeEntity(
                    "Big B. Boss",
                    "TOMSK",
                    100000L,
                    "boss",
                    "0xABCD1234",
                    appointmentEntity,
                    departmentEntity
            );

            em.persist(departmentEntity);
            em.persist(appointmentEntity);
            em.persist(employeEntity.getCredentials());
            em.persist(employeEntity);

            // em.flush(); // transaction.commit will flush?
            em.getTransaction().commit();
            out.append("_ \'" + employeEntity.getFullname() + "\' created.<br>");
        } catch (Exception e) {
            em.getTransaction().rollback();
            out.append("_ rollback occured.<br>");
            //throw new ServletException(e);
        } finally {
            out.append("final " + getMethodName() + ". <br>");
        }

        return out.toString();
    }

    public static String LoadAndCreateDepartamentsFromCsvFile(EntityManager em) {
        return getMethodName()+"<br>";
    }

    public static String LoadAndCreateAppointmentsFromCsvFile(EntityManager em) {
        return getMethodName()+"<br>";
    }

    public static String LoadAndCreateEmployesFromCsvFile(EntityManager em) {
        return getMethodName()+"<br>";
    }

    public static String PrintAllEmployes(EntityManager em) {
        return getMethodName()+"<br>";
    }

    public static String ModifyTwoRandomEmployeeByMovingToTopManagement(EntityManager em) {
        return getMethodName()+"<br>";
    }

    public static String RemoveThreeRandomEmployee(EntityManager em) {
        return getMethodName()+"<br>";
    }


    // **************************************************************************************

    private static EmployeEntity createEmployeEntity(
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





    private static String getMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return stackTraceElements[2].getMethodName() + "()";
    }

}
