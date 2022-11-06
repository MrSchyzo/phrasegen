package com.mrschyzo.phrasegen.grammar

interface WithId<T: Comparable<T>> {
    val id: T
}
