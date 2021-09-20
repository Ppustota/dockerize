package zura.pustota.securityjwt.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import zura.pustota.securityjwt.model.Role;
import zura.pustota.securityjwt.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

public class RefreshTokenUtil {

    public static String jwtTokenRoleBuilder(HttpServletRequest request, Algorithm algorithm, User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
    }
    public static String decodeUsername(String token, String secret){
        Algorithm algorithm = getAlgorithm(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public static Algorithm getAlgorithm(String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return algorithm;
    }
}
