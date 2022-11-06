package com.mrschyzo.phrasegen.grammar

class Dictionary(
    val words: Set<WordFamily>,
)

class WordFamily(
    val members: List<Word>,
    val semantics: List<SemanticTag>,
    val repeatable: Boolean,
)

class Word(
    override val id: Id,
    val grammarCompatibility: Exp<GrammarTag.Id>,
    val grammarTraits: List<GrammarTag>,
): WithId<Word.Id> {
    @JvmInline
    value class Id(val id: Int): Comparable<Id> {
        companion object {
            fun of(id: Int) = Id(id)
        }

        override fun compareTo(other: Id): Int = id.compareTo(other.id)
    }
}
