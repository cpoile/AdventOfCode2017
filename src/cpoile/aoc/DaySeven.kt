package cpoile.aoc

import java.io.File

fun main(argh: Array<String>) {
    //println(solveSeven("DaySevenOne-test.txt"))
    println(solveSeven("DaySevenOne.txt"))
}

class Node(val name: String,
           var weight: Int = 0,
           val children: ArrayList<Node> = ArrayList(),
           var parent: Node? = null,
           var totalWeight: Int = 0) {
    override fun toString() =
            "$name ($weight) tw: $totalWeight P: ${parent?.name ?: "none"}, C: " +
                    children.map { "${it.name} (${it.weight}) tw:${it.totalWeight}" }.joinToString(", ")
}

fun solveSeven(file: String): String {
    val allNodes: MutableMap<String, Node> = HashMap()

    val regex = Regex("^(\\w+) \\((\\d+)\\)(?: -> )?(.*)")

    var node: Node = Node("test")
    for (l in File(file).readLines()) {
        val match = regex.matchEntire(l)
        if (match != null) {
            // extract the information from the string
            val (rootName, weight, children) = match.destructured
            val chldNames = children.split(", ").filter { !it.equals("") }

            // construct the nodes
            node = allNodes.getOrPut(rootName, { Node(rootName) })
            node.weight = weight.toInt()
            for (c in chldNames) {
                val cNode = allNodes.getOrPut(c, { Node(c) })
                cNode.parent = node
                node.children.add(cNode)
            }
        }
    }

    // to find root, just start from the last one we read in:
    val root = findParent(node)

    // Part one:
    //return root.name

    // Below is Part Two
    val totalWeight = calculateTotalWeight(root)
    println("totalWeight: $totalWeight")

    //allNodes.forEach({ s, n -> println(n) })

    val unbalancedNode = findUnBalanced(allNodes)

    // Need to look and see yourself:
    return unbalancedNode.toString()
}

fun findParent(n: Node): Node {
    if (n.parent == null)
        return n
    else
        return findParent(n.parent!!)
}

fun calculateTotalWeight(n: Node): Int {
    n.totalWeight = n.weight + n.children.map{ calculateTotalWeight(it) }.sum()
    return n.totalWeight
}

fun areChildrenEqual(n: Node) = n.children.map{ it.totalWeight }.toSet().size == 1
fun areGrandChildrenEqual(n: Node) = n.children.size > 0 && n.children.all{ areChildrenEqual(it) }
// the unbalanced node has all children unbalanced and all grandchildren balanced.
fun isTheUnbalancedNode(n: Node) = !areChildrenEqual(n) && areGrandChildrenEqual(n)

fun findUnBalanced(nodes: Map<String, Node>): Node {
    return nodes.asSequence().find{ isTheUnbalancedNode(it.value) }!!.value
}