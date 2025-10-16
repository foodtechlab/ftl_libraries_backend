package io.foodtechlab.api;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Перечисление дополнительных HTTP заголовков используемых в приложении.
 * Здесь перечисляются заголовки которые, отличаются от стандартных заголовков.
 */
public class AppHttpHeaders {

    /**
     * Дата которую должно передавать устройство в
     * {@link DateTimeFormatter#RFC_1123_DATE_TIME} формате.
     */
    public static final String X_DATE = "X-Date";

    /**
     * Токен для обеспечения безопасности,
     * который выдаётся устройству в самом начале работы с API.
     * <p>
     * Как правило, обязательный.
     */
    public static final String X_SIGN = "X-Sign";

    /**
     * Тип устройства с которого выполняется запрос.
     * <p>
     * Как правило, обязательный.
     */
    public static final String X_DEVICE_TYPE = "X-Device-Type";

    /**
     * Идентификатор города в котором выполняется запрос.
     * У нас разные интеграции завязаны на конкретные города,
     * поэтому для нас важно понимать в для какого города выполняется запрос.
     */
    public static final String X_CITY_ID = "X-City-ID";

    /**
     * Информация о текущей версии приложения установленной на устройстве.
     */
    public static final String X_APP_VERSION = "X-App-Version";

    /**
     * Информация об операционной системе устройства.
     */
    public static final String X_OS_VERSION = "X-OS-Version";

    /**
     * Токен устройства, генерируемый на стороне устройства.
     */
    public static final String X_DEVICE_TOKEN = "X-Device-Token";

    /**
     * Код для проверки пароля генерируемого на основе даты.
     */
    public static final String X_VERSION = "X-Version";

    /**
     * Указывает, откуда пришел пользователь (например, google, yandex, facebook, email).
     * <p>
     * UTM-метка (Urchin Tracking Module), используется для отслеживания эффективности маркетинговых кампаний.
     */
    public static final String X_UTM_SOURCE = "X-UTM-Source";

    /**
     * Определяет тип трафика или маркетинговый канал. Например: cpc — контекстная реклама (Google Ads, Яндекс.Директ),
     * email — email-рассылка и др.
     * <p>
     * UTM-метка (Urchin Tracking Module), используется для отслеживания эффективности маркетинговых кампаний.
     */
    public static final String X_UTM_MEDIUM = "X-UTM-Medium";

    /**
     * Позволяет идентифицировать конкретную рекламную кампанию.
     * <p>
     * UTM-метка (Urchin Tracking Module), используется для отслеживания эффективности маркетинговых кампаний.
     */
    public static final String X_UTM_CAMPAIGN = "X-UTM-Campaign";

    /**
     * Заголовок X-Requested-With используется в HTTP-запросах для указания типа запроса и его источника.
     * <p>
     * Чаще всего заголовок имеет значение XMLHttpRequest, что указывает на то, что запрос был отправлен с помощью JavaScript (например, через fetch или XMLHttpRequest).
     */
    public static final String X_REQUESTED_WITH = "X-Requested-With";

    /**
     * Хранит в себе токен авторизации в формате JWT подписанный сервисом авторизации.
     */
    public static final String X_AUTH_TOKEN = "X-Auth-Token";

    /**
     * Для идентификации департамента отправившего запрос.
     * <p>
     * В контексте нашей системы это считается ресторанном.
     */
    public static final String X_DEPARTMENT_ID = "X-Department-Id";

    /**
     * Для идентификации департамента отправившего запрос.
     * <p>
     * В контексте нашей системы это считается ресторанном.
     */
    public static final String X_APPLICATION_PLATFORM = "X-Application-Platform";

    /**
     * Перечисление всех кастомных хедеров
     */
    public static final List<String> ALL = Arrays.asList(
            X_DATE,
            X_SIGN,
            X_DEVICE_TYPE,
            X_APP_VERSION,
            X_DEVICE_TOKEN,
            X_VERSION,
            X_OS_VERSION,
            X_APP_VERSION,
            X_CITY_ID,
            X_UTM_SOURCE,
            X_UTM_MEDIUM,
            X_UTM_CAMPAIGN
    );
}
