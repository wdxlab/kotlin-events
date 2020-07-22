package com.wdxlab.events

import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test


class Stub
class EventArg(val foo: String)

class MyLibraryTest {
    @Test
    fun testOnAndEmit() {
        val stub = Stub()
        val eventArg = EventArg("hello")
        val event = Event<Stub, EventArg>()
        val handler: Handler<Stub, EventArg> = spyk({ _, _ -> Unit })
        val handler2: Handler<Stub, EventArg> = spyk({ _, _ -> Unit })

        event.on { sender, arg -> /* do something with sender and arg */ }
        event.on(handler)
        event.on(handler)
        event.on(handler2)
        assertEquals(event.subscriptionsCount, 2)
        event.emit(stub, eventArg)

        verify(atLeast = 1) { handler(stub, eventArg) }
        verify(atLeast = 1) { handler2(stub, eventArg) }
    }

    @Test
    fun testOff() {
        val stub = Stub()
        val eventArg = EventArg("hello")
        val event = Event<Stub, EventArg>()
        val handler: Handler<Stub, EventArg> = spyk({ _, _ -> Unit })
        val handler2: Handler<Stub, EventArg> = spyk({ _, _ -> Unit })

        event.on(handler)
        event.on(handler2)
        assertEquals(event.subscriptionsCount, 2)
        event.emit(stub, eventArg)
        event.off(handler)
        event.emit(stub, eventArg)

        verify(atLeast = 1) { handler(stub, eventArg) }
        verify(atLeast = 2) { handler2(stub, eventArg) }
    }

    @Test
    fun testClear() {
        val stub = Stub()
        val eventArg = EventArg("hello")
        val event = Event<Stub, EventArg>()
        val handler: Handler<Stub, EventArg> = spyk({ _, _ -> Unit })
        val handler2: Handler<Stub, EventArg> = spyk({ _, _ -> Unit })

        event.on(handler)
        event.on(handler2)
        assertEquals(event.subscriptionsCount, 2)
        event.emit(stub, eventArg)
        event.clear()
        event.emit(stub, eventArg)

        verify(atLeast = 1) { handler(stub, eventArg) }
        verify(atLeast = 1) { handler2(stub, eventArg) }
    }
}