package com.streetdom.frontend.data.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class AndroidEncryptionManager : EncryptionManager {

    private companion object {
        const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        const val KEY_ALIAS = "streetdom_master_key"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val GCM_TAG_LENGTH = 128
        const val IV_LENGTH = 12
    }

    override fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)

        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())

        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
        val result = ByteArray(iv.size + encryptedBytes.size)

        System.arraycopy(
            iv,
            0,
            result,
            0,
            iv.size
        )

        System.arraycopy(
            encryptedBytes,
            0,
            result,
            iv.size,
            encryptedBytes.size
        )

        return Base64.encodeToString(result, Base64.NO_WRAP)
    }

    override fun decrypt(encryptedValue: String): String {
        val decoded = Base64.decode(encryptedValue, Base64.NO_WRAP)
        val iv = decoded.copyOfRange(0, IV_LENGTH)
        val encryptedBytes = decoded.copyOfRange(IV_LENGTH, decoded.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)

        cipher.init(
            Cipher.DECRYPT_MODE,
            getSecretKey(),
            GCMParameterSpec(
                GCM_TAG_LENGTH,
                iv
            )
        )

        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return decryptedBytes.toString(Charsets.UTF_8)
    }

    private fun getSecretKey(): SecretKey {

        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)
        keyStore.load(null)

        val existingKey = keyStore.getKey(KEY_ALIAS, null)

        if (existingKey is SecretKey)
            return existingKey

        return createSecretKey()
    }

    private fun createSecretKey(): SecretKey {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER)

        val spec =
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(
                    KeyProperties.BLOCK_MODE_GCM
                )
                .setEncryptionPaddings(
                    KeyProperties.ENCRYPTION_PADDING_NONE
                )
                .build()

        keyGenerator.init(spec)

        return keyGenerator.generateKey()
    }
}