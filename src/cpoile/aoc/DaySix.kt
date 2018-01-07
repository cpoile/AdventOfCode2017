package cpoile.aoc

import java.io.File

fun main(argh: Array<String>) {
    //println("\n" + solveSixOne("DaySixOne-test.txt"))
    //println("\n" + solveSixOne("DaySixOne.txt"))
    //println("\n" + solveSixTwo("DaySixOne-test.txt"))
    println("\n" + solveSixTwo("DaySixOne.txt"))
}

fun solveSixOne(file: String): Int {
    var count = 0
    val arr = File(file).readLines()[0].split("\\D+".toRegex()).map { it.toInt() }.toIntArray()
    var pastArrays: Set<String> = setOf()
    pastArrays = pastArrays.plus(arr.joinToString { " " })
    //println( arr.joinToString( " "))

    while(true) {
        val max = arr.withIndex().maxWith(compareBy({ it.value }, { -it.index }))
        var (idx, blocks) = max!!
        arr[idx] = 0
        for (i in 1..blocks) {
            idx = (++idx) % arr.size
            arr[idx]++
        }
        //println( arr.joinToString( " "))
        count++

        if (pastArrays.contains(arr.joinToString(" ")))
            return count
        else
            pastArrays = pastArrays.plus(arr.joinToString(" "))
    }
}

fun solveSixTwo(file: String): Int {
    var count = 0
    val arr = File(file).readLines()[0].split("\\D+".toRegex()).map { it.toInt() }.toIntArray()
    var pastArrays: Set<String> = setOf()
    pastArrays = pastArrays.plus(arr.joinToString( " " ))
    var pastArraysInOrder: ArrayList<String> = ArrayList()
    pastArraysInOrder.add(arr.joinToString( " " ))

    while(true) {
        val max = arr.withIndex().maxWith(compareBy({ it.value }, { -it.index }))
        var (idx, blocks) = max!!
        arr[idx] = 0
        for (i in 1..blocks) {
            idx = (++idx) % arr.size
            arr[idx]++
        }
        //println( arr.joinToString( " "))
        count++

        if (pastArrays.contains(arr.joinToString(" "))) {
            // find the answer in the in-order array, return the difference
            return (count - pastArraysInOrder.indexOf(arr.joinToString(" ")))
        } else {
            pastArrays = pastArrays.plus(arr.joinToString(" "))
            pastArraysInOrder.add(arr.joinToString(" "))
        }
    }
}