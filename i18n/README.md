# FTL Spring I18N Helper Lib

**`1.2-SNAPSHOT`**

--------
Данная библиотека предназначена для упрощения работы со статической локализацией в Spring Boot

## I18N

В Spring есть поддержка мультиязычности.
Для этого, в папке resources нужно создать файлы messages_locale.properties для поддерживаемых локализаций.
Например, _messages_ru_RU.properties_, _messages_en_US.properties_ и тд.
Состав файла произвольный, но суть в том, чтобы было просто искать по этому файлу.

## Простая локализация

В том случае если необходимо локализировать исключение, которые возвращает наше приложение, можно
воспользоваться простым классом локализации `I18NHelper`.
Он позволяет находить локализацию текста по следующему шаблону ошибок для message.properties следующий:

```properties
ExceptionClassSimpleName.title=Тайтл
ExceptionClassSimpleName.message=Сообщение
```

Такой формат связан с принятой в FTL моделью ошибок, в которой title и message - локализованные поля для пользователя.

Создав локализации мы можем сделать _ExceptionHandler_ для отлова исключений и перевода в формат, принятый в приложении.

```java

@RequiredArgsConstructor
@RestControllerAdvice
public class BasicExceptionHandler {

    private final I18NHelper i18NHelper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleException(Exception e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.internalServerError(
                Arrays.asList(Error.of(i18NHelper.getMessage(e, locale), i18NHelper.getTitle(e, locale), "domain", "details")),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }
}
```

Теперь, в зависимости от переданного в запросе Accept-Language будет подтягиваться необходимая локализация.

Статическую локализацию можно использовать не только при обработке исключений, но и в любом месте в коде, где это
требуется.

## Переводчики

Помимо обыкновенной локализации и локализации исключений можно использовать механизм переводчиков.
Переводчики добавляют к обыкновенному механизму локализации, возможность встраивать переменные в локализацию.
Так же, переводчики позволяет реализовать специфичные для региона правила грамматики и синтаксиса.

Например, вам нужно вывести следующую строку _"Стоимость товара 19 рублей"_, где стоимость будет передаваться в строку
числом.
Тогда слово рублей, должно уметь склонятся в зависимости от числа которое было передано (21 рубль или 23 рубля).
Это и подобные правила можно задавать внутри класса переводчика

### Установка

Подключите следующую зависимость в maven

```xml

<dependency>
    <groupId>io.foodtechlab</groupId>
    <artifactId>i18n-translator</artifactId>
    <version>${revision}</version>
</dependency>
```

Так же вам будет необходимо создать файлы `localization_locale.properties`.
Например, _localization_ru_RU.properties_, _localization_en_US.properties_ и тд.
Если создать файл `localization.properties` он будет использован по умолчанию.
В этих файлах вы можете настраивать локализацию в любом формате, но для переводчиков рекомендовано придерживаться
следующего формата

```properties
TRANSLATION_CODE.text=Перевод
```

С таким форматом по умолчанию работает абстрактный класс `LocalizationI18NTranslator`.
Так же именно этот клас настроен на работу с `localization_locale.properties` типами файлов

### Создание определителя переводчика

Для того чтобы вы могли использовать переводчики вам необходимо в конфигурации создать бин определителя
переводчика `I18NTranslatorQualifier`.
Этот класс будет по переданной ему локали определять какой переводчик требуется.
Если по переданной локали не подберётся подходящего переводчика, то будет выбран переводчик по умолчанию.

```java
    @Bean
public I18NTranslatorQualifier i18NTranslatorQualifier(MessageSource localizationSource){
        return I18NTranslatorQualifierBuilder
        .init()
        .add(new RuLocalizationI18NTranslator(localizationSource))
        .setDefault(new DefaultLocalizationI18NTranslator(localizationSource))
        .build();
        }
```

Далее для того, чтобы получить переводчик, необходимо воспользоваться методом `i18NTranslatorQualifier.get(locale)`

### Использование переводчика

После того как вы получите экземпляр класса переводчика вы можете использовать два метода `translate()`.
Оба этих метода позволяют находить необходимый перевод в файле локализации по коду перевода.

```java
//EMPTY.text=Empty cart

String translate=i18NTranslatorQualifier.get(Locale.ENGLISH).translate("EMPTY");
        assertEquals("Empty cart",translate);
```

Но второй метод на вход принимает дополнительный параметр `Map<String, T>`, словарь инъекций.
Значения из этого словаря будут встроены в переводимый текст.
Например: `PROFILE.text=Name is {name}; Age is {age}`

```java
Map<String, String> injections=new HashMap<>();
        injections.put("name","Vlados");
        injections.put("age","25");

        String translate=i18NTranslatorQualifier.get(Locale.ENGLISH).translate("PROFILE",injections);
        assertEquals("Name is Vlados; Age is 25",translate);
```

Обратите внимание, что в словарь передаются ключи инъекций без фигурных скобок `{}`, а в файле с переводами фигурные
скобки определяют место инъекции.

### Создание своих собственных переводчиков

Для того чтобы создать своего собственного переводчика, необходимо имплементировать интерфейс I18NTranslator.
В имплементации нужно будет задать источник перевода, а так же реализовать методы

- `isRightTranslator(Locale locale)` используется для определения подходящего переводчика
- `getLocale()` возвращает локаль, не может возвращать null;
- `translate(String code)` находит перевод по коду и адаптирует его под язык
- `translate(String code, Map<String, T> injections)` аналогично первому, но помимо прочего поддерживает механизм
  инъекций

Намного более предпочтительным вариантом является наследование абстрактного класса `LocalizationI18NTranslator`.
Там уже заданны источник, а так же реализован метод `getText(String code)`, который возвращает перевод из `.text`, а так
же методы обёрток для ключей инъекций
`toInjectionKey(String key)`

### Переводчик по умолчанию

Для простоты работы уже созданы два базовых переводчика `DefaultLocalizationI18NTranslator`
и `RuLocalizationI18NTranslator`.

Стандартный переводчик просто берёт текст из файла перевода, а так же выполняет инъекции.
Никакой дополнительной логики в него не заложено

### Переводчик на русский

`RuLocalizationI18NTranslator`, переводчик на русский помимо стандартных функций, так же умеет устанавливать необходимое
склонение к слову в зависимости от переданного в инъекцию числа.
Число должно быть при этом целочисленным.

Для того чтобы инъекция склоняло слово нужно задать конфигурацию в файле с локализацией следующим образом

```properties
#{price} - инъекция числа
#{price:case} инъекция варианта слова для этого числа
TRANSLATION_CODE.text=Стоимость латте {price} {price:case}
#Если установлен флаг true, то данную строку нужно обработать применяя правила склонения
TRANSLATION_CODE.hasNumberCase=true
#через запятую перечисляются поля для которых, необходима инъекция со склонением (Например: price,age,year)
TRANSLATION_CODE.caseFields=price
#Теперь используя в качестве параметра название ключа инъекции перечисляются варианты склонения
#Их обязательно должно быть три, в порядке 1 рубль, 2 рубля, 5 рублей
TRANSLATION_CODE.price=рубль,рубля,рублей
```

### Совместимость

Подразумевается что этот модуль будет работать совместно с модулем перевода исключений.
Оба этих модуля используют одни и те же бины `SourceMessage`, но оба модуля ссылаются на свои определённые, с помощью
bean qualifier.
Так что конфликтов при использовании быть не должно.
Так же стоит обратить внимание что это относится к `ftl-spring-i18n-helper-lib` версии 1.2 и выше

Отдельно внимание хочу обратить на название использования бинов типа `SourceMessage`.
Используя эти бины в своих проектах обязательно указывайте в качестве названия переменной именно тот бин который вам
нужен

```java
//Localization (для работы с локализацией)
private final SourceMessage localizationMessage;
//Messages (для работы с ошибками)
private final SourceMessage sourceMessage;

//Если вы не будете использовать @Qualifier и укажите в качестве название переменой что-то другое, 
// это вызовет конфликты при сборке приложения
private final SourceMessage source;
```