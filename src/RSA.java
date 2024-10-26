import java.math.BigInteger;
import java.util.Random;

class RSA {
    private BigInteger n;
    private BigInteger z;
    private BigInteger e;
    private BigInteger d;
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

        e = new BigInteger("65537");
        d = e.modInverse(z);
    }
    public BigInteger encrypt(String message, BigInteger e, BigInteger n) {
        BigInteger messageBigInt = new BigInteger(message.getBytes());
        return messageBigInt.modPow(e, n);
    }

    public String decrypt(BigInteger encryptedMessage) {
        BigInteger decryptedBigInt = encryptedMessage.modPow(d, n);
        byte[] decryptedBytes = decryptedBigInt.toByteArray();
        return (new String(decryptedBytes));
    }

    public BigInteger getPublicKey(){
        return e;
    }
    public BigInteger getModValue() {
        return n;
    }
}
