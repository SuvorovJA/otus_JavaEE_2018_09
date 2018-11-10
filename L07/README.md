### Практическая работа «Servlet и JSP» по курсу JavaEE.

- [ ] При первом обращении пользователя к приложению, необходимо средствами веб-фильтра проанализировать заголовок клиентского запроса, содержащего информацию об используемом пользователем браузере, и произвести перенаправление (без изменения URL-строки) на заранее предопределенную JSP-страницу тех пользователей, чьи версии браузеров устарели (IE –не менее 10, Chrome – 50, Firefox – 45, Opera – 38, Safari – можно не анализировать).  
    
- [ ] На странице JSP необходимо проинформировать пользователя о том, что используемая версия браузера устарела и разместить картинки-ссылки для этих 4 (при желании 5) вариантов браузеров на страницы скачивания ПО.
      
- [ ] Если же версия удовлетворяет критериям, то работать с приложением в «штатном» режиме, сохранив этот признак в качестве пользовательской Cookie. Если данная кука-маркер существует, то проверку версии браузера пропускать.
    
- [ ] При старте приложения необходимо инициировать генерацию тестовых данных, которые будут извлечены из внешнего XML-файла, 
    - [x] при останове контейнера–удалять всю информацию из БД. 
    
- [ ] Чтобы не генерировать XML файлы вручную, приветствуется сохранить их средствами JAXB в приложении и при последующем старте использовать эту информацию. Таким образом, при завершении работы контейнера сохраняется последнее актуальное состояние данных в базе, после чего производится удаление всех данных (допустимо удалять и сами таблицы). 
    - [x] При повторном старте полностью восстанавливать структуры БД.   
    
- [x] Все имеющиеся html-страницы заменить на JSP так, чтобы подключаемые ресурсы (css, js) использовали контекстный путь до приложения. 
       
- [x] Также имеющуюся хост-страницу следует разбить на блоки: шапку, подвал и основную часть, с возможностью их подключения средствами технологии JSP.
    
- [x] На странице отображения информации о сотрудниках добавить поисковую форму с полями поиска по вхождению символов логина или ФИО, по должности и городу, в котором работает сотрудник, 
    - [x] а также диапазону возраста сотрудника. 
    - [x] Если указанных атрибутов у сотрудника нет, то следует их добавить и в случае необходимости завести ~отдельный справочник(должность и город)~ поле д.р. ~и связь с таблицей~ в Employee. 
    - [x] При отправке формы, необходимо направить запрос на сервлет (при желании асинхронный), который сохранит поисковый запрос и полученную информацию в атрибуте запроса и вызовет JSP- страницу, отображающую результирующий список.    
    - [x] При этом для общности с другими страницами, шапку и подвал следует подключить как внешние файлы .
    
- [ ] Добавить слушателей добавления/изменения атрибутов запроса, в котором производится кэширование производимых запросов и соответствующих результатов на уровне контекста приложения. Последующие однотипные поисковые запросы извлекать из данного кэша.

- - - 

- [x] за основу взять L05 задание
- [x] сделать бэкпорт JPA части из L06
- [x] На странице отображения информации о сотрудниках - сделать вывод RO-таблицы с ALL-сотрудниками
- [x] восстановить функционал авторизации пользователя
- [x] восстановить функционал доступа к странице отображения информации о сотрудниках после авторизации
- [ ] решить все TODO

#### Решение

Поиск
```
14:04:13.740 [);110] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Search Strings: fullName=''; ageMaxStr='40'; ageMax=40; ageMinStr='20'; ageMin=20; city=''; departament=''; appointment=''; login=''; 
14:04:13.740 [);110] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Builded Query: dateOfBirth:[279529453740 TO 910681453740]
14:04:13.765 [);110] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Query result: { id:7; fullname:U. USER; birthdate:09.11.1998; city:NSK; salary:1000; departament:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:siteUser; passhash:*}}
14:04:46.035 [);111] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Search Strings: fullName=''; ageMaxStr='40'; ageMax=40; ageMinStr='20'; ageMin=20; city=''; departament='Users'; appointment='User'; login=''; 
14:04:46.054 [);111] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Builded Query: departament:users appointment:siteUser dateOfBirth:[279529486054 TO 910681486054]
14:04:46.066 [);111] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Query result: { id:7; fullname:U. USER; birthdate:09.11.1998; city:NSK; salary:1000; departament:{ id:6; name:Users}; appointment:{ id:5; name:User}; credentials:{ id:8; login:siteUser; passhash:*}}
14:05:46.763 [);122] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Search Strings: fullName=''; ageMaxStr='50'; ageMax=50; ageMinStr='40'; ageMin=40; city=''; departament=''; appointment=''; login=''; 
14:05:46.764 [);122] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Builded Query: dateOfBirth:[-36003253236 TO 279529546764]
14:05:46.767 [);122] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Query result: { id:3; fullname:A. DMINSKY; birthdate:15.05.1975; city:TOMSK; salary:2000; departament:{ id:2; name:IT Dept.}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}
14:05:53.531 [);120] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Search Strings: fullName=''; ageMaxStr='50'; ageMax=50; ageMinStr='40'; ageMin=40; city='TOMSK'; departament=''; appointment=''; login=''; 
14:05:53.532 [);120] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Builded Query: city:tomsk dateOfBirth:[-36003246469 TO 279529553532]
14:05:53.537 [);120] INFO  r.o.s.L.e.h.JpaDtoForEmployeEntity - Query result: { id:3; fullname:A. DMINSKY; birthdate:15.05.1975; city:TOMSK; salary:2000; departament:{ id:2; name:IT Dept.}; appointment:{ id:1; name:SysAdmin}; credentials:{ id:4; login:admin; passhash:*}}
```

    
#### Материалы

- [Deleting Objects with Hibernate](https://www.baeldung.com/delete-with-hibernate)

- [Introduction to Hibernate Search](https://www.baeldung.com/hibernate-search)

- [Hibernate Search: Reference Guide](http://docs.jboss.org/hibernate/search/5.10/reference/en-US/html_single/#_searching)    