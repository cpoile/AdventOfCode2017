package cpoile.aoc

import java.io.File

fun main(argh: Array<String>) {
    assert(solveFourOne("DayFourOne-1.txt") == 4)
    println(solveFourOne("DayFourOne.txt"))
    println(solveFourTwo("DayFourOne.txt"))

    println(solveFourOne2("DayFourOne-1.txt"))
    println(solveFourTwo2("DayFourOne.txt"))
}

fun solveFourOne2(file: String): Int {
    return File(file).readLines().map{ it.split(" ") }
            .count{ it.size == it.toSet().size }
}

fun solveFourTwo2(file: String): Int {
    return File(file).readLines().map{ it.split(" ") }
            .map{ it.map{ it.toCharArray().sorted().joinToString("") }}
            .count{ it.size == it.toSet().size }
}

fun solveFourOne(file: String): Int {
    var count = 0
    for (l in File(file).readLines()) {
        val words = l.split(" ").sorted()
        var unique = true
        for(i in 0..words.size-2) {
            if (words[i].equals(words[i+1])) {
                unique = false
                break
            }
        }
        if (unique) count++
    }
    return count
}

fun solveFourTwo(file: String): Int {
    var count = 0
    for (l in File(file).readLines()) {
        val words = l.split(" ")
                .map{ it.toCharArray().sorted().joinToString("")}
                .sortedWith(compareBy({it.length}, {it}))
        var unique = true
        for(i in 0..words.size-2) {
            if (words[i].equals(words[i+1])) {
                unique = false
                break
            }
        }
        if (unique) count++
    }
    return count
}

