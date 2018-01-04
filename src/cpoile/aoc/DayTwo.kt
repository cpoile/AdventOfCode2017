package cpoile.aoc

import java.io.File

fun main(argh: Array<String>) {
    var checksum = solve("DayTwoOne-1.txt")
    println(checksum)

    checksum = solve("DayTwoOne-input.txt")
    println(checksum)

    checksum = solve2("DayTwoTwo-1.txt")
    println(checksum)

    checksum = solve2("DayTwoTwo-input.txt")
    println(checksum)
}

private fun solve(file: String): Int {
    val lines = File(file).readLines()
    var checksum = 0
    for (l in lines) {
        val digits = l.split("\\D".toRegex()).map { it.toInt() }
        checksum += digits.max()!! - digits.min()!!
    }
    return checksum
}

// we don't need to compare all ab ba combinations, just once for each pair ab
fun solve2(file: String): Int {
    val lines = File(file).readLines()
    var checksum = 0
    for (l in lines) {
        val digits = l.split("\\D".toRegex()).map { it.toInt() }
        loop@ for (d in digits.indices) {
            for (e in (d+1) until digits.size) {
                if (digits[d] % digits[e] == 0 || digits[e] % digits[d] == 0) {
                    checksum += Math.max(digits[d], digits[e]) / Math.min(digits[d], digits[e])
                    break@loop
                }
            }
        }
    }
    return checksum
}