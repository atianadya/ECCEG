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
    private final BigInteger privateKey;
    public static Point publicKey;
    
    public ECCEG(EllipticCurve ec, BigInteger bi) {
        this.ec = ec;
        privateKey = bi;
        publicKey = ec.multiply(ec.getBasePoint(), privateKey);
    }
    
    public Point[] encrypt(Point pm, BigInteger k, Point recpPublicKey) {
        // k ∈ [1, p-1]
        // pc = [(kB, (Pm + kPb)]
        Point[] pc = new Point[2];
        pc[0] = ec.multiply(ec.getBasePoint(), k);
        pc[1] = ec.add(pm, ec.multiply(recpPublicKey, k));
        
        return pc;
    }
    
    public Point decrypt (Point[] pc) {
        Point pm = ec.subtract(pc[1], ec.multiply(pc[0], privateKey));
        return pm;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Point base = new Point(BigInteger.ZERO, BigInteger.ZERO);
        EllipticCurve ec = new EllipticCurve(new BigInteger("-1"), new BigInteger("188"), new BigInteger("751"), base);
//        Point b = ec.solveForY(new BigInteger("11"), new BigInteger("20"));
//        System.out.println(b.toString());
        
    }
    
}
