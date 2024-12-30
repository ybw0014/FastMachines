package net.guizhanss.fastmachines.utils.reflections

@Suppress("UNCHECKED_CAST")
fun <T> Any.invoke(methodName: String, vararg args: Any): T? {
    val argClasses = args.map { it::class.java }.toTypedArray()
    var clazz: Class<*>? = this.javaClass
    while (clazz != null) {
        try {
            clazz.getDeclaredMethod(methodName, *argClasses).let {
                it.isAccessible = true
                return it.invoke(this, *args) as? T
            }
        } catch (e: NoSuchMethodException) {
            clazz = clazz.superclass
        }
    }
    return null
}
