package com.mrschyzo.phrasegen

import com.mrschyzo.phrasegen.functional.`else`
import com.mrschyzo.phrasegen.functional.`if`
import com.mrschyzo.phrasegen.functional.just


fun main() {
    val regex = Regex("""\d+""")

    listOf("", " ", "a1112", "1", "1 1 a 1")
        .map(String::length `if` regex::matches `else` just(0))
        .forEach(::println)
}
