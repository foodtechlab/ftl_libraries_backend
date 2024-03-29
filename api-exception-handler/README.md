# API Exception Handler

Данная библиотека объявляет базовую модель ошибки в Response и реализует механизм перехвата исключений в API.

Механизм @ExceptionHandler в Spring позволяет обрабатывать исключения в приложении.
Он позволяет определить методы-обработчики, которые будут вызываться при возникновении определенного типа исключения.
Это позволяет централизованно управлять обработкой ошибок в приложении и возвращать пользователю информативные сообщения об ошибках.

Модуль `api-exception-handler-api` содержит базовый `ExceptionHandler` для перехвата исключений.

### Подключение
Требуется добавить в слой `api` или `application` следующую зависимость.

```xml
<dependency>
    <groupId>io.foodtechlab</groupId>
    <artifactId>api-exception-handler-api</artifactId>
</dependency>
```

### История изменений
[История изменений](CHANGELOG.md)