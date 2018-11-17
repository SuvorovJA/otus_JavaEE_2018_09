### Практическая работа      «Пользовательские теги» по курсу JavaEE.
этапы:
- [x] 1. Создание необходимых сущностей на уровне БД
- [x] 2. Разработка пользовательского тега и сервиса для сбора статистики
- [x] 3. Отображение статистической информации

подзадачи:    
- [x] Создать сущность БД с ниже перечисленными статистическими атрибутами:
    1. Идентификатор
    2. Название маркера статистики (значение запрашивается из системных переменных окружения)
    3. Наименование «JSP-страницы» или модуля для SPA-приложения
    4. Клиентский IP-адрес
    5. Информация о браузере
    6. Клиентское время обращения
    7. Серверное время поступления запроса
    8. Login
    9. Cookies
    10. Session
    11. Предыдущий идентификатор статистики (для всех последующих входов – для возможности анализа «маршрута» посещения Вашего приложения).
  - [x] DAO класс для сущности     
      
- [x] Разработать веб-сервлет, осуществляющий прием данных о клиенте и последующем сохранении данной информации
  - [x] Данную функциональность следует вынести в качестве отдельного сервиса для возможности повторного использования этой бизнес-логики. 
    - [x] ридми по применению пакета
    - [x] вынести в отдельный подключаемый jar и в основном модуле указывать его как зависимость
  - [x] В качестве ответа данного сервлета должен быть возвращен json-объект с идентификатором(только id?) созданной записи. 
  - [x] Предусмотреть возможность гибкого отключения сбора статистики без пересборки приложения. 
    - [x] Параметр в web.xml

- [x] Клиентский код сбора информации о пользователе обернуть в виде пользовательского тега, 
  - [x] который следует разместить на всех jsp-страницах Вашего приложения.
    
- [x] Предусмотреть дополнительную форму/страницу, сгенерированную средствами любого понравившегося шаблонизатора(JSP) 
  - [x] и доступную после авторизации в системе, 
  - [x] в которой  в табличном виде отображается собранная информация о посещениях пользоваталей с выводом всех статистических атрибутов. 

- [ ] ~Для возможности сбора статистики, в том числе и в сторонних приложениях, следует реализовать CORS для сервлета сбора статистической инфомации, для поддержки выполнения кросс-доменных JSONP-запросов (опциональное задание).~

- - - 

- [ ] заметь statisticView.jsp на шаблонизаторную страницу
- [ ] фильтрация на statisticView.jsp
- [ ] решить все TODO
- [ ] функционал Employe CRUD из интерфейса
- [x] заменить null проверки на _String Objects.toString(Object o, String nullDefault)_
- [ ] двуязычная интернационализация сообщений и сайта
- [ ] correct Lucene shutdown

#### Решение

просмотр статистики на скриншотах

CSV дамп
``` 
id,browser,clienttzoffset,clienttimestamp,ipaddress,login,marker,page,previd,servertimestamp,session,useragent
1,Yandex.Browser 18,-420,1542396498238,0:0:0:0:0:0:0:1,<undef>,SomeMarker,http://localhost:8080/L08/index.jsp,-1,1542396498610,1630EB12D86AB82D97FC1AEC5221C720,52009
2,Yandex.Browser 18,-420,1542396530246,0:0:0:0:0:0:0:1,<undef>,SomeMarker,http://localhost:8080/L08/employes.jsp,1,1542396530284,1630EB12D86AB82D97FC1AEC5221C720,52010
3,Yandex.Browser 18,-420,1542396569368,0:0:0:0:0:0:0:1,<undef>,SomeMarker,http://localhost:8080/L08/login.jsp,2,1542396569408,1630EB12D86AB82D97FC1AEC5221C720,52012
4,Yandex.Browser 18,-420,1542396570977,0:0:0:0:0:0:0:1,admin,SomeMarker,http://localhost:8080/L08/login.jsp,3,1542396571013,1630EB12D86AB82D97FC1AEC5221C720,52013
5,Yandex.Browser 18,-420,1542396573341,0:0:0:0:0:0:0:1,admin,SomeMarker,http://localhost:8080/L08/scripts.jsp,4,1542396573392,1630EB12D86AB82D97FC1AEC5221C720,52014
6,Yandex.Browser 18,-420,1542396575207,0:0:0:0:0:0:0:1,admin,SomeMarker,http://localhost:8080/L08/scripts.jsp,5,1542396575244,1630EB12D86AB82D97FC1AEC5221C720,52015
7,Yandex.Browser 18,-420,1542396576553,0:0:0:0:0:0:0:1,admin,SomeMarker,http://localhost:8080/L08/about.jsp,6,1542396576586,1630EB12D86AB82D97FC1AEC5221C720,52016
8,Yandex.Browser 18,-420,1542396577939,0:0:0:0:0:0:0:1,admin,SomeMarker,http://localhost:8080/L08/employes.jsp,7,1542396577969,1630EB12D86AB82D97FC1AEC5221C720,52017
9,Yandex.Browser 18,-420,1542396583151,0:0:0:0:0:0:0:1,admin,SomeMarker,http://localhost:8080/L08/employes.jsp?search_fullName=&search_age_min=&search_age_max=&search_city=TOMSK&search_departament=&search_appointment=&search_login=,8,1542396583183,1630EB12D86AB82D97FC1AEC5221C720,52018
10,Yandex.Browser 18,-420,1542396585405,0:0:0:0:0:0:0:1,admin,SomeMarker,http://localhost:8080/L08/index.jsp,9,1542396585437,1630EB12D86AB82D97FC1AEC5221C720,52019
11,Chrome 58,-420,1542396595399,0:0:0:0:0:0:0:1,<undef>,SomeMarker,http://localhost:8080/L08/,-1,1542396595530,96B91ED03A5374920D7159F5908D5CD6,52020
12,Chrome 58,-420,1542396604023,0:0:0:0:0:0:0:1,<undef>,SomeMarker,http://localhost:8080/L08/employes.jsp,11,1542396604187,96B91ED03A5374920D7159F5908D5CD6,52021
13,Chrome 58,-420,1542396606760,0:0:0:0:0:0:0:1,<undef>,SomeMarker,http://localhost:8080/L08/login.jsp,12,1542396606898,96B91ED03A5374920D7159F5908D5CD6,52022
14,Chrome 58,-420,1542396613028,0:0:0:0:0:0:0:1,user,SomeMarker,http://localhost:8080/L08/login.jsp,13,1542396613189,96B91ED03A5374920D7159F5908D5CD6,52023
15,Chrome 58,-420,1542396614748,0:0:0:0:0:0:0:1,user,SomeMarker,http://localhost:8080/L08/scripts.jsp,14,1542396614911,96B91ED03A5374920D7159F5908D5CD6,52024
16,Chrome 58,-420,1542396616617,0:0:0:0:0:0:0:1,user,SomeMarker,http://localhost:8080/L08/employes.jsp,15,1542396616780,96B91ED03A5374920D7159F5908D5CD6,52025
17,Chrome 58,-420,1542396623173,0:0:0:0:0:0:0:1,user,SomeMarker,http://localhost:8080/L08/employes.jsp?search_fullName=Smith&search_age_min=&search_age_max=&search_city=&search_departament=&search_appointment=&search_login=,16,1542396623313,96B91ED03A5374920D7159F5908D5CD6,52026
18,Chrome 58,-420,1542396630622,0:0:0:0:0:0:0:1,user,SomeMarker,http://localhost:8080/L08/employes.jsp,17,1542396630788,96B91ED03A5374920D7159F5908D5CD6,52027
19,Chrome 58,-420,1542396639839,0:0:0:0:0:0:0:1,user,SomeMarker,http://localhost:8080/L08/employes.jsp?search_fullName=&search_age_min=&search_age_max=&search_city=&search_departament=&search_appointment=User&search_login=,18,1542396639981,96B91ED03A5374920D7159F5908D5CD6,52028
20,Chrome 58,-420,1542396643509,0:0:0:0:0:0:0:1,user,SomeMarker,http://localhost:8080/L08/index.jsp,19,1542396643636,96B91ED03A5374920D7159F5908D5CD6,52029
```


``` 
-- auto-generated definition
create table statistic
(
  id              bigint not null
    constraint statistic_pkey
    primary key,
  browser         varchar(255),
  clienttzoffset  bigint not null,
  clienttimestamp bigint not null,
  ipaddress       varchar(255),
  login           varchar(255),
  marker          varchar(255),
  page            varchar(255),
  previd          bigint not null,
  servertimestamp bigint not null,
  session         varchar(255),
  useragent       text
);

alter table statistic
  owner to postgres;

```


``` 
-- auto-generated definition
create table statisticentity_cookies
(
  statisticentity_id bigint not null
    constraint fk1n8n24by0l22njdwh18q3ntny
    references statistic,
  cookies            bytea
);

alter table statisticentity_cookies
  owner to postgres;
```





#### Материалы

[JPA Pagination](https://www.baeldung.com/jpa-pagination)

[web-fragment.xml](https://blogs.oracle.com/swchan/servlet-30-web-fragmentxml)

[Implementing the Tag Handler](https://docs.oracle.com/cd/E13222_01/wls/docs100/taglib/handler.html)


