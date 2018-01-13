//** Author: justrowingby
import java.math.*;
//** This implementation of pi calculation is based on
//** the Gauss-Legendre algorithm for pi calculation
public class Pi {
    //Instance Variables
    private int digits; // current number of KNOWN digits past the decimal
    private BigDecimal a;
    private BigDecimal b;
    private BigDecimal t;
    private BigDecimal p;

    private BigDecimal a1;
    private BigDecimal b1;
    private BigDecimal t1;
    private BigDecimal p1;
    private BigDecimal messy;

    //Constructors
    public Pi(){
        digits = 0;
        a = new BigDecimal("1.0"); // = 1
        b = new BigDecimal("1.0").divide(sqrt(new BigDecimal("2"), digits + 20), digits + 20, BigDecimal.ROUND_HALF_UP); // = 1/√2
        t = new BigDecimal("0.25"); // = 1/4
        p = new BigDecimal("1.0"); // = 1
    }
    public Pi(Pi pi){
        this.digits = pi.digits;
        this.a = pi.a;
        this.b = pi.b;
        this.t = pi.t;
        this.p = pi.p;
    }
    public Pi(int digits){
        digits = 0;
        a = new BigDecimal("1.0"); // = 1
        b = new BigDecimal("1.0").divide(sqrt(new BigDecimal("2"), digits + 20), digits + 20, BigDecimal.ROUND_HALF_UP); // = 1/√2
        t = new BigDecimal("0.25"); // = 1/4
        p = new BigDecimal("1.0"); // = 1
        improveTo(digits);
    }

    //Export Methods
    public BigDecimal toBigDecimal(){
        //pi = new BigDecimal((Math.pow((a1 + b1), 2)) / (4 * t1));
        BigDecimal mess1 = a1;
        mess1 = mess1.add(b1);
        mess1 = mess1.multiply(mess1);
        BigDecimal mess2 = t1;
        mess2 = mess2.multiply(BigDecimal.valueOf(4));
        return mess1.divide(mess2, digits + 2, BigDecimal.ROUND_HALF_UP);
    }
    public String toString(){
        return toBigDecimal().toPlainString().substring(0, digits + 2);
    }
    public String toString(int d){
        return toBigDecimal().toPlainString().substring(0, d + 2);
    }

    //Core Methods
    public void improveTo(int digits){
        //based on the current known number of this.digits, loop improve() until the correct number is up to "digits"
    } //Guass-Legendre algorithm doubles known digits each loop, so use that info to determine number of loops needed
    public void printedImproveTo(int digits){
        //improveTo(), but on every iteration it prints out how many digits have been calculated
    }
    private void improve(){
        // a1 = (a + b) / 2;
        a1 = a;
        a1 = a1.add(b);
        a1 = a1.divide(BigDecimal.valueOf(2),digits + 5 + 10, BigDecimal.ROUND_HALF_UP);
        // b1 = Math.sqrt(a * b);
        b1 = b;
        b1 = b1.multiply(a);
        b1 = sqrt(b1, digits + 5 + 10);
        //t1 = t - (p * Math.pow((a - a1), 2));
        t1 = t;
        messy = a;
        messy = messy.subtract(a1);
        messy = messy.multiply(messy);
        messy = messy.multiply(p);
        t1 = t1.subtract(messy);
        //p1 = 2 * p;
        p1 = p;
        p1 = p1.multiply(BigDecimal.valueOf(2));

        a = a1;
        b = b1;
        t = t1;
        p = p1;
    }

    private static BigDecimal sqrt(BigDecimal input, final int SCALE) {
        BigDecimal x0 = new BigDecimal("0"), x1 = new BigDecimal(Math.sqrt(input.doubleValue())); //take an approximation of the square root
        while (!x0.equals(x1)) {
            x0 = x1; //make x0 be our approimation
            x1 = input.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP); //how far off is it
            //take the average of the two approximations, getting a new approximation that is closer than either
            x1 = x1.add(x0);
            x1 = x1.divide(BigDecimal.valueOf(2), SCALE, BigDecimal.ROUND_HALF_UP);
        } //keep iterating until (approximation == (originalValue / approximation) ),
          //meaning that the approximation is accurate to our scale degree
        return x1; //return the approximation, which is accurate to the needed scale
    }
}
