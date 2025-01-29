import java.math.BigInteger;
import java.util.*;

public class CertificationAuthority {
    private static final CertificationAuthority instance = new CertificationAuthority();
    public static CertificationAuthority getInstance() {
        return instance;
    }

    private final RSA encryptionTechnique = new RSA();  // CA's RSA key pair
    private final Map<String, KeyData> publicKeyMap = new HashMap<>();





//    Work further to resolve the Error while encrypting the public key with CA's private key and decrypting it with the CA's public key!




//    // Register public key with a digital signature
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
//        BigInteger verifiedPublicKey = encryptionTechnique.verifySignature(
//                signedPublicKeyData.getKey(),
//                encryptionTechnique.getPublicKey().getKey(),
//                encryptionTechnique.getPublicKey().getModValue()
//        );
//
//        BigInteger verifiedModValue = encryptionTechnique.verifySignature(
//                signedPublicKeyData.getModValue(),
//                encryptionTechnique.getPublicKey().getKey(),
//                encryptionTechnique.getPublicKey().getModValue()
//        );
//
//        return new KeyData(verifiedPublicKey, verifiedModValue);
//    }

    //
    public void registerPublicKey(String userName, KeyData publicKey) {
        publicKeyMap.put(userName, publicKey);
    }

    // Retrieve and verify a public key
    public KeyData getPublicKey(String userName) {
        KeyData publicKeyData = publicKeyMap.get(userName);

        if (publicKeyData == null) {
            throw new IllegalArgumentException("404, User Not Found!!");
        }

        return publicKeyData;
    }

    public boolean findIfUserNameAlreadyExists(String userName) {
        return publicKeyMap.containsKey(userName);
    }

    public List<String> getAllUsernames() {
        return new ArrayList<>(publicKeyMap.keySet());
    }
}