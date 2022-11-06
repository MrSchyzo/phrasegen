package com.mrschyzo.phrasegen.engine.graph

import io.github.mrschyzo.opiniom.types.Err
import io.github.mrschyzo.opiniom.types.Ok
import io.github.mrschyzo.opiniom.types.Result
import java.util.Stack

private const val UNDISCOVERED: Byte = 0
private const val ENCOUNTERED: Byte = 1
private const val EXPLORED: Byte = 2

fun <ID: Any> Map<ID, Set<ID>>.topologicalOrder(): Result<List<ID>, Error> {
    val nodes = this.keys

    val topologicalOrder = ArrayList<ID>(nodes.size)

    val traversalNode = Stack<ID>()
    val nodeStatus = nodes.associateWith { UNDISCOVERED }.toMutableMap()

    val notYetExplored: (ID) -> Boolean = { nodeStatus[it] != EXPLORED && it in nodes }

    for (start in nodes) {
        val path = mutableListOf<ID>()

        when(nodeStatus[start]?:EXPLORED) {
            UNDISCOVERED -> traversalNode.push(start)
            EXPLORED -> continue
            else -> return Err(UnexpectedState(partialOrder = topologicalOrder, offending = start))
        }

        while(traversalNode.isNotEmpty()) {
            val current = traversalNode.pop()
            val toAdd = this[current]?.filter(notYetExplored) ?: setOf()

            if (toAdd.isEmpty()) {
                topologicalOrder.add(current)
                nodeStatus[current] = EXPLORED
                continue
            }

            when (nodeStatus[current]?:EXPLORED) {
                ENCOUNTERED -> return Err(CycleDetected(path))
                EXPLORED -> continue
                else -> nodeStatus[current] = ENCOUNTERED
            }

            path.add(current)
            traversalNode.push(current)

            traversalNode.addAll(toAdd)
        }
    }

    return Ok(topologicalOrder)
}
