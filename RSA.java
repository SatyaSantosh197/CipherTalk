import java.math.BigInteger;
import java.util.Random;

class RSA {
    private BigInteger n; // n = p * q
    private BigInteger z; // z = (p-1) * (q-1)
    private BigInteger publicKeyValue;
    private BigInteger privateKeyValue;
    private KeyData publicKey;
    private KeyData privateKey;
    private final int keySize;

    public RSA() {
        keySize =  2048;
        Random random = new Random();

        // Constructs a randomly generated positive BigInteger that is probably prime, with the specified keySize
        BigInteger p = new BigInteger(keySize / 2, 100, random);
        BigInteger q = new BigInteger(keySize / 2, 100, random);

        n = p.multiply(q);
        z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        publicKeyValue = new BigInteger("65537");
        privateKeyValue = publicKeyValue.modInverse(z);
        publicKey = new KeyData(publicKeyValue, n);
        privateKey = new KeyData(privateKeyValue, n);
    }

    public BigInteger encrypt(String message, BigInteger publicKey, BigInteger n) {
        byte[] messageBytes = message.getBytes(); // Convert message to bytes
        BigInteger messageBigInt = new BigInteger(1, messageBytes); // Convert to BigInteger
        return messageBigInt.modPow(publicKey, n); // Encrypt using public key
    }

    public String decrypt(BigInteger encryptedMessage) {
        BigInteger decryptedBigInt = encryptedMessage.modPow(privateKeyValue, n);
        byte[] decryptedBytes = decryptedBigInt.toByteArray();

        // Handle leading zero in decrypted bytes
        if (decryptedBytes[0] == 0) {
            byte[] temp = new byte[decryptedBytes.length - 1];
            System.arraycopy(decryptedBytes, 1, temp, 0, temp.length);
            decryptedBytes = temp;
        }

        return (new String(decryptedBytes));
    }

    // Methods used by the   ** Certification Authority **   to Encrypt and Decrypt Public Keys
    public BigInteger encryptPublicKey(BigInteger value) {
        return value.modPow(privateKeyValue, n);
    }
    public BigInteger decryptPublicKey(BigInteger value, BigInteger publicKey, BigInteger n) {
        return value.modPow(publicKey, n);
    }

    // Methods used to get PublicKey
    public KeyData getPublicKey() {
        return publicKey;
    }
}