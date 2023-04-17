package kookmin.software.capstone2023.timebank.presentation.api.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {
    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("TimeBank API")
                .version("1.0.0")
                .description("TimeBank API 문서"),
        )
        .components(Components())
}
