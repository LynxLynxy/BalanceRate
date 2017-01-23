import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BalanceRate {

    public static void main(String[] args) {
        String choose = null;
        Scanner in = new Scanner(System.in);
        boolean work = true;
        while (work) {
            System.out.println("Please choose operation number of operation:");
            System.out.println("1:Input new account:");
            System.out.println("2:View rates for existed account");
            System.out.println("3:Exit");;
            choose = in.nextLine();
            switch(choose){
                case "1":
                    BigDecimal newValue = InputValue();
                    int rate = CheckValue(newValue);
                    SaveValues(newValue, rate);
                    break;
                case "2":
                    ShowValues ();
                    break;
                case "3":
                    work=false;
                    break;
                default:
                    System.out.println("Choose 1,2 or 3 please.");
                    break;
            }
        }

    }

    public static BigDecimal InputValue() {
        Scanner in = new Scanner(System.in);
        System.out.println("Set value for new account");
        String inputLine = in.nextLine();
        while (!reg(inputLine)) {
            System.out.println("Invalid currency or value set, please set values with digit only and in dollar currency. Don't use more than 3 digits after point.");
            inputLine = in.nextLine();
        }
        inputLine = inputLine.substring(0, inputLine.length()-1);
        BigDecimal bdValue = new BigDecimal(inputLine);
        return bdValue;
    }

    public static int CheckValue (BigDecimal checkValue) {
        int rate = 0;
        BigDecimal firstCheck = new BigDecimal(100);
        BigDecimal secondCheck = new BigDecimal(1000);
        if (checkValue.compareTo(firstCheck) == -1 || checkValue.compareTo(firstCheck)==0)
            rate = 3;
        if (checkValue.compareTo(secondCheck) == -1 && checkValue.compareTo(firstCheck) == 1)
            rate=5;
        if (checkValue.compareTo(secondCheck) == 1 || checkValue.compareTo(secondCheck)==0)
            rate=7;
        return rate;
    }

    public static void SaveValues (BigDecimal Value, int Rate) {
        Scanner in = new Scanner(System.in);
        System.out.println("Input Account Name");
        String AccountName = in.nextLine();
        File file = new File("BankAccounts.txt");
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter add = new FileWriter(file.getAbsoluteFile(),true);
            try {
                add.write(AccountName + " Value: " + Value + "$ Rate:" + Rate + "%\n");
            } finally {
                add.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ShowValues () {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("BankAccounts.txt")))){
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
        }
    }

    public static boolean reg(String S) {

        Pattern p = Pattern.compile("[0-9]+[.]?+[0-9]{0,3}+[$]");
        Matcher m = p.matcher(S);

        return m.matches();
    }
}