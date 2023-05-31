package xyz.izadi.adibide.feature.fibonacci

/**
 * function to check if a list of numbers is a sublist of the fibonacci series
 * @param list list that we want to check if it is formed by fibonacci sequence numbers
 */
fun isFibonacciSequence(list: List<Double>): Boolean {
    if (list.isEmpty()) return false

    // generate the fibonacci series until the max element of the list is reached or exceeded
    val fib = mutableListOf(0.0, 1.0)
    val max = list.max()

    while (fib.last() < max) {
        fib.add(fib.takeLast(2).sum())
    }

    // check if the list is a sublist of the generated fibonacci series
    return fib.size >= list.size &&
            fib.windowed(list.size).any { it == list || it == list.asReversed() }
}
