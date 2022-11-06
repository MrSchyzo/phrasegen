package com.mrschyzo.phrasegen.grammar

sealed class BooleanExpression<T>
typealias Exp<T> = BooleanExpression<T>

sealed class AssociativeBooleanExpression<T>(
    open val first: Exp<T>,
    open val others: List<Exp<T>>
) : Exp<T>()
typealias AExp<T> = AssociativeBooleanExpression<T>

class Intersection<T>(
    first: Exp<T>,
    others: List<Exp<T>>,
): AExp<T>(first, others)

class Union<T>(
    first: Exp<T>,
    others: List<Exp<T>>,
): AExp<T>(first, others)

class Complementary<T>(val exp: Exp<T>): Exp<T>()
class Just<T>(val value: T): Exp<T>()

fun <T> BooleanExpression<T>.allOperands(): List<T> =
    when (this) {
        is Intersection -> this.first.allOperands() + this.others.flatMap { it.allOperands() }
        is Union -> this.first.allOperands() + this.others.flatMap { it.allOperands() }
        is Complementary -> this.exp.allOperands()
        is Just -> listOf(value)
    }
