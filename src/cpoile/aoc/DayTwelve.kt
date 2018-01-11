package cpoile.aoc

import java.io.File

fun main(argh: Array<String>) {
   solveTwelve("DayTwelve-test.txt")
   solveTwelve("DayTwelve-test2.txt")
   solveTwelve("DayTwelve.txt")
}

class Node12(val name: Int,
             var parent: Node12? = null,
             val adjs: MutableSet<Node12> = mutableSetOf())

fun solveTwelve(file: String) {
    val allNodes = HashMap<Int, Node12>()

    for (l in File(file).readLines()) {
        val groups = l.split("<->")
        val prntName = groups[0].trim().toInt()
        val parent = allNodes.getOrPut(prntName, { Node12(prntName) })
        val adjs = groups[1].split(",")
                .map { it.trim() }
                .map { it.toInt() }
                .map { allNodes.getOrPut(it, { Node12(it) }) }
                .map { it.adjs.add(parent); it }
                .toSet()
                parent.adjs.addAll(adjs)
    }

    // now do a dfs and find who is connected to 0
    fun visit(node: Node12, visited: MutableSet<Int>) {
        if (visited.contains(node.name))
            return
        visited.add(node.name)
        node.adjs.forEach { visit(it, visited) }
    }

    // for Part1:
    val node0 = allNodes[0]!!
    val visitedNode0 = mutableSetOf<Int>()
    visit(node0, visitedNode0)
    println("There are ${visitedNode0.size} nodes in the group containing 0.")

    // for Part2:
    val allUnvisitedNodes = allNodes.keys.toMutableSet()
    var count = 0

    while (allUnvisitedNodes.isNotEmpty()) {
        val nextUnvisited = allUnvisitedNodes.first()
        val visited = mutableSetOf<Int>()
        val unvisitedNode = allNodes[nextUnvisited]!!
        visit(unvisitedNode, visited)
        count++
        allUnvisitedNodes.removeAll(visited)
    }
    println("There are $count groups.")
}