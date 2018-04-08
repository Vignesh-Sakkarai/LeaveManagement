package com.vw.hexad.UserService.serviceImpl

import com.vw.hexad.UserService.service.SHA256HashingService
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.SecureRandom
import javax.xml.bind.DatatypeConverter
import kotlin.experimental.and


@Service
class SHA256HashingServiceImpl: SHA256HashingService{

    val SHA256_MESSAGE_DIGEST_ALGORITHM: String = "SHA-256"
    val SHA1PRNG_SECURE_RANDOM_ALGORITHM: String = "SHA1PRNG"

    override fun generateSecurePassword(password: String, salt: ByteArray): String {
        val md: MessageDigest = updateMessageDigest(salt)
        val bytes: ByteArray = md.digest(password.toByteArray())
        return convertByteArrayToHex(bytes)
    }

    override fun convertByteArrayToHex(array: ByteArray): String {
        val sb = StringBuilder(array.size * 2)
        for (byte: Byte in array) {
            var hex: String = String.format("%x", byte)
            if(hex.length == 1){
                hex = "0" + hex
            }
            sb.append(hex)
        }
        return sb.toString()
    }

    override fun convertStringTOByteArray(saltHexString: String): ByteArray {
        var result: ByteArray = ByteArray(saltHexString.length/2)
        for (i in 0 until saltHexString.length step 2){
            var byte: Byte = Integer.valueOf(saltHexString.substring(i, i+2), 16).toByte()
            result[i/2] = byte
        }
        return result
    }

    private fun updateMessageDigest(salt: ByteArray): MessageDigest {
        val md: MessageDigest = MessageDigest.getInstance(SHA256_MESSAGE_DIGEST_ALGORITHM)
        md.update(salt)
        return md
    }

    override fun generateSalt(): ByteArray{
        //SHA1PRNG is a pseudo random number generator : Uses - to generate a stream of random numbers
        val sr:SecureRandom = SecureRandom.getInstance(SHA1PRNG_SECURE_RANDOM_ALGORITHM)
        val salt = ByteArray(16)
        sr.nextBytes(salt)
        return salt
    }
}