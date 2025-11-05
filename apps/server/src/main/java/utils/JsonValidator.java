import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;

import java.io.InputStream;
import java.util.Set;
import java.util.Map;

public class JsonValidator {
  private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

  public static final Map<String, JsonSchema> schemas = Map.of(
    "LOGIN", buildSchema("LOGIN"),
    "CRIAR_USUARIO", buildSchema("CRIAR_USUARIO"),
    "EDITAR_PROPRIO_USUARIO", buildSchema("EDITAR_PROPRIO_USUARIO"),
    "ADMIN_EDITAR_USUARIO", buildSchema("ADMIN_EDITAR_USUARIO"),
    "ADMIN_EXCLUIR_USUARIO", buildSchema("ADMIN_EXCLUIR_FILME-USUARIO"),
    "CRIAR_FILME", buildSchema("CRIAR_FILME"),
    "EDITAR_FILME", buildSchema("EDITAR_FILME"),
    "EXCLUIR_FILME", buildSchema("ADMIN_EXCLUIR_FILME-USUARIO"),
    "CRIAR_REVIEW", buildSchema("CRIAR_REVIEW")
  );

  static private JsonSchema buildSchema(String name) {
    try (InputStream schemaStream = Main.class.getResourceAsStream("/schemas/" + name +".json")) {
      SchemaValidatorsConfig config = SchemaValidatorsConfig.builder()
        .errorMessageKeyword("message")
        .typeLoose(true)
        .failFast(true)
        .build();

      return schemaFactory.getSchema(schemaStream, config);
    } catch (Exception e) {
      System.out.println(e.getMessage());

      System.exit(1);
      return null;
    }
  }

  public static String validate(JsonNode jsonNode, String schemaName) {
    JsonSchema schema = schemas.get(schemaName);
    if(schema == null) return null;

    Set<ValidationMessage> errors = schema.validate(jsonNode);
    if (!errors.isEmpty()) return Failed.response(400, errors.iterator().next().getMessage());

    return null;
  }
}
