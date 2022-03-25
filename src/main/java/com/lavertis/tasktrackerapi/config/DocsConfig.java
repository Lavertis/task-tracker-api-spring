package com.lavertis.tasktrackerapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@OpenAPIDefinition
public class DocsConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        var jsonPatchSchema = new ArraySchema()
                .items(new Schema<Map<String, Object>>()
                        .addProperties("op", new StringSchema().example("replace"))
                        .addProperties("path", new StringSchema().example("/field"))
                        .addProperties("value", new StringSchema().example("newValue"))
                );

        return new OpenAPI()
                .info(new Info()
                        .title("Task tracker API")
                        .version("1.0")
                )
                .components(new Components()
                        .addSchemas("JsonPatch", jsonPatchSchema)
                );
    }
}
