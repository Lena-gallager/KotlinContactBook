package utils

object Converter {
    fun readNumber(): Int?{
        return try {
            val line = readLine()
            Integer.parseInt(line)
        }
        catch (e: NumberFormatException){
            null
        }
    }
    fun readString(): String{
        try {
            val line = readLine()
            return line.toString()
        }
        catch (e: Exception){
            throw IllegalArgumentException("Cant be null")
        }
    }
}