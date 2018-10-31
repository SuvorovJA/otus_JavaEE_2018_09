### Практическая работа «Популярные JS-фреймворки» по курсу JavaEE.

- [x] JPA-подсистема, запуск на сконфигурированном tomcat из L03,L04. 
  - [x] создание в БД записи Admin при старте приложения, через ServletContextListener
  - [x] logback|slf4j serverside logger (debug to file, info to stdout)
  - [ ] ~~Для запуска JPA на "GWT Jetty 9.2" нужно готовить WEB-INF/jetty-*.xml для jndi записи для jdbc/PostgresDS.~~
  - [x] Tomcat deployment добавить в maven

- [x] Имеющуюся структуру приложения(L05) следует адаптировать под использование фреймворка GWT, 
  - [x] создав необходимые client/shared/server-пакеты.

- [x] UIBinder. Содержимое главной страницы необходимо переключать, воспользовавшись возможностями DeckPanel.
  - [x] Sample-приложение на DeckPanel
  - [x] Sample-приложение на UiBinder
  - [x] Html skeleton  
  
- [x] Для коммуникации с сервером следует воспользоваться GWT-RPC, переработав механизм авторизации: проверка согласно данным таблицы ~~Employee~~ Credentials (логин и пароль/хэш-пароля хранятся в этой таблице).

- [x] При взаимодействия с новостным сервлетом следует воспользоваться возможностями JsonpRequestBuilder.

- [x] При работе с новостным сервисом ЦБ(currency?) следует воспользоваться клиентскими возможностями работы с XML, предусмотренных в GWT.

- [x] Дополнительно к имеющейся функциональности следует добавить форму, доступную после авторизации в системе, в которой выводится информация о всех зарегистрированных сотрудников. 
  - [x] Для отображения информации необходимо создать сервлет, возвращающий все значимые поля о сотруднике (на усмотрение слушателя) в виде JSON-ответа 
    - [x] JSON readonly servlet 
  - [x] для отображения на клиентской стороне подойдет DataGrid. 
    - [x] DataGrid Widget
  - [x] CRUD: возможность создания, редактирования и удаления информации о сотрудниках (на текущем этапе необязательно учитывать права авторизованного пользователя).
    - [x] CRUD RPC Service, трансфер json строкой (не shared entity obj)
    - [x] widget для редактирования записи сотрудника

опционально
- [ ] ~~Интернационализация EN RU~~
- [ ] ~~Валидация через @GwtValidation~~
- [ ] ~~WrongCredentialException~~
- [ ] ~~Pефакторинг спагетти, DI(gin(?)), styles, JPA, ... да в общем перепроектирование~~


#### Решение

``` 
Лог приложения со старта на операциях CRUD к скриншоту 9


[2018-11-01 01:48:24,915] Artifact L06:war exploded2: Artifact is being deployed, please wait...
01:48:26.267 [RMI TCP Connection(10)-127.0.0.1] INFO  r.o.s.l.AppServletContextListener - Create admin account on startup application.
01:48:26.457 [RMI TCP Connection(10)-127.0.0.1] INFO  org.hibernate.Version - HHH000412: Hibernate Core {5.3.6.Final}
01:48:26.458 [RMI TCP Connection(10)-127.0.0.1] INFO  org.hibernate.cfg.Environment - HHH000206: hibernate.properties not found
01:48:26.628 [RMI TCP Connection(10)-127.0.0.1] INFO  o.h.annotations.common.Version - HCANN000001: Hibernate Commons Annotations {5.0.4.Final}
01:48:26.823 [RMI TCP Connection(10)-127.0.0.1] INFO  org.hibernate.dialect.Dialect - HHH000400: Using dialect: org.hibernate.dialect.PostgreSQL94Dialect
01:48:26.865 [RMI TCP Connection(10)-127.0.0.1] INFO  o.h.e.j.e.i.LobCreatorBuilderImpl - HHH000424: Disabling contextual LOB creation as createClob() method threw error : java.lang.reflect.InvocationTargetException
01:48:26.872 [RMI TCP Connection(10)-127.0.0.1] INFO  org.hibernate.type.BasicTypeRegistry - HHH000270: Type registration [java.util.UUID] overrides previous : org.hibernate.type.UUIDBinaryType@14794750
01:48:28.255 [RMI TCP Connection(10)-127.0.0.1] INFO  o.h.t.s.internal.SchemaCreatorImpl - HHH000476: Executing import script 'org.hibernate.tool.schema.internal.exec.ScriptSourceInputNonExistentImpl@1651a72'
01:48:28.360 [RMI TCP Connection(10)-127.0.0.1] INFO  o.h.h.i.QueryTranslatorFactoryInitiator - HHH000397: Using ASTQueryTranslatorFactory
01:48:28.467 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.JpaHelper6 - No result "SysAdmin" in containsAppointmentEntityByName
01:48:28.467 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create AppointmentEntity: { id:0; name:SysAdmin}
01:48:28.511 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.JpaHelper6 - No result "IT Dept." in containsDepartmentEntityByName
01:48:28.511 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create DepartmentEntity: { id:0; name:IT Dept.}
01:48:28.520 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create EmployeEntity obj: { id:0; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:2; name:IT Dept.}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:0; login:admin; passhash:*}}
01:48:28.537 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.JpaHelper6 - Created in DB { id:3; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:2; name:IT Dept.}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}.hash=3
01:48:28.537 [RMI TCP Connection(10)-127.0.0.1] INFO  r.o.s.l.AppServletContextListener - Create user account on startup application.
01:48:28.538 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.JpaHelper6 - No result "User" in containsAppointmentEntityByName
01:48:28.538 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create AppointmentEntity: { id:0; name:User}
01:48:28.555 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.JpaHelper6 - No result "Users" in containsDepartmentEntityByName
01:48:28.555 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create DepartmentEntity: { id:0; name:Users}
01:48:28.570 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create EmployeEntity obj: { id:0; fullname:USER; city:TOMSK; salary:1000; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:0; login:user; passhash:*}}
01:48:28.587 [RMI TCP Connection(10)-127.0.0.1] INFO  ru.otus.sua.helpers.JpaHelper6 - Created in DB { id:7; fullname:USER; city:TOMSK; salary:1000; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}.hash=7
[2018-11-01 01:48:29,104] Artifact L06:war exploded2: Artifact is deployed successfully
[2018-11-01 01:48:29,104] Artifact L06:war exploded2: Deploy took 4 189 milliseconds

изменение зарплаты у USER
01:49:01.509 [http-nio-8080-exec-19] INFO  ru.otus.sua.server.CrudServiceImpl - Edit obj: {"employeId":"7", "departmentId":"6", "appointmentId":"5", "credentialsId":"8", "fullName":"USER", "city":"TOMSK", "department":"Users", "appointment":"User", "salary":"9999", "login":"user", "passhash":"user"}
01:49:01.524 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Reuse AppointmentEntity: { id:0; name:User}
01:49:01.526 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Reuse DepartmentEntity: { id:0; name:Users}
01:49:01.528 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create EmployeEntity obj: { id:0; fullname:USER; city:TOMSK; salary:9999; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:0; login:user; passhash:*}}
01:49:01.528 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.EntytiesHelper6 - From json create EmployeEntity obj: { id:7; fullname:USER; city:TOMSK; salary:9999; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}
01:49:01.532 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.JpaHelper6 - readEmployeById(7): { id:7; fullname:USER; city:TOMSK; salary:1000; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}
01:49:01.533 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Start Copy obj from { id:7; fullname:USER; city:TOMSK; salary:9999; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}.hash=7 to obj { id:7; fullname:USER; city:TOMSK; salary:1000; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}.hash=7
01:49:01.533 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.EntytiesHelper6 - After Copy obj state { id:7; fullname:USER; city:TOMSK; salary:9999; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}.hash=7
01:49:01.533 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.JpaHelper6 - Refreshing in DB { id:7; fullname:USER; city:TOMSK; salary:9999; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}.hash=7
01:49:01.644 [http-nio-8080-exec-19] INFO  ru.otus.sua.helpers.JpaHelper6 - Refreshed in DB { id:7; fullname:USER; city:TOMSK; salary:9999; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}.hash=7

изменение департамента у ADMIN:
01:49:33.726 [http-nio-8080-exec-21] INFO  ru.otus.sua.server.CrudServiceImpl - Edit obj: {"employeId":"3", "departmentId":"2", "appointmentId":"1", "credentialsId":"4", "fullName":"ADMIN", "city":"TOMSK", "department":"Производство", "appointment":"SysAdmin", "salary":"2000", "login":"admin", "passhash":"admin"}
01:49:33.732 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Reuse AppointmentEntity: { id:0; name:SysAdmin}
01:49:33.734 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.JpaHelper6 - No result "Производство" in containsDepartmentEntityByName
01:49:33.734 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create DepartmentEntity: { id:0; name:Производство}
01:49:33.835 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create EmployeEntity obj: { id:0; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:9; name:Производство}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:0; login:admin; passhash:*}}
01:49:33.835 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - From json create EmployeEntity obj: { id:3; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:9; name:Производство}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}
01:49:33.837 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.JpaHelper6 - readEmployeById(3): { id:3; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:2; name:IT Dept.}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}
01:49:33.838 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Start Copy obj from { id:3; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:9; name:Производство}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}.hash=3 to obj { id:3; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:2; name:IT Dept.}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}.hash=3
01:49:33.838 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - After Copy obj state { id:3; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:9; name:Производство}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}.hash=3
01:49:33.838 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.JpaHelper6 - Refreshing in DB { id:3; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:9; name:Производство}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}.hash=3
01:49:33.851 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.JpaHelper6 - Refreshed in DB { id:3; fullname:ADMIN; city:TOMSK; salary:2000; department:{ id:9; name:Производство}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}.hash=3

создание работника first
01:50:24.890 [http-nio-8080-exec-23] INFO  ru.otus.sua.server.CrudServiceImpl - Create obj: {"employeId":"", "departmentId":"", "appointmentId":"", "credentialsId":"", "fullName":"first", "city":"Омск", "department":"Бухгалтерия", "appointment":"Рабочий", "salary":"1234", "login":"qwerty", "passhash":"qwerty"}
01:50:24.897 [http-nio-8080-exec-23] INFO  ru.otus.sua.helpers.JpaHelper6 - No result "Рабочий" in containsAppointmentEntityByName
01:50:24.897 [http-nio-8080-exec-23] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create AppointmentEntity: { id:0; name:Рабочий}
01:50:24.943 [http-nio-8080-exec-23] INFO  ru.otus.sua.helpers.JpaHelper6 - No result "Бухгалтерия" in containsDepartmentEntityByName
01:50:24.943 [http-nio-8080-exec-23] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create DepartmentEntity: { id:0; name:Бухгалтерия}
01:50:25.033 [http-nio-8080-exec-23] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create EmployeEntity obj: { id:0; fullname:first; city:Омск; salary:1234; department:{ id:11; name:Бухгалтерия}; appointment:{ id:10; name:Рабочий}; credentials:{ id:0; login:qwerty; passhash:*}}
01:50:25.033 [http-nio-8080-exec-23] INFO  ru.otus.sua.helpers.EntytiesHelper6 - From json create EmployeEntity obj: { id:0; fullname:first; city:Омск; salary:1234; department:{ id:11; name:Бухгалтерия}; appointment:{ id:10; name:Рабочий}; credentials:{ id:0; login:qwerty; passhash:*}}
01:50:25.049 [http-nio-8080-exec-23] INFO  ru.otus.sua.helpers.JpaHelper6 - Created in DB { id:12; fullname:first; city:Омск; salary:1234; department:{ id:11; name:Бухгалтерия}; appointment:{ id:10; name:Рабочий}; credentials:{ id:13; login:qwerty; passhash:*}}.hash=12

создание работника second
01:51:08.192 [http-nio-8080-exec-21] INFO  ru.otus.sua.server.CrudServiceImpl - Create obj: {"employeId":"", "departmentId":"", "appointmentId":"", "credentialsId":"", "fullName":"second", "city":"Омск", "department":"Бухгалтерия", "appointment":"Рабочий", "salary":"2345", "login":"asdfgh", "passhash":"asdfgh"}
01:51:08.200 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Reuse AppointmentEntity: { id:0; name:Рабочий}
01:51:08.203 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Reuse DepartmentEntity: { id:0; name:Бухгалтерия}
01:51:08.204 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create EmployeEntity obj: { id:0; fullname:second; city:Омск; salary:2345; department:{ id:11; name:Бухгалтерия}; appointment:{ id:10; name:Рабочий}; credentials:{ id:0; login:asdfgh; passhash:*}}
01:51:08.204 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.EntytiesHelper6 - From json create EmployeEntity obj: { id:0; fullname:second; city:Омск; salary:2345; department:{ id:11; name:Бухгалтерия}; appointment:{ id:10; name:Рабочий}; credentials:{ id:0; login:asdfgh; passhash:*}}
01:51:08.298 [http-nio-8080-exec-21] INFO  ru.otus.sua.helpers.JpaHelper6 - Created in DB { id:14; fullname:second; city:Омск; salary:2345; department:{ id:11; name:Бухгалтерия}; appointment:{ id:10; name:Рабочий}; credentials:{ id:15; login:asdfgh; passhash:*}}.hash=14

удаление работника USER
01:51:16.819 [http-nio-8080-exec-27] INFO  ru.otus.sua.server.CrudServiceImpl - Delete obj: {"employeId":"7", "departmentId":"6", "appointmentId":"5", "credentialsId":"8", "fullName":"USER", "city":"TOMSK", "department":"Users", "appointment":"User", "salary":"9999", "login":"user", "passhash":"user"}
01:51:16.880 [http-nio-8080-exec-27] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Reuse AppointmentEntity: { id:0; name:User}
01:51:16.884 [http-nio-8080-exec-27] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Reuse DepartmentEntity: { id:0; name:Users}
01:51:16.885 [http-nio-8080-exec-27] INFO  ru.otus.sua.helpers.EntytiesHelper6 - Create EmployeEntity obj: { id:0; fullname:USER; city:TOMSK; salary:9999; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:0; login:user; passhash:*}}
01:51:16.885 [http-nio-8080-exec-27] INFO  ru.otus.sua.helpers.EntytiesHelper6 - From json create EmployeEntity obj: { id:7; fullname:USER; city:TOMSK; salary:9999; department:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:user; passhash:*}}

далее на скриншоте  9 перелогин как first
```

#### Материалы

1) примеры панелей 

    https://examples.javacodegeeks.com/enterprise-java/gwt/gwt-panel-example/
    https://www.tutorialspoint.com/gwt/gwt_layout_panels.htm

2) вообще о

    https://www.javacodegeeks.com/wp-content/uploads/2016/09/GWT-Programming-Cookbook.pdf
    
3) JPA. detached objects

    https://xebia.com/blog/jpa-implementation-patterns-saving-detached-entities/
    
    