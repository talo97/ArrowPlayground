package either

import arrow.core.Either
import arrow.core.left
import arrow.core.right

/*
    Examples for creating Either type
 */

fun createRight(value: Int): Either<String, Int> =
    Either.Right(value)

fun createLeft(): Either<String, Int> =
    Either.Left("left")

fun creatingEitherWithExtensionFunction(valid: Boolean): Either<String, Int> =
    if (valid) 1.right() else "left".left()

fun creatingEitherWithConstructorLeft(valid: Boolean): Either<String, Int> =
    if (valid) Either.Right(1) else Either.Left("left")
