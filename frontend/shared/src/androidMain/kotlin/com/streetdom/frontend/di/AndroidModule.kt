package com.streetdom.frontend.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.streetdom.frontend.data.repository.AndroidLocationRepository
import com.streetdom.frontend.data.security.AndroidEncryptionManager
import com.streetdom.frontend.data.security.EncryptionManager
import com.streetdom.frontend.data.storage.AndroidSecureStorage
import com.streetdom.frontend.domain.repository.LocationRepository
import com.streetdom.frontend.domain.storage.SecureStorage
import com.streetdom.frontend.presentation.screens.play.map.MapConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


fun androidModule(mapTilerApiKey: String) = module {

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

    single {
        MapConfig(
            mapTilerApiKey = mapTilerApiKey
        )
    }

    single<LocationRepository> {
        AndroidLocationRepository(androidContext())
    }

}