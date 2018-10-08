### Практическая работа «Обработка XML и JSON» по курсу JavaEE.

1. создание XML.

- [ ] Используя сущность Employee (Сотрудники) из ДЗ2 и возможности, предоставляемые технологией JAXB, необходимо произвести процедуру маршалинга состояния объектов данного класса во внешний файл employee.xml. 
Для этих целей рекомендую использовать отдельный сервлет MarshalXMLServlet.

2. поиск XPath.

- [ ] Считав содержимое xml-документа employee.xml в объект org.w3c.dom.Document или org.xml.sax.InputSource, 
- [ ] необходимо найти все xml-теги сотрудников, у которых зарплата превышает среднее значение. 
Допустимо логику описать в том же сервлете MarshalXMLServlet либо создать запускаемый класс.

3. конвертация XML в JSON.

- [ ] Используя возможности библиотеки JSON или любой другой (при желании можно реализовать свой конвертер), преобразовать содержимое файла empolyee.xml в JSON-формат.
- [ ] Результат вывести в качестве ответа сервлета, 
- [ ] одновременно сохранив в виде внешнего файла employee.json.

4. отображение внешнего JSON-файла на объектную модель.

- [ ] Используя содержимое employee.json и возможности технологии JSON-B, получить список объектов Employee 
- [ ] и вывести в сервлете или запускаемом классе информацию о сотрудниках с нечетными индексами в списке.

### Решение

``` 
http://localhost:8080/L04/
L04. XML & JSON Working
0. fill database
1. xml marshaling
2. xpath
3. xml to json
4. json mapping
```

``` 
http://localhost:8080/L04/dbinit
start servlet.
start strategyExecutorForCsvLoader().
_ read departm.csv.
final strategyExecutorForCsvLoader().
start strategyExecutorForCsvLoader().
_ read appoint.csv.
final strategyExecutorForCsvLoader().
start strategyExecutorForCsvLoader().
_ read employs.csv.
final strategyExecutorForCsvLoader().
final servlet.
```

``` 
http://localhost:8080/L04/db2xml
start servlet.
Read from db 10 Employes.
start PrintAllEmployesStrategy.
{ id:30; fullname:Graeme Fishbourne; city:Ngamba; salary:3337; department:{ id:4; name:Training}; appointment:{ id:6; name:Engineer}; credentials:{ id:31; login:Chariot; passhash:*}}
{ id:28; fullname:Vina Titcombe; city:Sokol?niki; salary:6568; department:{ id:5; name:Support}; appointment:{ id:7; name:Accountant}; credentials:{ id:29; login:Yukon XL 1500; passhash:*}}
{ id:26; fullname:Gisella Magee; city:Mendefera; salary:8204; department:{ id:4; name:Training}; appointment:{ id:8; name:Manager}; credentials:{ id:27; login:Z4; passhash:*}}
{ id:24; fullname:Violette Gathercole; city:?ibenik; salary:4416; department:{ id:1; name:Sales}; appointment:{ id:9; name:Developer}; credentials:{ id:25; login:Legend; passhash:*}}
{ id:22; fullname:Tomasine Beevis; city:Firminópolis; salary:9589; department:{ id:1; name:Sales}; appointment:{ id:10; name:Professor}; credentials:{ id:23; login:Quest; passhash:*}}
{ id:20; fullname:Don Fryd; city:Kista; salary:8740; department:{ id:3; name:Services}; appointment:{ id:6; name:Engineer}; credentials:{ id:21; login:Ranger; passhash:*}}
{ id:18; fullname:Leora Crotty; city:Tân Hi?p; salary:9278; department:{ id:3; name:Services}; appointment:{ id:11; name:Administrator}; credentials:{ id:19; login:Dakota Club; passhash:*}}
{ id:16; fullname:Launce Rookeby; city:Xiacang; salary:5233; department:{ id:2; name:Marketing}; appointment:{ id:6; name:Engineer}; credentials:{ id:17; login:300; passhash:*}}
{ id:14; fullname:Normie Lutas; city:Yongfeng; salary:4622; department:{ id:3; name:Services}; appointment:{ id:7; name:Accountant}; credentials:{ id:15; login:Eldorado; passhash:*}}
{ id:12; fullname:Ray Riggey; city:Banag; salary:8084; department:{ id:1; name:Sales}; appointment:{ id:6; name:Engineer}; credentials:{ id:13; login:Ram 3500; passhash:*}}
final PrintAllEmployesStrategy.
final servlet.
```