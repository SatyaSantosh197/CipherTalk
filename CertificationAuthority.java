public class CertificationAuthority {
    private static final CertificationAuthority instance = new CertificationAuthority();
    public static CertificationAuthority getInstance() {
        return instance;
    }

    private final int keySize = 2048;
    private final RSA encryptionTechinque = new RSA(keySize);;
    private final Map<String, BigInteger> publicKeyMap = new Hashmap();;

    public void registerPublickey(String userName, BigInteger publicKey, BigIntegr n) {
        BigInteger encryptedPublicKey = encryptionTechinque.encryptPublicKey(publicKey);
        BigInteger encryptedModval = encryptionTechinque.encryptPublicKey(n);
        publicKeyMap.put(userName, ( new KeyData(encryptedPublicKey, encryptedModval) ));
    }

    public KeyData getPublicKey(String userName) {
        KeyData publicKeyValue = publicKeyMap.get(userName);

        if(!publicKeyValue) {
            throw new IllegalArgumentException("404, User Not Found!!");
        }
        BigInteger decryptedPublicKey = encryptionTechinque.decryptPublicKey(publicKeyValue.getKey(), encryptionTechinque.getPublicKey(), encryptionTechinque.getModValue());
        BigInteger decryptedModVal = encryptionTechinque.decryptPublicKey(publicKeyValue.getModValue(), encryptionTechinque.getPublicKey(), encryptionTechinque.getModValue());

        return new KeyData(decryptedPublicKey, decryptedModVal);
    }

}