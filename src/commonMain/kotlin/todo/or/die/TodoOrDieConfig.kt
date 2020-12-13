package todo.or.die

import todo.or.die._Config.messageLevel
import todo.or.die._Config.printCantDie
import kotlin.native.concurrent.ThreadLocal

class TodoOrDieConfig(configure: _Config.() -> Unit) {

    init {
        _Config.apply(configure)
    }
}

/**
 * @property printCantDie set to `true` to print a message with [messageLevel] to `System.out`
 *  instead of throwing [OverdueError]s for overdue tasks.
 * @property messageLevel determines color of the output when `printCantDie == true`
 */
@Suppress("ClassName")
@ThreadLocal
object _Config {
    var printCantDie: Boolean = false
    var messageLevel: Level = Level.ERROR
}

enum class Level {
    STACKTRACE,
    ERROR,
    WARN,
    INFO,
    DEBUG
}
