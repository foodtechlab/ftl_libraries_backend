package io.foodtechlab.tests.wiremock.utils;


import com.github.tomakehurst.wiremock.matching.StringValuePattern;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;


public class WireMockPropertiesUtils {

    public static String ONLY_NUMBERS = "^[0-9]*$";
    public static String LITTERS = "^([A-Za-z]+)$";

    /**
     * SearchAPiRequest as query map
     * @return
     */
    public static Map<String, StringValuePattern> fullSearchApiRequest() {
        var queryMap = new HashMap<String, StringValuePattern>();
        queryMap.put("sortName", matching(LITTERS));
        queryMap.put("sortDirection", matching(LITTERS));
        queryMap.put("limit", matching(ONLY_NUMBERS));
        queryMap.put("offset", matching(ONLY_NUMBERS));
        queryMap.put("query", matching(LITTERS));
        return queryMap;
    }

    /**
     * SearchAPiRequest as query map
     * @return
     */
    public static Map<String, StringValuePattern> searchApiRequestWithLimitAndOffset() {
        var queryMap = new HashMap<String, StringValuePattern>();
        queryMap.put("limit", matching(ONLY_NUMBERS));
        queryMap.put("offset", matching(ONLY_NUMBERS));
        return queryMap;
    }
}
