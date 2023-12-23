import utils.readFileLineByLineUsingForEachLine

fun main() {
    val input: Array<CharArray> = readFileLineByLineUsingForEachLine("./inputFiles/day10.txt").map {
        it.toCharArray()
    }.toTypedArray()

    var iS = 0;
    var jS = 0;

    for (i in 0..<input.size) {
        for (j in 0..<input[i].size) {
            if (input[i][j] == 'S') {
                iS = i
                jS = j
            }
        }
    }
    start(input, iS, jS)
}

fun start(input: Array<CharArray>, i: Int, j: Int) {
    println("DLA UP")
    val resultUp = input.nextStep(i - 1, j, "UP", 0)
    println("SUMA: $resultUp")
    println("DLA DOWN")
    val resultDown = input.nextStep(i + 1, j, "DOWN", 0)
    println("SUMA: $resultDown")
    println("DLA LEFY")
    val resultLeft = input.nextStep(i, j - 1, "LEFT", 0)
    println("SUMA: $resultLeft")
    println("DLA RIGHT")
    val resultRight = input.nextStep(i, j + 1, "RIGHT", 0)
    println("SUMA: $resultRight")
}

tailrec fun Array<CharArray>.nextStep(startI: Int, startJ: Int, previousStep: String, loopSize: Int): Int {
    var localLoopSize = loopSize

    if ((this[startI][startJ] == 'S')) {
        return localLoopSize + 1
    }

    if (this[startI][startJ] == '|' && previousStep == "UP") {
        localLoopSize += 1
        return nextStep(startI - 1, startJ, "UP", localLoopSize)
    }

    if (this[startI][startJ] == '|' && previousStep == "DOWN") {
        localLoopSize += 1
        return nextStep(startI + 1, startJ, "DOWN", localLoopSize)
    }

    if (this[startI][startJ] == 'L' && previousStep == "DOWN") {
        localLoopSize += 1
        return nextStep(startI, startJ + 1, "RIGHT", localLoopSize)
    }

    if (this[startI][startJ] == 'L' && previousStep == "LEFT") {
        localLoopSize += 1
        return nextStep(startI - 1, startJ, "UP", localLoopSize)
    }

    if (this[startI][startJ] == 'J' && previousStep == "DOWN") {
        localLoopSize += 1
        return nextStep(startI, startJ - 1, "LEFT", localLoopSize)
    }

    if (this[startI][startJ] == 'J' && previousStep == "RIGHT") {
        localLoopSize += 1
        return nextStep(startI - 1, startJ, "UP", localLoopSize)
    }

    if (this[startI][startJ] == '7' && previousStep == "UP") {
        localLoopSize += 1
        return nextStep(startI, startJ - 1, "LEFT", localLoopSize)
    }

    if (this[startI][startJ] == '7' && previousStep == "RIGHT") {
        localLoopSize += 1
        return nextStep(startI + 1, startJ, "DOWN", localLoopSize)
    }

    if (this[startI][startJ] == 'F' && previousStep == "UP") {
        localLoopSize += 1
        return nextStep(startI, startJ + 1, "RIGHT", localLoopSize)
    }

    if (this[startI][startJ] == 'F' && previousStep == "LEFT") {
        localLoopSize += 1
        return nextStep(startI + 1, startJ, "DOWN", localLoopSize)
    }

    if (this[startI][startJ] == '-' && previousStep == "RIGHT") {
        localLoopSize += 1
        return nextStep(startI, startJ + 1, "RIGHT", localLoopSize)
    }

    if (this[startI][startJ] == '-' && previousStep == "LEFT") {
        localLoopSize + 1
        return nextStep(startI, startJ - 1, "LEFT", localLoopSize)
    }

    return -1
}