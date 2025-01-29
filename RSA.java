import java.math.BigInteger;
import java.util.Random;

class RSA {
    private BigInteger n;  // n = p * q
    private BigInteger z;  // z = (p-1) * (q-1)
    private BigInteger publicKeyValue;  // e
    private BigInteger privateKeyValue; // d
    private KeyData publicKey;
    private KeyData privateKey;
    private final int keySize;

    public RSA() {
        keySize = 2048;
        Random random = new Random();

        BigInteger p = new BigInteger(keySize / 2, 100, random);
        BigInteger q = new BigInteger(keySize / 2, 100, random);

        n = p.multiply(q);
        z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        publicKeyValue = new BigInteger("65537");
        privateKeyValue = publicKeyValue.modInverse(z);

        publicKey = new KeyData(publicKeyValue, n);
        privateKey = new KeyData(privateKeyValue, n);
    }

    // Encrypt a message using the recipient’s public key
    public BigInteger encrypt(String message, BigInteger publicKey, BigInteger n) {
        byte[] messageBytes = message.getBytes();
        BigInteger messageBigInt = new BigInteger(1, messageBytes);
        return messageBigInt.modPow(publicKey, n);
    }

    // Decrypt a message using the private key
    public String decrypt(BigInteger encryptedMessage) {
        BigInteger decryptedBigInt = encryptedMessage.modPow(privateKeyValue, n);
        byte[] decryptedBytes = decryptedBigInt.toByteArray();

        if (decryptedBytes[0] == 0) {
            byte[] temp = new byte[decryptedBytes.length - 1];
            System.arraycopy(decryptedBytes, 1, temp, 0, temp.length);
            decryptedBytes = temp;
        }

        return new String(decryptedBytes);
    }

    // Sign data using the private key (Digital Signature)
    public BigInteger signData(BigInteger value) {
        return value.modPow(privateKeyValue, n);  // Encrypt with CA's private key
    }

    // Verify a signature using the public key
    public BigInteger verifySignature(BigInteger signedValue, BigInteger publicKey, BigInteger n) {
        return signedValue.modPow(publicKey, n);  // Decrypt using CA's public key (to verify)
    }

    public KeyData getPublicKey() {
        return publicKey;
    }

    public KeyData getPrivateKey() {
        return privateKey;
    }
}
