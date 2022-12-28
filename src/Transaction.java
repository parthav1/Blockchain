import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.PrivateKey;

public class Transaction {
    
    private User sender;
    private User receiver;
    private double amount;
    private byte[] senderSignature;
    private PrivateKey senderKey;

    // Sets everything and signs the transaction to verify the sender actually sent what is claimed to have been sent
    public Transaction(User sender, User receiver, double amount, PrivateKey senderKey) {
        this.sender = sender;
        this.receiver = receiver;

        if(sender.getBalance() - amount < 0) {
            throw new IllegalArgumentException("NOT ENOUGH FUNDS. TRANSACTION NOT CREATED");
        } 

        this.amount = amount;
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        this.senderKey = senderKey;

        try {
            sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    } 

    // Creating digital signature using senders private key
    public void sign() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signer = Signature.getInstance("SHA256withRSA");

        signer.initSign(senderKey);
        byte[] bytes = this.toString().getBytes();
        signer.update(bytes);

        senderSignature = signer.sign();
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    public byte[] getSignature() {
        return senderSignature;
    }

    // For testing purposes only
    public void tamperSignature() {
        senderSignature[2] = 100;
    }

    // Format: APPLE SENDS DELL 50.0, so Apple's private key will be needed to verify the transaction
    @Override
    public String toString() {
        return sender.getName() + receiver.getName() + Double.toString(amount);
    }
}