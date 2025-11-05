import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;

import java.io.InputStream;
import java.util.Set;
import java.util.Map;
import static java.util.Map.entry;

public class JsonValidator {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

  public static final Map<String, JsonSchema> schemas = Map.ofEntries(
    entry("LOGIN", buildSchema("LOGIN")),
    entry("CRIAR_USUARIO", buildSchema("CRIAR_USUARIO")),
    entry("EDITAR_PROPRIO_USUARIO", buildSchema("EDITAR_PROPRIO_USUARIO")),
    entry("ADMIN_EDITAR_USUARIO", buildSchema("ADMIN_EDITAR_USUARIO")),
    entry("ADMIN_EXCLUIR_USUARIO", buildSchema("ADMIN_EXCLUIR_FILME-USUARIO")),
    entry("CRIAR_FILME", buildSchema("CRIAR_FILME")),
    entry("EDITAR_FILME", buildSchema("EDITAR_FILME")),
    entry("EXCLUIR_FILME", buildSchema("ADMIN_EXCLUIR_FILME-USUARIO")),
    entry("CRIAR_REVIEW", buildSchema("CRIAR_REVIEW")),
    entry("EDITAR_REVIEW", buildSchema("EDITAR_REVIEW")),
    entry("BUSCAR_FILME_ID", buildSchema("BUSCAR_FILME_ID"))
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
