import java.math.BigInteger;
import java.util.*;

public class CertificationAuthority {
    private static final CertificationAuthority instance = new CertificationAuthority();
    public static CertificationAuthority getInstance() {
        return instance;
    }

    private final RSA encryptionTechnique = new RSA();
    private final Map<String, KeyData> publicKeyMap = new HashMap<>();

    public void registerPublicKey(String userName, KeyData publicKey) {
        BigInteger encryptedPublicKey = encryptionTechnique.encryptPublicKey(publicKey.getKey());
        BigInteger encryptedModVal = encryptionTechnique.encryptPublicKey(publicKey.getModValue());
        publicKeyMap.put(userName, ( new KeyData(encryptedPublicKey, encryptedModVal) ));
    }

    public KeyData getPublicKey(String userName) {
        KeyData publicKeyValue = publicKeyMap.get(userName);

        if(publicKeyValue == null) {
            throw new IllegalArgumentException("404, User Not Found!!");
        }

        BigInteger decryptedPublicKey = encryptionTechnique.decryptPublicKey(publicKeyValue.getKey(), encryptionTechnique.getPublicKey().getKey(), encryptionTechnique.getPublicKey().getModValue());
        BigInteger decryptedModValue = encryptionTechnique.decryptPublicKey(publicKeyValue.getModValue(), encryptionTechnique.getPublicKey().getKey(), encryptionTechnique.getPublicKey().getModValue());

        return new KeyData(decryptedPublicKey, decryptedModValue);
    }

    public boolean findIfUserNameAlreadyExists(String userName) {
        if(publicKeyMap.containsKey(userName)) {
            return true;
        }
        return false;
    }

    public List<String> getAllUsernames() {
        return new ArrayList<>(publicKeyMap.keySet());
    }
}