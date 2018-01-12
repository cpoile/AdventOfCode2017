package cpoile.aoc

import kotlin.coroutines.experimental.buildSequence

fun main(argh: Array<String>) {
    // testing:
    //val genA = createGenerator(65, 16807)
    //val genB = createGenerator(8921, 48271)
    // the real values:
    //val genA = createGenerator(591, 16807)
    //val genB = createGenerator(393, 48271)

    // testing Part 2:
    //val genA = createPickyGenerator(65, 16807, 4)
    //val genB = createPickyGenerator(8921, 48271, 8)
    // the real values:
    val genA = createPickyGenerator(591, 16807, 4)
    val genB = createPickyGenerator(393, 48271, 8)

    //genA.drop(1).take(5).forEach { println(convertToBinary(it).takeLast(16)) }
    //genB.drop(1).take(5).forEach { println(convertToBinary(it).takeLast(16)) }

    val aIt = genA.drop(1).iterator()
    val bIt = genB.drop(1).iterator()

    var count = 0
    // for part 1, change to 40000000
    // Part 2:
    for (i in 1..5000000) {
        if( convertToBinary(aIt.next()).takeLast(16).equals(convertToBinary(bIt.next()).takeLast(16)) )
            count++
    }
    println("Matched pairs: $count")
}

fun createGenerator(seed: Int, factor: Int) =
        generateSequence(seed) { ((it.toLong() * factor.toLong()) % 2147483647).toInt() }

fun createPickyGenerator(seed: Int, factor: Int, picky: Int) =
    buildSequence {
        var cur = seed.toLong()
        while(true) {
            cur = ((cur.toLong() * factor.toLong()) % 2147483647)
            if (cur % picky == 0L)
                yield(cur.toInt())
        }
    }

fun convertToBinary(i: Int) =
        String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0')
