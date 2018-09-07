import java.math.*;
import java.util.Scanner;
import java.util.regex.*;

public class FindPi
{
    static Scanner userInput = new Scanner(System.in);

    private static int YorN(String A)
    {
        String input = new String(A);

        Pattern yp1 = Pattern.compile("(?i)yes");
        Pattern yp2 = Pattern.compile("(?i)ye?i");
        Pattern yp3 = Pattern.compile("(?i)y");
        Matcher ym1 = yp1.matcher(input);
        Matcher ym2 = yp2.matcher(input);
        Matcher ym3 = yp3.matcher(input);
        boolean yb = ym1.matches() || ym2.matches() || ym3.matches();

        Pattern np1 = Pattern.compile("(?i)no");
        Pattern np2 = Pattern.compile("(?i)n");
        Matcher nm1 = np1.matcher(input);
        Matcher nm2 = np2.matcher(input);
        boolean nb = nm1.matches() || nm2.matches();

        if(yb) {
            return 1;
        }
        else if(nb) {
            return 2;
        }
        else if(A == " " || A == "\n") {
            return -1;
        }
        else {
            return 0;
        }
    }

    private static boolean contYorN(String prompt, String ifTrue, String ifFalse)
    {
        int contYorN = -1;


        while(1 > 0)
        {
            System.out.println(prompt);
            if(userInput.hasNextLine())
            {
                contYorN = YorN(userInput.nextLine());
                if(contYorN == 1)
                {
                    System.out.println(ifTrue);
                    return true;
                }
                if(contYorN == 2)
                {
                    System.out.println(ifFalse);
                    return false;
                }
                if(contYorN == 0)
                {
                    System.out.println("Please enter Y/N");
                    continue;
                }
            }
        }
    }


    private static BigDecimal sqrt(BigDecimal input, MathContext precision) {;
        BigDecimal x0 = new BigDecimal("0"); //initialized value means nothing, just need a value to initialize
        BigDecimal x1 = new BigDecimal(Math.sqrt(input.doubleValue())); //take an approximation of the square root
        while (!x0.equals(x1)) {
            x0 = x1; //make x0 be our approimation
            x1 = input.divide(x0, precision); //how far off is it
            //take the average of the two approximations, getting a new approximation that is closer than either
            x1 = x1.add(x0);
            x1 = x1.divide(BigDecimal.valueOf(2), precision);
        } //keep iterating until (approximation == (originalValue / approximation) ),
          //meaning that the approximation is accurate to our scale degree
        return x1; //return the approximation, which is accurate to the needed scale
    }


    private static int howManyDigits()
    {
        int digits = 0;
        boolean success;
        String d;
        while(1 > 0)
        {
            System.out.println("How many digits of pi would you like to calculate beyond the decimal?");
            d = userInput.nextLine();
            try
            {
                digits = Integer.parseInt(d);
                success = true;
            } catch (Exception e) {
                System.out.println("Please enter an integer");
                success = false;
                continue;
            }
            if(success)
            {
                break;
            }
        }
        return digits;
    }

    public static void main(String[] args)
    {
        int digits = howManyDigits();
        MathContext calulate = new MathContext(digits + 20);
        int iterations = (int) ( Math.log((double) digits) / Math.log(2.0) + 1);
        boolean execute = contYorN(
                "This script can take a long time, are you sure you want to continue? (y/n)",
                "",
                "Exiting program...");
        if(!execute)
        {
            System.exit(0);
        }

        ////////an implementation of the Gauss–Legendre algorithm


        BigDecimal a = new BigDecimal("1.0");
        //BigDecimal b = new BigDecimal(1 / Math.sqrt(2));
        BigDecimal mess0 = sqrt(new BigDecimal("2"), calulate);
        BigDecimal b = new BigDecimal("1.0").divide(mess0, calulate);
        BigDecimal t = new BigDecimal("0.25");
        BigDecimal p = new BigDecimal("1.0");

        BigDecimal a1;
        BigDecimal b1;
        BigDecimal t1;
        BigDecimal p1;

        BigDecimal pi;

        String myPi;

        BigDecimal messy;
        BigDecimal mess1;
        BigDecimal mess2;

        int i = 0;

        do
        {
            // a1 = (a + b) / 2;
            a1 = a;
            a1 = a1.add(b);
            a1 = a1.divide(BigDecimal.valueOf(2), calulate);
            // b1 = Math.sqrt(a * b);
            b1 = b;
            b1 = b1.multiply(a);
            b1 = sqrt(b1, calulate);
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

            //pi = new BigDecimal((Math.pow((a1 + b1), 2)) / (4 * t1));
            mess1 = a1;
            mess1 = mess1.add(b1);
            mess1 = mess1.multiply(mess1);
            mess2 = t1;
            mess2 = mess2.multiply(BigDecimal.valueOf(4));
            pi = mess1.divide(mess2, calulate);

            myPi = new String(pi.toPlainString());

            if(i % 90 == 0)
            {
                //System.out.println(myPi.substring(0, (i+1)*4));
                System.out.println("Executing...");
            }

            a = a1;
            b = b1;
            t = t1;
            p = p1;

            i++;
        } while(i < iterations);
        System.out.println("\nYour approximation of PI:");
        System.out.println(myPi.substring(0, digits + 2));
        System.out.println("That was pi with "
        + (((myPi.substring(0, digits + 2)).length()) - 2)
        + " digits past the decimal point!");
    }
}
