import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class User {
    
    private String name;
    private double balance;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public static int TOTAL_USERS;

    // Generates a public and private key for the user using RSA encryption algorithm
    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        TOTAL_USERS++;

        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            publicKey = pair.getPublic();
            privateKey = pair.getPrivate();
        } catch(NoSuchAlgorithmException e) {
            System.err.print("Error generating users keys");
        }

    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
