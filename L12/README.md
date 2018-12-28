### Практическая работа « Enterprise Java Beans »
###                          «JavaEE Security» по курсу JavaEE

L13: Security

- [x] Разработать стилизованную страницы входа в приложение с поддержкой FORM-метода аутентификации, используя стандартные механизмы, предлагаемые JavaEE Security.
- [x] создание класса, имплементирующего интерфейс ~LoginModule~ IdentityStore(JSR375), производящего авторизацию на основе данных, хранящихся на уровне БД (таблица пользователей – логин и хэш-пароля). 
- [x] После успешной авторизации пользователю должны быть выданы права на основании связанной таблицы ролей.
- [x] Добавить кнопку «Выход» на всех авторизованных страницах приложения (например, в разделе навигации).
- [x] разграничение ролевого доступа к бизнес-логике сервлетов, EJB и т.д. 
- [x] Необходимо иметь, как минимум две основных роли: 
  - [x] рядовой пользователь (создание заказов)
  - [x] +менеджер (просмотр заказов, создание продукта) 
  - [x] администратор (справочник ролей) 
- [x] Разработать интерфейсную часть для управлением справочником ролей с возможностью присвоения их пользователям системы. Данный функционал доступен только администраторам системы.
- [x] Разработать сервисы и интерфейсную часть с поддержкой ДФА в системе. В данном задании не требуется реальной отправки смс-сообщения пользователя, это значение достаточно генерировать случайно на сервере (опциональное задание)*.
  - [x] регистрация с флагом ДФА
  - [x] изменение флага из админки
  - [x] поле в БД
  - [x] бин по обслуживанию "кода"
  - [x] страница для ввода "кода"

- [x] Предусмотреть RESTful веб-сервисы, предоставляющие возможность программной авторизации пользователя в приложении, а также возможного выхода из него.
- [x] скорректировать rmi client на авторизацию под REMOTE ролью (см. Note в клиете)
    
L12: EJB    
    
- [x] разработать «простенькое» веб-приложение похожее на интернет магазин (EJB, CDI, JPA, JTA, JSF, Bean Validation, JPQL)
  - [x] с возможностью добавления товаров из некоторого каталога (достаточно предоставить ассортимент из >15 возможных товаров к покупке).
    - [x] товарная сущность
    - [x] ui: страница для наполнения товарного каталога
      - [x] форма для создания
        - [x] upload image
        - [x] валидация полей
      - [x] просмотр товара 
      - [x] список товаров 
  - [x] В качестве клиентских возможностей предусмотреть пополнение потребительской корзины 
    - [x] список товаров(каталог) с функцией добавить в корзину, предполагается бесконечное наличие на складе
    - [x] страница корзины 
      - [x] удаление товара
      - [x] оформление заказа 
        - [x] с последующей фиксацией выбранных категорий и общей цены заказа на уровне БД.
          - [x] сущность заказа
          - [x] страница со списком заказов   
          - [x] просмотр отдельного заказа
  - [x] отдельное приложение получающая по rmi последний заказ, и отображающее его в консоли на манер tail 
    - [x] JavaSE консольное, подключение по jndi, периодичность по sleep
    - [x] вызываемый метод разместить в синглтоне, конкурентность
            
#### Решение

REST auth requests (registered user=aaa, pass=aaa)
``` 
$ curl -X GET -H "Accept: application/json" http://pg-payara.docker:8080/L12/auth/login/aaa/aaa
logged in: SUCCESS as 'aaa'
$ curl -X GET -H "Accept: application/json" http://pg-payara.docker:8080/L12/auth/logout/aaa
logged out: no principal
$ curl -X GET -H "Accept: application/json" http://pg-payara.docker:8080/L12/auth/login/unknownuser/aaa
logged in: NOT_DONE
$ curl -X GET -H "Accept: application/json" http://pg-payara.docker:8080/L12/auth/login/aaa/unknownpass
logged in: NOT_DONE
$ curl -X GET -H "Accept: application/json" http://pg-payara.docker:8080/L12/auth/login/aaa/aaa
logged in: SUCCESS as 'aaa'
$ 

```

REST auth server logs
``` 
  14:22:13.579 [http-thread-pool::http-listener-1(3)] INFO  r.o.s.L12.appSecure.AuthRestService - REST login: aaa
  14:22:13.581 [http-thread-pool::http-listener-1(3)] INFO  r.o.s.L.a.AppFormAuthenticationMechanism - Credential {0}
  14:22:13.591 [http-thread-pool::http-listener-1(3)] INFO  r.o.s.L12.appSecure.AppIdentityStore - Validated login: aaa, [CUSTOMER, ADMIN, REMOTE]
  14:22:18.434 [http-thread-pool::http-listener-1(1)] INFO  r.o.s.L12.appSecure.AuthRestService - REST logout: aaa
  14:22:33.546 [http-thread-pool::http-listener-1(2)] INFO  r.o.s.L12.appSecure.AuthRestService - REST login: unknownuser
  14:22:33.549 [http-thread-pool::http-listener-1(2)] INFO  r.o.s.L.a.AppFormAuthenticationMechanism - Credential {0}
  ru.otus.sua.L12.appSecure.exception.InvalidUsernameException
  14:22:44.321 [http-thread-pool::http-listener-1(5)] INFO  r.o.s.L12.appSecure.AuthRestService - REST login: aaa
  14:22:44.324 [http-thread-pool::http-listener-1(5)] INFO  r.o.s.L.a.AppFormAuthenticationMechanism - Credential {0}
  JASPIC: http msg authentication fail
  14:22:58.313 [http-thread-pool::http-listener-1(1)] INFO  r.o.s.L12.appSecure.AuthRestService - REST login: aaa
  14:22:58.316 [http-thread-pool::http-listener-1(1)] INFO  r.o.s.L.a.AppFormAuthenticationMechanism - Credential {0}
  14:22:58.322 [http-thread-pool::http-listener-1(1)] INFO  r.o.s.L12.appSecure.AppIdentityStore - Validated login: aaa, [CUSTOMER, ADMIN, REMOTE]

```


RMI client with JAAS auth
``` 
дек 28, 2018 7:28:49 PM com.sun.enterprise.v3.server.CommonClassLoaderServiceImpl findDerbyClient
INFO: Cannot find javadb client jar file, derby jdbc driver will not be available by default.
19:28:56.071 [main] INFO  ru.otus.sua.L12client.RmiClient - none orders
19:29:50.346 [main] INFO  ru.otus.sua.L12client.RmiClient - id: 951; customer: assigned roles; address: REMOTE;ADMIN;CUSTOMER;); total summ: 10000.0
```


TFA (ДФА) login process
```
  11:32:12.048 [http-thread-pool::http-listener-1(5)] INFO  r.o.s.L.a.AppFormAuthenticationMechanism - Credential {0}
  11:32:12.072 [http-thread-pool::http-listener-1(5)] INFO  r.o.s.L12.appSecure.AppIdentityStore - Validated login: tre, [CUSTOMER, REMOTE, ADMIN]
  11:32:12.087 [http-thread-pool::http-listener-1(5)] INFO  r.o.s.L.a.presentation.LoginStatus - LoginStatus account: tre,  (assigned roles: REMOTE;ADMIN;CUSTOMER;)
  11:32:12.195 [http-thread-pool::http-listener-1(1)] INFO  ru.otus.sua.L12.ejbs.ImageBean - Get image from product id=1, size =3977 bytes.
  11:32:12.212 [http-thread-pool::http-listener-1(1)] INFO  ru.otus.sua.L12.ejbs.ImageBean - Get image from product id=703, size =9290 bytes.
  11:32:20.665 [http-thread-pool::http-listener-1(1)] INFO  r.o.s.L.a.p.LogoutController - Logout user tre.
  11:32:52.780 [http-thread-pool::http-listener-1(4)] INFO  r.o.s.L.a.ejbs.TfaGeneratorEJB - Security code for account 'aaa' is '3064'
  11:33:16.388 [http-thread-pool::http-listener-1(4)] INFO  r.o.s.L.a.p.LoginController - Entered correct security code '3064' for account 'aaa'
  11:33:16.399 [http-thread-pool::http-listener-1(4)] INFO  r.o.s.L.a.AppFormAuthenticationMechanism - Credential {0}
  11:33:16.408 [http-thread-pool::http-listener-1(4)] INFO  r.o.s.L12.appSecure.AppIdentityStore - Validated login: aaa, [CUSTOMER, ADMIN, REMOTE]
  11:33:16.421 [http-thread-pool::http-listener-1(4)] INFO  r.o.s.L.a.presentation.LoginStatus - LoginStatus account: aaa,  (assigned roles: REMOTE;ADMIN;CUSTOMER;)
  11:33:16.467 [http-thread-pool::http-listener-1(2)] INFO  ru.otus.sua.L12.ejbs.ImageBean - Get image from product id=1, size =3977 bytes.
  11:33:16.473 [http-thread-pool::http-listener-1(2)] INFO  ru.otus.sua.L12.ejbs.ImageBean - Get image from product id=703, size =9290 bytes.
  11:33:42.160 [http-thread-pool::http-listener-1(4)] INFO  r.o.s.L.a.p.LogoutController - Logout user aaa.
  11:33:49.254 [http-thread-pool::http-listener-1(2)] INFO  r.o.s.L.a.ejbs.TfaGeneratorEJB - Security code for account 'aaa' is '3491'
  11:34:16.515 [http-thread-pool::http-listener-1(4)] INFO  r.o.s.L.a.p.LoginController - Entered incorrect security code '1234' for account 'aaa'
```

no auth RMI client
```
дек 27, 2018 11:16:44 PM com.sun.enterprise.v3.server.CommonClassLoaderServiceImpl findDerbyClient
INFO: Cannot find javadb client jar file, derby jdbc driver will not be available by default.
Exception in thread "main" javax.naming.NamingException: Lookup failed for 'java:global/L12/OrderRemoteMonEJB!ru.otus.sua.L12.ejbs.OrderRemote' in SerialContext[myEnv={org.omg.CORBA.ORBInitialPort=3700, java.naming.factory.initial=com.sun.enterprise.naming.SerialInitContextFactory, org.omg.CORBA.ORBInitialHost=localhost, java.naming.factory.state=com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl, java.naming.factory.url.pkgs=com.sun.enterprise.naming} [Root exception is javax.naming.NamingException: ejb ref resolution error for remote business interfaceru.otus.sua.L12.ejbs.OrderRemote [Root exception is java.rmi.AccessException: CORBA NO_PERMISSION 0 No; nested exception is: 
	org.omg.CORBA.NO_PERMISSION: ----------BEGIN server-side stack trace----------
```

rmi client log
``` 
/usr/lib/jvm/java-8-oracle/bin/java -javaagent:/home/lsua/bin/idea-IU-182.4323.46/lib/idea_rt.jar=42527:/home/lsua/bin/idea-IU-182.4323.46/bin -Dfile.encoding=UTF-8 -classpath /usr/lib/jvm/java-8-oracle/jre/lib/charsets.jar:/usr/lib/jvm/java-8-oracle/jre/lib/deploy.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/cldrdata.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/jaccess.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/jfxrt.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/localedata.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/nashorn.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/sunec.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/java-8-oracle/jre/lib/ext/zipfs.jar:/usr/lib/jvm/java-8-oracle/jre/lib/javaws.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jce.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jfr.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jfxswt.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jsse.jar:/usr/lib/jvm/java-8-oracle/jre/lib/management-agent.jar:/usr/lib/jvm/java-8-oracle/jre/lib/plugin.jar:/usr/lib/jvm/java-8-oracle/jre/lib/resources.jar:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:/home/lsua/DEV/OTUS/java-ee/project/L12-client/target/classes:/home/lsua/.m2/repository/org/glassfish/main/appclient/client/gf-client/3.1.2.2/gf-client-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/appclient/client/gf-client-module/3.1.2.2/gf-client-module-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/hk2/hk2/1.1.15/hk2-1.1.15.jar:/home/lsua/.m2/repository/org/glassfish/hk2/hk2-core/1.1.15/hk2-core-1.1.15.jar:/home/lsua/.m2/repository/org/glassfish/hk2/class-model/1.1.15/class-model-1.1.15.jar:/home/lsua/.m2/repository/junit/junit/4.3.1/junit-4.3.1.jar:/home/lsua/.m2/repository/org/glassfish/hk2/config/1.1.15/config-1.1.15.jar:/home/lsua/.m2/repository/org/jvnet/tiger-types/1.2/tiger-types-1.2.jar:/home/lsua/.m2/repository/org/glassfish/hk2/external/bean-validator/1.1.15/bean-validator-1.1.15.jar:/home/lsua/.m2/repository/org/jboss/logging/jboss-logging/3.1.0.GA/jboss-logging-3.1.0.GA.jar:/home/lsua/.m2/repository/org/glassfish/hk2/auto-depends/1.1.15/auto-depends-1.1.15.jar:/home/lsua/.m2/repository/org/glassfish/main/common/common-util/3.1.2.2/common-util-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/external/j-interop-repackaged/3.1.2.2/j-interop-repackaged-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/ejb/ejb-container/3.1.2.2/ejb-container-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/ejb/javax.ejb/3.1.2.2/javax.ejb-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/security/security/3.1.2.2/security-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/security/ssl-impl/3.1.2.2/ssl-impl-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/security/javax.security.auth.message/3.1.2.2/javax.security.auth.message-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/security/javax.security.jacc/3.1.2.2/javax.security.jacc-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/security/jaspic.provider.framework/3.1.2.2/jaspic.provider.framework-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/external/ldapbp-repackaged/3.1.2.2/ldapbp-repackaged-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/external/libpam4j-repackaged/3.1.2.2/libpam4j-repackaged-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/core/kernel/3.1.2.2/kernel-3.1.2.2.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-framework/1.9.50/grizzly-framework-1.9.50.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-http/1.9.50/grizzly-http-1.9.50.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-rcm/1.9.50/grizzly-rcm-1.9.50.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-portunif/1.9.50/grizzly-portunif-1.9.50.jar:/home/lsua/.m2/repository/com/sun/pkg/pkg-client/1.122-56.2852/pkg-client-1.122-56.2852.jar:/home/lsua/.m2/repository/org/glassfish/main/flashlight/flashlight-framework/3.1.2.2/flashlight-framework-3.1.2.2.jar:/usr/lib/jvm/java-8-oracle/lib/tools.jar:/home/lsua/.m2/repository/org/glassfish/main/core/logging/3.1.2.2/logging-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/admin/monitoring-core/3.1.2.2/monitoring-core-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/admin/config-api/3.1.2.2/config-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/stats77/3.1.2.2/stats77-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/deployment/deployment-common/3.1.2.2/deployment-common-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/deployment/deployment-javaee-core/3.1.2.2/deployment-javaee-core-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/persistence/persistence-common/3.1.2.2/persistence-common-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/connectors/connectors-internal-api/3.1.2.2/connectors-internal-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/internal-api/3.1.2.2/internal-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/transaction/transaction-internal-api/3.1.2.2/transaction-internal-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/ejb/ejb-internal-api/3.1.2.2/ejb-internal-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/ejb/gf-ejb-connector/3.1.2.2/gf-ejb-connector-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/hk2/external/asm-all-repackaged/1.1.15/asm-all-repackaged-1.1.15.jar:/home/lsua/.m2/repository/org/glassfish/ha/ha-api/3.1.8/ha-api-3.1.8.jar:/home/lsua/.m2/repository/org/glassfish/main/cluster/gms-bootstrap/3.1.2.2/gms-bootstrap-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/container-common/3.1.2.2/container-common-3.1.2.2.jar:/home/lsua/.m2/repository/javax/servlet/javax.servlet-api/3.0.1/javax.servlet-api-3.0.1.jar:/home/lsua/.m2/repository/org/glassfish/main/admin/admin-util/3.1.2.2/admin-util-3.1.2.2.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-comet/1.9.50/grizzly-comet-1.9.50.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-websockets/1.9.50/grizzly-websockets-1.9.50.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-http-ajp/1.9.50/grizzly-http-ajp-1.9.50.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-config/1.9.50/grizzly-config-1.9.50.jar:/home/lsua/.m2/repository/com/sun/enterprise/hk2/1.0.25/hk2-1.0.25.jar:/home/lsua/.m2/repository/com/sun/enterprise/hk2-core/1.0.25/hk2-core-1.0.25.jar:/home/lsua/.m2/repository/com/sun/enterprise/config/1.0.25/config-1.0.25.jar:/home/lsua/.m2/repository/org/glassfish/bean-validator/3.0-JBoss-4.0.2_03/bean-validator-3.0-JBoss-4.0.2_03.jar:/home/lsua/.m2/repository/com/sun/enterprise/auto-depends/1.0.25/auto-depends-1.0.25.jar:/home/lsua/.m2/repository/com/sun/enterprise/config-types/1.0.25/config-types-1.0.25.jar:/home/lsua/.m2/repository/com/sun/enterprise/osgi-adapter/1.0.25/osgi-adapter-1.0.25.jar:/home/lsua/.m2/repository/org/glassfish/main/deployment/dol/3.1.2.2/dol-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/transaction/javax.transaction/3.1.2.2/javax.transaction-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/deployment/javax.enterprise.deploy/3.1.2.2/javax.enterprise.deploy-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/connectors/javax.resource/3.1.2.2/javax.resource-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/annotation-framework/3.1.2.2/annotation-framework-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/security/appclient.security/3.1.2.2/appclient.security-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/security/ejb.security/3.1.2.2/ejb.security-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/corba/glassfish-corba-csiv2-idl/3.1.0-b032/glassfish-corba-csiv2-idl-3.1.0-b032.jar:/home/lsua/.m2/repository/org/glassfish/main/common/glassfish-api/3.1.2.2/glassfish-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/scattered-archive-api/3.1.2.2/scattered-archive-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/hk2/config-types/1.1.15/config-types-1.1.15.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-utils/1.9.50/grizzly-utils-1.9.50.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-lzma/1.9.50/grizzly-lzma-1.9.50.jar:/home/lsua/.m2/repository/org/glassfish/external/management-api/3.1.0-b001/management-api-3.1.0-b001.jar:/home/lsua/.m2/repository/org/glassfish/main/security/webservices.security/3.1.2.2/webservices.security-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/web/web-glue/3.1.2.2/web-glue-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/web/web-cli/3.1.2.2/web-cli-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/amx-j2ee/3.1.2.2/amx-j2ee-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/amx-core/3.1.2.2/amx-core-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/admin/javax.management.j2ee/3.1.2.2/javax.management.j2ee-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/web/war-util/3.1.2.2/war-util-3.1.2.2.jar:/home/lsua/.m2/repository/javax/servlet/jsp/javax.servlet.jsp-api/2.2.1/javax.servlet.jsp-api-2.2.1.jar:/home/lsua/.m2/repository/javax/el/javax.el-api/2.2.4/javax.el-api-2.2.4.jar:/home/lsua/.m2/repository/org/glassfish/main/web/web-naming/3.1.2.2/web-naming-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/web/web-core/3.1.2.2/web-core-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/web/javax.servlet.jsp/2.2.5/javax.servlet.jsp-2.2.5.jar:/home/lsua/.m2/repository/org/glassfish/web/javax.el/2.2.3/javax.el-2.2.3.jar:/home/lsua/.m2/repository/org/glassfish/main/admin/admin-core/3.1.2.2/admin-core-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/web/web-gui-plugin-common/3.1.2.2/web-gui-plugin-common-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/web/web-embed/web-embed-api/3.1.2.2/web-embed-api-3.1.2.2.jar:/home/lsua/.m2/repository/javax/servlet/jsp/jstl/javax.servlet.jsp.jstl-api/1.2.1/javax.servlet.jsp.jstl-api-1.2.1.jar:/home/lsua/.m2/repository/org/glassfish/javax.faces/2.1.6/javax.faces-2.1.6.jar:/home/lsua/.m2/repository/org/glassfish/metro/webservices-osgi/2.2.0-1/webservices-osgi-2.2.0-1.jar:/home/lsua/.m2/repository/javax/xml/jaxrpc-api-osgi/1.1-b01/jaxrpc-api-osgi-1.1-b01.jar:/home/lsua/.m2/repository/javax/xml/jaxr-api-osgi/1.0-b01/jaxr-api-osgi-1.0-b01.jar:/home/lsua/.m2/repository/org/glassfish/metro/webservices-extra-jdk-packages/2.2.0-1/webservices-extra-jdk-packages-2.2.0-1.jar:/home/lsua/.m2/repository/org/codehaus/woodstox/woodstox-core-asl/4.1.2/woodstox-core-asl-4.1.2.jar:/home/lsua/.m2/repository/javax/xml/stream/stax-api/1.0-2/stax-api-1.0-2.jar:/home/lsua/.m2/repository/org/codehaus/woodstox/stax2-api/3.1.1/stax2-api-3.1.1.jar:/home/lsua/.m2/repository/org/jvnet/mimepull/mimepull/1.8/mimepull-1.8.jar:/home/lsua/.m2/repository/com/sun/xml/ws/jaxws-eclipselink-plugin/2.2.6-2/jaxws-eclipselink-plugin-2.2.6-2.jar:/home/lsua/.m2/repository/org/glassfish/main/security/websecurity/3.1.2.2/websecurity-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/webservices/jsr109-impl/3.1.2.2/jsr109-impl-3.1.2.2.jar:/home/lsua/.m2/repository/com/sun/grizzly/grizzly-http-servlet/1.9.50/grizzly-http-servlet-1.9.50.jar:/home/lsua/.m2/repository/org/glassfish/main/common/glassfish-naming/3.1.2.2/glassfish-naming-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/corba/glassfish-corba-omgapi/3.1.0-b032/glassfish-corba-omgapi-3.1.0-b032.jar:/home/lsua/.m2/repository/org/glassfish/corba/glassfish-corba-internal-api/3.1.0-b032/glassfish-corba-internal-api-3.1.0-b032.jar:/home/lsua/.m2/repository/org/glassfish/main/connectors/work-management/3.1.2.2/work-management-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/gmbal/gmbal/3.1.0-b001/gmbal-3.1.0-b001.jar:/home/lsua/.m2/repository/org/glassfish/main/connectors/connectors-inbound-runtime/3.1.2.2/connectors-inbound-runtime-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/connectors/connectors-runtime/3.1.2.2/connectors-runtime-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/glassfish-ee-api/3.1.2.2/glassfish-ee-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/orb/orb-connector/3.1.2.2/orb-connector-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/corba/glassfish-corba-orbgeneric/3.1.0-b032/glassfish-corba-orbgeneric-3.1.0-b032.jar:/home/lsua/.m2/repository/org/glassfish/corba/glassfish-corba-orb/3.1.0-b032/glassfish-corba-orb-3.1.0-b032.jar:/home/lsua/.m2/repository/org/glassfish/corba/glassfish-corba-newtimer/3.1.0-b032/glassfish-corba-newtimer-3.1.0-b032.jar:/home/lsua/.m2/repository/org/glassfish/corba/glassfish-corba-codegen/3.1.0-b032/glassfish-corba-codegen-3.1.0-b032.jar:/home/lsua/.m2/repository/org/glassfish/main/orb/orb-iiop/3.1.2.2/orb-iiop-3.1.2.2.jar:/home/lsua/.m2/repository/org/shoal/shoal-gms-api/1.6.21/shoal-gms-api-1.6.21.jar:/home/lsua/.m2/repository/org/glassfish/corba/glassfish-corba-asm/3.1.0-b032/glassfish-corba-asm-3.1.0-b032.jar:/home/lsua/.m2/repository/org/glassfish/main/core/glassfish/3.1.2.2/glassfish-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/common/simple-glassfish-api/3.1.2.2/simple-glassfish-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/appclient/client/acc-config/3.1.2.2/acc-config-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/transaction/jts/3.1.2.2/jts-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/transaction/jta/3.1.2.2/jta-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/jms/jms-core/3.1.2.2/jms-core-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/javaee-api/javax.jms/3.1.2.2/javax.jms-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/persistence/jpa-connector/3.1.2.2/jpa-connector-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/webservices/webservices-connector/3.1.2.2/webservices-connector-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/persistence/cmp/cmp-internal-api/3.1.2.2/cmp-internal-api-3.1.2.2.jar:/home/lsua/.m2/repository/org/glassfish/main/javaee-api/javax.annotation/3.1.2.2/javax.annotation-3.1.2.2.jar:/home/lsua/.m2/repository/javax/xml/bind/jaxb-api-osgi/2.2.6/jaxb-api-osgi-2.2.6.jar:/home/lsua/.m2/repository/com/sun/xml/bind/jaxb-osgi/2.2.5-2/jaxb-osgi-2.2.5-2.jar:/home/lsua/.m2/repository/org/glassfish/metro/webservices-api-osgi/2.2.0-1/webservices-api-osgi-2.2.0-1.jar:/home/lsua/.m2/repository/org/apache/derby/derby/10.14.2.0/derby-10.14.2.0.jar:/home/lsua/.m2/repository/com/sun/mail/javax.mail/1.6.0/javax.mail-1.6.0.jar:/home/lsua/.m2/repository/javax/activation/activation/1.1/activation-1.1.jar:/home/lsua/.m2/repository/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar:/home/lsua/.m2/repository/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar:/home/lsua/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/home/lsua/DEV/OTUS/java-ee/project/L12/target/classes:/home/lsua/.m2/repository/org/omnifaces/omnifaces/3.2/omnifaces-3.2.jar ru.otus.sua.L12client.RmiClient
дек 18, 2018 11:23:57 PM com.sun.enterprise.v3.server.CommonClassLoaderServiceImpl findDerbyClient
INFO: Cannot find javadb client jar file, derby jdbc driver will not be available by default.
дек 18, 2018 11:24:01 PM ru.otus.sua.L12client.RmiClient main
INFO: none orders
дек 18, 2018 11:24:46 PM ru.otus.sua.L12client.RmiClient main
INFO: id: 7; customer: Джек Синица; address: Томск-сити, Улица 1.; total summ: 751.0
дек 18, 2018 11:34:18 PM ru.otus.sua.L12client.RmiClient main
INFO: id: 51; customer: Джохн Смитх; address: Джакарта, Индия.; total summ: 9990.0
дек 18, 2018 11:40:16 PM ru.otus.sua.L12client.RmiClient main
INFO: id: 101; customer: Администрация города; address: Город Н-ск, Администрация.; total summ: 1000000.0
дек 18, 2018 11:41:52 PM ru.otus.sua.L12client.RmiClient main
INFO: id: 103; customer: Леопольд Катц; address: DIY Center, New-york, 45 42; total summ: 570.0
```

#### Материалы

RMI auth:
[IIOP Client Authentication and ProgrammaticLogin in Glassfish v3](https://stackoverflow.com/questions/3519676/iiop-client-authentication-and-programmaticlogin-in-glassfish-v3)
[Call a secured remote ejb deployed in glassfish from a Java client application](https://stackoverflow.com/questions/10370475/call-a-secured-remote-ejb-deployed-in-glassfish-from-a-java-client-application)