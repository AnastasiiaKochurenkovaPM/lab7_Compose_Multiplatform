
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    items: List<TodoItem>,
    inputText: String,
    inputBool: String,
    inputNumber: String,
    inputPrice: String,
    onItemClicked: (id: Long) -> Unit,
    onItemDoneChanged: (id: Long, isDone: Boolean) -> Unit,
    onItemDeleteClicked: (id: Long) -> Unit,
    onAddItemClicked: () -> Unit,
    onInputDescriptionChanged: (String) -> Unit,
    onInputIsDoneChanged: (String) -> Unit,
    onInputNumberChanged: (String) -> Unit,
    onInputPriceChanged: (String) -> Unit,
    onFilterSelected: (String?) -> Unit
) {
    Column(modifier) {
        TopAppBar(title = { Text(text = "Список кімнат") })

        Box(Modifier.weight(1F)) {
            ListContent(
                items = items,
                onItemClicked = onItemClicked,
                onItemDoneChanged = onItemDoneChanged,
                onItemDeleteClicked = onItemDeleteClicked
            )
        }

        Filter(
            onFilterSelected = onFilterSelected
        )

        Input(
            description = inputText,
            isDone = inputBool,
            numberOfSeats = inputNumber,
            price = inputPrice,
            onDescriptionChanged = onInputDescriptionChanged,
            onNumberOfSeatsChanged = onInputNumberChanged,
            onIsDoneChanged = onInputIsDoneChanged,
            onPriceChanged = onInputPriceChanged,
            onAddClicked = onAddItemClicked
        )
    }
}

@Composable
private fun ListContent(
    items: List<TodoItem>,
    onItemClicked: (id: Long) -> Unit,
    onItemDoneChanged: (id: Long, isDone: Boolean) -> Unit,
    onItemDeleteClicked: (id: Long) -> Unit,
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(items) { item ->
                Item(
                    item = item,
                    onClicked = { onItemClicked(item.id) },
                    onDoneChanged = { onItemDoneChanged(item.id, it) },
                    onDeleteClicked = { onItemDeleteClicked(item.id) }
                )

                Divider()
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = listState)
        )
    }
}

@Composable
private fun Item(
    item: TodoItem,
    onClicked: () -> Unit,
    onDoneChanged: (Boolean) -> Unit,
    onDeleteClicked: () -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = onClicked)) {
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString("${item.description}, Зайнятий: ${item.isDone}, Кількість місць: ${item.numberOfSeats}, Ціна: ${item.price}"),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onDeleteClicked) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Input(
    description: String,
    isDone: String,
    numberOfSeats: String,
    price: String,
    onDescriptionChanged: (String) -> Unit,
    onIsDoneChanged: (String) -> Unit,
    onNumberOfSeatsChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onAddClicked: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = description,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onDescriptionChanged,
            label = { Text(text = "Опис") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = numberOfSeats,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onNumberOfSeatsChanged,
            label = { Text(text = "Кількість місць") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = price,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onPriceChanged,
            label = { Text(text = "Ціна") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = isDone,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onIsDoneChanged,
            label = { Text(text = "Зайнятий") }
        )


        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = {
            onAddClicked()
            onIsDoneChanged("") // Очищення поля isDone
            onNumberOfSeatsChanged("") // Очищення поля numberOfSeats
            onPriceChanged("") // Очищення поля price
        }){ 
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,

            )
        }
    }
}


@Composable
private fun Filter(
    onFilterSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<Boolean?>(null) }

    Spacer(modifier = Modifier.width(8.dp))

    Box(modifier = Modifier.wrapContentSize()) {
        Button(
            onClick = { expanded = true },
            contentPadding = PaddingValues(8.dp)
        ) {
            Text(text = selectedFilter?.toString() ?: "all")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    selectedFilter = null
                    onFilterSelected(null)
                    expanded = false
                }
            ) {
                Text(text = "all")
            }

            DropdownMenuItem(
                onClick = {
                    selectedFilter = true
                    onFilterSelected("true")
                    expanded = false
                }
            ) {
                Text(text = "true")
            }

            DropdownMenuItem(
                onClick = {
                    selectedFilter = false
                    onFilterSelected("false")
                    expanded = false
                }
            ) {
                Text(text = "false")
            }
        }
    }
}


