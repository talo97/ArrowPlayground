package exposed

/**
 * Implementing transactional database access with Exposed and Arrow Either error handling instead of exceptions.
 */
fun main() {
    connectDatabase()
    databaseMigration()

    // if you forget to bind() the result of any function that returns Either, it will not behave as expected - meaning,
    // the error will be ignored and no rollback will happen! - kind shit ngl, but no clue how to do it better w/o using
    // exceptions :/
    testWithoutError()
    testWithoutErrorNested()
    testWithError()
    testWithErrorNested()
    testWithErrorNested2()
    testWithErrorNested3()

    // and don't ask me why the tests aren't done simpler with JUnit and h2db ¯\_(ツ)_/¯
}

fun testWithoutError() {
    transactionButVeryChad {
        saveExample("should save the value :)").bind()
    }.fold(
        { println(it) },
        { println(it) }
    )
}

fun testWithoutErrorNested() {
    transactionButVeryChad {
        saveExample("should save the value :)").bind()
        // it works well with nested execution as well (:
        transactionButVeryChad {
            saveExample("should save the value within nested :)").bind()
            transactionButVeryChad {
                saveExample("should save the value within nested :)").bind()
            }.bind()
        }.bind()
    }.fold(
        { println(it) },
        { println(it) }
    )
}

fun testWithError() {
    transactionButVeryChad {
        saveExample("Should not be saved").bind()
        saveExampleButFail("Here is error").bind()
        println("it will not even execute here, because there is error above this line :)")
        saveExample("Should not be saved").bind()
    }.fold(
        { println(it) },
        { println(it) }
    )
}

fun testWithErrorNested() {
    transactionButVeryChad {
        saveExample("Should not be saved").bind()
        // it works well with nested execution as well (:
        transactionButVeryChad {
            transactionButVeryChad {
                saveExampleButFail("Here is error").bind()
            }.bind()
        }.bind()
    }.fold(
        { println(it) },
        { println(it) }
    )
}

fun testWithErrorNested2() {
    transactionButVeryChad {
        saveExample("Should not be saved").bind()
        // it works well with nested execution as well (:
        transactionButVeryChad {
            transactionButVeryChad {
                saveExampleButFail("Should not be saved").bind()
            }.bind()
        }.bind()
        saveExample("Should not be saved").bind()
    }.fold(
        { println(it) },
        { println(it) }
    )
}

fun testWithErrorNested3() {
    transactionButVeryChad {
        saveExample("Should not be saved").bind()
        // it works well with nested execution as well (:
        transactionButVeryChad {
            saveExample("Should not be saved").bind()
        }.bind()
        saveExample("Should not be saved").bind()
        saveExampleButFail("Should not be saved").bind()
    }.fold(
        { println(it) },
        { println(it) }
    )
}
