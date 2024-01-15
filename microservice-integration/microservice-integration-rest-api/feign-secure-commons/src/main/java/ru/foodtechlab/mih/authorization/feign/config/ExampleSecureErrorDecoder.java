package io.foodtechlab.authorization.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcore.rest.api.commons.exception.HttpCommunicationException;
import io.foodtechlab.core.Error;
import io.foodtechlab.core.ErrorApiResponse;
import ru.foodtechlab.lib.auth.integration.core.AccessTokenService;
import ru.foodtechlab.lib.auth.integration.restapi.feign.commons.AuthorizationErrorDecoder;

public class ExampleSecureErrorDecoder extends AuthorizationErrorDecoder {
    public ExampleSecureErrorDecoder(ObjectMapper mapper, AccessTokenService accessTokenService) {
        super(mapper, accessTokenService);
    }

    @Override
    public Exception decode(ErrorApiResponse<Error> errorApiResponse) {
        return new HttpCommunicationException(errorApiResponse);
    }
}
