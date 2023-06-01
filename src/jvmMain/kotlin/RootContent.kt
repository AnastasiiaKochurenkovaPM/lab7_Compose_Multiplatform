import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun RootContent(modifier: Modifier = Modifier) {
    val model = remember { RootStore() }
    val state = model.state

    MainContent(
        modifier = modifier,
        items = state.items,
        inputText = state.inputText,
        inputBool = state.inputBool,
        inputNumber = state.inputNumber,
        inputPrice = state.inputPrice,
        onItemClicked = model::onItemClicked,
        onItemDoneChanged = model::onItemDoneChanged,
        onItemDeleteClicked = model::onItemDeleteClicked,
        onAddItemClicked = model::onAddItemClicked,
        onInputDescriptionChanged = model::onInputDescriptionChanged,
        onInputNumberChanged = model::onInputNumberChanged,
        onInputIsDoneChanged = model::onInputIsDoneChanged,
        onInputPriceChanged = model::onInputPriceChanged,
        onFilterSelected = model::onFilterSelected,
    )

    state.editingItem?.also { item ->
        EditDialog(
            item = item,
            onCloseClicked = model::onEditorCloseClicked,
            onDescriptionChanged = model::onEditorDescriptionChanged,
            onIsDoneChanged = model::onEditorIsDoneChanged,
            onNumberChanged = model::onEditorNumberChanged,
            onPriceChanged = model::onEditorPriceChanged,
            onDoneChanged = model::onEditorDoneChanged,
        )
    }
}

private val RootStore.RootState.editingItem: TodoItem?
    get() = editingItemId?.let(items::firstById)

private fun List<TodoItem>.firstById(id: Long): TodoItem? =
    firstOrNull { it.id == id }
