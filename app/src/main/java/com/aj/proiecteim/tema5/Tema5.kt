package com.aj.proiecteim.tema5

import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.recreate
import com.example.reply.ui.theme.MaterialThemeEIM
import java.io.File

class Tema5 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        this.actionBar?.hide()

        super.onCreate(savedInstanceState)

        setContent {
            MaterialThemeEIM {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TextFields(context = this)
                    ListDisplay(context = this)
                }
            }
        }
    }
}

@Composable
fun TextFields(context: ComponentActivity) {
    var stringInput by remember { mutableStateOf("") }
    var numberInput by remember { mutableStateOf("") }
    var internalCheckbox by remember { mutableStateOf(true) }
    var externalCheckbox by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = stringInput,
            onValueChange = { stringInput = it },
            label = { Text("Șir de caractere") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        OutlinedTextField(
            value = numberInput,
            onValueChange = { numberInput = it },
            label = { Text("Număr") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (stringInput.isEmpty() || numberInput.isEmpty())
                    Toast.makeText(context, "Introduceți valori valide", Toast.LENGTH_SHORT).show()
                else
                    if (!internalCheckbox && !externalCheckbox)
                        Toast.makeText(context, "Selectați cel puțin o locație", Toast.LENGTH_SHORT).show()
                    else {
                        if (internalCheckbox)
                            writeToFile(context, "internal", "data.txt", "String: $stringInput, Number: $numberInput")
                        if (externalCheckbox)
                            writeToFile(context, "external", "data.txt", "String: $stringInput, Number: $numberInput")
                        recreate(context)
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Scriere",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Row(
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(
                text = "Scrierea se face în mod: ",
                modifier = Modifier.align(CenterVertically),
                color = MaterialTheme.colorScheme.secondary
            )

            RoundedCornerCheckbox(
                label = "intern",
                isChecked = internalCheckbox,
                onValueChange = { internalCheckbox = it },
                modifier = Modifier.padding(end = 6.dp)
            )

            RoundedCornerCheckbox(
                label = "extern",
                isChecked = externalCheckbox,
                onValueChange = { externalCheckbox = it },
            )
        }
    }
}

@Composable
fun ListDisplay(context: ComponentActivity) {
    val internalData = remember { readFromFile(context, "internal", "data.txt") }
    val externalData = remember { readFromFile(context, "external", "data.txt") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 270.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text (
            "Valori interne:",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        for ((index, item) in internalData.withIndex()) {
            Column {
                Text(
                    text = "Șir de caractere: " +
                            item.substringAfter("String: ").substringBefore(", Number: ") +
                            "\nNumăr: " +
                            item.substringAfter(", Number: "),
                    color = MaterialTheme.colorScheme.secondary
                )
                Button(
                    onClick = {
                        deleteItem(context, "internal", "data.txt", index)
                        recreate(context)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Ștergere")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text (
            "Valori externe:",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        for ((index, item) in externalData.withIndex()) {
            Column {
                Text(
                    text = "Șir de caractere: " +
                            item.substringAfter("String: ").substringBefore(", Number: ") +
                            "\nNumăr: " +
                            item.substringAfter(", Number: "),
                    color = MaterialTheme.colorScheme.secondary
                )
                Button(
                    onClick = {
                        deleteItem(context, "external", "data.txt", index)
                        recreate(context)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Ștergere")
                }
            }
        }
    }
}

fun deleteItem(context: ComponentActivity, location: String, fileName: String, index: Int) {
    val file = when (location) {
        "internal" -> File(context.filesDir, fileName)
        "external" -> File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
        else -> throw IllegalArgumentException("Invalid location")
    }
    val lines = file.readLines()

    if (index in lines.indices) {
        file.writeText("")
        for ((i, line) in lines.withIndex()) {
            if (i != index) {
                file.appendText("$line\n")
            }
        }
    } else {
        throw IndexOutOfBoundsException("Index out of bounds: $index")
    }
}

fun writeToFile(context: ComponentActivity, location: String, fileName: String, data: String) {
    val file = when (location) {
        "internal" -> File(context.filesDir, fileName)
        "external" -> File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
        else -> throw IllegalArgumentException("Invalid location")
    }

    file.appendText("$data\n") // appendText pentru adaugare, writeText pt overwrite
}

fun readFromFile(context: ComponentActivity, location: String, fileName: String): List<String> {
    val file = when (location) {
        "internal" -> File(context.filesDir, fileName)
        "external" -> File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
        else -> throw IllegalArgumentException("Invalid location")
    }
    return if (file.exists()) {
        file.readLines()
    } else {
        emptyList()
    }
}

@Composable
fun RoundedCornerCheckbox(
    label: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    size: Float = 24f,
    checkedColor: Color = MaterialTheme.colorScheme.primary,
    uncheckedColor: Color = MaterialTheme.colorScheme.background,
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor: Color by animateColorAsState(if (isChecked) checkedColor else uncheckedColor,
        label = ""
    )
    val density = LocalDensity.current
    val duration = 200

    Row(
        verticalAlignment = CenterVertically,
        modifier = modifier
            .heightIn(48.dp) // height of 48dp to comply with minimum touch target size
            .toggleable(
                value = isChecked,
                role = androidx.compose.ui.semantics.Role.Checkbox,
                onValueChange = onValueChange
            )
    ) {
        Spacer(modifier = Modifier.padding(4.dp))
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(color = checkboxColor, shape = RoundedCornerShape(4.dp))
                .border(width = 1.5.dp, color = checkedColor, shape = RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isChecked,
                enter = slideInHorizontally(animationSpec = tween(duration)) {
                    with(density) { (size * -0.5).dp.roundToPx() }
                } + expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(duration)
                ),
                exit = fadeOut()
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = uncheckedColor
                )
            }
        }
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            text = label,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
