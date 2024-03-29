# FTL Microservice Integration Helper Lib

Помощник в интеграции с Kafka и Feign

## Feign

---

Начиная с `4.0.0` версии в модулях `feign-commons` реализованы
стандартные обработчики ошибок и конфигурация feign

Модуль без интеграции сервиса авторизации

```maven
<dependency>
    <groupId>io.foodtechlab</groupId>
    <artifactId>feign-commons</artifactId>
</dependency>
```

> !!!! Модуль для sdk с нашей системой авторизации был перенесён в Auth-Libraries

```maven
<dependency>
    <groupId>io.foodtechlab</groupId>
    <artifactId>auth-integration-restapi-feign-commons</artifactId>
</dependency>
```

### Использование

#### ErrorDecoder ####

Подразумевается что вы будете использовать базовую конфигурацию дополняя её своей.

В первую очередь вы должны дополнить `ExampleErrorDecoder` обработкой своих ошибок.
Как правило, этого не требуется, но вы так же можете переопределить методы `decode` для того чтоб
ваш `ErrorDecoder` выбрасывал ваши `Exception`.

```java
public class CategoryRequestErrorDecoder extends ExampleErrorDecoder {
    public CategoryRequestErrorDecoder(ObjectMapper mapper, AccessTokenService accessTokenService) {
        super(mapper, accessTokenService);
    }
}
```

Если ваш сервис использует систему авторизации, то должен быть унаследован от `AuthorizationErrorDecoder`

```java
public class CategoryRequestErrorDecoder extends AuthorizationErrorDecoder {
    public CategoryRequestErrorDecoder(ObjectMapper mapper, AccessTokenService accessTokenService) {
        super(mapper, accessTokenService);
    }
}
```

#### Example Config ####

Во вторых вы должны дополнить свой конфиг унаследовав его от базового.
Чаще всего нужно просто указать там бин для `ErrorDecoder`, но вы так же можете регистрировать любые бины необходимые
для работы `feign`

```java
public class CategoryRequestConfig extends ExampleSecureConfig {
    @Bean
    public ErrorDecoder feignErrorDecoder(
            ObjectMapper mapper,
            AccessTokenService accessTokenService
    ) {
        return new CategoryRequestErrorDecoder(mapper, accessTokenService);
    }
}
```

После этого вы можете применить ваши конфиги объявляя sdk

```java

@FeignClient(name = "feedback-category-request-sdk-client", url = "${foodtechlab.infrastructure.microservice.feedback.url}", configuration = CategoryRequestConfig.class)
public interface CategoryRequestClient extends CategoryRequestResource {
}
```
