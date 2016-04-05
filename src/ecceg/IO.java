/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecceg;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author atia
 */
public class IO {
    
    public static String plain;
    public static String cipher;
    
    public static void getInputString(String path, String mode) throws FileNotFoundException {
        String input = new Scanner(new File(path)).useDelimiter("\\Z").next();
        switch (mode) {
            case "pub":
                String[] inp = input.split("\n");
                Point pub = new Point(new BigInteger(inp[0]), new BigInteger(inp[1]));
                ECCEG.publicKey = pub;
                break;
            case "pri":
                ECCEG.privateKey = new BigInteger(input);
                break;
            case "plain":
                ECCEG.plain = input;
                plain = input;
                break;
            case "cipher":
                cipher = input;
                Point[][] encrypted;
                
                break;
        }
        
    }
}
