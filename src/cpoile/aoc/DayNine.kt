package cpoile.aoc

import java.io.File
import java.util.*

fun main(argh: Array<String>) {
    //solveNine("DayNine-test.txt")
    //solveNine("DayNine.txt")
    //solveNine("DayNineTwo-test.txt")
    solveNine("DayNine.txt")
}

fun solveNine(file: String) {
    File(file).readLines().forEach {
        val stack = ArrayDeque<Char>()
        var curGroupLevel = 0
        var total = 0
        var garbageTotal = 0
        it.forEach {
            if (stack.peekFirst() == '!')
                stack.removeFirst() // and ignore current char
            else if (stack.peekFirst() == '<')
                when (it) {
                    '>' -> stack.removeFirst()
                    '!' -> stack.addFirst(it)
                    else -> garbageTotal++
                }
            else when {
                it == '{' -> {
                    stack.addFirst(it)
                    curGroupLevel++
                }
                it == '}' && stack.peekFirst() == '{' -> {
                    total += curGroupLevel--
                    stack.removeFirst()
                }
                it == '<' -> stack.addFirst(it)
                else -> 0 // do nothing
            }
        }
        // for part one:
        //println(total)

        // for part two:
        println(garbageTotal)
    }
}