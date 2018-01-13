import java.util.Scanner;
import java.util.regex.*;

public class FindPi
{
    static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args)
    {
        int digits = howManyDigits();
        if(digits > 999){
            boolean execute = contYorN(
                    "This script can take a long time, are you sure you want to continue? (y/n)",
                    "Awesome! Let's execute...",
                    "Exiting program...");
            if(!execute)
            {
                System.exit(0);
            }
        }

        Pi myPi = new Pi();
        myPi.printedImproveTo(digits);
        System.out.println("\nYour approximation of PI:");
        System.out.println(myPi.toString().substring(0, digits + 2));
    }

    //I/O Methods
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

    private static int YorN(String A)
    {
        String input = new String(A);

        Pattern yp1 = Pattern.compile("(?i)yes");
        Pattern yp2 = Pattern.compile("(?i)y");
        Matcher ym1 = yp1.matcher(input);
        Matcher ym2 = yp2.matcher(input);
        boolean yb = ym1.matches() || ym2.matches();

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
}
