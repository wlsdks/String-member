package com.tony.string.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val SECURITY_SCHEME_NAME = "authorization"	// 추가

@Configuration
class SwaggerConfig {
    /**
     * 기본 스웨거 화면에서는 토큰을 발급받아도 사용할 수가 없다. 토큰을 헤더에 추가해서 사용하기 위해 SwaggerConfig의 OpenAPI 빈을 아래와 같이 수정한다.
     */
    @Bean
    fun swaggerApi(): OpenAPI =
        OpenAPI()
            .components(
                Components()
                    // 여기부터 추가 부분
                    .addSecuritySchemes(
                        SECURITY_SCHEME_NAME,
                        SecurityScheme() // addSecuritySchemes()는 인증 정보 입력을 위한 버튼을 추가하는 메서드
                            .name(SECURITY_SCHEME_NAME)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .addSecurityItem(
                SecurityRequirement().addList(SECURITY_SCHEME_NAME)
            ) // addSecurityItem()은 시큐리티 요구 사항을 스웨거에 추가한다.
            // 여기까지
            .info(
                Info()
                    .title("스프링시큐리티 + JWT 예제")
                    .description("스프링시큐리티와 JWT를 이용한 사용자 인증 예제입니다.")
                    .version("1.0.0")
            )
}
