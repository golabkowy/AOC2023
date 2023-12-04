import utils.readTo2DCharArray

fun main() {
    val array: Array<Array<Char>> = readTo2DCharArray("./inputFiles/day3.txt")
    val result = array.addArtificialBoarder(array).checkNeighbours()
    println(result)
}

fun Array<Array<Char>>.addArtificialBoarder(array: Array<Array<Char>>): Array<Array<Char>> {
    val newArray: Array<Array<Char>> = Array(array.size + 2) { Array(array[0].size + 2) { 'X' } }
    for (i in array.indices) {
        for (j in array[i].indices) {
            newArray[i + 1][j + 1] = array[i][j]
        }
    }
    return newArray
}

fun Array<Array<Char>>.checkNeighbours(): Int {
    var acc = 0;
    for (i in 1 until this.size - 1) {
        for (j in 1 until this[i].size - 1) {
            if (this[i][j].isDigit() && !this[i][j + 1].isDigit()) {
                val sb: StringBuilder = StringBuilder()
                var isPartNumber: Boolean = false
                var k = j
                while (this[i][k].isDigit()) {
                    if (this[i + 1][k - 1].isSpecialChar() || this[i + 1][k].isSpecialChar() || this[i + 1][k + 1].isSpecialChar() ||
                        this[i][k - 1].isSpecialChar() || this[i][k + 1].isSpecialChar() ||
                        this[i - 1][k - 1].isSpecialChar() || this[i - 1][k].isSpecialChar() || this[i - 1][k + 1].isSpecialChar()
                    ) {
                        isPartNumber = true
                    }
                    sb.append(this[i][k])
                    k--
                }
                if (isPartNumber) {
                    val temp = sb.toString().reversed().toInt()
                    acc += temp
                }
            }
        }
    }
    return acc
}

fun Array<Array<Char>>.partTwo(): Int {
    var acc = 0;
    for (i in 1 until this.size - 1) {
        for (j in 1 until this[i].size - 1) {
            if (this[i][j].isDigit() && !this[i][j + 1].isDigit()) {
                val sb: StringBuilder = StringBuilder()
                var isPartNumber: Boolean = false
                var k = j
                while (this[i][k].isDigit()) {
                    if (this[i + 1][k - 1].isSpecialChar() || this[i + 1][k].isSpecialChar() || this[i + 1][k + 1].isSpecialChar() ||
                        this[i][k - 1].isSpecialChar() || this[i][k + 1].isSpecialChar() ||
                        this[i - 1][k - 1].isSpecialChar() || this[i - 1][k].isSpecialChar() || this[i - 1][k + 1].isSpecialChar()
                    ) {
                        isPartNumber = true
                    }
                    sb.append(this[i][k])
                    k--
                }
                if (isPartNumber) {
                    val temp = sb.toString().reversed().toInt()
                    acc += temp
                }
            }
        }
    }
    return acc
}

fun Char.isSpecialChar(): Boolean {
    val regex = Regex("[!@#\$%^&*+\\-=/]")
    return regex.matches(this.toString()) && this != '.'
}