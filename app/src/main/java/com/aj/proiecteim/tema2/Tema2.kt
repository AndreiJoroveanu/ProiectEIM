package com.aj.proiecteim.tema2

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reply.ui.theme.MaterialThemeEIM

class Tema2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        this.actionBar?.hide()

        super.onCreate(savedInstanceState)

        val contact1 = Contact("John Doe", "1234567890", "john@example.com", "123 Main St", "01/01/1990")
        val contact2 = Contact("Jane Smith", "0987654321", "jane@example.com", "456 Elm St", "02/02/1995")
        val contact3 = Contact("Alice Johnson", "1112223333", "alice@example.com", "789 Oak St", "03/03/2000")
        val contact4 = Contact("Bob Brown", "4445556666", "bob@example.com", "321 Pine St", "04/04/1985")
        val contact5 = Contact("Emily Davis", "7778889999", "emily@example.com", "654 Cedar St", "05/05/1992")
        val contact6 = Contact("Michael Johnson", "3334445555", "michael@example.com", "987 Maple St", "06/06/1988")
        val contact7 = Contact("Sarah Wilson", "6667778888", "sarah@example.com", "741 Birch St", "07/07/1993")
        val contact8 = Contact("David Martinez", "9990001111", "david@example.com", "852 Walnut St", "08/08/1980")
        val contact9 = Contact("Jessica Lee", "2223334444", "jessica@example.com", "369 Cherry St", "09/09/1997")
        val contact10 = Contact("Ryan Taylor", "5556667777", "ryan@example.com", "147 Poplar St", "10/10/1983")

        setContent {
            MaterialThemeEIM {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(listOf(contact1, contact2, contact3, contact4, contact5, contact6, contact7, contact8, contact9, contact10))
                }
            }
        }
    }
}

@Composable
fun MainScreen(contacts: List<Contact>) {
    var nameCondition by remember { mutableStateOf(TextFieldValue()) }
    var phoneCondition by remember { mutableStateOf(TextFieldValue()) }
    var filteredContacts by remember { mutableStateOf(contacts) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nameCondition,
            onValueChange = { nameCondition = it },
            label = { Text("Nume") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        OutlinedTextField(
            value = phoneCondition,
            onValueChange = { phoneCondition = it },
            label = { Text("Nr. Telefon") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            filteredContacts = contacts.filter {
                    contact -> contactMatchesConditions(contact, nameCondition.text, phoneCondition.text)
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Filtrează",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Persoane:",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn {
            items(filteredContacts.size) {
                    index -> val contact = filteredContacts[index]
                ContactItem(contact)
            }
        }
    }
}

fun contactMatchesConditions(contact: Contact, nameCondition: String, phoneCondition: String): Boolean {
    val nameConditionMatch = contact.name.contains(nameCondition, ignoreCase = true)
    val phoneConditionMatch = contact.phoneNumber.contains(phoneCondition, ignoreCase = true)
    return nameConditionMatch && phoneConditionMatch
}

@Composable
fun ContactItem(contact: Contact) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Nume: ${contact.name}\n" +
                    "Nr. Telefon: ${contact.phoneNumber}\n" +
                    "Email: ${contact.email}\n" +
                    "Adresă: ${contact.address}\n" +
                    "Dată naștere: ${contact.birthdate}\n",
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
