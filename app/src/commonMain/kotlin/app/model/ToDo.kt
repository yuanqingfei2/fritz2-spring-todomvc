package app.model

import dev.fritz2.validation.ValidationMessage
import dev.fritz2.core.Id
import dev.fritz2.core.IdProvider
import dev.fritz2.core.Lenses
//import dev.fritz2.repository.Resource
import dev.fritz2.validation.Validation
import dev.fritz2.validation.validation
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

data class ToDoMessage(val id: String, val text: String): ValidationMessage {
    override val isError: Boolean
        get() = true
    override val path: String
        get() = TODO("Not yet implemented")
}


@Serializable
@Lenses
data class ToDo(
    val id: String = Id.next(),
    val text: String,
    val completed: Boolean = false
){
    companion object{
        private const val maxTextLength = 50
        val validation: Validation<ToDo, Unit, ToDoMessage> = validation<ToDo, ToDoMessage>{ inspector ->
            val textInspector = inspector.sub(ToDo.text())
            if (textInspector.data.trim().length < 3) {
                add(ToDoMessage(textInspector.path, "Text length must be at least 3 characters"))
            }
            if (textInspector.data.trim().length > maxTextLength) {
                add(ToDoMessage(textInspector.path, "Text length is to long (max $maxTextLength chars)."))
            }
        }

        fun deserialize(source: String): ToDo {
            val split = source.split(';')
            return ToDo(split[0], split[1], split[2].toBoolean())
        }

        fun serialize(item: ToDo): String {
            return "${item.id};${item.text};${item.completed}"
        }
    }
}

//object ToDoResource : Resource<ToDo, Long> {
//    override val idProvider: IdProvider<ToDo, Long> = ToDo::id
//    override fun deserialize(source: String): ToDo = Json.decodeFromString(ToDo.serializer(), source)
//    override fun deserializeList(source: String): List<ToDo> = Json.decodeFromString(ListSerializer(ToDo.serializer()), source)
//    override fun serialize(item: ToDo): String = Json.encodeToString(ToDo.serializer(), item)
//    override fun serializeList(items: List<ToDo>): String = Json.encodeToString(ListSerializer(ToDo.serializer()), items)
//}