package cpoile.aoc

import java.io.File

fun main(argh: Array<String>) {
    //println(solveFiveOne("DayFiveOne-test.txt"))
    //println(solveFiveOne("DayFiveOne.txt"))
    println(solveFiveTwo("DayFiveOne-test.txt"))
    println(solveFiveTwo("DayFiveOne.txt"))
}

fun solveFiveOne(file: String): Int {
    val l = File(file).readLines().map { it.toInt() }.toIntArray()
    var idx = 0
    var jumps = 0

    while(true) {
        if (idx + l[idx] >= l.size)
            return ++jumps
        else {
            idx += l[idx]++
            jumps++
        }
    }
}

fun solveFiveTwo(file: String): Int {
    val l = File(file).readLines().map { it.toInt() }.toIntArray()
    var idx = 0
    var jumps = 0

    while(true) {
        if (idx + l[idx] >= l.size)
            return ++jumps
        else {
            val change = if (l[idx] >= 3) -1 else 1
            val prevIdx = idx
            idx += l[idx]
            l[prevIdx] += change
            jumps++
        }
    }
}