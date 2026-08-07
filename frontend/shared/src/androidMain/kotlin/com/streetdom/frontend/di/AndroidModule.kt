package com.streetdom.frontend.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.streetdom.frontend.data.security.AndroidEncryptionManager
import com.streetdom.frontend.data.security.EncryptionManager
import com.streetdom.frontend.data.storage.AndroidSecureStorage
import com.streetdom.frontend.domain.storage.SecureStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                androidContext().preferencesDataStoreFile(
                    "secure_storage"
                )
            }
        )

    }

    single<SecureStorage>{
        AndroidSecureStorage(get(), get())
    }

    single<EncryptionManager> {
        AndroidEncryptionManager()
    }

}