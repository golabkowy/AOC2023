import utils.readFileLineByLineUsingForEachLine

fun main() {
    val input: MutableList<MutableList<Int>> = readFileLineByLineUsingForEachLine("./inputFiles/day9.txt")
        .map {
            it.split(' ')
                .map { it.toInt() }
                .toMutableList()
        }.toMutableList()

    var result = 0
    input.forEachIndexed { indexI, array ->
        val bucket: MutableList<MutableList<Int>> = mutableListOf(mutableListOf())
        bucket[0] = array
        for (i in 1..array.size - 2) {
            bucket.add(MutableList(array.size - i) { 0 })
            for (j in 0..array.size - 1 - i) {
                bucket[i][j] = bucket[i - 1][j + 1] - bucket[i - 1][j]
            }
            var isZero = true
            bucket[i].forEach {
                if (it != 0) {
                    isZero = false
                }
            }
            if (isZero) {
                break
            }
        }
        // PART ONE
        for (k in bucket.size - 1 downTo 0) {
            if (k == bucket.size - 1) {
                bucket[k].add(0)
            } else {
                bucket[k].add(bucket[k + 1][bucket[k + 1].size - 1] + bucket[k][bucket[k].size - 1])
            }

        }
        val bucketResult = bucket[0].last();
        result += bucketResult

        /** PART TWO
        for (k in bucket.size - 1 downTo 0) {
        if (k == bucket.size - 1) {
        bucket[k].add(0, 0)
        } else {
        //                bucket[k].add(bucket[k + 1][bucket[k + 1].size - 1] + bucket[k][bucket[k].size - 1])
        bucket[k].add(0, bucket[k][0] - bucket[k + 1][0])
        }
        }
        val bucketResult = bucket[0].first();
        result += bucketResult
         */

    }
    println(result)
}
