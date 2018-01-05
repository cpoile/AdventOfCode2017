package cpoile.aoc

// Given a square's number, the containing 2d array will have sides of length
// round(sqrt(x)) up to nearest odd number. (let that equal d)
// Then the index of the square's number will tell us how far from 1 we are,
// because 1 will be at (d-1)/2, (d-1)/2.
// So take index of square's number - index of 1 to get distance to 1.

// To generate the square, follow the pattern: start center facing right, place 1
// move 1, place number, turn counter clockwise
// move 1, place number, turn counter clockwise
// move 2 (placing numbers), turn
// move 2 (placing numbers), turn
// move 3, turn
// move 3, turn
// move 4, turn, and so on.

fun main(argh: Array<String>) {
    //println(solve(361527))
    //println(solve2(747))
    println(solve2(361527))
}

fun solve(squareTarget: Int): Int {
    val rawD = Math.ceil(Math.sqrt(squareTarget.toDouble())).toInt()
    val d = if (rawD % 2 == 0) rawD+1 else rawD
    var mat = Array<IntArray>(d, { _ -> IntArray(d) })

    // OR: just keep track of our x,y coord and spiral out until we hit our target:
    // but I'll still make the matrix just in case
    // NOTE: matrix 0,0 is top left
    val dir = arrayOf(arrayOf(1, 0), arrayOf(0, -1), arrayOf(-1, 0), arrayOf(0, 1))
    var pos = intArrayOf(d/2, d/2)
    var dirIdx = 0     // x=1, y=0 means moving right (add it to an x,y pair)

    var squareNum = 1
    var step = 1
    mat[pos[1]][pos[0]] = squareNum

    outer@ while(true) {
        for(j in 0..1) {
            // move x steps and place numbers
            for (i in 1..step) {
                pos[0] += dir[dirIdx][0]
                pos[1] += dir[dirIdx][1]
                // place number
                mat[pos[1]][pos[0]] = ++squareNum
                if (squareNum >= squareTarget)
                    break@outer
            }
            // turn counter clockwise
            dirIdx = (dirIdx + 1) % 4
        }
        step++
    }

//    for(i in mat.indices) {
//        for(j in mat[i].indices)
//            print(" %03d".format(mat[i][j]))
//        println()
//    }
    val distance = Math.abs(pos[0]-d/2) + Math.abs(pos[1]-d/2)
    println("pos: ${pos[0]}, ${pos[1]}\nAnd distance is: $distance")

    return distance
}

fun solve2(squareTarget: Int): Int {
    val rawD = Math.ceil(Math.sqrt((squareTarget/2).toDouble())).toInt()
    val d = if (rawD % 2 == 0) rawD+1 else rawD
    var mat = Array<IntArray>(d, { _ -> IntArray(d) })

    // NOTE: matrix 0,0 is top left
    val dir = arrayOf(arrayOf(1, 0), arrayOf(0, -1), arrayOf(-1, 0), arrayOf(0, 1))
    var pos = intArrayOf(d/2, d/2)
    var dirIdx = 0     // x=1, y=0 means moving right (add it to an x,y pair)

    var squareNum = 1
    var step = 1
    mat[pos[1]][pos[0]] = squareNum

    outer@ while(true) {
        for(j in 0..1) {
            // move x steps and place numbers
            for (i in 1..step) {
                pos[0] += dir[dirIdx][0]
                pos[1] += dir[dirIdx][1]
                // place number by finding adjacent squares and summing their values
                mat[pos[1]][pos[0]] = (mat[pos[1]][pos[0]+1] // right
                        + mat[pos[1]-1][pos[0]+1]   // up-right
                        + mat[pos[1]-1][pos[0]]   // up
                        + mat[pos[1]-1][pos[0]-1]   // up-left
                        + mat[pos[1]][pos[0]-1]   // left
                        + mat[pos[1]+1][pos[0]-1]   // down-left
                        + mat[pos[1]+1][pos[0]]   // down
                        + mat[pos[1]+1][pos[0]+1]   // down-right
                        )
                if (mat[pos[1]][pos[0]] > squareTarget)
                    break@outer
            }
            // turn counter clockwise
            dirIdx = (dirIdx + 1) % 4
        }
        step++
    }

//    for(i in mat.indices) {
//        for(j in mat[i].indices)
//            print(" %04d".format(mat[i][j]))
//        println()
//    }

    return mat[pos[1]][pos[0]]
}