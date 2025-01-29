import java.math.BigInteger;
import java.util.*;

public class CertificationAuthority {
    private static final CertificationAuthority instance = new CertificationAuthority();
    public static CertificationAuthority getInstance() {
        return instance;
    }

    private final RSA encryptionTechnique;

    private CertificationAuthority() {
        this.encryptionTechnique = new RSA();
    }
    private final Map<String, KeyData> publicKeyMap = new HashMap<>();



//    NOTE: this approach throws error because signing the modValue is cause the overflow of bigInteger, and we are lost with the public key when signed.

//   // Register public key with a digital signature
//    public void registerPublicKey(String userName, KeyData publicKey) {
//        BigInteger signedPublicKey = encryptionTechnique.signData(publicKey.getKey());  // Signing public key
//        BigInteger signedModValue = encryptionTechnique.signData(publicKey.getModValue()); // Signing n
//
//        publicKeyMap.put(userName, new KeyData(signedPublicKey, signedModValue));
//    }
//
//    // Retrieve and verify a public key
//    public KeyData getPublicKey(String userName) {
//        KeyData signedPublicKeyData = publicKeyMap.get(userName);
//
//        if (signedPublicKeyData == null) {
//            throw new IllegalArgumentException("404, User Not Found!!");
//        }
//
//        // Verify the signature using CA's public key
//        BigInteger verifiedPublicKey = encryptionTechnique.verifySignature(signedPublicKeyData.getKey());
//        BigInteger verifiedModValue = encryptionTechnique.verifySignature(signedPublicKeyData.getModValue());
//
//        return new KeyData(verifiedPublicKey, verifiedModValue);
//    }


//    NOTE: this approach works, but there isn't any signature of CA over public keys. So if wanted we can consider this or the one below this

//    // Register public key without digital signature
//    public void registerPublicKey(String userName, KeyData publicKey) {
//        publicKeyMap.put(userName, publicKey);
//    }
//
//    // Retrieve the public key
//    public KeyData getPublicKey(String userName) {
//        KeyData publicKeyData = publicKeyMap.get(userName);
//
//        if (publicKeyData == null) {
//            throw new IllegalArgumentException("404, User Not Found!!");
//        }
//
//        return publicKeyData;
//    }


    // Register public key with a digital signature
    public void registerPublicKey(String userName, KeyData publicKey) {
        BigInteger signedPublicKey = encryptionTechnique.signData(publicKey.getKey());  // Signing public key
        BigInteger signedModValue = publicKey.getModValue(); // Here I'm not signing n because n is a large prime number, modPow of this will resul

        publicKeyMap.put(userName, new KeyData(signedPublicKey, signedModValue));
    }

    // Retrieve and verify a public key
    public KeyData getPublicKey(String userName) {
        KeyData signedPublicKeyData = publicKeyMap.get(userName);

        if (signedPublicKeyData == null) {
            throw new IllegalArgumentException("404, User Not Found!!");
        }

        // Verify the signature using CA's public key
        BigInteger verifiedPublicKey = encryptionTechnique.verifySignature(signedPublicKeyData.getKey());
        BigInteger verifiedModValue = signedPublicKeyData.getModValue();

        return new KeyData(verifiedPublicKey, verifiedModValue);
    }

    public boolean findIfUserNameAlreadyExists(String userName) {
        return publicKeyMap.containsKey(userName);
    }

    public List<String> getAllUsernames() {
        return new ArrayList<>(publicKeyMap.keySet());
    }
}