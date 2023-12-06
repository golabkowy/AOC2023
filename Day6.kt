fun main() {
    val time = 58996469L
    val distance = 478223210191071L
    var holdTime = 0
    var acc = 0
    while (holdTime <= time) {
        if ((holdTime) * (time - holdTime) > distance)
            acc++
        holdTime++
    }
    println(acc)
}