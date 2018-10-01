##### Практическая работа «Работа с базами данных» по курсу JavaEE.


1. :ballot_box_with_check: проектирование.

    - :ballot_box_with_check: составить UML-диаграмму классов, включающую основные сущности изучаемой предметной области 

    диаграмма `jcd.uml`, `jcd.png` сгенерирована по полученной из JPA базе

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
        
        файл скрипта `ddl.sql` сгенерирован из idea, по базе созданной JPA.    
 
3. :ballot_box_with_check: подготовка Application уровня.

    - :ballot_box_with_check: создать Maven проект веб-приложения (на базе maven-archetype-webapp)
 
    - :ballot_box_with_check: соединяться с созданной ранее БД и выполнять DML-операции  или осуществлять отображение таблиц БД (JPA)

4. :ballot_box_with_check: наполнение таблиц.

    - :ballot_box_with_check: Заполнить данными имеющиеся таблицы произвольными статичными данными или данными из внешнего файла (csv, txt, xml). Достаточно десятка записей.

    генерация на https://www.mockaroo.com/
    
    загрузка csv файлов

5. :ballot_box_with_check: поиск, модификация и удаление данных.

    - :ballot_box_with_check: вывести информацию с сотрудниками, отсортированную по ИД сотрудника по убыванию.**
  
    - :ballot_box_with_check: произвести изменение 2 строк данной таблицы, подменив фамилию сотрудника, а также его должность (на данном этапе не обязательно брать во внимание предусмотренные в системе роли: рядовой сотрудник, Бухгалтер, HR-специалист, директор).**
  
    - :ballot_box_with_check: Удалить произвольные 3 строки из данной таблицы.**

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
   _ read departm.csv.
   final LoadAndCreateDepartamentsFromCsvFile().
   start LoadAndCreateAppointmentsFromCsvFile().
   _ read appoint.csv.
   final LoadAndCreateAppointmentsFromCsvFile().
   start LoadAndCreateEmployesFromCsvFile().
   _ read employs.csv.
   final LoadAndCreateEmployesFromCsvFile().
   start PrintAllEmployes().
   { id:34; fullname:Graeme Fishbourne; city:Ngamba; salary:3337; department:{ id:8; name:Training}; appointment:{ id:10; name:Engineer}; credentials:{ id:35; login:Chariot; passhash:*}}
   { id:32; fullname:Vina Titcombe; city:Sokol?niki; salary:6568; department:{ id:9; name:Support}; appointment:{ id:11; name:Accountant}; credentials:{ id:33; login:Yukon XL 1500; passhash:*}}
   { id:30; fullname:Gisella Magee; city:Mendefera; salary:8204; department:{ id:8; name:Training}; appointment:{ id:12; name:Manager}; credentials:{ id:31; login:Z4; passhash:*}}
   { id:28; fullname:Violette Gathercole; city:?ibenik; salary:4416; department:{ id:5; name:Sales}; appointment:{ id:13; name:Developer}; credentials:{ id:29; login:Legend; passhash:*}}
   { id:26; fullname:Tomasine Beevis; city:Firminópolis; salary:9589; department:{ id:5; name:Sales}; appointment:{ id:14; name:Professor}; credentials:{ id:27; login:Quest; passhash:*}}
   { id:24; fullname:Don Fryd; city:Kista; salary:8740; department:{ id:7; name:Services}; appointment:{ id:10; name:Engineer}; credentials:{ id:25; login:Ranger; passhash:*}}
   { id:22; fullname:Leora Crotty; city:Tân Hi?p; salary:9278; department:{ id:7; name:Services}; appointment:{ id:15; name:Administrator}; credentials:{ id:23; login:Dakota Club; passhash:*}}
   { id:20; fullname:Launce Rookeby; city:Xiacang; salary:5233; department:{ id:6; name:Marketing}; appointment:{ id:10; name:Engineer}; credentials:{ id:21; login:300; passhash:*}}
   { id:18; fullname:Normie Lutas; city:Yongfeng; salary:4622; department:{ id:7; name:Services}; appointment:{ id:11; name:Accountant}; credentials:{ id:19; login:Eldorado; passhash:*}}
   { id:16; fullname:Ray Riggey; city:Banag; salary:8084; department:{ id:5; name:Sales}; appointment:{ id:10; name:Engineer}; credentials:{ id:17; login:Ram 3500; passhash:*}}
   { id:4; fullname:Big B. Boss; city:TOMSK; salary:100000; department:{ id:1; name:TOP MANAGEMENT}; appointment:{ id:2; name:BOSS}; credentials:{ id:3; login:boss; passhash:*}}
   final PrintAllEmployes().
   start ModifyTwoRandomEmployeeByMovingToTopManagement().
   { id:22; fullname:Leora Crotty; city:Tân Hi?p; salary:9278; department:{ id:1; name:TOP MANAGEMENT}; appointment:{ id:15; name:Administrator}; credentials:{ id:23; login:Dakota Club; passhash:*}}.
   { id:26; fullname:Tomasine Beevis; city:Firminópolis; salary:9589; department:{ id:1; name:TOP MANAGEMENT}; appointment:{ id:14; name:Professor}; credentials:{ id:27; login:Quest; passhash:*}}.
   final ModifyTwoRandomEmployeeByMovingToTopManagement().
   start RemoveThreeRandomEmployee().
   removed Tomasine Beevis
   removed Don Fryd
   removed Vina Titcombe
   final RemoveThreeRandomEmployee().
   start PrintAllEmployes().
   { id:34; fullname:Graeme Fishbourne; city:Ngamba; salary:3337; department:{ id:8; name:Training}; appointment:{ id:10; name:Engineer}; credentials:{ id:35; login:Chariot; passhash:*}}
   { id:30; fullname:Gisella Magee; city:Mendefera; salary:8204; department:{ id:8; name:Training}; appointment:{ id:12; name:Manager}; credentials:{ id:31; login:Z4; passhash:*}}
   { id:28; fullname:Violette Gathercole; city:?ibenik; salary:4416; department:{ id:5; name:Sales}; appointment:{ id:13; name:Developer}; credentials:{ id:29; login:Legend; passhash:*}}
   { id:22; fullname:Leora Crotty; city:Tân Hi?p; salary:9278; department:{ id:1; name:TOP MANAGEMENT}; appointment:{ id:15; name:Administrator}; credentials:{ id:23; login:Dakota Club; passhash:*}}
   { id:20; fullname:Launce Rookeby; city:Xiacang; salary:5233; department:{ id:6; name:Marketing}; appointment:{ id:10; name:Engineer}; credentials:{ id:21; login:300; passhash:*}}
   { id:18; fullname:Normie Lutas; city:Yongfeng; salary:4622; department:{ id:7; name:Services}; appointment:{ id:11; name:Accountant}; credentials:{ id:19; login:Eldorado; passhash:*}}
   { id:16; fullname:Ray Riggey; city:Banag; salary:8084; department:{ id:5; name:Sales}; appointment:{ id:10; name:Engineer}; credentials:{ id:17; login:Ram 3500; passhash:*}}
   { id:4; fullname:Big B. Boss; city:TOMSK; salary:100000; department:{ id:1; name:TOP MANAGEMENT}; appointment:{ id:2; name:BOSS}; credentials:{ id:3; login:boss; passhash:*}}
   final PrintAllEmployes().
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
