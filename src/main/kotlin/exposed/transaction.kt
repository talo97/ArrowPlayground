package exposed

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.fold
import org.jetbrains.exposed.sql.transactions.transaction

fun <Error, A> transactionButVeryChad(block: Raise<Error>.() -> A): Either<Error, A> =
    fold(
        { transaction { block.invoke(this@fold) } },
        { Either.Left(it) },
        { Either.Right(it) }
    )
