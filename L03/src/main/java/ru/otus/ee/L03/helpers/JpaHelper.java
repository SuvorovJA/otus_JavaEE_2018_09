package ru.otus.ee.L03.helpers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import ru.otus.ee.L03.entityes.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import java.io.FileReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class JpaHelper {

    public static String createAndSaveBigBoss(EntityManager em) {
        return strategyExecutor(em, new CreateAndSaveBigBossStrategy());
    }

    public static String loadAndCreateDepartamentsFromCsvFile(EntityManager em, ServletContext context) throws MalformedURLException {
        return strategyExecutorForCsvLoader(em, context, "departm.csv", new LoadAndCreateDepartamentsFromCsvFileStrategy());
    }

    public static String loadAndCreateAppointmentsFromCsvFile(EntityManager em, ServletContext context) throws MalformedURLException {
        return strategyExecutorForCsvLoader(em, context, "appoint.csv", new LoadAndCreateAppointmentsFromCsvFileStrategy());
    }

    public static String loadAndCreateEmployesFromCsvFile(EntityManager em, ServletContext context) throws MalformedURLException {
        return strategyExecutorForCsvLoader(em, context, "employs.csv", new LoadAndCreateEmployesFromCsvFileStrategy());
    }

    public static String printAllEmployes(EntityManager em) {
        return strategyExecutor(em, new PrintAllEmployesStrategy());
    }

    public static String modifyTwoRandomEmployeeByMovingToTopManagement(EntityManager em) {
        return strategyExecutor(em, new ModifyTwoRandomEmployeeByMovingToTopManagementStrategy());
    }

    public static String removeThreeRandomEmployee(EntityManager em) {
        return strategyExecutor(em, new RemoveThreeRandomEmployeeStrategy());
    }

    public static Employes getAllEmployes(EntityManager em) throws SQLException {
        return strategyExecutorForGetter(em, new GetAllEmployesStrategyForGetter());
    }

    // **************************************************************************************

    private static String strategyExecutor(EntityManager em, Strategy strategy) {
        StringBuilder out = new StringBuilder();
        try {
            out.append("start ").append(strategy.getClass().getSimpleName()).append(". <br>");
            em.getTransaction().begin();
            strategy.invoke(em, out);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            out.append("_ rollback occured.<br>");
            out.append("_ ").append(e.getMessage()).append("<br>");
        } finally {
            out.append("final ").append(strategy.getClass().getSimpleName()).append(". <br>");
        }
        return out.toString();
    }

    private static Employes strategyExecutorForGetter(EntityManager em, StrategyForGetter strategy) throws SQLException {
        Employes list = new Employes();
        try {
            em.getTransaction().begin();
            list=strategy.invoke(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new SQLException(e);
        }
        return list;
    }

    private static String strategyExecutorForCsvLoader(EntityManager em, ServletContext context, String file, StrategyForCsvLoader strategy) throws MalformedURLException {
        StringBuilder out = new StringBuilder();
        out.append("start ").append(getMethodName()).append(". <br>");
        URL fileUrl = context.getResource("/WEB-INF/classes/" + file);
        try (Reader in = new FileReader(fileUrl.getFile())) {
            em.getTransaction().begin();
            out.append("_ read ").append(file).append(". <br>");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            strategy.invoke(em, records);
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
     * @param em     - EntityManager
     * @param tClass - тип Entity (T.class)
     * @param value  - искомая строка
     * @return - объект Entity
     */
    private static <T> T getEntity(EntityManager em, Class<T> tClass, String value) {
        return em.unwrap(Session.class).bySimpleNaturalId(tClass).load(value);
    }

    // **************************************************************************************


    private interface Strategy {
        void invoke(EntityManager em, StringBuilder out);
    }

    private interface StrategyForGetter {
        Employes invoke(EntityManager em);
    }

    private interface StrategyForCsvLoader {
        void invoke(EntityManager em, Iterable<CSVRecord> records);
    }

    private static class PrintAllEmployesStrategy implements Strategy {
        @Override
        public void invoke(EntityManager em, StringBuilder out) {
            Query q = em.createQuery("select e from EmployeEntity e order by e.id desc");
            List<EmployeEntity> result = q.getResultList();
            result.stream().forEach(r -> out.append(r).append("<br>"));
        }
    }

    private static class ModifyTwoRandomEmployeeByMovingToTopManagementStrategy implements Strategy {
        @Override
        public void invoke(EntityManager em, StringBuilder out) {
            Query q = em.createQuery("select e from EmployeEntity e order by e.fullname desc");
            List<EmployeEntity> result = q.getResultList();

            EmployeEntity ee1 = result.get(5);
            ee1.setDepartment(getEntity(em, DepartmentEntity.class, "TOP MANAGEMENT"));
            out.append(ee1.toString()).append(". <br>");
            em.persist(ee1);

            EmployeEntity ee2 = result.get(2);
            ee2.setDepartment(getEntity(em, DepartmentEntity.class, "TOP MANAGEMENT"));
            out.append(ee2.toString()).append(". <br>");
            em.persist(ee2);
        }
    }

    private static class RemoveThreeRandomEmployeeStrategy implements Strategy {
        @Override
        public void invoke(EntityManager em, StringBuilder out) {
            Query q = em.createQuery("select e from EmployeEntity e order by e.salary desc");
            List<EmployeEntity> result = q.getResultList();
            out.append("removed ").append(result.get(1).getFullname()).append("<br>");
            out.append("removed ").append(result.get(3).getFullname()).append("<br>");
            out.append("removed ").append(result.get(6).getFullname()).append("<br>");
            em.remove(result.get(1));
            em.remove(result.get(3));
            em.remove(result.get(6));
        }
    }

    private static class CreateAndSaveBigBossStrategy implements Strategy {
        @Override
        public void invoke(EntityManager em, StringBuilder out) {
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
        }
    }

    private static class GetAllEmployesStrategyForGetter implements StrategyForGetter {
        @Override
        public Employes invoke(EntityManager em) {
            Query q = em.createQuery("select e from EmployeEntity e order by e.id asc");
            Employes result = new Employes();
            result.setEmployes(q.getResultList());
            return result;
        }
    }

    private static class LoadAndCreateEmployesFromCsvFileStrategy implements StrategyForCsvLoader {
        @Override
        public void invoke(EntityManager em, Iterable<CSVRecord> records) {
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
        }
    }

    private static class LoadAndCreateAppointmentsFromCsvFileStrategy implements StrategyForCsvLoader {
        @Override
        public void invoke(EntityManager em, Iterable<CSVRecord> records) {
            for (CSVRecord record : records) {
                AppointmentEntity appointmentEntity = new AppointmentEntity();
                appointmentEntity.setName(record.get("appointment"));
                em.persist(appointmentEntity);
            }
        }
    }

    private static class LoadAndCreateDepartamentsFromCsvFileStrategy implements StrategyForCsvLoader {
        @Override
        public void invoke(EntityManager em, Iterable<CSVRecord> records) {
            for (CSVRecord record : records) {
                DepartmentEntity departmentEntity = new DepartmentEntity();
                departmentEntity.setName(record.get("departament"));
                em.persist(departmentEntity);
            }
        }
    }


//    private static class TemplateStrategy implements Strategy {
//        @Override
//        public void invoke(EntityManager em, StringBuilder out) {
//
//        }
//    }

//    private static class TemplateStrategy implements StrategyForCsvLoader {
//        @Override
//        public void invoke(EntityManager em, Iterable<CSVRecord> records) {
//
//        }
//    }

}
