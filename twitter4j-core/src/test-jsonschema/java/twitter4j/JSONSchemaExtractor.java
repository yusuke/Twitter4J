package twitter4j;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@SuppressWarnings("SameParameterValue")
class JSONSchemaExtractor {
    private final Map<String, JSONSchema> extracted;

    private JSONSchemaExtractor(@Language("JSON") String schemaDefinition, String path) {
        extracted = JSONSchema.extract(path, schemaDefinition);
    }

    static JSONSchemaExtractor from(@Language("JSON") String schemaDefinition, String path) {
        return new JSONSchemaExtractor(schemaDefinition, path);
    }

    List<JavaFile> javaImplFiles(@NotNull String packageName, @NotNull String interfacePackageName, boolean noPrefix) {
        return extracted.values().stream().filter(e -> e instanceof ObjectSchema)
                .filter(JSONSchema::hasAnyProperties)
                .map(e -> e.asJavaImpl(packageName, interfacePackageName, noPrefix))
                .toList();
    }

    List<JavaFile> interfaceFiles(@NotNull String interfacePackageName, boolean noPrefix) {
        return extracted.values().stream().filter(e -> e instanceof ObjectSchema)
                .filter(JSONSchema::hasAnyProperties)
                .map(e -> e.asInterface(interfacePackageName, noPrefix))
                .toList();
    }
}