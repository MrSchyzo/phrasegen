package com.mrschyzo.phrasegen.engine

import com.mrschyzo.phrasegen.grammar.NonTerminalSymbol

class NoReplacementsError(id: NonTerminalSymbol.Id) : Error(
    "Unable to find replacement rules for non-terminal symbol with ID $id"
)
