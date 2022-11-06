package com.mrschyzo.phrasegen.engine

import com.mrschyzo.phrasegen.engine.graph.topologicalOrder
import com.mrschyzo.phrasegen.grammar.Grammar
import com.mrschyzo.phrasegen.grammar.NonTerminalSymbol
import com.mrschyzo.phrasegen.grammar.ReplacementRule
import com.mrschyzo.phrasegen.grammar.TerminalSymbol
import com.mrschyzo.phrasegen.grammar.allOperands
import com.mrschyzo.phrasegen.phrase.Phrase
import io.github.mrschyzo.opiniom.types.Err
import io.github.mrschyzo.opiniom.types.Result
import kotlin.random.Random

class GenerationOptions

interface PhraseGenerator {
    fun generate(options: GenerationOptions): Result<Phrase, Error>
}

class NaiveStaticGenerator(private val grammar: Grammar): PhraseGenerator {
    override fun generate(options: GenerationOptions): Result<Phrase, Error> {
        val replacementRules = grammar
            .replacementRules[grammar.start]
            ?.takeIf(Collection<*>::isNotEmpty)
            ?: return NoReplacementsError(grammar.start).let(::Err)

        val rule = Random.nextInt(0, replacementRules.lastIndex)
            .let(replacementRules::get)

        val order = computeEvaluationOrder(rule)
            .flatmapOk {
                it.forEach {
                    when (val symbol = rule.sequence[it]) {
                        is NonTerminalSymbol -> grammar.nonTerminals[symbol.id]
                        is TerminalSymbol -> grammar.terminals[symbol.id]
                    }
                }
                Err(GenericError("TODO"))
            }
            .mapErr {
                Err<Phrase, Error>(it)
            }


        TODO()
    }

    private fun computeEvaluationOrder(rule: ReplacementRule): Result<List<Int>, Error> {
        val (gDeps, sDeps) = rule.grammarDependencies to rule.semanticDependencies

        val grammarDependencyGraph =
            gDeps.mapIndexed { i, dep ->
                i to dep.map { it.allOperands() }.orElse(::listOf).toSet()
            }.toMap()

        val semanticDependencyGraph =
            sDeps.mapIndexed { i, dep ->
                i to dep.map { it.allOperands() }.orElse(::listOf).toSet()
            }.toMap()

        val totalDependencyGraph =
            (grammarDependencyGraph.entries + semanticDependencyGraph.entries)
                .fold(mutableMapOf<Int, Set<Int>>()) { acc, (i, deps) ->
                    acc.also { it.merge(i, deps, Set<Int>::union) }
                }.toMap()

        return totalDependencyGraph.topologicalOrder().mapErr(::OrderEvaluationError)
    }


}
