package todo.or.die

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@ExperimentalTime
class TodoOrDieTest {

    @BeforeTest
    fun resetDefaultConfig() {
        TodoOrDieConfig {
            printCantDie = false
            messageLevel = Level.ERROR
        }
    }

    @Test
    fun shouldDieIfTodoIsPastDue() {
        assertFailsWith(OverdueError::class) {
            TODO("1970-01-01") { "Find The Answer to the Ultimate Question of Life, The Universe, and Everything" }
        }
    }

    @Test
    fun shouldPassIfTodoIsNotDue() {
        TODO("2061-05-14") { "Massively decrease the net amount of entropy of the universe" }
    }

    @Test
    fun shouldPassIfTodoIsToday() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        TODO(today.toString()) { "Reverse the workings of the second law of thermodynamics" }
    }

    @Test
    fun shouldNotThrowExceptionWhenEmitWarnIsTrue() {
        TodoOrDieConfig {
            printCantDie = true
        }

        val yesterday = Clock.System
            .now()
            .minus(24.hours)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        TODO(yesterday.toString()) { "Forget to do something very important" }
    }

    @Test
    fun shouldFailIfThePredicateReturnsTrue() {
        assertFailsWith(OverdueError::class) {
            TODO({ "This will never be completed" }) {
                true
            }
        }
    }

    @Test
    fun shouldNotFailIfThePredicateReturnsFalse() {
        TODO({ "This can be postponed until after the Sun explodes" }) {
            false
        }
    }
}