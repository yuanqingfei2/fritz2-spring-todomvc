package app.model

import dev.fritz2.validation.ValidationMessage
import dev.fritz2.core.Id
import dev.fritz2.core.IdProvider
import dev.fritz2.core.Lenses
import dev.fritz2.repository.Resource
import dev.fritz2.validation.Validation
import dev.fritz2.validation.validation
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

enum class Severity {
    Info,
    Warning,
    Error
}

data class Message(override val path: String, val severity: Severity, val text: String): ValidationMessage {
    override val isError: Boolean = severity > Severity.Warning
}

@Serializable
@Lenses
data class ToDo(
    val id: Long = Id.next().toLong(),
    val text: String,
    val completed: Boolean = false
){
    companion object
}




//data class ToDoMessage(val id: String, val text: String) : ValidationMessage {
//    override val isError: Boolean
//        get() = true
//    override val path: String
//        get() = TODO("Not yet implemented")
//}

//class ToDoValidator : Validator<ToDo, ToDoMessage, Unit>() {
//
//    private val maxTextLength = 50
//
//    override fun validate(data: ToDo, metadata: Unit): List<ToDoMessage> {
//        val msgs = mutableListOf<ToDoMessage>()
//        val inspector = inspect(data, "todos")
//
//        val textInspector = inspector.sub(L.ToDo.text)
//
//        if (textInspector.data.trim().length < 3) msgs.add(
//            ToDoMessage(
//                textInspector.id,
//                "Text length must be at least 3 characters."
//            )
//        )
//        if (textInspector.data.length > maxTextLength) msgs.add(
//            ToDoMessage(
//                textInspector.id,
//                "Text length is to long (max $maxTextLength chars)."
//            )
//        )
//
//        return msgs
//    }
//}

object ToDoResource : Resource<ToDo, Long> {
    override val idProvider: IdProvider<ToDo, Long> = ToDo::id
    override fun deserialize(source: String): ToDo = Json.decodeFromString(ToDo.serializer(), source)
    override fun deserializeList(source: String): List<ToDo> = Json.decodeFromString(ListSerializer(ToDo.serializer()), source)
    override fun serialize(item: ToDo): String = Json.encodeToString(ToDo.serializer(), item)
    override fun serializeList(items: List<ToDo>): String = Json.encodeToString(ListSerializer(ToDo.serializer()), items)
}