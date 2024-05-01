package com.tony.string.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import java.util.Locale

/**
 * P6Spy SQL 포맷터
 */
@Configuration
class P6SpySqlFormatter : MessageFormattingStrategy {
    // 로그 메시지 포맷 설정
    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = this.javaClass.name
    }

    // 메시지 포맷 설정
    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?,
    ): String {
        if (sql != null && isBatchTableQuery(sql)) {
            return "" // 배치 테이블 관련 쿼리는 로그에서 제외
        }
        return "[$category] | ${elapsed}ms | ${category?.let { formatSql(it, sql) }}"
    }

    // 배치 테이블 쿼리 정의
    private fun isBatchTableQuery(sql: String): Boolean {
        val lowerCaseSql = sql.lowercase(Locale.ROOT)
        return lowerCaseSql.contains("batch_job_instance") ||
            lowerCaseSql.contains("batch_job_execution") ||
            lowerCaseSql.contains("batch_step_execution") ||
            lowerCaseSql.contains("batch_step_execution_context")
    }

    // SQL 포맷팅
    private fun formatSql(
        category: String?,
        sql: String?,
    ): String? {
        if (!sql.isNullOrBlank() && Category.STATEMENT.name == category) {
            val trimmedSQL = sql.trim().lowercase(Locale.ROOT)

            return when {
                trimmedSQL.startsWith("select") || trimmedSQL.startsWith("insert") ||
                    trimmedSQL.startsWith("update") || trimmedSQL.startsWith("delete") ->
                    FormatStyle.BASIC.formatter.format(sql)

                trimmedSQL.startsWith("create") || trimmedSQL.startsWith("alter") ||
                    trimmedSQL.startsWith("comment") ->
                    FormatStyle.DDL.formatter.format(sql)

                else ->
                    sql // 기타 SQL은 기본 형식 그대로 출력
            }
        }
        return sql
    }

//    private fun formatSql(category: String?, sql: String?): String? {
//        if (!sql.isNullOrBlank() && Category.STATEMENT.name == category) {
//            val trimmedSQL = sql.trim().lowercase(Locale.ROOT)
//            return when {
//                trimmedSQL.startsWith("create") || trimmedSQL.startsWith("alter") || trimmedSQL.startsWith("comment") ->
//                    FormatStyle.DDL.formatter.format(sql)
//                else ->
//                    FormatStyle.BASIC.formatter.format(sql)
//            }
//        }
//        return sql
//    }
}
