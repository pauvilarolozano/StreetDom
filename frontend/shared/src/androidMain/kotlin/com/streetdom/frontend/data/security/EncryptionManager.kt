package com.streetdom.frontend.data.security

interface EncryptionManager {
    fun encrypt(value: String): String
    fun decrypt(encryptedValue: String): String
}