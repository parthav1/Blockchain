import java.util.ArrayList;

public class Blockchain {
    
    private ArrayList<Block> blocks;
    private ArrayList<ArrayList<Integer>> signatureMismatches;
    private ArrayList<Integer> hashMismatches;
    private boolean validSignatures;

    // Verifies ALL signatures and verifies ALL hashes are correct
    public Blockchain(ArrayList<Block> blocks) throws Exception {

        this.blocks = blocks;
        signatureMismatches = new ArrayList<ArrayList<Integer>>();
        hashMismatches = new ArrayList<Integer>();
        int count = 0;
        
        // verifying all signatures
        for(int i = 0; i<blocks.size(); i++) {
            signatureMismatches.add(blocks.get(i).verifySignatures());
            if(signatureMismatches.get(i).size() > 0) {
                count++;
            }
        }
        if(count == 0) {
            validSignatures = true;
        }
        
        // verifying hashes are correct
        for(int i = 1; i<blocks.size(); i++) {
            if(blocks.get(i).getPreviousHash().equals(blocks.get(i - 1).getHash())) {
                
            } else {
                hashMismatches.add(i);
            }
        }

        if(hashMismatches.size() > 0 || !validSignatures) {
            System.err.println("SIGNATURE OR HASH MISMATCH FOUND. CHECK MISMATCH CONTAINER ARRAYS FOR LOCATION");
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public boolean getValidSigns() {
        return validSignatures;
    }

    /**
     * @return 2D arraylist that has its rows as blocks and each column as an index of the transaction with an invalid sigature in that block
     */
    public ArrayList<ArrayList<Integer>> getSignatureMismatches() {
        return signatureMismatches;
    }

    /**
     * @return ArrayList with indicies of which blocks don't have their previous hash matched with the actual previous hash
     */
    public ArrayList<Integer> getHashMismatches() {
        return hashMismatches;
    }

    @Override
    public String toString() {
        return null;

    }
}