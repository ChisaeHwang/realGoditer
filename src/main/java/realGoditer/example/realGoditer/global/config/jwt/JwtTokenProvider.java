package realGoditer.example.realGoditer.global.config.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import realGoditer.example.realGoditer.domain.member.domain.CustomUserDetails;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import javax.xml.bind.DatatypeConverter;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
    }


    public String generateToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String userId = null;

        if (principal instanceof CustomUserDetails) {
            userId = ((CustomUserDetails) principal).getId().toString();
        } else if (principal instanceof DefaultOAuth2User) {
            // DefaultOAuth2User에서 필요한 정보를 추출
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) principal;
            userId = defaultOAuth2User.getName();  // 또는 다른 필요한 정보를 사용
        } else {
            throw new RuntimeException("Unsupported principal type");
        }

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userId)
                .claim("auth", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        CustomUserDetails principal = new CustomUserDetails();
        principal.setId(Long.parseLong(claims.getSubject()));  // 문자열로 된 ID를 Long으로 변환
        // ... 여기에 다른 필드들을 설정할 수 있습니다.

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }



    public String getEmailFromToken(String token, @Value("${jwt.secret}") String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.get("email", String.class);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
