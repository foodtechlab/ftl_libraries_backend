# Таблица совместимости

## Пакеты и группа

Все пакеты переименованы из `ru.foodtechlab` в `io.foodtechlab`. Это же касается groupId maven.

Было
```xml
<dependency>
    <groupId>ru.foodtechlab.i18n</groupId>
    <artifactId>i18n</artifactId>
    <version>1.8.1</version>
</dependency>
```

Стало
```xml
<dependency>
    <groupId>io.foodtechlab</groupId>
    <artifactId>i18n</artifactId>
    <version>4.0.0-SNAPSHOT</version>
</dependency>
```

## Версия

Версия всех библиотек теперь едина и начинается с 4.0.0.

## Таблица соответствия

Далее будет таблица соответствия предыдущих названий модулей с новыми.

### api-exception-handler-api

|          | Предыдущее                 | Новое                        |
|----------|----------------------------|------------------------------|
| Название | ftl-api-error-handling-lib | api-exception-handler        |
| GroupId  | ru.foodtechlab.aeh         | io.foodtechlab               |
| Модули   | core                       | api-exception-handler-api    |
|          | spring-api-error-handling  | api-exception-handler-core   |
| Пакет    | ru.foodtechlab.aeh         | io.foodtechlab.exceptionable |

### common-entities

|          | Предыдущее                       | Новое                  |
|----------|----------------------------------|------------------------|
| Название | ftl-additional-base-entities-lib | common-entities        |
| GroupId  | ru.foodtechlab.abe               | io.foodtechlab         |
| Модули   | additional-base-entities-api     | common-entities-api    |
|          | additional-base-entities-core    | common-entities-core   |
|          | additional-base-entities-domain  | common-entities-domain |
|          | additional-base-entities-mongo   | common-entities-mongo  |
| Пакет    | ru.foodtechlab.abe               | io.foodtechlab.common  |

### data-replication

|          | Предыдущее                  | Новое                      |
|----------|-----------------------------|----------------------------|
| Название | ftl-data-replication-helper | data-replication           |
| GroupId  | ru.foodtechlab              | io.foodtechlab             |
| Пакет    | ru.foodtechlab.replication  | io.foodtechlab.replication |

### domain-beans

|          | Предыдущее                  | Новое                      |
|----------|-----------------------------|----------------------------|
| Название | rcore-spring-lib            | domain-beans               |
| GroupId  | ru.foodtechlab              | io.foodtechlab             |
| Пакет    | ru.foodtechlab.rcore.spring | io.foodtechlab.domainbeans |

### exception-converter

|          | Предыдущее                          | Новое                              |
|----------|-------------------------------------|------------------------------------|
| Название | checked-exceptions                  | exception-converter                |
| GroupId  | ru.foodtechlab.lib                  | io.foodtechlab                     |
| Модули   | converter                           | exception-converter-core           |
|          | all                                 | exception-converter-configuration  |
|          | catalog                             | УДАЛЁН                             |
|          | domain                              | exception-converter-domain         |
|          | handler                             | exception-converter-handler        |
|          | rcore                               | exception-converter-rcore          |
|          | resource                            | exception-converter-resource       |
|          | rcore:client                        | exception-converter-rcore-client   |
|          | rcore:domain                        | exception-converter-rcore-domain   |
|          | rcore:handler                       | exception-converter-rcore-handler  |
|          | rcore:resource                      | exception-converter-rcore-resource |
| Пакет    | ru.foodtechlab.lib.checkedException | io.foodtechlab.exception.converter |

### i18n

|          | Предыдущее                 | Новое               |
|----------|----------------------------|---------------------|
| Название | Cell 2                     | i18n                |
| GroupId  | ru.foodtechlab.i18n        | io.foodtechlab      |
| Модули   | ftl-spring-i18n-helper-lib | i18n-helper         |
|          | ftl-spring-i18n-core       | i18n-core           |
|          | ftl-spring-i18n-translator | i18n-translator     |
| Пакет    | ru.foodtechlab.i18n        | io.foodtechlab.i18n |

### microservice-integration

|          | Предыдущее                               | Новое                                   |
|----------|------------------------------------------|-----------------------------------------|
| Название | ftl-microservice-integration-helpers-lib | microservice-integration                |
| GroupId  | ru.foodtechlab.mih                       | io.foodtechlab                          |
| Модули   | messaging                                | microservice-integration-messaging      |
|          | rest-api                                 | microservice-integration-rest-api       |
|          | integration-channel                      | УДАЛЁН                                  |
|          | kafka-commons                            | kafka-commons                           |
|          | feign-commons                            | feign-commons                           |
|          | feign-secure-commons                     | feign-secure-commons                    |
| Пакет    | ru.foodtechlab.mih.channel               | io.foodtechlab.microservice.integration |

### tests-helper

|          | Предыдущее            | Новое                 |
|----------|-----------------------|-----------------------|
| Название | ftl-test-halper-lib   | tests-helper          |
| GroupId  | ru.foodtechlab.tests  | io.foodtechlab        |
| Модули   | kafka-tests-helper    | kafka-tests-helper    |
|          | wiremock-tests-helper | wiremock-tests-helper |
| Пакет    | ru.foodtechlab.tests  | io.foodtechlab.tests  |
