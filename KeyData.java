import java.math.BigInteger;
public class KeyData {
    private final BigInteger key;
    private final BigInteger modValue;

    public KeyData(BigInteger key, BigInteger modValue) {
        this.key = key;
        this.modValue = modValue;
    }

    public BigInteger getKey() {
        return key;
    }

    public BigInteger getModValue() {
        return modValue;
    }
}
