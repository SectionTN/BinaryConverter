import java.math.BigInteger

fun getUserInput(prompt: String): String {
    print("$prompt: ")
    return readlnOrNull() ?: ""
}

fun toDecimal(input: String, base: Int): BigInteger {
    val chars = "0123456789ABCDEF"
    var result = BigInteger.ZERO
    for ((index, value) in input.withIndex()) {
        val power = input.length - index - 1
        val digit = chars.indexOf(value.uppercaseChar())
        if (digit == -1 || digit >= base) {
            throw IllegalArgumentException("$value is not valid for base $base.")
        }
        result += digit.toBigInteger() * base.toBigInteger().pow(power)
    }
    return result
}

fun fromDecimal(decimal: BigInteger, base: Int): String {
    val chars = "0123456789ABCDEF"
    var remaining = decimal
    val result = StringBuilder()
    while (remaining > BigInteger.ZERO) {
        val digit = remaining.mod(base.toBigInteger()).toInt()
        remaining /= base.toBigInteger()
        result.insert(0, chars[digit])
    }
    return if (result.isEmpty()) "0" else result.toString()
}

fun main() {
    while (true) {
        try {
            val inputValue = getUserInput("Enter the number to convert (or 'exit' to quit)")
            if (inputValue.lowercase() == "exit") break

            val baseValue = getUserInput("Enter the base of this number (2, 8, 10, 16)")
            val baseFrom = baseValue.toInt()

            val targetBaseValue = getUserInput("Enter the base you want to convert to (2, 8, 10, 16)")
            val baseTo = targetBaseValue.toInt()

            val decimalValue = toDecimal(inputValue, baseFrom)
            val result = fromDecimal(decimalValue, baseTo)

            println("The number $inputValue in base $baseFrom is $result in base $baseTo.")
        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
        }
    }
}