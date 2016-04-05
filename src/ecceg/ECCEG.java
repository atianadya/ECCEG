/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecceg;

import java.math.BigInteger;

/**
 *
 * @author atia
 */
public class ECCEG {

    /**
     * @param args the command line arguments
     */
    
    private final EllipticCurve ec;
    public static BigInteger privateKey;
    public static Point publicKey;
    public static String plain;
    public static Point[][] encrypted;
    public static Point[] encoded;
    public static Point[] decrypted;
    
    public Point getPublicKey() {
        return publicKey;
    }
    
    public BigInteger getPrivateKey() {
        return privateKey;
    }
    
    public void setPublicKey(Point publicKey) {
        this.publicKey = publicKey;
    }
    
    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }
    
    public ECCEG(EllipticCurve ec) {
        this.ec = ec;
        privateKey = Gen.generateK(ec.getP());
        publicKey = ec.multiply(ec.getBasePoint(), privateKey);
        System.out.println("private key: " + privateKey);
        System.out.println("public key: " + publicKey);
    }
    
    public Point[] encrypt(Point pm, BigInteger k) {
        // k ∈ [1, p-1]
        // pc = [(kB, (Pm + kPb)]
        Point[] pc = new Point[2];
        System.out.println("k = " + k);
        System.out.println("pm = " + pm);
        pc[0] = ec.multiply(ec.getBasePoint(), k);
        System.out.println("kB = " + pc[0]);
        pc[1] = ec.add(pm, ec.multiply(publicKey, k));
        System.out.println("kPB = " + ec.multiply(publicKey, k));
        System.out.println("Pm + kPB = " + pc[1]);
        
        return pc;
    }
    
    public Point decrypt (Point[] pc) {
        // (Pm + kPB) - b.kB
        Point pm = ec.subtract(pc[1], ec.multiply(pc[0], privateKey));
        System.out.println("dec0: " + pc[0]);
        System.out.println("dec1: " + pc[1]);
        return pm;
    }
    
    public String getEncryptedString() {
        String enc = "";
        for (int y=0; y<encoded.length; y++) {
//            System.out.println(encrypted[y][0]+ "," + encrypted[y][1]);
            enc += encrypted[y][0] + "," + encrypted[y][1] + "\n";
        }
        return enc;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
       
        
        EllipticCurve ec = new EllipticCurve(new BigInteger("-1"), new BigInteger("188"), new BigInteger("98764321261"));
        // 98764321261
        // 2147483647
        Point b = ec.solveForY(new BigInteger("11"), new BigInteger("20"));
        ec.setBase(b);
        System.out.println("base " + b);
        
        ECCEG ecc = new ECCEG(ec);
        
        System.out.println("");
        String datain = "ARGH what the f";
        System.out.println("String: " + datain + " size: " + datain.length());
        System.out.println("ENcodind...");
        encoded = Koblitz.encode(datain, ec);
        System.out.println("Encoded");
        System.out.println("Size: " + encoded.length);
        for (Point encoded1 : encoded) {
            System.out.println(encoded1);
        }
        
        System.out.println("\n\nEncrytpgin...");
        encrypted = new Point[encoded.length][2];
        
        BigInteger K = Gen.generateK(ec.getP());
        
        for (int x=0; x<encoded.length; x++) {
            encrypted[x] = ecc.encrypt(encoded[x], K);
        }
        
        for (int y=0; y<encoded.length; y++) {
            System.out.println(encrypted[y][0]+ "," + encrypted[y][1]);
        }
        
        System.out.println("\nDecrypting...");
        
        decrypted = new Point[encoded.length];
        for (int z=0; z<encoded.length; z++) {
            System.out.println("enc0: " + encrypted[z][0]);
            System.out.println("enc1: " + encrypted[z][1]);
            decrypted[z] = ecc.decrypt(encrypted[z]);
            System.out.println(decrypted[z]);
        }
        
        System.out.println("\ndecoding");
        String newdec = Koblitz.decode(decrypted, new BigInteger("20"));
        
        System.out.println();
        System.out.println("decoded: " + newdec);
    }
    
}
