import java.security.MessageDigest;  

public class Miner {

    /**
     * @param data Will be encoded 
     * @return encoded data which will be treated as the blocks "hash"
     */
    public static String generateHash(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes("UTF-8"));  
        StringBuffer hexString = new StringBuffer();  

        for (int i = 0; i < hash.length; i++) {   
            String hexadecimal = Integer.toHexString(0xff & hash[i]);  
            if (hexadecimal.length() == 1) hexString.append('0');  
            hexString.append(hexadecimal);  
        }  
        return hexString.toString();  
    }

}
