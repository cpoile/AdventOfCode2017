package cpoile.aoc

import java.io.File

fun main(argh: Array<String>) {
    //solveFourteen("flqrgnkx")
    solveFourteen("hfdlxzhv")

    //solveFourteenTwo("flqrgnkx")
    solveFourteenTwo("hfdlxzhv")
}

fun solveFourteen(input: String) {
    val hashArr = Array<String>(128, {""})
    for (row in 0..127) {
        hashArr[row] = hexToBinary(knotHash("$input-$row"))
    }
    val sum = sumUsedSquares(hashArr)
    println("Used squares: $sum")
}

fun sumUsedSquares(hashArr: Array<String>) =
        hashArr.fold(0, { acc, s -> acc + s.count { it == '1' } })

fun solveFourteenTwo(input: String) {
    val hashArr = Array<IntArray>(128, { IntArray(128) })
    for (row in 0..127) {
        hashArr[row] = hexToBinary(knotHash("$input-$row")).map { Integer.parseInt(it.toString()) }.toIntArray()
    }
    // now do a dfs to find all connected regions
    var numRegions = 0
    while (hasNext1(hashArr)) {
        val (row, col) = next1(hashArr)
        dfsReplaceWith0s(hashArr, row, col)
        numRegions++
    }
    print("Number of regions: $numRegions")
}

fun dfsReplaceWith0s(hashArr: Array<IntArray>, row: Int, col: Int) {
    // we are at a 1, so replace self with 0, and then visit each neighbor who has 1
    hashArr[row][col] = 0
    // north
    if (row > 0 && hashArr[row-1][col] == 1)
        dfsReplaceWith0s(hashArr, row-1, col)
    // east
    if (col < 127 && hashArr[row][col+1] == 1)
        dfsReplaceWith0s(hashArr, row, col+1)
    // south
    if (row < 127 && hashArr[row+1][col] == 1)
        dfsReplaceWith0s(hashArr, row+1, col)
    // west
    if (col > 0 && hashArr[row][col-1] == 1)
        dfsReplaceWith0s(hashArr, row, col-1)
}

fun hasNext1(hashArr: Array<IntArray>): Boolean =
        hashArr.fold(0, {acc, ints -> acc + ints.sum()}) > 0

fun next1(hashArr: Array<IntArray>): Pair<Int, Int> {
    for (row in 0..127) {
        for (col in 0..127) {
            if (hashArr[row][col] == 1)
                return Pair(row, col)
        }
    }
    return Pair(0,0)
}

fun hexToBinary(hex: String): String {
    // Integer.toBinaryString can only handle chunks of 8, so just for ease:
    return hex.map {
        String.format("%4s", Integer.toBinaryString(Integer.parseInt(it.toString(), 16)))
                .replace(' ', '0')
    }.joinToString("")
}