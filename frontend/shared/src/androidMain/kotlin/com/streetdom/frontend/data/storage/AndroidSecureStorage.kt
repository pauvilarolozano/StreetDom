package com.streetdom.frontend.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.streetdom.frontend.data.security.EncryptionManager
import com.streetdom.frontend.domain.storage.SecureStorage
import kotlinx.coroutines.flow.first

class AndroidSecureStorage (
    private val dataStore: DataStore<Preferences>,
    private val encryptionManager: EncryptionManager
) : SecureStorage {

    override suspend fun putString(key: String, value: String) {
        val encryptedValue = encryptionManager.encrypt(value)

        dataStore.edit {
            preferences -> preferences[stringPreferencesKey(key)] = encryptedValue
        }
    }

    override suspend fun getString(key: String): String? {
        val preferences = dataStore.data.first()
        val encryptedValue = preferences[stringPreferencesKey(key)] ?: return null

        return encryptionManager.decrypt(encryptedValue)
    }

    override suspend fun remove(key: String) {
        dataStore.edit {
            it.remove(
                stringPreferencesKey(key)
            )
        }
    }
}