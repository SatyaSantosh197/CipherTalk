import java.math.BigInteger;
import java.util.Random;

class RSA {
    private BigInteger n; // n = p * q
    private BigInteger z; // z = (p-1) * (q-1)
    private BigInteger publicKey;
    private BigInteger privateKey;
    private final int keySize;

    public RSA(int keySize) {

        // Make sure that KeySize is between 1024 and 2048 bits
        if (keySize < 1024 || keySize > 2048) {
            throw new IllegalArgumentException("Key size must be between 1024 and 2048 bits.");
        }
        this.keySize = keySize;

        Random random = new Random();
        // Constructs a randomly generated positive BigInteger that is probably prime, with the specified keySize
        BigInteger p = new BigInteger(keySize / 2, 100, random);
        BigInteger q = new BigInteger(keySize / 2, 100, random);

        n = p.multiply(q);
        z = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        publicKey = new BigInteger("65537");
        privateKey = publicKey.modInverse(z);
    }

    public BigInteger encrypt(String message, BigInteger publicKey, BigInteger n) {
        BigInteger messageBigInt = new BigInteger(message.getBytes());
        return messageBigInt.modPow(publicKey, n);
    }

    public String decrypt(BigInteger encryptedMessage) {
        BigInteger decryptedBigInt = encryptedMessage.modPow(privateKey, n);
        byte[] decryptedBytes = decryptedBigInt.toByteArray();
        return (new String(decryptedBytes));
    }

    public BigInteger getPublicKey(){
        return publicKey;
    }

    public BigInteger getModValue() {
        return n;
    }


    // Methods used by the Certification Authority to Encrypt and Decrypt Public Keys
    private BigInteger encryptPublicKey(BigInteger value) {
        return value.modPow(privateKey, n);
    }
    public BigInteger decryptPublicKey(BigInteger value, BigIntegr publicKey, BigInteger n) {
        return value.modPow(publicKey, n);
    }
}
