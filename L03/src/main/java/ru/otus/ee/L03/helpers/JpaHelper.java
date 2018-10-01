package ru.otus.ee.L03.helpers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import ru.otus.ee.L03.entityes.AppointmentEntity;
import ru.otus.ee.L03.entityes.CredentialEntity;
import ru.otus.ee.L03.entityes.DepartmentEntity;
import ru.otus.ee.L03.entityes.EmployeEntity;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import java.io.FileReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class JpaHelper {

    public static String CreateAndSaveBigBoss(EntityManager em) {

        StringBuilder out = new StringBuilder();
        try {
            out.append("start ").append(getMethodName()).append(". <br>");
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
            out.append("_ hardcoded \'").append(employeEntity.getFullname()).append("\' create.<br>");
        } catch (Exception e) {
            em.getTransaction().rollback();
            out.append("_ rollback occured.<br>");
            //throw new ServletException(e);
        } finally {
            out.append("final ").append(getMethodName()).append(". <br>");
        }

        return out.toString();
    }

    // TODO ennoble need
    public static String LoadAndCreateDepartamentsFromCsvFile(EntityManager em, ServletContext context) throws MalformedURLException {
        StringBuilder out = new StringBuilder();
        String file = "departm.csv";

        out.append("start ").append(getMethodName()).append(". <br>");
        URL departmUrl = context.getResource("/WEB-INF/classes/" + file);
        try (Reader in = new FileReader(departmUrl.getFile())) {
            em.getTransaction().begin();
            out.append("_ read ").append(file).append(". <br>");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                DepartmentEntity departmentEntity = new DepartmentEntity();
                departmentEntity.setName(record.get(0));
                em.persist(departmentEntity);
            }
            em.getTransaction().commit();
        } catch (NullPointerException e) {
            out.append("_ not found ").append(file).append(". <br>");
        } catch (Exception e) {
            em.getTransaction().rollback();
            out.append("_ rollback occured.<br>");
            out.append("_ ").append(e.getMessage()).append("<br>");
        }
        out.append("final ").append(getMethodName()).append(". <br>");
        return out.toString();
    }

    // TODO ennoble need
    public static String LoadAndCreateAppointmentsFromCsvFile(EntityManager em, ServletContext context) throws MalformedURLException {
        StringBuilder out = new StringBuilder();
        String file = "appoint.csv";

        out.append("start ").append(getMethodName()).append(". <br>");
        URL departmUrl = context.getResource("/WEB-INF/classes/" + file);
        try (Reader in = new FileReader(departmUrl.getFile())) {
            em.getTransaction().begin();
            out.append("_ read ").append(file).append(". <br>");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                AppointmentEntity appointmentEntity = new AppointmentEntity();
                appointmentEntity.setName(record.get(0));
                em.persist(appointmentEntity);
            }
            em.getTransaction().commit();
        } catch (NullPointerException e) {
            out.append("_ not found ").append(file).append(". <br>");
        } catch (Exception e) {
            em.getTransaction().rollback();
            out.append("_ rollback occured.<br>");
            out.append("_ ").append(e.getMessage()).append("<br>");
        }
        out.append("final ").append(getMethodName()).append(". <br>");
        return out.toString();
    }

    // TODO ennoble need
    public static String LoadAndCreateEmployesFromCsvFile(EntityManager em, ServletContext context) throws MalformedURLException {
        StringBuilder out = new StringBuilder();
        String file = "employs.csv";

        out.append("start ").append(getMethodName()).append(". <br>");
        URL departmUrl = context.getResource("/WEB-INF/classes/" + file);
        try (Reader in = new FileReader(departmUrl.getFile())) {
            em.getTransaction().begin();
            out.append("_ read ").append(file).append(". <br>");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                EmployeEntity employeEntity = createEmployeEntity(
                        record.get("fullname"),
                        record.get("city"),
                        Long.parseLong(record.get("salary")),
                        record.get("login"),
                        record.get("passhash"),
                        getEntity(em, AppointmentEntity.class, record.get("appointment")),
                        getEntity(em, DepartmentEntity.class, record.get("departament"))
                );
                em.persist(employeEntity);
            }
            em.getTransaction().commit();
        } catch (NullPointerException e) {
            out.append("_ not found ").append(file).append(". <br>");
        } catch (Exception e) {
            em.getTransaction().rollback();
            out.append("_ rollback occured.<br>");
            out.append("_ ").append(e.getMessage()).append("<br>");
        }
        out.append("final ").append(getMethodName()).append(". <br>");
        return out.toString();
    }

    public static String PrintAllEmployes(EntityManager em) {
        return getMethodName() + "<br>";
    }

    public static String ModifyTwoRandomEmployeeByMovingToTopManagement(EntityManager em) {
        return getMethodName() + "<br>";
    }

    public static String RemoveThreeRandomEmployee(EntityManager em) {
        return getMethodName() + "<br>";
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

    /**
     * взять объект из базы по содержимому(@param value) не-id поля.
     * поле должно быть отмечено @NaturalId и @Column(nullable = false, unique = true)
     * Session.class => import org.hibernate.Session;
     *
     * @param em - EntityManager
     * @param tClass - тип Entity (T.class)
     * @param value - искомая строка
     * @return - объект Entity
     */
    private static <T> T getEntity(EntityManager em, Class<T> tClass, String value) {
        return em.unwrap(Session.class).bySimpleNaturalId(tClass).load(value);
    }

}
