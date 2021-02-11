class Messages(val text: String, val contactName: String) {
    fun getDetails(){
        println("Contact Name: $contactName")
        println("Message: $text")
    }
}