package realGoditer.example.realGoditer.infra.OAuth;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
