##### Практическая работа «Работа с базами данных» по курсу JavaEE.


1. :black_square_button: проектирование.

    - :black_square_button: составить UML-диаграмму классов, включающую основные сущности изучаемой предметной области 

2. :ballot_box_with_check: подготовка схемы БД.

    - :ballot_box_with_check: выбрать любую понравившуюся СУБД, развернуть ее на локальном окружении
 
        PostgreSQL  
   
    - :ballot_box_with_check: создать в ней новую схему и/или базу данных.
 
        таблица сотрудников должна содержать поля: 
            ИД сотрудника (автоинкрементальное), 
            ФИО, 
            подразделение(или внешний ключ на сущность департамента), 
            город, 
            должность (или внешний ключ на сущность должность), 
            зарплата, 
            логин, 
            пароль/хэш (или внешний ключ на сущность оператора).
   
        > также можно автоматизировать Application уровне используя DDL (создание базы, схемы, таблиц схемы) – JDBC, или генерируя таблицы фреймворком Hibernate.
        
        используется генерация таблиц через Hibernate.
 
3. :ballot_box_with_check: подготовка Application уровня.

    - :ballot_box_with_check: создать Maven проект веб-приложения (на базе maven-archetype-webapp)
 
    - :ballot_box_with_check: соединяться с созданной ранее БД и выполнять DML-операции  или осуществлять отображение таблиц БД (JPA)

4. :ballot_box_with_check: наполнение таблиц.

    - :ballot_box_with_check: Заполнить данными имеющиеся таблицы произвольными статичными данными или данными из внешнего файла (csv, txt, xml). Достаточно десятка записей.

    генерация на https://www.mockaroo.com/
    
    загрузка csv файлов

5. :black_square_button: поиск, модификация и удаление данных.

    - :black_square_button: вывести информацию с сотрудниками, отсортированную по ИД сотрудника по убыванию.**
  
    - :black_square_button: произвести изменение 2 строк данной таблицы, подменив фамилию сотрудника, а также его должность (на данном этапе не обязательно брать во внимание предусмотренные в системе роли: рядовой сотрудник, Бухгалтер, HR-специалист, директор).**
  
    - :black_square_button: Удалить произвольные 3 строки из данной таблицы.**

6. :black_square_button: работа с хранимыми процедурами.

    - :black_square_button: Создать хранимую функцию (средствами JDBC или непосредственно в БД), возвращающую
   фамилия сотрудника, имеющего наибольшую зарплату. 
   
    - :black_square_button: Произвести вызов это процедуры из приложения и вернуть** результат

** вывод в консоль или в ответ сервлета или во внешний файл

#### Решение 

Выполнение 4, 5, 6, последовательно при обращении по http://localhost:8080/L03/first 
Вывод происходит в браузер.

```
start servlet.
start CreateAndSaveBigBoss().
_ hardcoded 'Big B. Boss' create.
final CreateAndSaveBigBoss().
start LoadAndCreateDepartamentsFromCsvFile().
_ read departm.csv
final LoadAndCreateDepartamentsFromCsvFile().
start LoadAndCreateAppointmentsFromCsvFile().
_ read appoint.csv
final LoadAndCreateAppointmentsFromCsvFile().
start LoadAndCreateEmployesFromCsvFile().
_ read employs.csv
final LoadAndCreateEmployesFromCsvFile().
PrintAllEmployes()
ModifyTwoRandomEmployeeByMovingToTopManagement()
PrintAllEmployes()
RemoveThreeRandomEmployee()
PrintAllEmployes()
*** PL/SQL WAS HERE ***
final servlet.
```

##### Tomcat config

`$ cp ~/.m2/repository/org/postgresql/postgresql/42.2.5/postgresql-42.2.5.jar ~/bin/tomcat-9.0.12/lib/`

add to server.xml 
```
<GlobalNamingResources>
    ...
        <!--precreated database tutorial in PostgreSQL-->
        <Resource name="jdbc/PostgresDS" auth="Container" type="javax.sql.DataSource"
                  driverClassName="org.postgresql.Driver" url="jdbc:postgresql://localhost/tutorial"
                  username="postgres" password="postgres"/>
</GlobalNamingResources>
```
add to context.xml 
```
<Context>
    ...
     <ResourceLink name="jdbc/PostgresDS" global="jdbc/PostgresDS" type="javax.sql.DataSource"/>
    ...
</Context>
```

##### Problems

`WARN  org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator.initiateService HHH000342: Could not obtain connection to query metadata : Not supported by BasicDataSource`
`INFO  org.hibernate.engine.jdbc.env.internal.LobCreatorBuilderImpl.makeLobCreatorBuilder HHH000422: Disabling contextual LOB creation as connection was null`
`javax.persistence.PersistenceException: [PersistenceUnit: JPAPersistenceUnit] Unable to build Hibernate SessionFactory`
`java.lang.UnsupportedOperationException: Not supported by BasicDataSource`

https://wiki.apache.org/tomcat/TomcatHibernate
закомментировано 5 строк в persistence.xml


`INFO  org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory.getObjectInstance Name = PostgresDS Ignoring unknown property: value of "DB Connection" for "description" property`

закомментировано "description" в web.xml

`INFO  org.hibernate.engine.jdbc.env.internal.LobCreatorBuilderImpl.useContextualLobCreation HHH000424: Disabling contextual LOB creation as createClob() method threw error : java.lang.reflect.InvocationTargetException`

?

```
...entityes/CredentialEntity.java
Error:(10, 16) Cannot resolve table 'credentials'
...entityes/EmployeEntity.java
Error:(10, 16) Cannot resolve table 'employes'
Error:(27, 25) <statement> expected, got 'depart_id'
Error:(27, 25) Cannot resolve column 'depart_id'
Error:(27, 61) Cannot resolve column 'depart_id'
Error:(31, 25) <statement> expected, got 'appointment_id'
Error:(31, 25) Cannot resolve column 'appointment_id'
Error:(31, 66) Cannot resolve column 'appointment_id'
Error:(35, 25) <statement> expected, got 'credentials_id'
Error:(35, 25) Cannot resolve column 'credentials_id'
...entityes/DepartmentEntity.java
Error:(11, 16) Cannot resolve table 'departs'
Error:(16, 21) Cannot resolve column 'depart_id'
...entityes/AppointmentEntity.java
Error:(12, 16) Cannot resolve table 'appointments'
Error:(17, 21) Cannot resolve column 'appointment_id'
```

что оно хочет?

сборка и запуск при этом работают.
