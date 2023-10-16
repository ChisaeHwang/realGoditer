package realGoditer.example.realGoditer.infra.OAuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GoogleOAuth {

    private static final String GOOGLE_LOGIN_URL = "https://accounts.google.com";
    private static final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private static String googleClientId = null;
    private static String googleRedirectUrl = null;
    private final String googleClientSecret;

    @Autowired
    public GoogleOAuth(ObjectMapper objectMapper, RestTemplate restTemplate,
                       @Value("${spring.security.oauth2.client.registration.google.client-id}") String googleClientId,
                       @Value("${spring.security.oauth2.client.registration.google.redirect-uri}") String googleRedirectUrl,
                       @Value("${spring.security.oauth2.client.registration.google.client-secret}") String googleClientSecret) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.googleClientId = googleClientId;
        this.googleRedirectUrl = googleRedirectUrl;
        this.googleClientSecret = googleClientSecret;
    }

    public static String getGoogleRedirectUrl() {
        return GOOGLE_LOGIN_URL + "/o/oauth2/auth?" +
                "client_id=" + googleClientId +
                "&redirect_uri=" + googleRedirectUrl +
                "&response_type=token" + // 이 부분을 "&response_type=code"로 변경
                "&scope=https://www.googleapis.com/auth/userinfo.email";
    }

    public ResponseEntity<String> getGoogleAccessToken(String accessCode) {
        RestTemplate restTemplate1 = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        // 아래 URL을 토큰을 받는 URL로 변경
        ResponseEntity<String> responseEntity = restTemplate1.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }

        return null;
    }

}
