package com.wdxlab.events

typealias Handler<TSender, TArg> = (sender: TSender, arg: TArg) -> Unit

class Event<TSender : Any, TArg> {
    private val subscribers = mutableSetOf<Handler<TSender, TArg>>()

    val subscriptionsCount
        get() = subscribers.size

    fun on(handler: Handler<TSender, TArg>) = subscribers.add(handler)

    fun off(handler: Handler<TSender, TArg>) = subscribers.remove(handler)

    fun emit(sender: TSender, arg: TArg) = subscribers.forEach { it(sender, arg) }

    fun clear() = subscribers.clear()
}