### Практическая работа «JAX-RS» по курсу JavaEE

- [x] 1. Создание standalone сервиса расчета согласно некоторому алгоритму.
- [x] 2. Создание серверной части сервиса для работы со справочником сущностей БД.
- [x] 3. Документирование сервисов.
- [x] 4. Разработка клиентской части редактирования и отображения сущности БД.
----
- [x] Создать RESTful-сервис, задачей которого является расчет некоторой характеристики согласно заранее предопределенному алгоритму. В качестве примера предлагается создание калькулятора расчета ежемесячного платежа по двум типам возможных платежей:
```
а) дифференцированный платеж:
Плi = Кр /T + Кр*(T- i +1)*Ст /Т , где
    T - количество периодов оплаты;
    Кр - сумма кредита;
    Ст - процентная ставка, начисляемая на задолженность за период;
    Плi - размер платежа за i - й период (i принимает значения от 1 до T);
    Пример расчёта платежей и суммы процентов, выплаченных за период:
    Т = 6 мес.; Кр = $10 000; Ст = 15% годовых/ 12 мес. = 0.0125
    Пл1 = 10000/6 + 10000*6*0.0125/6 = $1791.7 .......
    Пл6 = 10000/6 + 10000*1*0.0125/6 = $1687,5
б) аннуитетный платеж:
Плi = Кр*Ст / (1 - 1 / (1+Ст)^T) - размер платежа не зависит от i, все платежи равны между собой. 
    Используются те же обозначения переменных, что и в предыдущей формуле
    Пример расчёта платежей и суммы процентов, выплаченных за период:
    Т = 6 мес.; Кр = $10 000; Ст = 15% годовых/ 12 мес. = 0.0125
    Пл = 10 000 * 0.0125 / (1 – 1/ (1.0125)^6) = 125 / 0.071825 = $1740
```    
- [x] Для данного сервиса разработать отдельную HTML-форму для возможности передачи расчетных входных параметров и получения результата.
- [x] Два варианта расчета необходимо предусмотреть в виде версионности API, используя компонент URI (v1, v2), кастомный заголовок запроса или любой другой способ.

----
- [x] Разработать/отрефакторить справочник ролей/городов/подразделений/сотрудников (любой понравившийся или все при желании) с поддержкой операций создания, чтения, редактирования и удаления данных сущностей в виде RESTful-вебсервисов.
  - [x] service
  - [x] Разработать удобный UI для работы с вышеобозначенными сервисами. 
----
- [x] Используя возможности библиотеки Swagger, задокументировать разработанные сервисы.
----

#### Решение

платежи
``` 
$ 
$ curl -X GET -H "Accept: application/xml" http://pg-payara.docker:8080/L11-jaxrs-service_Web/v1/montlyPayment/6/10000/15
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<estimates>
<MonthlyPayment>1791.6666666666667</MonthlyPayment>
<MonthlyPayment>1770.8333333333335</MonthlyPayment>
<MonthlyPayment>1750.0</MonthlyPayment>
<MonthlyPayment>1729.1666666666667</MonthlyPayment>
<MonthlyPayment>1708.3333333333335</MonthlyPayment>
<MonthlyPayment>1687.5</MonthlyPayment>
</estimates>

$ curl -X GET -H "Accept: application/json" http://pg-payara.docker:8080/L11-jaxrs-service_Web/v1/montlyPayment/6/10000/15
[1791.6666666666667,1770.8333333333335,1750.0,1729.1666666666667,1708.3333333333335,1687.5]

$ curl -X GET -H "Accept: application/xml" http://pg-payara.docker:8080/L11-jaxrs-service_Web/v2/montlyPayment/6/10000/15
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<estimates>
<MonthlyPayment>1740.3381021345717</MonthlyPayment>
</estimates>

$ curl -X GET -H "Accept: application/json" http://pg-payara.docker:8080/L11-jaxrs-service_Web/v2/montlyPayment/6/10000/15
[1740.3381021345717]

$ curl -X GET -H "Accept: application/json" http://pg-payara.docker:8080/L11-jaxrs-service_Web/v2/montlyPayment/6/10000/-15
java.lang.IllegalArgumentException: interestRate <=0

```

From UI 
``` 
[#|2018-12-08T15:50:32.887+0000|INFO|Payara 5.183||_ThreadID=38;_ThreadName=http-thread-pool::http-listener-1(3);_TimeMillis=1544284232887;_LevelValue=800;|
  15:50:32.879 [http-thread-pool::http-listener-1(3)] INFO  r.o.sua.L11.MonthlyPaymentServiceV1 - V1_POST(http://localhost:8080/L11-jaxrs-service_Web/v1/monthlyPayment): [1791.6666666666667, 1770.8333333333335, 1750.0, 1729.1666666666667, 1708.3333333333335, 1687.5]
|#]

[#|2018-12-08T15:50:38.501+0000|INFO|Payara 5.183||_ThreadID=36;_ThreadName=http-thread-pool::http-listener-1(1);_TimeMillis=1544284238501;_LevelValue=800;|
  15:50:38.500 [http-thread-pool::http-listener-1(1)] INFO  r.o.sua.L11.MonthlyPaymentServiceV1 - V1_PATH(http://localhost:8080/L11-jaxrs-service_Web/v1/monthlyPayment/6/10000.0/15.0): [1791.6666666666667, 1770.8333333333335, 1750.0, 1729.1666666666667, 1708.3333333333335, 1687.5]
|#]

[#|2018-12-08T15:50:48.222+0000|INFO|Payara 5.183||_ThreadID=38;_ThreadName=http-thread-pool::http-listener-1(3);_TimeMillis=1544284248222;_LevelValue=800;|
  15:50:48.221 [http-thread-pool::http-listener-1(3)] INFO  r.o.sua.L11.MonthlyPaymentServiceV2 - V2_PATH(http://localhost:8080/L11-jaxrs-service_Web/v2/monthlyPayment/6/10000.0/15.0): [1740.3381021345717]
|#]

[#|2018-12-08T15:50:49.463+0000|INFO|Payara 5.183||_ThreadID=38;_ThreadName=http-thread-pool::http-listener-1(3);_TimeMillis=1544284249463;_LevelValue=800;|
  15:50:49.463 [http-thread-pool::http-listener-1(3)] INFO  r.o.sua.L11.MonthlyPaymentServiceV2 - V2_POST(http://localhost:8080/L11-jaxrs-service_Web/v2/monthlyPayment): [1740.3381021345717]
|#]



```

#### Материалы

