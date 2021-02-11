import Database.ContactsDb
import utils.Converter
import kotlin.system.exitProcess

fun main() {

//    val allContacts = ContactsDb.allContacts
//    allContacts.add(Contact("Lena", "324234234"))
//    allContacts.add(Contact("Artem", "32342332432"))

    println("What are u going to do?" +
            "\n\t1. Manage your contacts " +
            "\n\t2. Messages " +
            "\n\t3. Quit")

    when(Converter.readNumber()){
        null -> {
            println("Please enter a number")
            main()
        }
        1 -> manageContacts()
        2 -> messages()
        3 -> exitProcess(-1)
        else -> exitProcess(-2)
    }
}

fun manageContacts(){
    println("Manage your contact:")
    println("\t1.Show all contacts")
    println("\t2. Add a new contact")
    println("\t3. Search for a contact")
    println("\t4. Delete a contact")
    println("\t5. Go back to the previous menu")

    when(Converter.readNumber()){
        1 -> showAllContacts()
        2 -> addNewContact()
        3 -> searchContact()
        4 -> deleteContact()
        5 -> main()
        else -> exitProcess(-2)
    }
}

fun showAllContacts(){
    println("Show all contacts: ")
    val getContactDatabase = ContactsDb.allContacts
    getContactDatabase.forEach {
        println("\t${it.name}: ${it.number}")
    }
    main()
}

fun addNewContact(){
    println("Add a new contact.")
    println("Enter a name: ")
    val nameContact = Converter.readString()
    if (ifTheContactNameExist(nameContact) != null){
        println("The contact with this name already exist")
        addNewContact()
    }
    println("Enter a contact number: ")
    val numberContact = Converter.readString()

    val newContact = Contact(name = nameContact, number = numberContact)
    saveNewContact(newContact)
    println("Contact with the name $nameContact was added to your contact book")
    println("**************")
    main()
}

fun ifTheContactNameExist(name: String): Contact? {
    val allContacts = ContactsDb.allContacts
    allContacts.forEach {
        if (it.name.equals(name, ignoreCase = true)) return it
    }
    return null
}

fun searchContact(){
    println("Enter a name of the contact you are looking for: ")
    val readContactName = Converter.readString()

    val contact = ifTheContactNameExist(readContactName)
    if (contact != null){
        println("Name: ${contact.name}. Number: ${contact.number}\n")
        println("**************")
        manageContacts()
    }
    println("There is no contact with this name\n")
    println("**************")
    manageContacts()
}

fun deleteContact(){
    println("Enter a name of the contact you want to delete: ")
    val readContactName = Converter.readString()

    val contact = ifTheContactNameExist(readContactName)
    val databaseContact = ContactsDb.allContacts

    if (contact != null){
        databaseContact.remove(contact)
        println("${contact.name} was deleted from your project\n")
        println("**************")
        manageContacts()
    }
    println("There is no contact with this name\n")
    println("**************")
    manageContacts()
}

fun saveNewContact(newContact: Contact){
    ContactsDb.allContacts.add(newContact)
}

fun messages(){
    println("Messages:")
    println("\t1. See the list of all messages")
    println("\t2. Send a new message")
    println("\t3. Go back to the previous menu")

    when(Converter.readNumber()) {
        null -> {
            println("Please enter a number")
            messages()
        }
        1 -> seeAllMessages()
        2 -> sendNewMessage()
        else -> main()
    }
}

fun seeAllMessages(){
    val allMessages = arrayListOf<Messages>()
    println("All messages: ")
    val allContacts = ContactsDb.allContacts
    allContacts.forEach {
        allMessages.addAll(it.messages)
    }
    if (allMessages.size > 0){
        allMessages.forEach {
            it.getDetails()
            println("**************")
        }
    }else{
        println("You don't have any messages")
    }
    messages()
}

fun sendNewMessage(){
    println("Enter a name of the contact you want to send a message: ")
    val readName = Converter.readString()
    val contact = ifTheContactNameExist(readName)

    if (contact == null){
        println("Contact with this name does not exist")
        println("**************")
        sendNewMessage()
    }

    println("Type a message you want to send: ")
    val readTextMessage = Converter.readString()

    val message = Messages(readTextMessage, readName)
    contact!!.messages.add(message)

    println("Your message: $readTextMessage was delivered to $readName")
    messages()
}
