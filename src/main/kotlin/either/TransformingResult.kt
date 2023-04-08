package either

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.getOrElse

fun doTransformResultStuff() {
    transformResultWithWhen()
    transformResultWithFold()
    transformResultWithMap()
    transformResultWithFlatMap()
}

fun transformResultWithWhen() {
    println("--------------------------------")
    println("transformResultWithWhen")
    when (val result = createLeft()) {
        // smart cast to Either.Left
        is Either.Left -> println("left: ${result.value}")
        // smart cast to Either.Right
        is Either.Right -> println("right: ${result.value}")
    }

    when (val result = createRight(1)) {
        // smart cast to Either.Left
        is Either.Left -> println("left: ${result.value}")
        // smart cast to Either.Right
        is Either.Right -> println("right: ${result.value}")
    }
}

fun transformResultWithFold() {
    println("--------------------------------")
    println("transformResultWithFold")
    val resultLeft = createLeft()
    resultLeft.fold(
        // first function is always left case
        { println("left: $it") },
        // second function is always right case
        { println("right: $it") }
    )

    val resultRight = createRight(1)
    resultLeft.fold(
        // first function is always left case
        { println("left: $it") },
        // second function is always right case
        { println("right: $it") }
    )
}

fun transformResultWithMap() {
    println("--------------------------------")
    println("transformResultWithMap")
    val resultLeft = createLeft()
    val resultRight = createRight(10)

    // won't execute
    val mappedLeft = resultLeft.map { it + 1 }
    // will execute and add 1 to 10
    val mappedRight = resultRight.map { it + 1 }

    println("mappedLeft: $mappedLeft")
    // will be 11
    println("mappedRight: $mappedRight")

    // will be 0 because it is of type Either.Left
    val valueLeft = mappedLeft.getOrElse { 0 }
    // will return 11 because it is of type Either.Right
    val valueRight = mappedRight.getOrElse { 0 }
    println("valueLeft: $valueLeft")
    println("valueRight: $valueRight")
}

fun transformResultWithFlatMap() {
    println("--------------------------------")
    println("transformResultWithFlatMap")
    val firstResult = createRight(37)
    val secondResult = createRight(2100)

    // flat map is using for combining Either results
    // if not flatMap, then the result would be Either.Right(Either.Right(2100))
    val flatMappedResult = firstResult.flatMap { first ->
        secondResult.map { second ->
            first + second
        }
    }

    flatMappedResult.fold(
        // first function is always left case
        { println("left: $it") },
        // second function is always right case
        { println("right: $it") }
    )
}
