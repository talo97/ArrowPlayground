package exposed

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

private const val JDBC_URL: String = "jdbc:postgresql://localhost:5432/arrow-exposed-test"
private const val JDBC_DRIVER: String = "org.postgresql.Driver"
private const val JDBC_USER: String = "postgres"
private const val JDBC_PW: String = "root"

fun connectDatabase() {
    Database.connect(
        url = JDBC_URL,
        driver = JDBC_DRIVER,
        user = JDBC_USER,
        password = JDBC_PW
    )
}

fun databaseMigration() {
    transaction {
        SchemaUtils.create(
            ExampleTable
        )
    }
}
