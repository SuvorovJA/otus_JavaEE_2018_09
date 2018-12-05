### Практическая работа «JAX-WS» по курсу JavaEE

- [x] 1. Создание серверной части сервиса расчета согласно некоторому алгоритму.
- [x] 2. Создание клиентской части для возможности обращения к функциональности сервиса.
- [x] 3. Интеграция со сторонними сервисами.

- [x]  Создать SOAP-сервис, задачей которого является расчет некоторой характеристики согласно заранее предопределенному алгоритму. В качестве примера предлагается создание калькулятора расчета налога на прибыль организации:
Нп = (До - Ро) * Нс /100, где
До – доходы (от реализации и внереализационные) нарастающим итогом с начала года;
Ро – расходы (связанные с производством и реализацией и внереализационные);
Нс - налоговая ставка отчетного периода (год);
Нп – размер налога на прибыль за отчетный период;
Пример расчёта налога на прибыль организации:
Д = 1000000 руб; Р = 200000 руб; Нс = 20%
Нп = (1000000 – 200000)*20/100 = 160000

Для данного сервиса разработать возможность обращения, 
- [x] используя standalone java-клиент, (as integration test EstimateIncomeTaxTestIT)
- [x] а также используя возможности сервлетов. (module L10-jaxws-webclient)

- [ ]  ~Разработать SOAP-сервисы, предоставив функциональность вычисления средней/максимальной зарплаты сотрудников в организации (из предыдущих домашних работ) для внешнего доступа.~

- [x] Выбрать любые ~два~ понравившихся SOAP-веб сервиса из [каталога](https://www.programmableweb.com/category/russian/apis?keyword=soap) и 
  - [x] разработать для них соответствующие клиенты для возможности обращения к их функциональности.
  - [x] Вызываемый сервис необходимо «обернуть» в RESTful сервис с возможностью обращения с некоторой страницы разрабатываемого приложения.
- [ ] ~В качестве альтернативного задания предлагается разработать сервисную прослойку для создания RESTful-сервисов, используя возможности JAX-WS (@WebServiceProvider). В качестве целевого сервиса рекомендуется взять Yandex.Geocoder.~


#### Решение


L10-jaxws-yandexspeller  REST WRAPPER
``` 
[#|2018-12-05T20:54:09.985+0000|INFO|Payara 5.183|javax.enterprise.system.tools.deployment.autodeploy|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043249985;_LevelValue=800;_MessageID=NCLS-DEPLOYMENT-02027;|
  Selecting file /opt/payara5/glassfish/domains/domain1/autodeploy/L10-jaxws-yandexspeller_war.war for autodeployment|#]

[#|2018-12-05T20:54:10.916+0000|INFO|Payara 5.183|javax.enterprise.webservices|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043250916;_LevelValue=800;_MessageID=AS-WSJSR109IMPL-00018;|
  Webservice Endpoint deployed ru.otus.sua.L10.YandexSpellerServiceAsRESTSimulator
 listening at address at http://b28d2e63012d:8080/L10-jaxws-yandexspeller_war/YandexSpellerServiceAsRESTSimulator.|#]

[#|2018-12-05T20:54:10.943+0000|INFO|Payara 5.183|javax.enterprise.ejb.container|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043250943;_LevelValue=800;_MessageID=AS-EJB-00054;|
  Portable JNDI names for EJB SpellServiceAction: [java:global/L10-jaxws-yandexspeller_war/SpellServiceAction!ru.otus.sua.L10.SpellServiceAction, java:global/L10-jaxws-yandexspeller_war/SpellServiceAction]|#]

[#|2018-12-05T20:54:11.240+0000|INFO|Payara 5.183|fish.payara.micro.cdi.extension.ClusteredCDIEventBusImpl|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043251240;_LevelValue=800;|
  Clustered CDI Event bus initialized|#]

[#|2018-12-05T20:54:11.281+0000|INFO|Payara 5.183|org.glassfish.soteria.servlet.SamRegistrationInstaller|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043251281;_LevelValue=800;|
  Initializing Soteria 1.1-b01 for context '/L10-jaxws-yandexspeller_war'|#]

[#|2018-12-05T20:54:11.287+0000|INFO|Payara 5.183|javax.enterprise.resource.webcontainer.jsf.config|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043251287;_LevelValue=800;_MessageID=jsf.config.listener.version;|
  Initializing Mojarra 2.4.0-m01.payara-p5 for context '/L10-jaxws-yandexspeller_war'|#]

[#|2018-12-05T20:54:11.479+0000|INFO|Payara 5.183|javax.enterprise.web|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043251479;_LevelValue=800;_MessageID=AS-WEB-GLUE-00172;|
  Loading application [L10-jaxws-yandexspeller_war] at [/L10-jaxws-yandexspeller_war]|#]

[#|2018-12-05T20:54:11.612+0000|INFO|Payara 5.183|javax.enterprise.system.core|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043251612;_LevelValue=800;|
  L10-jaxws-yandexspeller_war was successfully deployed in 1,499 milliseconds.|#]

[#|2018-12-05T20:54:11.613+0000|INFO|Payara 5.183|javax.enterprise.system.tools.deployment.autodeploy|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544043251613;_LevelValue=800;_MessageID=NCLS-DEPLOYMENT-02035;|
  [AutoDeploy] Successfully autodeployed : /opt/payara5/glassfish/domains/domain1/autodeploy/L10-jaxws-yandexspeller_war.war.|#]

[#|2018-12-05T20:54:23.765+0000|INFO|Payara 5.183||_ThreadID=39;_ThreadName=http-thread-pool::http-listener-1(5);_TimeMillis=1544043263765;_LevelValue=800;|
  20:54:23.758 [http-thread-pool::http-listener-1(5)] INFO ru.otus.sua.L10.SpellServiceAction - REQUEST: синхрафазатрон
|#]

[#|2018-12-05T20:54:24.023+0000|INFO|Payara 5.183||_ThreadID=39;_ThreadName=http-thread-pool::http-listener-1(5);_TimeMillis=1544043264023;_LevelValue=800;|
  20:54:24.023 [http-thread-pool::http-listener-1(5)] INFO ru.otus.sua.L10.SpellServiceAction - RESPONSE: синхрофазотрон (Codes: 1)
|#]

[#|2018-12-05T20:54:27.995+0000|INFO|Payara 5.183||_ThreadID=35;_ThreadName=http-thread-pool::http-listener-1(1);_TimeMillis=1544043267995;_LevelValue=800;|
  20:54:27.995 [http-thread-pool::http-listener-1(1)] INFO ru.otus.sua.L10.SpellServiceAction - REQUEST: опельсин
|#]

[#|2018-12-05T20:54:28.168+0000|INFO|Payara 5.183||_ThreadID=35;_ThreadName=http-thread-pool::http-listener-1(1);_TimeMillis=1544043268168;_LevelValue=800;|
  20:54:28.168 [http-thread-pool::http-listener-1(1)] INFO ru.otus.sua.L10.SpellServiceAction - RESPONSE: апельсин (Codes: 1)
|#]

[#|2018-12-05T20:54:28.173+0000|INFO|Payara 5.183||_ThreadID=35;_ThreadName=http-thread-pool::http-listener-1(1);_TimeMillis=1544043268173;_LevelValue=800;|
  20:54:28.173 [http-thread-pool::http-listener-1(1)] INFO ru.otus.sua.L10.YandexSpellerServiceAsRESTSimulator - ON REST WRAPPER: опельсин --> <?xml version="1.0"?><string>апельсин (Codes: 1)</string>
|#]


```



L10-jaxws-yandexspeller
``` 

[#|2018-12-05T18:59:26.645+0000|INFO|Payara 5.183||_ThreadID=38;_ThreadName=http-thread-pool::http-listener-1(4);_TimeMillis=1544036366645;_LevelValue=800;|
  18:59:26.645 [http-thread-pool::http-listener-1(4)] INFO ru.otus.sua.L10.JaxwsClientServlet - REQUEST: синхрафазатрон синхрафазатрон
|#]

[#|2018-12-05T18:59:26.800+0000|INFO|Payara 5.183||_ThreadID=38;_ThreadName=http-thread-pool::http-listener-1(4);_TimeMillis=1544036366800;_LevelValue=800;|
  18:59:26.800 [http-thread-pool::http-listener-1(4)] INFO ru.otus.sua.L10.JaxwsClientServlet - RESPONSE: синхрофазотрон;синхрофазотрон (Codes: 1,1)
|#]

```


L10-jaxws-service Tests
``` 
/usr/lib/jvm/java-8-oracle/bin/java ...
18:40:53.773 [main] INFO ru.otus.sua.L10.EstimateIncomeTax - received data for tax estimating: TaxData(revenuesYear=1000000.0, expenses=200000.0, taxRate=20.0)
18:40:53.781 [main] INFO ru.otus.sua.L10.EstimateIncomeTax - result of estimating: 160000.0
18:40:54.357 [pool-1-thread-1] INFO ru.otus.sua.L10.EstimateIncomeTax - received data for tax estimating: TaxData(revenuesYear=1000000.0, expenses=200000.0, taxRate=20.0)
18:40:54.357 [pool-1-thread-1] INFO ru.otus.sua.L10.EstimateIncomeTax - result of estimating: 160000.0

Process finished with exit code 0
```

L10-jaxws-webclient Log
``` 
[#|2018-12-05T14:37:07.884+0000|INFO|Payara 5.183|javax.enterprise.system.tools.deployment.autodeploy|_ThreadID=125;_ThreadName=AutoDeployer;_TimeMillis=1544020627884;_LevelValue=800;_MessageID=NCLS-DEPLOYMENT-02035;|
  [AutoDeploy] Successfully autodeployed : /opt/payara5/glassfish/domains/domain1/autodeploy/L10-jaxws-webclient_war.war.|#]

[#|2018-12-05T14:37:13.824+0000|INFO|Payara 5.183||_ThreadID=38;_ThreadName=http-thread-pool::http-listener-1(4);_TimeMillis=1544020633824;_LevelValue=800;|
  14:37:13.824 [http-thread-pool::http-listener-1(4)] INFO ru.otus.sua.L10.EstimateIncomeTax - received data for tax estimating: TaxData(revenuesYear=1000000.0, expenses=200000.0, taxRate=20.0)
|#]

[#|2018-12-05T14:37:13.824+0000|INFO|Payara 5.183||_ThreadID=38;_ThreadName=http-thread-pool::http-listener-1(4);_TimeMillis=1544020633824;_LevelValue=800;|
  14:37:13.824 [http-thread-pool::http-listener-1(4)] INFO ru.otus.sua.L10.EstimateIncomeTax - result of estimating: 160000.0

[#|2018-12-05T14:38:50.249+0000|INFO|Payara 5.183||_ThreadID=36;_ThreadName=http-thread-pool::http-listener-1(2);_TimeMillis=1544020730249;_LevelValue=800;|
  14:38:50.249 [http-thread-pool::http-listener-1(2)] INFO ru.otus.sua.L10.EstimateIncomeTax - received data for tax estimating: TaxData(revenuesYear=100.0, expenses=99.0, taxRate=50.0)
|#]

[#|2018-12-05T14:38:50.250+0000|INFO|Payara 5.183||_ThreadID=36;_ThreadName=http-thread-pool::http-listener-1(2);_TimeMillis=1544020730250;_LevelValue=800;|
  14:38:50.250 [http-thread-pool::http-listener-1(2)] INFO ru.otus.sua.L10.EstimateIncomeTax - result of estimating: 0.5
|#]

```

#### Материалы

