package com.kingarmstring.dindinnexam.utils


/**
 * This class is meant to be a wrapper to hold the data that we need to handle only once like error
 * , when we have an error, surly we don't want our users to see a dialog telling them there is an
 * error more than once.
 */
data class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}