import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockchainTests {

    User hp = new User("HP", 1000);
    User dell = new User("Dell", 1500);
    User microsoft = new User("Microsoft", 850);
    User apple = new User("Apple", 2000);

    ArrayList<Transaction> transactions0 = new ArrayList<Transaction>(Arrays.asList(new Transaction(dell, microsoft, 100, dell.getPrivateKey()), new Transaction(microsoft, apple, 200, microsoft.getPrivateKey()), new Transaction(dell, hp, 200, dell.getPrivateKey()), new Transaction(apple, microsoft, 300, apple.getPrivateKey())));
    ArrayList<Transaction> transactions1 = new ArrayList<Transaction>(Arrays.asList(new Transaction(dell, apple, 300, dell.getPrivateKey()), new Transaction(microsoft, hp, 400, microsoft.getPrivateKey()), new Transaction(dell, microsoft, 200, dell.getPrivateKey()), new Transaction(apple, hp, 400, apple.getPrivateKey())));
    ArrayList<Transaction> transactions2 = new ArrayList<Transaction>(Arrays.asList(new Transaction(hp, apple, 5, hp.getPrivateKey()), new Transaction(dell, hp, 30, dell.getPrivateKey()), new Transaction(apple, dell, 20, apple.getPrivateKey()), new Transaction(hp, microsoft, 70, hp.getPrivateKey())));

    Block block0 = new Block(transactions0, null, 0);
    Block block1 = new Block(transactions1, block0.getHash(), 1);
    Block block2 = new Block(transactions2, block1.getHash(), 2);

    @Test
    public void userGeneratesKeyTest() {
        User John = new User("John", 100.0);
        String johnsPublic = John.getPublicKey().toString();
        String johnsPrivate = John.getPrivateKey().toString();
        assertTrue(johnsPrivate.length() > 0 && johnsPublic.length() > 0);
    }

    @Test
    public void transactionTest() throws Exception{
        Transaction t = new Transaction(dell, apple, 300, dell.getPrivateKey());
        t.sign();
        assertEquals(2085, apple.getBalance(), 0);
        assertEquals(390, dell.getBalance(), 0);
    }

    @Test
    public void blockAndMinerTest() throws Exception {
        assertTrue(block2.getPreviousHash().equals(block1.getHash()) && block1.getPreviousHash().equals(block0.getHash()));
    }

    @Test
    public void blockchainTest() throws Exception {
        ArrayList<Block> blocks = new ArrayList<Block>(Arrays.asList(block0, block2, block1));
        Blockchain chain = new Blockchain(blocks);
        ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(1, 2));
        assertEquals(expected.toString(), chain.getHashMismatches().toString());
    }

    @Test
    public void blockchainAndSignatureVerificationTest() throws Exception {
        //Attempting to illegally manipulate transactions 
        ArrayList<Block> blocks = new ArrayList<Block>(Arrays.asList(block0, block1, block2));
        transactions1.get(1).tamperSignature();
        Blockchain chain = new Blockchain(blocks);
        assertFalse(chain.getValidSigns());

    }

}
