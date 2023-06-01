

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File

private const val FILE_PATH = "C:\\Users\\KV-User\\IdeaProjects\\TodoLIghtV01String\\src\\jvmMain\\kotlin\\data.txt"

internal class RootStore {

    var state: RootState by mutableStateOf(initialState())
        private set

    fun onItemClicked(id: Long) {
        setState { copy(editingItemId = id) }
    }

    fun onItemDoneChanged(id: Long, isDone: Boolean) {
        setState {
            updateItem(id = id) { it.copy(isDone = isDone.toString()) }
        }
    }

    fun onItemDeleteClicked(id: Long) {
        setState {
            val updatedItems = items.filterNot { it.id == id }
            updateFile(updatedItems)
            copy(items = updatedItems)
        }
    }

    private fun updateFile(items: List<TodoItem>) {
        val lines = items.map { "${it.id},${it.description},${it.isDone},${it.numberOfSeats},${it.price}" }
        File(FILE_PATH).writeText(lines.joinToString("\n"))
    }

    fun onAddItemClicked() {
        setState {
            val newItem =
                TodoItem(
                    id = items.maxOfOrNull(TodoItem::id)?.plus(1L) ?: 1L,
                    description = inputText,
                    isDone = inputBool,
                    numberOfSeats = inputNumber,
                    price = inputPrice
                )

            val updatedItems = items + newItem

            // Збереження елементів у файл
            updateFile(updatedItems)

            copy(items = updatedItems, inputText = "")

        }
    }

    fun onInputDescriptionChanged(description: String) {
        setState { copy(inputText = description) }
    }

    fun onInputNumberChanged(numberOfSeats: String) {
        setState { copy(inputNumber = numberOfSeats) }
    }

    fun onInputIsDoneChanged(isDone: String) {
        setState { copy(inputBool = isDone) }
    }

    fun onInputPriceChanged(price: String) {
        setState { copy(inputPrice = price) }
    }

    fun onEditorCloseClicked() {
        setState { copy(editingItemId = null) }
    }

    fun onEditorDescriptionChanged(description: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(description = description) }
        }
    }

    fun onEditorIsDoneChanged(isDone: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(isDone = isDone) }
        }
    }

    fun onEditorNumberChanged(numberOfSeats: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(numberOfSeats = numberOfSeats) }
        }
    }

    fun onEditorPriceChanged(price: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(price = price) }
        }
    }

    fun onEditorDoneChanged(isDone: Boolean) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(isDone = isDone.toString()) }
        }
    }

    fun onFilterSelected(filterValue: String?) {
        setState {
            copy(
                items = if (filterValue == null) {
                    initialState().items // Фільтр "All" - повертаємо всі елементи
                } else {
                    initialState().items.filter { it.isDone == filterValue } // Фільтр за значенням isDone
                }
            )
        }
    }




    private fun RootState.updateItem(id: Long, transformer: (TodoItem) -> TodoItem): RootState {
        val updatedItems = items.updateItem(id = id, transformer = transformer)
        updateFile(updatedItems)
        return copy(items = updatedItems)
    }

    private fun List<TodoItem>.updateItem(id: Long, transformer: (TodoItem) -> TodoItem): List<TodoItem> {
        return map { item -> if (item.id == id) transformer(item) else item }
    }

    // Функція для зчитування елементів з файлу
    private fun readItemsFromFile(): List<TodoItem> {
        val file = File(FILE_PATH)
        if (!file.exists()) return emptyList()

        val lines = file.readLines()
        return lines.mapNotNull { line ->
            val parts = line.split(",")
            if (parts.size == 5) {
                val id = parts[0].toLongOrNull()
                val description = parts[1]
                val isDone = parts[2]
                val numberOfSeats = parts[3]
                val price = parts[4]

                if (id != null) {
                    TodoItem(id, description, isDone, numberOfSeats, price)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }

    // Початкове завантаження елементів з файлу
    private fun loadItemsFromFile() {
        val items = readItemsFromFile()
        setState { copy(items = items) }
    }

    private fun initialState(): RootState {
        val items = readItemsFromFile()
        items.forEach { item ->
            println("ID: ${item.id}")
            println("Description: ${item.description}")
            println("Number of Seats: ${item.numberOfSeats}")
            println("Price: ${item.price}")
            println()
        }

        return RootState(
            items = items
        )
    }

    private inline fun setState(update: RootState.() -> RootState) {
        state = state.update()
    }


    data class RootState(
        val items: List<TodoItem> = emptyList(),
        val inputText: String = "",
        val inputBool: String = "",
        val inputNumber: String = "",
        val inputPrice: String = "",
        val editingItemId: Long? = null,
    )
}
