package cpoile.aoc

import java.io.File

fun main(argh: Array<String>) {
    solveThirteen("DayThirteen-test.txt")
    solveThirteen("DayThirteen.txt")

    //for (pico in 0..20)
    //print(m(5, pico))
}
fun caught(range: Int, pico: Int): Int {
    val i = pico % ((range -1)*2)
    return if (i > (range -1))
        range - (i-(range -2))
    else
        i
}
fun findSeverity(firewall: List<List<Int>>, picoDelay: Int, defaultSeverity: Int = 0): Int {
    var severity = 0
    for ((depth, range) in firewall) {
        val pico = depth + picoDelay
        if (caught(range, pico) == 0) // caught
            severity += range*pico + defaultSeverity
    }
    return severity
}

fun solveThirteen(file: String) {
    val firewall = File(file).readLines()
            .map { it.split(": ").map { it.toInt() } }

    // Part One:
    var severity = findSeverity(firewall, 0)
    println("severity: $severity")

    // Part Two:
    var delay = 10
    while (true) {
        if (findSeverity(firewall, delay, 1) == 0)
            break
        delay++
    }
    println("To pass through undetected would take a delay of: $delay")
}
