package cpoile.aoc

import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.max

fun main(argh: Array<String>) {
    //println(solveEight("DayEightOne-test.txt"))
    println(solveEight("DayEight.txt"))
}

fun solveEight(file: String): Int {
    val regs = HashMap<String, Int>()
    var highestEverVal = Int.MIN_VALUE

    File(file).readLines().map { it.split(" ") }.forEach { l ->
        val reg = l[0]
        val op = l[1]
        val amount = l[2].toInt()
        val condReg = l[4]
        val condOp = l[5]
        val condAmount = l[6].toInt()

        val condRegVal = regs.getOrPut(condReg, { 0 })

        if (when (condOp) {
            ">" -> condRegVal > condAmount
            "<" -> condRegVal < condAmount
            ">=" -> condRegVal >= condAmount
            "<=" -> condRegVal <= condAmount
            "!=" -> condRegVal != condAmount
            "==" -> condRegVal == condAmount
            else -> false
        }) {
            val incBy = when (op) {
                "inc" -> amount
                "dec" -> amount * -1
                else -> 0
            }
            regs.merge(reg, incBy, Int::plus)
        }
        highestEverVal = max(regs.maxBy { it.value }!!.value, highestEverVal)
    }
    //regs.forEach { k, v -> println("$k $v") }
    // For part one:
    //return regs.maxBy { it.value }!!.value
    // For part two:
    return highestEverVal
}