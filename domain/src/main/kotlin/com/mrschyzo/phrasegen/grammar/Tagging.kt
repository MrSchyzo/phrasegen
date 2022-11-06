package com.mrschyzo.phrasegen.grammar

import com.mrschyzo.phrasegen.grammar.SemanticTag.Id

class SemanticTag(
    override val id: Id,
    val sticky: Boolean,
): WithId<Id> {
    @JvmInline
    value class Id(val id: Int): Comparable<Id> {
        companion object {
            fun of(id: Int) = Id(id)
        }

        override fun compareTo(other: Id): Int = id.compareTo(other.id)
    }
}

class GrammarTag(
    override val id: Id
): WithId<GrammarTag.Id> {
    @JvmInline
    value class Id(val id: Int): Comparable<Id> {
        companion object {
            fun of(id: Int) = Id(id)
        }

        override fun compareTo(other: Id): Int = id.compareTo(other.id)
    }
}
