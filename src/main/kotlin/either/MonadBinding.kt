package either

import arrow.core.raise.either

fun doMonadBindingStuff() {
    monadBindingBasic()
}

fun monadBindingBasic() {
    println("--------------------------------")
    println("monadBindingBasic")
    // case 1: both results are Either.Right
    val resultExampleGoodVal1 = createRight(555)
    val resultExampleGoodVal2 = createRight(111)

    val resultTotal = either {
        val one = resultExampleGoodVal1.bind()
        val two = resultExampleGoodVal2.bind()
        one + two
    }
    println(resultTotal)

    // case 2: at least one result is Either.Left
    val resultExampleBadVal1 = createRight(555)
    val resultExampleBadVal2 = createLeft()

    val resultTotal2 = either {
        val one = resultExampleBadVal1.bind()
        // it will suspend the execution and return Either.Left on this line
        val two = resultExampleBadVal2.bind()
        println("Will never reach this line because resultBad2 is Either.Left")
        one + two
    }
    println(resultTotal2)
}