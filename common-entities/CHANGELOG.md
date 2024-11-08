## 4.1.3 (08.11.2024)

----

### Release Highlight

### Features

### Fixes
- В метод `addExternalLink` класса `BaseExternalDeleteEntity`добавлена логика очистки 
внешних ссылок не имеющих id, как уже созданных, так и новых.

### Docs

## 4.1.2 (18.04.2024)

----

### Release Highlight

- Добавлен новый объект `TimeObject` для работы со временем (Без учета зоны)
- `TimeObject` имеет два статических вабричных метода `of` и `ofNanoOfDay`
- `of` принимает на вход `LocalTime`
- `ofNanoOfDay` принимает на вход `Long` (значение в наносекундах)

### Features

### Fixes

### Docs


## 4.0.1 (26.03.2024)

----

### Release Highlight ###
- Добавлены поля FormattedLocalDateTime и TimeZoneProperties в класс DateTimeObject для хранения дополнительной 
  информации о времени и временных зонах. Написаны тесты в DateTimeObjectTest для проверки корректности создания 
  объекта DateTimeObject на основе различных вариантов времени и временных зон.
- Изменен класс PhoneNumber:
  - Удалены поля phoneNumber, countryCode, phoneNumberInternational, phoneNumberE164. 
  - Добавлены поля value (значение телефонного номера в формате E.164), isoTwoLetterCountryCode
   (ISO 3166-1 alpha-2 код страны), type (тип телефонного номера), valid (флаг, указывающий, является ли телефонный 
   номер действительным и валидным для указанной страны, invalidReason(информация о том, почему номер не прошел валидацию)).
  - Изменен конструктор класса и метод setPhoneNumber для установки телефонного номера и кода страны. 
  - Добавлен метод parsePhoneNumber для парсинга и форматирования телефонного номера. 
  - Удален метод format, так как он больше не используется.
- Изменен класс CountryCode:
  - Переименован в Country
  - Удалены поля prefix, prefixString, countryName, phoneMask, phoneLength. 
  - Добавлены поля isoTwoLetterCountryCode (ISO 3166-1 alpha-2 код страны), phoneCodes (коды стран для телефонных номеров), 
    phonePrefixes (префиксы номеров для страны). 
  - Изменен метод parse на findByPhoneNumber для поиска кода страны по телефонному номеру. 
  - Добавлен метод normalizePhoneNumber для нормализации телефонного номера. 
- Изменен класс PhoneNumberTest:
  - Добавлены и изменены тесты для проверки различных сценариев работы с телефонными номерами, включая действительные 
    и недействительные номера, номера без указания кода страны, номера с различным форматированием. 
  - Добавлены тесты для проверки методов equals и hashCode.

## 1.2 (10.11.2022)

----

### Release Highlight

**Первый changelog** 🎉

Расширен класс `ExternalLink`. Переопределены методы `equals` и `hashCode`.
Теперь мы можем сравнивать два экземпляра `ExternalLink` по-содержимому пользуясь методом equals.
Так же теперь мы можем использовать `HashSet`, для того чтобы собирать уникальную коллекцию.

Сравнение происходит по полям `id` и `type`

### Features

- Переопределены методы `equals` и `hashCode` для класса `ExternalLink`

### Docs

- Добавлена документация класса `QuarterRange`

## 1.1.1 (15.08.2022)

----

### Release Highlight

Теперь время и дата удаления для объектов `BaseDeleteEntity` сохраняется в поле `deletedAt`

Исправления работы класса утилиты `QuarterRange`. Метод `end` возвращал время начала дня, а требовалось время окончания
дня

### Features

- Добавлено сохранение времени удаления для `BaseDeleteEntity`

### Fixes

- Исправления метода end класса `QuarterRange`
- Методы работы с `DateTimeObject` в `DateTimeObjectCriteria` помечены как `Deprecated`. При работе они вызывают ошибки

### Docs

- Расширил описание методов `DateTimeObjectCriteria`, указал какие ошибки они вызывают
