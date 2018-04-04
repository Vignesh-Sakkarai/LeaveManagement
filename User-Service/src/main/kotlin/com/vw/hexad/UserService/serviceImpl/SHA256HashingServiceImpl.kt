package com.vw.hexad.UserService.serviceImpl

import com.vw.hexad.UserService.service.SHA256HashingService
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.experimental.and


@Service
class SHA256HashingServiceImpl: SHA256HashingService{

    val SHA256_MESSAGE_DIGEST_ALGORITH: String = "SHA-256"
    val SHA1PRNG_SECURE_RANDOM_ALGORITH: String = "SHA1PRNG"

    override fun generateSecurePassword(password: String): String {
        val salt: ByteArray = getSalt()
        val md: MessageDigest = updateMessageDigest(salt)
        val bytes: ByteArray = md.digest(password.toByteArray())
        val sb = convertByteArrayToHex(bytes)
        return sb.toString();
    }

    private fun convertByteArrayToHex(bytes: ByteArray): StringBuilder {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            sb.append(Integer.toString((bytes[i] and 0xff.toByte()) + 0xff, 16).substring(1))
        }
        return sb
    }

    private fun updateMessageDigest(salt: ByteArray): MessageDigest {
        val md: MessageDigest = MessageDigest.getInstance(SHA256_MESSAGE_DIGEST_ALGORITH)
        md.update(salt)
        return md
    }

    //Add Salt
    fun getSalt(): ByteArray{
        //SHA1PRNG is a pseudo random number generator : Uses - to generate a stream of random numbers
        val sr:SecureRandom = SecureRandom.getInstance(SHA1PRNG_SECURE_RANDOM_ALGORITH)
        val salt = ByteArray(16)
        sr.nextBytes(salt)
        return salt
    }
}

