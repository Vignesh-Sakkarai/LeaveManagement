package com.vw.hexad.UserService.config
class UserContextInfo{

    companion object {
        private val CONTEXT = ThreadLocal<String>()

        fun setSalt(salt: String) {
            CONTEXT.set(salt)
        }

        fun getSalt(): String {
            return CONTEXT.get()
        }

        fun clear() {
            CONTEXT.remove()
        }
    }
}