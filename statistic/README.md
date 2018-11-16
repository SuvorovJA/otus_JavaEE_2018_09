### site usage statictic

предполагается сбор статистики через 
custom tag на каждой jsp-странице, 
либо через сервлет-фильтр для произвольных url.
сервлет-фильтр функционал не реализован.

1) опубликовать библиотеку в виде jar в maven repo

    через maven 'install' goal, или настройкой run configuration в idea 

- - -

Подключение к приложению

2) зависимость (pom.xml)
```
        <dependency>
            <groupId>ru.otus.sua</groupId>
            <artifactId>statistic</artifactId>
            <version>1.0</version>
            <scope>compile</scope>
            <type>jar</type>
        </dependency>
```

3) добавить сервлет в web.xml,  

    по умолчанию сервлет подключается аннотациями, 
    `@WebServlet(name = "StatServiceServlet", urlPatterns = "/statistic")`
    добавление в web.xml нужно только для применения параметров.
    по умолчанию сбор статистики _включен_ и применяется _custom tag_ вариант интеграции.
    
``` 
    <servlet>
        <servlet-name>StatServiceServlet</servlet-name>
        <servlet-class>ru.otus.sua.statistic.StatServiceServlet</servlet-class>
        <init-param>
            <param-name>ru.otus.sua.statistic.GATHERING</param-name>
            <param-value>ENABLED</param-value>
            <!--<param-value>DISABLED</param-value>-->
        </init-param>
        <init-param>
            <param-name>ru.otus.sua.statistic.INTEGRATION_TYPE</param-name>
            <param-value>CUSTOMTAG</param-value>
            <!--<param-value>FILTER</param-value>-->
        </init-param>
        <init-param>
            <param-name>ru.otus.sua.statistic.MARKER_NAME</param-name>
            <param-value>SomeMarker</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>StatServiceServlet</servlet-name>
        <url-pattern>/statistic</url-pattern>
    </servlet-mapping>
```

4) добавить фильтр в web.xml если используется. 

    _подключить можно, но функциональность фильтра не реализована_
    
``` 
    <filter>
        <filter-name>StatisticFilter</filter-name>
        <filter-class>ru.otus.sua.statistic.StatisticFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>StatisticFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

5) обеспечить datasource `"java:comp/env/jdbc/PostgresDS"`

6) custom tag, применение на jsp страницах
    
    `<%@ taglib prefix = "doit" uri = "/stattags"%>`

    где-нибудь в body `<doit:statistic/>`

7) страница с просмотром отчета
  
    открыть url get-запросом: `<YouContext>/statistic`

8) servlet response: json

    в ответ на каждый Тэг отправляется id stat-записи `{"statisticId":1}`

9) приладить костыль на останов контейнера сервлетов

    `ru.otus.sua.statistic.EntityManagerHolder.shutdownIndexer();` 

