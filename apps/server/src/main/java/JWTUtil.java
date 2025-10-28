import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
  private static final String SECRET_KEY = "N1NqQmpvclRkclhGYlV1TDFoM0JjVjZIeDB3MndqVVo=";

  public static String generateToken(String id) {
    return Jwts.builder()
            .setSubject(id) 
            .setIssuedAt(new Date(System.currentTimeMillis())) 
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY) 
            .compact(); 
  }

  public static Claims validateToken(String token) {
    try {
      return Jwts.parser()
              .setSigningKey(SECRET_KEY)
              .parseClaimsJws(token)
              .getBody(); 
    } catch (Exception e) {
      return null;
    }
  }

}
