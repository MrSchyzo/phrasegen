package com.mrschyzo.phrasegen.functional

import io.github.mrschyzo.opiniom.types.Maybe
import io.github.mrschyzo.opiniom.types.None
import io.github.mrschyzo.opiniom.types.Some

infix fun <I: Any, O: Any> ((I) -> O).`if`(condition: Boolean) : (I) -> Maybe<O> = {
    if (condition)
        Some(this(it))
    else
        None()
}

infix fun <I: Any, O: Any> ((I) -> O).`if`(condition: (I) -> Boolean) : (I) -> Maybe<O> = {
    (this `if` condition(it)).invoke(it)
}

infix fun <I: Any, O: Any> ((I) -> Maybe<O>).`else`(alternative: (I) -> O): (I) -> O = {
    this(it).orElse(alternative(it))
}

fun <I, O> just(output: O): (I) -> O = {output}
