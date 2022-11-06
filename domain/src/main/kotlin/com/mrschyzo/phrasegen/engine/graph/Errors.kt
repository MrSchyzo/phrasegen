package com.mrschyzo.phrasegen.engine.graph

sealed class Error(val message: String, val cause: Error? = null)

class GenericError(message: String): Error(message, null)
class CycleDetected<ID>(val cycle: List<ID>): Error("Cycle detected: ${cycle.joinToString(" -> ")}", null)
class UnexpectedState<ID>(val partialOrder: List<ID>, val offending: ID): Error("DFS cycle failed after determining order $partialOrder; node $offending found in ENCOUNTERED state")
