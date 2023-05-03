package generator.exec

import generator.GeneratedObject
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

interface ObjectsSource {}

class GeneratedObjects(
    val source: ObjectsSource,
    objs: List<KClass<out GeneratedObject>>,
    filters: List<KClass<out GeneratedObject>>
) {
    private val defsClasses = mutableMapOf<String, KClass<*>>()
    init {
        objs.forEach { defsClasses[it.simpleName.toString()] = it }
        filters.forEach { defsClasses[it.simpleName.toString()] = it }
    }

    fun getDef(name: String): KClass<*> {
        return defsClasses[name]!!
    }

    fun getDefMethod(name: String, subObjName: String): KCallable<*> {
        val methodName = "get${subObjName.capitalize()}"
        return defsClasses[name]!!.members.single { it.name == methodName }
    }

    fun getRevDefMethod(name: String, revName: String): KCallable<*> {
        val methodName = "parent${revName.capitalize()}"
        return defsClasses[name]!!.members.single { it.name == methodName }
    }

    fun getAllObjectsOfType(t: String): List<GeneratedObject> {
        val methodName = "getAll$t"
        val method = ObjectsSource::class.members.single { it.name == methodName }

        return method.call(source) as List<GeneratedObject>
    }

    fun getAllObjectsByProperty(obj: String, property: String, value: ValueObject): List<GeneratedObject> {
        val methodName = "get${property.capitalize()}By${obj.capitalize()}"
        val method = ObjectsSource::class.members.single { it.name == methodName }

        return method.call(source, value) as List<GeneratedObject>

    }
}