### Практическая работа      «WebSocket API» по курсу JavaEE.


- [ ] Для существующей функциональности получения информации о курсе валют и новостях
  - [ ] разработать Server Endpoint, 
  - [x] который с периодичностью в 5 минут запрашивает информацию у «конечных» сервисов 
  - [x] и в случае найденных расхождений от закэшированных на сервере 
  - [ ] производить уведомление клиентской стороны.
  - [ ] Периодичность следует вынести как параметризируемое значение, поддающееся изменению. 
  - [x] При передаче данных рекомендуется использовать формат JSON.

- [ ] Внести изменения клиентского кода страниц/модулей новостей и курса валют для возможности интеграции с серверной частью с целью получения обновленных данных 
  - [ ] и их последующего отображения для _всех_ подключенных пользователей, 
        без необходимости обновления страниц, используя возможности JS.

- [ ] Аналогичный механизм обновления предусмотреть для dashboard статистической информации (адаптировать клиент/сервер).
  - [ ] добавить в nav-bar "dashboard"  в виде одного поля с количеством сессий на сайте 

- [ ] ~удалить предыдущий функционал страниц/модулей новостей и курса валют~

- - - 

- [ ] attempt migrate to Glassfish5 
    - [x] DB config in admin console (db pool, jdbc resource with jndi name), 
    - [x] DB driver jar download to domain lib dir, 
    - [x] Idea Run config, 
    - [x] [JNDI issue](http://mjremijan.blogspot.com/2015/11/payaraglassfish-datasource-reference.html))
    - [x] [glassfish-web.xml example](https://javaee.github.io/glassfish/doc/5.0/application-deployment-guide.pdf)
    - [x] set `<res-auth>` to  `Container` in web.xml
    - [ ] `[glassfish 5.0] [WARNING] [] [javax.enterprise.web] [tid: _ThreadID=31 _ThreadName=http-listener-1(4)] [timeMillis: 1542637467483] [levelValue: 900] [[ StandardWrapperValve[StatServiceServlet]: Servlet.service() for servlet StatServiceServlet threw exception java.lang.IllegalStateException: Transaction already active at ru.otus.sua.statistic.StatisticEntityDAO.saveStatisticEntityWithIdReturn(StatisticEntityDAO.java:42)`
    - [ ] сломался импорт из xml `.AppServletContextListener - Create hardcoded admin account on startup application.`
- [x] заменить TagHandler в статистике на AJAX/XMLHttpRequest
- [ ] заменить statisticView.jsp на шаблонизаторную страницу
- [ ] фильтрация на statisticView.jsp
- [ ] функционал Employe CRUD из интерфейса
- [ ] двуязычная интернационализация сообщений и сайта
- [ ] решить все TODO
  - [ ] correct Lucene shutdown

#### Решение

#### Материалы

