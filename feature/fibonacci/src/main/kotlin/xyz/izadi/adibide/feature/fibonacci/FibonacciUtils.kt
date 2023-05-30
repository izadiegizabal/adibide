package xyz.izadi.adibide.feature.fibonacci

/**
 * function to check if a list of numbers is a sublist of the fibonacci series
 * @param list list that we want to check if it is formed by fibonacci sequence numbers
 * @param minSize minimum number of fibonacci sequence numbers we should have in the list
 */
fun isFibonacciSublist(list: List<Double>, minSize: Int): Boolean {
    // generate the fibonacci series until the last element of the list is reached or exceeded
    val fibonacci = mutableListOf(0.0, 1.0)
    while (fibonacci.last() < list.last()) {
        fibonacci.add(fibonacci.takeLast(2).sum())
    }

    // check if the list is a sublist of the generated fibonacci series
    return fibonacci.size >= minSize && fibonacci.windowed(list.size).any { it == list }
}
