package exposed

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// for simplicity, we will keep everything related to the example table in this file

object ExampleTable : IntIdTable(name = "example") {
    val value = varchar("value", length = 128)
}

class ExampleEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExampleEntity>(ExampleTable)

    var value by ExampleTable.value
}

data class DatabaseError(val message: String) : Error

fun saveExample(value: String): Either<DatabaseError, ExampleEntity> {
    return ExampleEntity.new {
        this.value = value
    }.right()
}

fun saveExampleButFail(value: String): Either<DatabaseError, ExampleEntity> {
    return DatabaseError("Failed to save example").left()
}
