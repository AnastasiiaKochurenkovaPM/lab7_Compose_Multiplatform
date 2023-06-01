//package example.todoapp.lite.common

internal data class TodoItem(
    val id: Long = 0L,
    val description: String = "",
    val isDone: String = "",
    val numberOfSeats: String,
    val price: String
)

//internal data class TodoItem(
//    val id: Long = 0L,
//    val description: String = "",
//    val numberOfSeats: Int = 0,
//    val price: Double = 0.0,
//    val checkInDate: Date? = null,
//    val durationOfStay: Int = 0
//)