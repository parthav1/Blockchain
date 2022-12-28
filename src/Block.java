import java.util.Date;
import java.security.Signature;
import java.util.ArrayList;

public class Block {
    
    private ArrayList<Transaction> transactions;
    private long timeStamp;
    private String hash;
    private String previousHash;
    private int index;

    // Sets the previous block hash and then the miner calculates the hash for the current block
    public Block(ArrayList<Transaction> transactions, String previousHash, int index) {
        this.transactions = transactions;
        this.index = index;
        timeStamp = new Date().getTime();

        if(index == 0) {
            this.previousHash = "GENESIS BLOCK"; 
        } else {
            this.previousHash = previousHash;
        }

        try {
            hash = Miner.generateHash(Integer.toString(index) + previousHash + Long.toString(timeStamp) + transactions.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
 
    /**
     * @return ArrayList of Integers so mismatched signatures can be found and delt with
     */
    public ArrayList<Integer> verifySignatures() throws Exception {
        ArrayList<Integer> mismatchIndicies = new ArrayList<Integer>();
        for(int i = 0; i<transactions.size(); i++) {
            Signature signer = Signature.getInstance("SHA256withRSA");
            signer.initVerify(transactions.get(i).getSender().getPublicKey());
            signer.update(transactions.get(i).toString().getBytes());

            boolean verified = signer.verify(transactions.get(i).getSignature());

            if(!verified) {
                mismatchIndicies.add(i);
            }
        }
        return mismatchIndicies;   
    }

    public String getHash() {
        return hash;
    }

    public int getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    @Override
    public String toString() {
        return "PREVIOUS HASH: " + previousHash + "\nINDEX: " + index + "\nTRANSACTIONS: " + transactions.toString() 
                                    + "\nTIMESTAMP: " + Long.toString(timeStamp) + "\nPROOF OF WORK (HASH): " + hash;
    }

}
