package com.mrschyzo.phrasegen.grammar

import com.mrschyzo.phrasegen.grammar.NonTerminalSymbol.Id as NonTerminalId
import io.github.mrschyzo.opiniom.types.Maybe

class Grammar(
    val start: NonTerminalId,
    val nonTerminals: Map<NonTerminalId, NonTerminalSymbol>,
    val terminals: Map<TerminalSymbol.Id, TerminalSymbol>,
    val replacementRules: Map<NonTerminalId, List<ReplacementRule>>,
    val dictionary: Dictionary
)

sealed class Symbol(val label: String)
class NonTerminalSymbol(
    override val id: Id,
    label: String,
) : Symbol(label), WithId<NonTerminalId> {
    @JvmInline
    value class Id(val id: Int): Comparable<Id> {
        companion object {
            fun of(id: Int) = Id(id)
        }

        override fun compareTo(other: Id): Int = id.compareTo(other.id)
    }
}

class TerminalSymbol(
    override val id: Id,
    label: String,
) : Symbol(label), WithId<TerminalSymbol.Id> {
    @JvmInline
    value class Id(val id: Int): Comparable<Id> {
        companion object {
            fun of(id: Int) = Id(id)
        }

        override fun compareTo(other: Id): Int = id.compareTo(other.id)
    }
}

class ReplacementRule(
    override val id: Int,
    val sequence: List<Symbol>,
    val grammarComposition: Exp<Int>,
    val semanticComposition: Exp<Int>,
    val grammarDependencies: List<Maybe<Exp<Int>>>,
    val semanticDependencies: List<Maybe<Exp<Int>>>,
): WithId<Int>
