import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TokenManager {
  private static final Path TOKEN_PATH = Paths.get("resources", "token");

  public static boolean saveToken(String token) {
    try {
      Files.createDirectories(TOKEN_PATH.getParent());
        
      Files.writeString(TOKEN_PATH, token);
        
      return true;

    } catch (IOException e) {
      return false;
    }
  }

  public static String getToken() {
    try {
      if (!Files.exists(TOKEN_PATH)) return null;
      
      return Files.readString(TOKEN_PATH);
      
    } catch (IOException e) {
      return null;
    }
  }
}
