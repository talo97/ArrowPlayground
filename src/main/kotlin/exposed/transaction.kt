package exposed

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.fold
import org.jetbrains.exposed.sql.transactions.transaction

sealed interface Error
data class UnknownError(val message: String) : Error

fun <A> transactionButVeryChad(block: Raise<Error>.() -> A): Either<Error, A> =
    fold(
        block = { transaction { block(this@fold) } },
        catch = { catchException(it) },
        recover = { Either.Left(it) },
        transform = { Either.Right(it) }
    )

private fun <A> catchException(throwable: Throwable): Either<Error, A> =
    UnknownError(throwable.message ?: "Unknown error").left()