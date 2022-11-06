package com.mrschyzo.phrasegen.engine

import com.mrschyzo.phrasegen.engine.graph.Error as GraphError

sealed class Error(val message: String, val cause: Error? = null)

class GenericError(message: String): Error(message, null)
class ThrownError(val throwable: Throwable): Error(throwable.localizedMessage, null)
class OrderEvaluationError(val error: GraphError): Error("Unable to compute order evaluation error: ${error.message}")
