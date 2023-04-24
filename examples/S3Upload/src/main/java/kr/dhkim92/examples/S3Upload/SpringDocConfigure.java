package kr.dhkim92.examples.S3Upload;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigure {
  @Bean
  public OpenAPI openAPI() {
    Info info = new Info()
            .version("v1.0.0")
            .title("Spring boot S3 file upload example")
            .description("");

    return new OpenAPI()
            .info(info);
  }
}
