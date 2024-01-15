# FTL Additional Base Entities Lib Backend #

**Version: 1.2**

**Автор: Петр Зелинский**

Библиотека, которая содержит в себе дополнительные сущности, расширяющие BaseEntity из rcore. Дополнительно содержит
сценарии, валидации, ошибки.

[Ссылка на origin](https://bitbucket.org/sushiveslarf/ftl_additional_base_entities_lib_backend)

[Последние изменения](CHANGELOG.md)

## Четыре основных модуля

В библиотеке содержится четыре основных модуля, которые могут использоваться как вмести, так и раздельно

- common-entities-api
- common-entities-core
- common-entities-domain
- common-entities-mongo

Модули API, Mongo, Domain должны использоваться в соответствующих слоях. А вот модуль Core может подключаться к любому
слою и в нём можно реализовывать универсальные (не зависимые от слоя)
сущности и типы данных.

## Как подключить ##

Библиотека подключается через nexus, поэтому у тебя должен быть к нему доступ

* добавляем common-entities-backend в список серверов (файла .m2/settings.xml)
* в pom добавляем `<repository>`:

```xml

<repositories>
    <repository>
        <id>common-entities-backend</id>
        <url>http://nexus.foodtech-lab.ru/repository/ftl-maven-libs-group/</url>
    </repository>
</repositories>
```

* в pom добавляем зависимость:

```xml

<!--Модуль с расширенными сущностями и кейсам-->
<dependency>
    <groupId>io.foodtechlab</groupId>
    <artifactId>common-entities-domain</artifactId>
    <version>${abe.version}</version>
</dependency>
        <!--Модуль с расширенными сущностями и кейсам-->
```

> Подробнее про подключение
> библиотек: [тут](https://www.notion.so/foodtech/Use-Nexus-5e55aef2f84a4a32a3c49dd1ecf8850c#e6b1fde9b5604c5884e203868923caf4)

## Как пользоваться ##

Расширяй классы сущностей одной из сущностей библиотеки

#### Примеры кода ####

```java
public class CityEntity extends BaseDeleteEntity<String> {

    private String id;
    private String name;

    public CityEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

в Application.java:

```java
public class Application {
    public static void main(String[] args) {
        CitiyEntity city = new CityEntity("1", "Moscow");
        System.out.println(city.isDeleted()); // возвращает false
    }
}
```

```java
public class HouseEntity extends BaseExternalEntity<String> {
}
```

в Application.java

```java
public class Application {
    public static void main(String[] args) {
        HouseEntity house = new HouseEntity();

        house.setExternalLinks(Arrays.asList(
                new ExternalLink("234b14787dacc", "type", "ab1247f87d65c", LocalDate.now()),
                new ExternalLink("234b14787dacc", "type", "ab1247f87d65c", LocalDate.now())
        ));
    }
}
```

## Сущности удаления и синхронизации

У сущностей есть интерфейс по типу `Property`, он необходим для того чтоб можно было определить каким свойством обладает
класс.

```java
public abstract class BaseDeleteEntity<Id> extends BaseEntity<Id> implements DeleteProperty {
}
```

### Удаление и Восстановление

**BaseDeleteEntity** Содержит поля для мягкого удаления, т.е. объект не удаляется, а помечается удалённым.

Для облегчения работы с этими сущностями можно использовать абстрактные сценарии для

- удаления (`AbstractDeleteDeletedUseCase`)
- восстановления (`AbstractRestoreDeletedUseCase`)
- обновления (`AbstractUpdateDeletedUseCase`)

##### Абстрактные сценарии удаление и восстановление

Для классов удаления есть абстрактные аналоги
(`AbstractDeleteDeletedWithEventUseCase` и `AbstractRestoreDeletedWithEventUseCase`), которые поддерживают систему
событий. При удалении и восстановлении выбрасывают соответствующие события в EventDispatcher

Добавляя сценарии удаления или восстановления достаточно просто унаследовать сценарий от соответствующих классов. При
использовании сценариев с событиями необходимо реализовывать метод, который создаёт событиями

```java
    @Override
public TopicCategoryRestoredEvent createEvent(TopicCategoryEntity entity){
        return new TopicCategoryRestoredEvent(entity);
        }
```

Для удобной работы с событиями есть два абстрактных события `EntityDeletedEvent` и `EntityRestoredEvent` соответственно

##### Обновление

Особенностью обновления удалённых сущностей является то что, **мы не можем обновлять удалённые сущности**. Абстрактный
класс удаления проверяет перед обновлением не удалён ли объект, если он удалён выбрасывает
исключение `EditDeletedEntityException`, а если объект представлен то продолжает обновление.

### Сущность синхронизации и гибрид

**External Entity** Необходим для синхронизации со внешними системами, содержит массив указателей с id на внешние
системы

**External Delete** Объединение первых двух объектов

### Mongo

Аналогичны сущностям из модуля `domain`, только для базового класса документа

## Расширенное удаление

Абстрактный класс `AbstractExtendedDeleteUseCase` реализующий механизм стандартного удаления и предоставляющий для
переопределения два метода

- `boolean before(Entity entity)` вызывается перед удалением. Если этот метод вернёт false удаление не произойдёт
- `void after(Entity entity)` вызывается после успешного удаления. Можно использовать для отправки событий.

Класс требует указать тип `Id`, тип `Entity` удаления и полный CRUD репозиторий

## Модуль Core

#### Location

Сущность предназначена для хранения местоположения чего-либо с помощью latitude и longitude. У сущности переопределённые
методы equals и hashCode, поэтому можно спокойно сравнивать их между собой

#### SortedData

Представляет собой структуру данных, предназначенную для хранения других сущностей в определённой последовательности.
Сущность хранящаяся в SortedData **обязательно** должна переопределять метод equals и hashcode, для этого лучше всего
подходят сущности с аннотацией @Value

#### PhoneNumber

Хранит в себе номер телефона в формате long, а так же enum код страны. Так же можно сравнивать с помощью equals

#### QuarterRange

Класс утилита возвращает `LocalDateTime` начала квартала (метод `start`) и `LocalDateTime` окончания квартала (метод `end`)  

### Api ###

#### Location ####

Есть модели запроса и ответа для сущности `Location`. Они покрыты аннотациями для **Swagger**, так же у нах есть методы
трансформации в сущность (для запроса) и методы трансформации в из сущности для ответа.

## DateTimeObject ##

Это объект, который находится в модуле core. Он отвечает за развёрнутое представление даты в БД. При этом сам объект
никак не зависит от баз данных и его можно использовать в любом модуле

### Создание ###

Для создания необходимо указывать временную зону даты и времени. Это необходимо для того чтобы мы знали не только
мгновение, но и время в указанном часовом поясе.

```java

ZoneId zoneId=ZoneId.of("UTC");

        Instant instant=Instant.now();
        DateTimeObject byInstant=DateTimeObject.of(instant,zoneId);

        Date date=new Date();
        DateTimeObject byDate=DateTimeObject.of(date,zoneId);

        DateTimeObject byMillis=DateTimeObject.of(instant.toEpochMilli(),zoneId);
```

### Создание критериев ###

В модуле с базами данными `common-entities-mongo` есть класс который помогает быстро создавать критерии для
поиска в mongo по этому объекту.
`DateTimeObjectCriteria` позволяет для поля создать критерии от и до, по range. В качестве аргументов можно
использовать `LocalDate`, `LocalTime` и `LocalDateTime`

```java
Optional<Criteria> criteria=DateTimeObjectCriteria
        .withField("createdDate") //Указываем название поля по которому выполняется поиск
        .byLocalDateTimeRange(filter.getFrom(),filter.getTo()); //Дата от и дата до
```

В ответе возвращается опциональный объект `Optional`. При это пустой вернётся в том случае если один из аргументов
даты `null`.

## Правила контрибуции ##

Если вам необходимо расширить эту библиотеку, то

1. Выберите самую крайнюю ветку релиза или снапшота
2. Создайте новую ветку под ваши изменения
3. После завершения работы, создайте Пул Реквест на крайнюю ветку релиза или снапшота и в ревьюера выберете Владислава
   Кирьянова
4. Он проведёт валидацию кода и сохранит целостность библиотеку

По возможности документируйте свой код и добавляйте описания в README

## Связь со мной ##

**Владислав Кирьянов**

Slack: [kiryanovvi](https://foodtech-lab.slack.com/archives/DUFCDQ2MD)

Email: kiryanovvi@sushivesla.team

## Активные контрибьюторы ##

**Петр Зелинский:**  (slack: [zelinskiypk](https://foodtech-lab.slack.com/archives/D01LJ4SNQ07) | email:
zelinskiypk@sushivesla.team)

**Илья Усков:**  (slack: [uskovid](https://foodtech-lab.slack.com/archives/D01BAM6UTPY) | email:
zelinskiypk@sushivesla.team)

**Роман Зелинский:**  (slack: [zelinskiyrk](https://foodtech-lab.slack.com/archives/D01M1LE20UR) | email:
zelinskiyrk@sushivesla.team)

**Владислав Кирьянов:**  (slack: [kiryanovvi](https://foodtech-lab.slack.com/archives/DUFCDQ2MD) | email:
kiryanovvi@sushivesla.team)