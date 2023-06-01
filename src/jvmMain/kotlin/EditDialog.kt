//package example.todoapp.lite.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
internal fun EditDialog(
    item: TodoItem,
    onCloseClicked: () -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onIsDoneChanged: (String) -> Unit,
    onNumberChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onDoneChanged: (Boolean) -> Unit,
) {
    Dialog(
        title = "Змінити запис",
        onCloseRequest = onCloseClicked,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = item.description,
                modifier = Modifier.weight(1F).fillMaxWidth().sizeIn(minHeight = 192.dp),
                label = { Text("Опис") },
                onValueChange = onDescriptionChanged,
            )

            TextField(
                value = item.numberOfSeats,
                modifier = Modifier.weight(1F).fillMaxWidth().sizeIn(minHeight = 192.dp),
                label = { Text("Кількість місць") },
                onValueChange = onNumberChanged,
            )

            TextField(
                value = item.price,
                modifier = Modifier.weight(1F).fillMaxWidth().sizeIn(minHeight = 192.dp),
                label = { Text("Ціна") },
                onValueChange = onPriceChanged,
            )

            TextField(
                value = item.isDone,
                modifier = Modifier.weight(1F).fillMaxWidth().sizeIn(minHeight = 192.dp),
                label = { Text("Вільний/зайнятий") },
                onValueChange = onIsDoneChanged,
            )
        }
    }
}
