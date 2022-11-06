package com.mrschyzo.phrasegen.engine.graph

import io.github.mrschyzo.opiniom.types.Ok
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test

internal class GraphUtilsKtTest {

    @Test
    fun `list is correctly topologically sorted`() {
        val graph = mapOf(
            4 to setOf(),
            3 to setOf(4),
            2 to setOf(3),
            1 to setOf(2)
        )

        graph.topologicalOrder() shouldBe Ok(listOf(4, 3, 2, 1))
    }

    @Test
    fun `tree is topologically sorted`() {
        val graph = mapOf(
            3 to setOf(1,0),
            2 to setOf(),
            4 to setOf(3,2),
            1 to setOf(),
            0 to setOf()
        )
        val order = graph.topologicalOrder().unwrap()

        order shouldContainAll graph.keys

        graph.entries.forEach {(node, dependencies) ->
            dependencies.forEach { dependency ->
                order.indexOf(node) shouldBeGreaterThan order.indexOf(dependency)
            }
        }
    }

    @Test
    fun `a DAG is topologically sorted`() {
        val graph = mapOf(
            1 to setOf(2, 3, 4),
            2 to setOf(4, 3),
            3 to setOf(5),
            4 to setOf(6, 7),
            7 to setOf(5, 6),
            5 to setOf(),
            8 to setOf()
        )
        val order = graph.topologicalOrder().unwrap()

        order shouldContainAll graph.keys

        graph.entries.forEach {(node, dependencies) ->
            dependencies.forEach { dependency ->
                order.indexOf(node) shouldBeGreaterThan order.indexOf(dependency)
            }
        }
    }

    @Test
    fun `cyclic graph is detected`() {
        val graph = mapOf(
            1 to setOf(2, 4),
            2 to setOf(4, 3),
            3 to setOf(1),
            4 to setOf(),
        )
        val order = graph.topologicalOrder().extractErr().unwrap()

        order.shouldBeInstanceOf<CycleDetected<Int>>()

        order.cycle shouldContainAll listOf(1, 2, 3)
    }

    @Test
    fun `very dense DAG is properly ordered`() {
        val size = 3 * 1024

        val graph = (1..size).reversed().associateWith {
            (it + 1..size).toSet()
        }

        val order = graph.topologicalOrder().unwrap()

        order shouldContainAll graph.keys

        graph.entries.forEach {(node, dependencies) ->
            dependencies.forEach { dependency ->
                order.indexOf(node) shouldBeGreaterThan order.indexOf(dependency)
            }
        }
    }

    @Test
    fun `very long list is properly ordered`() {
        val size = 2 * 1024 * 1024

        val graph = (1..size).reversed().associateWith {
            if (it < size) setOf(it + 1) else setOf()
        }

        val order = graph.topologicalOrder().unwrap()

        order shouldContainInOrder (1..size).reversed().toList()
    }

    @Test
    fun `dependencies to non-existant nodes are ignored`() {
        val graph = mapOf(
            4 to setOf(1500),
            1 to setOf(2, 4),
            2 to setOf(4, 3),
            3 to setOf(),
        )
        val order = graph.topologicalOrder().unwrap()

        order shouldContainAll graph.keys
    }
}
