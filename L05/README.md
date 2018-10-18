### Практическая работа «Статичный и динамичный HTML» по курсу JavaEE.

- [x] Вёртска HTML-страниц.
  - [x] шапка
  - [x] горизонтальное меню (Главная, Вход в систему и еще любые 3 раздела)
  - [x] Блок основного контента (уникален для каждого раздела сайта)
  - [x] Блок бокового меню ~~(вывести актуальную информацию о курсе валют)~~
  - [x] Блок бокового меню (вывести актуальную информацию о курсе валют)
  - [x] подвал
  - [x] на странице Входа в систему должны присутствовать стандартные элементы для ввода Логина и пароля пользователя, а также кнопка Submit, которые расположены в html-форме.
  - [x] для страницы авторизации необходимо предусмотреть валидацию входных данных.         
  - [x] предусмотреть возможность поиска в Шапке сайта путем перехода на
        предпочитаемую поисковую систему с данными переданными в данном текстовом поле.

- [x] Извлечение данных посредством Jsoup.  создание поискового crawler.
  - [x] получить информацию о новостях с новостного сайта (например, rbc.ru) . Полученный список
        заголовков новостей с соответствующими переходами вернуть в виде JSON-ответа сервлета и
        последующего вывода данной информации в блоке бокового меню средствами Javascript.

- [x] Вызов JS на server side.
  - [x] создать сервлет, предоставляющий возможность исполнения JS на серверной стороне.
  - [x] доступ после ввода логина и пароля 123 123

### Решение

http://localhost:8080/L05/index.html

``` 
валидация логин/пароля

http://localhost:8080/L05/login

login:123abcd
password:123456789
[ConstraintViolationImpl{interpolatedMessage='Password must contain at max 8 characters.', 
propertyPath=password, rootBeanClass=class ru.otus.sua.L05.validation.User, 
messageTemplate='Password must contain at max 8 characters.'}]

login:123
password:123
VALID

===================================
после интернационализации сообщений

login:123
password:223456789
-----
password: '223456789' должен содержать от 3 до 8 символов


```

```
js serverside

http://localhost:8080/L05/execute

GRANTED JS EXECUTE
null

hello from js to tomcat console
```

``` 
курс валют
любая страница http://localhost:8080/L05  

CurrencyServlet:34
INFO [http-nio-8080-exec-100] org.apache.catalina.core.ApplicationContext.log 
        {"currency_list" : [
        
                    {
                    "id": "R01235",
                    "char_code": "USD",
                    "name": "Доллар США",
                    "value": "65,4026",
                    "nominal": "1"
                    }
                
                    , {
                    "id": "R01239",
                    "char_code": "EUR",
                    "name": "Евро",
                    "value": "75,6512",
                    "nominal": "1"
                    }
                
                    , {
                    "id": "R01375",
                    "char_code": "CNY",
                    "name": "Китайских юаней",
                    "value": "94,4469",
                    "nominal": "10"
                    }
                
        ]}

```

``` 
новости по jsoup
любая страница http://localhost:8080/L05  

NewsServlet:37
INFO [http-nio-8080-exec-32] org.apache.catalina.core.ApplicationContext.log 
{"news":[{"text":"Власти: вода на Большой Подгорной отступает","href":"http://www.tomsk.ru/news/view/137451"},
{"text":"«Томь» оштрафовали за задержку матча","href":"http://www.tomsk.ru/news/view/137450"},
{"text":"Жильцы на Большой Подгорной боятся, что вода попадет в дом","href":"http://www.tomsk.ru/news/view/137449"},
{"text":"Томские ученые будут исследовать шизофрению","href":"http://www.tomsk.ru/news/view/137444"}, 
..skip..   ,{"text":"Со студенческой скамьи на работу мечты","href":"http://www.tomsk.ru/news/view/137258"}]}
```