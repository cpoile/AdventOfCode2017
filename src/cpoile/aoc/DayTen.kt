package cpoile.aoc

import java.util.*

val testInput = arrayOf(3, 4, 1, 5)
val input = arrayOf(147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70)

val testInput2 = "1,2,3"
val testInput3 = "3,4,1,5"
val testInput4 = ""
val testInput5 = "AoC 2017"
val testInput6 = "1,2,4"
val realInput = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70"

fun main(argh: Array<String>) {
    // change this:
    val theInput  = realInput
    println(knotHash(theInput))
}

fun knotHash(input: String): String {
        val arr = IntArray(256) { i -> i }
    var idx = 0
    var skipSize = 0
    val lengthSeq = input.toCharArray().map { it.toInt() } + arrayOf( 17, 31, 73, 47, 23 )

    for (i in 1..64) {
        for (lengthInstr in lengthSeq) {
            val elems = getNextElements(arr, idx, lengthInstr)
            elems.reverse()
            setNextElements(arr, idx, lengthInstr, elems)
            idx = (idx + lengthInstr + skipSize) % arr.size
            skipSize++
            //arr.forEach { print("$it, ") }
            //println()
        }
    }
    // now generate the dense hash
    val sparseHash = ArrayDeque<Int>()
    for (i in 0 until arr.size step 16) {
        var accum = 0
        for (j in i..(i+15)) {
            val tmp = arr[j]
            accum = accum xor tmp
        }
        sparseHash.addLast(accum)
    }
    // now format it is as lowercase hex
    var hex = ""
    for (i in sparseHash) {
        hex += String.format("%02x", i)
    }
    //println("Answer: ${arr[0] * arr[1]}")
    return hex
}

fun getNextElements(arr: IntArray, curIdx: Int, numElems: Int): IntArray {
    val ret = IntArray(numElems)
    for (i in 0 until numElems) {
        ret[i] = arr[(curIdx + i) % arr.size]
    }
    return ret
}

fun setNextElements(arr: IntArray, curIdx: Int, numElems: Int, newElems: IntArray) {
    for (i in 0 until numElems) {
        arr[(i + curIdx) % arr.size] = newElems[i]
    }
}