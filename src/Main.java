import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    
    // Visualizing in the output the tests in BlockchainTests.java
    public static void main(String[] args) throws Exception{
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

        System.out.println(block0);
        System.out.println(block1);
        System.out.println(block2);

        ArrayList<Block> blocks = new ArrayList<Block>(Arrays.asList(block0, block2, block1));
        Blockchain chain = new Blockchain(blocks);
        System.out.println(chain.getHashMismatches().toString());

        //Attempting to illegally manipulate transactions 
        ArrayList<Block> blocks1 = new ArrayList<Block>(Arrays.asList(block0, block1, block2));
        transactions1.get(1).tamperSignature();
        transactions1.get(2).tamperSignature();
        transactions1.get(3).tamperSignature();
        transactions2.get(2).tamperSignature();
        chain = new Blockchain(blocks1);
        System.out.println(chain.getSignatureMismatches().toString());

    }
}
