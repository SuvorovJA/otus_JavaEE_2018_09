### site usage statictic

1) how to publication in maven repo
    simple by 'install' goal

attach to main app:

2) usage in maven project (pom.xml)
```
        <dependency>
            <groupId>ru.otus.sua</groupId>
            <artifactId>statistic</artifactId>
            <version>1.0</version>
            <scope>compile</scope>
            <type>jar</type>
        </dependency>
```


3) attach in web.xml
``` 
    <filter>
        <filter-name>StatisticFilter</filter-name>
        <filter-class>ru.otus.sua.statistic.StatisticFilter</filter-class>
        <init-param>
            <param-name>ru.otus.sua.statistic.ENABLED</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
```

4) attach class in persistems.xml
```
    <persistence-unit name="JPAPersistenceUnit7" transaction-type="RESOURCE_LOCAL">
        ...
        <class>ru.otus.sua.statistic.StatisticEntity</class>
        ...
    </persistence-unit>
```    

5) servlet from statictics package
`@WebServlet(name = "StatServiceServlet", urlPatterns = "/statistic")`

6) custom tag
` `

7) report statisticView.jsp page
    jar integrated. 
    access by forward from StatServiceServlet

8) servlet response: json
` `

