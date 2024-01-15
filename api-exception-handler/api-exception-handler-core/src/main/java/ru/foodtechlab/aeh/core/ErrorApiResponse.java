package io.foodtechlab.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class ErrorApiResponse<Error> {
    //Массив ошибок
    private List<Error> errors;

    //Статус
    private int status;

    //Путь запроса, вызвавшего исключение
    private String path;

    //Время ошибки
    private Instant timestamp = Instant.now();

    //Идентификатор для отслеживания (полезно для поиска в логих)
    private String traceId;

    public ErrorApiResponse(List<Error> errors, int status) {
        this.errors = errors;
        this.status = status;
    }

    public ErrorApiResponse(List<Error> errors, int status, String path, String traceId) {
        this.errors = errors;
        this.status = status;
        this.path = path;
    }

    public static <Error> ErrorApiResponse<Error> of(List<Error> errors, int status, String path, String traceId) {
        return new ErrorApiResponse<>(errors, status, path, traceId);
    }

    public static <Error> ErrorApiResponse<Error> badRequest(List<Error> errors, String path, String traceId) {
        return new ErrorApiResponse<>(errors, 400, path, traceId);
    }

    public static <Error> ErrorApiResponse<Error> unauthorized(List<Error> errors, String path, String traceId) {
        return new ErrorApiResponse<>(errors, 401, path, traceId);
    }

    public static <Error> ErrorApiResponse<Error> tooManyRequest(List<Error> errors, String path, String traceId) {
        return new ErrorApiResponse<>(errors, 429, path, traceId);
    }

    public static <Error> ErrorApiResponse<Error> forbidden(List<Error> errors, String path, String traceId) {
        return new ErrorApiResponse<>(errors, 403, path, traceId);
    }

    public static <Error> ErrorApiResponse<Error> notFound(List<Error> errors, String path, String traceId) {
        return new ErrorApiResponse<>(errors, 404, path, traceId);
    }

    public static <Error> ErrorApiResponse<Error> internalServerError(List<Error> errors, String path, String traceId) {
        return new ErrorApiResponse<>(errors, 500, path, traceId);
    }

}
