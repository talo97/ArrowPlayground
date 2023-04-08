package either

/*
    This is a playground for the "Either" type from Arrow.
    The "Either" type is a sum type that represents two possible values.
    It is a right-biased type, meaning that the right value is the default value.
    The left value is used for error handling.
 */

fun main() {
    doTransformResultStuff()
    doMonadBindingStuff()
}