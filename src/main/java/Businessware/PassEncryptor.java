package Businessware;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

final public class PassEncryptor {

    public String getHashedPass(String rawPass){
        byte[] hashedPass = {};
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hashedPass = digest.digest(rawPass.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            System.out.println("No such algorithm.");
        }
        String output = "";
        for (byte b : hashedPass){
            output += b + " ";
        }
        return output;
    }

}