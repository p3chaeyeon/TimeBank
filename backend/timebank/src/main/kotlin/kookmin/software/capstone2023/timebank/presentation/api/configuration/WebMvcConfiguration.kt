package kookmin.software.capstone2023.timebank.presentation.api.configuration

import kookmin.software.capstone2023.timebank.presentation.api.auth.interceptor.UserAuthenticationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(
    private val userAuthenticationInterceptor: UserAuthenticationInterceptor,
): WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userAuthenticationInterceptor)
            .addPathPatterns("/api/**")
    }

    /**
     * CORS 설정
     *
     * 개발 환경이라 전체 허용하지만 실제 서비스에서는 필요한 도메인만 허용하는 것이 좋아요.
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
    }
}