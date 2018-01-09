package cpoile.aoc

import java.io.File
import kotlin.math.abs
import kotlin.math.max

fun main(argh: Array<String>) {
    solveTen("DayEleven-test.txt")
    solveTen("DayEleven.txt")
}

fun addCoords(p1: Pair<Int, Int>, p2: Pair<Int, Int>) = Pair(p1.first + p2.first, p1.second + p2.second)

val axialDirection = mapOf("ne" to Pair(1, -1),
        "se" to Pair(1, 0),
        "s" to Pair(0, 1),
        "sw" to Pair(-1, 1),
        "nw" to Pair(-1, 0),
        "n" to Pair(0, -1))

fun axialDistance(a1: Pair<Int, Int>, a2: Pair<Int, Int>) =
        (abs(a1.first - a2.first)
                + abs(a1.first + a1.second - a2.first - a2.second)
                + abs(a1.second - a2.second))/2

fun solveTen(file: String) {
    File(file).readLines().forEach {
        var loc = Pair(0, 0)
        var furthest = 0
        it.split(",").forEach {
            loc = addCoords(loc, axialDirection[it]!!)
            furthest = max(furthest, axialDistance(Pair(0,0), loc))
        }
        println("loc: $loc, distance to 0,0: ${axialDistance(Pair(0,0), loc)}, furthest: $furthest")
    }
}