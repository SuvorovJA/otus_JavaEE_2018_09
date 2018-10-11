### Практическая работа «Обработка XML и JSON» по курсу JavaEE.

1. создание XML.

- [x] Используя сущность Employee (Сотрудники) из ДЗ2 и возможности, предоставляемые технологией JAXB, необходимо произвести процедуру маршалинга состояния объектов данного класса во внешний файл employee.xml. 
Для этих целей рекомендую использовать отдельный сервлет MarshalXMLServlet.

2. поиск XPath.

- [x] Считав содержимое xml-документа employee.xml в объект org.w3c.dom.Document или org.xml.sax.InputSource, 
- [x] необходимо найти все xml-теги сотрудников, у которых зарплата превышает среднее значение. 
Допустимо логику описать в том же сервлете MarshalXMLServlet либо создать запускаемый класс.

3. конвертация XML в JSON.

- [x] Используя возможности библиотеки JSON или любой другой (при желании можно реализовать свой конвертер), преобразовать содержимое файла empolyee.xml в JSON-формат.
- [x] Результат вывести в качестве ответа сервлета, 
- [x] одновременно сохранив в виде внешнего файла employee.json.

4. отображение внешнего JSON-файла на объектную модель.

- [x] Используя содержимое employee.json и возможности технологии JSON-B, получить список объектов Employee 
- [x] и вывести в сервлете или запускаемом классе информацию о сотрудниках с нечетными индексами в списке.

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
Export to file: /home/lsua/.IntelliJIdea2018.2/system/tomcat/tomcat9_(1)_project/work/Catalina/localhost/L04/employes_jaxb.xml
File length before= 100
File length after= 5389
final servlet.
```

``` 
$ cat '/home/lsua/.IntelliJIdea2018.2/system/tomcat/tomcat9_(1)_project/work/Catalina/localhost/L04/employes_jaxb.xml'
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<employes>
    <employes-list>
        <employee id="12">
            <fullname>Ray Riggey</fullname>
            <city>Banag</city>
            <salary>8084</salary>
            <department id="1">
                <name>Sales</name>
            </department>
            <appointment id="6">
                <name>Engineer</name>
            </appointment>
            <credentials id="13">
                <login>Ram 3500</login>
                <passhash>0acc84d958e88dfc3b205fc26b67b623</passhash>
            </credentials>
        </employee>
        <employee id="14">
            <fullname>Normie Lutas</fullname>
            <city>Yongfeng</city>
            <salary>4622</salary>
            <department id="3">
                <name>Services</name>
            </department>
            <appointment id="7">
                <name>Accountant</name>
            </appointment>
            <credentials id="15">
                <login>Eldorado</login>
                <passhash>87dc36d8239839ca2525d030afd284cd</passhash>
            </credentials>
        </employee>
        <employee id="16">
            <fullname>Launce Rookeby</fullname>
            <city>Xiacang</city>
            <salary>5233</salary>
            <department id="2">
                <name>Marketing</name>
            </department>
            <appointment id="6">
                <name>Engineer</name>
            </appointment>
            <credentials id="17">
                <login>300</login>
                <passhash>8136f81e7fbb51082e4367be72cc0d1f</passhash>
            </credentials>
        </employee>
        <employee id="18">
            <fullname>Leora Crotty</fullname>
            <city>Tân Hiệp</city>
            <salary>9278</salary>
            <department id="3">
                <name>Services</name>
            </department>
            <appointment id="11">
                <name>Administrator</name>
            </appointment>
            <credentials id="19">
                <login>Dakota Club</login>
                <passhash>8947e944fd93a5a17345417675da857e</passhash>
            </credentials>
        </employee>
        <employee id="20">
            <fullname>Don Fryd</fullname>
            <city>Kista</city>
            <salary>8740</salary>
            <department id="3">
                <name>Services</name>
            </department>
            <appointment id="6">
                <name>Engineer</name>
            </appointment>
            <credentials id="21">
                <login>Ranger</login>
                <passhash>f68d3786f76369a2fd010425e67c770e</passhash>
            </credentials>
        </employee>
        <employee id="22">
            <fullname>Tomasine Beevis</fullname>
            <city>Firminópolis</city>
            <salary>9589</salary>
            <department id="1">
                <name>Sales</name>
            </department>
            <appointment id="10">
                <name>Professor</name>
            </appointment>
            <credentials id="23">
                <login>Quest</login>
                <passhash>ea94886f8c4080fe2957c192ff09845f</passhash>
            </credentials>
        </employee>
        <employee id="24">
            <fullname>Violette Gathercole</fullname>
            <city>Šibenik</city>
            <salary>4416</salary>
            <department id="1">
                <name>Sales</name>
            </department>
            <appointment id="9">
                <name>Developer</name>
            </appointment>
            <credentials id="25">
                <login>Legend</login>
                <passhash>e1172d7a844c89d2f477e4218b1143f4</passhash>
            </credentials>
        </employee>
        <employee id="26">
            <fullname>Gisella Magee</fullname>
            <city>Mendefera</city>
            <salary>8204</salary>
            <department id="4">
                <name>Training</name>
            </department>
            <appointment id="8">
                <name>Manager</name>
            </appointment>
            <credentials id="27">
                <login>Z4</login>
                <passhash>4738d7ba03a3cb44327e23a0ad4e9503</passhash>
            </credentials>
        </employee>
        <employee id="28">
            <fullname>Vina Titcombe</fullname>
            <city>Sokol’niki</city>
            <salary>6568</salary>
            <department id="5">
                <name>Support</name>
            </department>
            <appointment id="7">
                <name>Accountant</name>
            </appointment>
            <credentials id="29">
                <login>Yukon XL 1500</login>
                <passhash>9b15bba26f73f83889472ad2e4ceff62</passhash>
            </credentials>
        </employee>
        <employee id="30">
            <fullname>Graeme Fishbourne</fullname>
            <city>Ngamba</city>
            <salary>3337</salary>
            <department id="4">
                <name>Training</name>
            </department>
            <appointment id="6">
                <name>Engineer</name>
            </appointment>
            <credentials id="31">
                <login>Chariot</login>
                <passhash>e51120e6b86abbc702fc66abc6dac399</passhash>
            </credentials>
        </employee>
    </employes-list>
</employes>
```

``` 
http://localhost:8080/L04/xpathinxml
start servlet.
Import from file: /home/lsua/.IntelliJIdea2018.2/system/tomcat/tomcat9_(1)_project/work/Catalina/localhost/L04/employes_jaxb.xml
Get 10 salary records with content: 8084 4622 5233 9278 8740 9589 4416 8204 6568 3337
Avegage salary: 6807
Get 5 employes with salary over average: 
Ray Riggey Banag 8084 Sales Engineer Ram 3500 0acc84d958e88dfc3b205fc26b67b623
Leora Crotty Tân Hi?p 9278 Services Administrator Dakota Club 8947e944fd93a5a17345417675da857e
Don Fryd Kista 8740 Services Engineer Ranger f68d3786f76369a2fd010425e67c770e
Tomasine Beevis Firminópolis 9589 Sales Professor Quest ea94886f8c4080fe2957c192ff09845f
Gisella Magee Mendefera 8204 Training Manager Z4 4738d7ba03a3cb44327e23a0ad4e9503

final servlet.
```

```
http://localhost:8080/L04/xml2json
start servlet.
Import from file: /home/lsua/.IntelliJIdea2018.2/system/tomcat/tomcat9_(1)_project/work/Catalina/localhost/L04/employes_jaxb.xml

[
  {
    "city": "Banag",
    "credentials": {
      "id": 13,
      "login": "Ram 3500",
      "passhash": "0acc84d958e88dfc3b205fc26b67b623"
    },
    "appointment": {
      "name": "Engineer",
      "id": 6
    },
    "id": 12,
    "fullname": "Ray Riggey",
    "salary": 8084,
    "department": {
      "name": "Sales",
      "id": 1
    }
  },
  {
    "city": "Yongfeng",
    "credentials": {
      "id": 15,
      "login": "Eldorado",
      "passhash": "87dc36d8239839ca2525d030afd284cd"
    },
    "appointment": {
      "name": "Accountant",
      "id": 7
    },
    "id": 14,
    "fullname": "Normie Lutas",
    "salary": 4622,
    "department": {
      "name": "Services",
      "id": 3
    }
  },
  {
    "city": "Xiacang",
    "credentials": {
      "id": 17,
      "login": 300,
      "passhash": "8136f81e7fbb51082e4367be72cc0d1f"
    },
    "appointment": {
      "name": "Engineer",
      "id": 6
    },
    "id": 16,
    "fullname": "Launce Rookeby",
    "salary": 5233,
    "department": {
      "name": "Marketing",
      "id": 2
    }
  },
  {
    "city": "Tân Hi?p",
    "credentials": {
      "id": 19,
      "login": "Dakota Club",
      "passhash": "8947e944fd93a5a17345417675da857e"
    },
    "appointment": {
      "name": "Administrator",
      "id": 11
    },
    "id": 18,
    "fullname": "Leora Crotty",
    "salary": 9278,
    "department": {
      "name": "Services",
      "id": 3
    }
  },
  {
    "city": "Kista",
    "credentials": {
      "id": 21,
      "login": "Ranger",
      "passhash": "f68d3786f76369a2fd010425e67c770e"
    },
    "appointment": {
      "name": "Engineer",
      "id": 6
    },
    "id": 20,
    "fullname": "Don Fryd",
    "salary": 8740,
    "department": {
      "name": "Services",
      "id": 3
    }
  },
  {
    "city": "Firminópolis",
    "credentials": {
      "id": 23,
      "login": "Quest",
      "passhash": "ea94886f8c4080fe2957c192ff09845f"
    },
    "appointment": {
      "name": "Professor",
      "id": 10
    },
    "id": 22,
    "fullname": "Tomasine Beevis",
    "salary": 9589,
    "department": {
      "name": "Sales",
      "id": 1
    }
  },
  {
    "city": "?ibenik",
    "credentials": {
      "id": 25,
      "login": "Legend",
      "passhash": "e1172d7a844c89d2f477e4218b1143f4"
    },
    "appointment": {
      "name": "Developer",
      "id": 9
    },
    "id": 24,
    "fullname": "Violette Gathercole",
    "salary": 4416,
    "department": {
      "name": "Sales",
      "id": 1
    }
  },
  {
    "city": "Mendefera",
    "credentials": {
      "id": 27,
      "login": "Z4",
      "passhash": "4738d7ba03a3cb44327e23a0ad4e9503"
    },
    "appointment": {
      "name": "Manager",
      "id": 8
    },
    "id": 26,
    "fullname": "Gisella Magee",
    "salary": 8204,
    "department": {
      "name": "Training",
      "id": 4
    }
  },
  {
    "city": "Sokol\u2019niki",
    "credentials": {
      "id": 29,
      "login": "Yukon XL 1500",
      "passhash": "9b15bba26f73f83889472ad2e4ceff62"
    },
    "appointment": {
      "name": "Accountant",
      "id": 7
    },
    "id": 28,
    "fullname": "Vina Titcombe",
    "salary": 6568,
    "department": {
      "name": "Support",
      "id": 5
    }
  },
  {
    "city": "Ngamba",
    "credentials": {
      "id": 31,
      "login": "Chariot",
      "passhash": "e51120e6b86abbc702fc66abc6dac399"
    },
    "appointment": {
      "name": "Engineer",
      "id": 6
    },
    "id": 30,
    "fullname": "Graeme Fishbourne",
    "salary": 3337,
    "department": {
      "name": "Training",
      "id": 4
    }
  }
]

Export to file: /home/lsua/.IntelliJIdea2018.2/system/tomcat/tomcat9_(1)_project/work/Catalina/localhost/L04/employes.json
Json file length = 3582
final servlet.
``` 

``` 
http://localhost:8080/L04/json2obj
start servlet.
Import from file: /home/lsua/.IntelliJIdea2018.2/system/tomcat/tomcat9_(1)_project/work/Catalina/localhost/L04/employes.json
not have odd indexes, will print all.
id 12 is Ray Riggey
id 14 is Normie Lutas
id 16 is Launce Rookeby
id 18 is Leora Crotty
id 20 is Don Fryd
id 22 is Tomasine Beevis
id 24 is Violette Gathercole
id 26 is Gisella Magee
id 28 is Vina Titcombe
id 30 is Graeme Fishbourne
final servlet.
```


#### Проблемы

 - стирается база между 0 и 1 при первых кликах
 
        0. fill database - база загружена
        1. xml marshaling - база стерта, маршалер ничего не читает из базы
        0. fill database - база загружена еще раз
        1. xml marshaling - база осталась, видится маршалером
        
