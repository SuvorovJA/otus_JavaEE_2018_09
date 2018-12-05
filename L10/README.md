### Практическая работа «JAX-WS» по курсу JavaEE

- [x] 1. Создание серверной части сервиса расчета согласно некоторому алгоритму.
- [x] 2. Создание клиентской части для возможности обращения к функциональности сервиса.
- [ ] 3. Интеграция со сторонними сервисами.

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

- [ ]  Разработать SOAP-сервисы, предоставив функциональность вычисления средней/максимальной зарплаты сотрудников в организации (из предыдущих домашних работ) для внешнего доступа.

- [ ] Выбрать любые два понравившихся SOAP-веб сервиса из каталога(?) и 
  - [ ] разработать для них соответствующие клиенты для возможности обращения к их функциональности.
  - [ ] Вызываемый сервис необходимо «обернуть» в RESTful сервис с возможностью обращения с некоторой страницы разрабатываемого приложения.
- [ ] В качестве альтернативного задания предлагается разработать сервисную прослойку для создания RESTful-сервисов, используя возможности JAX-WS (@WebServiceProvider). В качестве целевого сервиса рекомендуется взять Yandex.Geocoder.


#### Решение

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

