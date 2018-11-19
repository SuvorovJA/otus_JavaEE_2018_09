### Практическая работа      «WebSocket API» по курсу JavaEE.


- [ ] Для существующей функциональности получения информации о курсе валют и новостях
  - [ ] разработать Server Endpoint, 
  - [ ] который с периодичностью в 5 минут запрашивает информацию у «конечных» сервисов 
  - [ ] и в случае найденных расхождений от закэшированных на сервере 
  - [ ] производить уведомление клиентской стороны.
  - [ ] Периодичность следует вынести как параметризируемое значение, поддающееся изменению. 
  - [ ] При передаче данных рекомендуется использовать формат JSON.

- [ ] Внести изменения клиентского кода страниц/модулей новостей и курса валют для возможности интеграции с серверной частью с целью получения обновленных данных 
  - [ ] и их последующего отображения для _всех_ подключенных пользователей, 
        без необходимости обновления страниц, используя возможности JS.

- [ ] Аналогичный механизм обновления предусмотреть для dashboard статистической информации (адаптировать клиент/сервер).
  - [ ] добавить в nav-bar "dashboard"  в виде одного поля с количеством сессий на сайте 

- [ ] удалить предыдущий функционал страниц/модулей новостей и курса валют

- - - 

- [x] migrate to Glassfish5 
    - DB config in admin console (db pool, jdbc resource with jndi name), 
    - DB driver jar download to domain lib dir, 
    - Idea Run config, 
    - [JNDI issue](http://mjremijan.blogspot.com/2015/11/payaraglassfish-datasource-reference.html))
    - [glassfish-web.xml example](https://javaee.github.io/glassfish/doc/5.0/application-deployment-guide.pdf)
    - set `<res-auth>` to  `Container` in web.xml
    - [ ] `Transaction already active at org.hibernate.engine.transaction.internal.TransactionImpl.begin(TransactionImpl.java:75) at ru.otus.sua.statistic.StatisticEntityDAO.saveStatisticEntityWithIdReturn(StatisticEntityDAO.java:42)`
- [x] заменить TagHandler в статистике на AJAX/XMLHttpRequest
- [ ] заметь statisticView.jsp на шаблонизаторную страницу
- [ ] фильтрация на statisticView.jsp
- [ ] функционал Employe CRUD из интерфейса
- [ ] двуязычная интернационализация сообщений и сайта
- [ ] решить все TODO
  - [ ] correct Lucene shutdown

#### Решение

#### Материалы

