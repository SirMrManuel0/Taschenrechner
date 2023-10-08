/*

import java.math.BigDecimal;
import java.math.RoundingMode;

 */

public class Taschenrechner {
    private int[] split(double num) {
        String str_floaz = Double.toString(num);
        int int_floaz1 = Integer.parseInt(str_floaz.substring(0, str_floaz.indexOf(".")));
        String str_floaz2 = str_floaz.substring(str_floaz.indexOf(".") + 1);
        int int_floaz2 = 0;
        if (str_floaz2.length() - 1 >= 12){
            int_floaz2 = Integer.parseInt(str_floaz.substring(str_floaz.indexOf(".") + 1, 11));
        } else {
            int_floaz2 = Integer.parseInt(str_floaz.substring(str_floaz.indexOf(".") + 1));
        }

        int original_len = str_floaz.substring(str_floaz.indexOf(".") + 1).length();
        return new int[]{int_floaz1, int_floaz2, original_len};
    }
    private int getTotalNumLen(double... numbers) {
        int totalLen = 0;
        for (double num : numbers) {
            totalLen += split(num)[2];
        }
        return totalLen;
    }
    private double combine(int num_vor, int num_nach, int num_len) {
        return num_vor + (num_nach / Math.pow(10, num_len));
    }

    private int getMaxAfterDigit (double... numbers){
        int maxAfterDigit = 0;

        for (double num : numbers){
            int[] splitTemp = split(num);
            if (splitTemp[2] > maxAfterDigit){
                maxAfterDigit = splitTemp[2];
            }
        }
        return maxAfterDigit;
    }

    private  String[] num_before(String term, String chara){
        String str_num1_temp = "";
        int index = 0;
        for (int i = term.indexOf(chara) - 1; i > -1; i --){
            try{
                char termChar = term.charAt(i);
                String termStr = String.valueOf(termChar);
                double temp = Double.parseDouble(termStr);
                str_num1_temp += termStr;
            } catch (NumberFormatException e){
                char termChar = term.charAt(i);
                String termStr = String.valueOf(termChar);
                if (!(termChar == '.') && !(termChar == '-')){
                    index = i;
                    break;
                }
                str_num1_temp += termStr;
                if (termChar == '-'){
                    index = i;
                    break;
                }
            }
        }
        String str_num1 = "";
        for (int i = str_num1_temp.length() - 1; i > -1; i --){
            str_num1 +=  str_num1_temp.charAt(i);
        }
        String[] returner = new String[2];
        returner[0] = str_num1;
        returner[1] = String.valueOf(index);

        return returner;
    }
    private  String[] num_after(String term, String chara){
        String str_num2 = "";
        int index = term.length() - 1;
        for (int i = term.indexOf(chara) + 1; i < term.length(); i ++){
            try{
                char termChar = term.charAt(i);
                String termStr = String.valueOf(termChar);
                double temp = Double.parseDouble(termStr);
                str_num2 += termStr;
            } catch (NumberFormatException e){
                char termChar = term.charAt(i);
                String termStr = String.valueOf(termChar);
                if (!(termChar == '.') && !(termChar == '-' && str_num2.isEmpty())){
                    index = i;
                    break;
                }
                str_num2 += termStr;
            }
        }
        String[] returner = new String[2];
        returner[0] = str_num2;
        returner[1] = String.valueOf(index);
        return returner;
    }

    private String[] num_after_root(String term){
        String chara = "^";
        String str_num2 = "";
        String root = "false";
        int index = term.length() - 1;
        for (int i = term.indexOf(chara) + 1; i < term.length(); i ++){
            try{
                char termChar = term.charAt(i);
                String termStr = String.valueOf(termChar);
                double temp = Double.parseDouble(termStr);
                str_num2 += termStr;
            } catch (NumberFormatException e){
                char termChar = term.charAt(i);
                String termStr = String.valueOf(termChar);

                if (!(termChar == '.') && !(termChar == '-' && str_num2.isEmpty())){
                    if (!(termChar == '\\')){
                        index = i;
                        break;
                    }
                    root = "true";
                }
                str_num2 += termStr;
            }
        }
        String[] returner = new String[3];
        returner[0] = str_num2;
        returner[1] = String.valueOf(index);
        returner[2] = root;
        return returner;
    }

    private double addieren(double... numbers) {
        int sum_vor = 0;
        int sum_nach = 0;

        int maxAfterDigit = getMaxAfterDigit(numbers);

        for (double num : numbers){
            int[] splitNum1 = split(num);
            int num_vor = splitNum1[0];
            int num_nach = splitNum1[1];
            int num_len = splitNum1[2];

            num_nach *= (int) Math.pow(10, maxAfterDigit-num_len);

            sum_vor += num_vor;
            sum_nach += num_nach;
        }


        return combine(sum_vor, sum_nach, maxAfterDigit);
    }
    private double subtrahieren(double... numbers) {

        int sum_vor = 0;
        int sum_nach = 0;

        boolean first = true;

        int maxAfterDigit = getMaxAfterDigit (numbers);

        for (double num : numbers){
            int[] splitNum1 = split(num);
            int num_vor = splitNum1[0];
            int num_nach = splitNum1[1];
            int num_len = splitNum1[2];

            if (first){
                first = false;
                sum_vor = num_vor;
                sum_nach = num_nach * (int) Math.pow(10, maxAfterDigit-num_len);
                continue;
            }
            num_nach *= (int) Math.pow(10, maxAfterDigit-num_len);

            sum_vor -= num_vor;
            sum_nach -= num_nach;
        }


        return combine(sum_vor, sum_nach, maxAfterDigit);
    }

    private double multiplizieren(double... numbers) {
        if (numbers.length == 0) {return 0.0;}
        if (numbers.length == 1) {return numbers[0];}

        double product = 1.0;

        for (double num : numbers) {
            if (num == 0) {
                return 0.0;
            }

            int[] splitNum = split(num);
            int num_vor = splitNum[0];
            int num_nach = splitNum[1];
            int num_len = splitNum[2];

            int x = num_vor * (int) Math.pow(10, num_len) + num_nach;
            product *= x;
        }

        int totalNumLen = getTotalNumLen(numbers);

        product /= Math.pow(10, totalNumLen);

        return product;
    }

    private double dividieren(double... numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Es müssen mindestens zwei Zahlen zum Teilen angegeben werden.");
        }

        double quotient = 0.0;
        boolean first = true;

        for (double num : numbers) {

            if (num == 0.0) {
                throw new ArithmeticException("Teilen durch Null ist nicht erlaubt.");
            }
            int[] splitNum = split(num);
            int num_len = splitNum[2];

            double divisor = num;



            if (first){
                quotient = divisor;
                first = false;
                continue;
            }
            int[] quotient_list = split(quotient);
            int quotient_len = quotient_list[2];

            int maxAfterDigit = Math.max(num_len, quotient_len);

            quotient *=  (int) Math.pow(10, maxAfterDigit);
            divisor *= (int) Math.pow(10, maxAfterDigit);

            quotient /= divisor;
        }

        return quotient;
    }

    private double potenzieren(double base, int exponent) {
        int[] base_split = split(base);
        int afterDigits = base_split[2] * exponent;
        double result =  Math.pow(base * Math.pow(10, base_split[2]), exponent);
        return result / Math.pow(10, afterDigits);
    }
    private double wurzel(double base, String exponent) {
        String[] arr_exp1 = num_before(exponent, "\\");
        String[] arr_exp2 = num_after(exponent, "\\");
        return Math.pow(base, Double.parseDouble(arr_exp1[0]) / Double.parseDouble(arr_exp2[0]));
    }

    private  double potenzieren_ungenau(double base, double exponent) {return Math.pow(base, exponent);}

    public String interpreter (String term){
        try {
            term = term.replace(" ", "");
            term = term.replace(",", ".");
            term = term.replace("\n", "");

            if (term.contains("+-")) {
                int index = term.indexOf("+-");
                term = term.substring(0, index) + "-" + term.substring(index + 2);
            }
            if (term.contains("--")) {
                int index = term.indexOf("--");
                term = term.substring(0, index) + "+" + term.substring(index + 2);
            }


            if (term.contains("E")) {
                int index = term.indexOf("E");
                String[] num_after = num_after(term, "E");
                int index_num_after = Integer.parseInt(num_after[1]) + 1;

                if (index_num_after < term.length()) {
                    term = term.substring(0, index) + term.substring(index_num_after);
                } else {
                    term = term.substring(0, index);
                }


                /*

                String[] arr_num1 = num_before(term, "E");
                String[] arr_num2 = num_after(term, "E");

                double num1_double = Double.parseDouble(arr_num1[0]);
                int num2 = Integer.parseInt(arr_num1[1]);

                BigDecimal num1 = BigDecimal.valueOf(num1_double);

                BigDecimal result;
                if (num2 < 0){
                    num2 = num2 * -1;
                    BigDecimal zehner = BigDecimal.valueOf(Math.pow(10, num2));
                    result = num1.divide(zehner, 10, RoundingMode.UP);
                } else {
                    BigDecimal zehner = BigDecimal.valueOf(Math.pow(10, num2));
                    result = num1.divide(zehner, 10, RoundingMode.UP);
                }


                String returner = String.valueOf(result);
                if (!(Integer.parseInt(arr_num2[1]) + 1 == term.length())){
                    returner = returner + term.substring(Integer.parseInt(arr_num2[1]));
                }
                if (!(Integer.parseInt(arr_num1[1]) == 0)){
                    returner = term.substring(0, Integer.parseInt(arr_num1[1])) + returner;
                }
                return interpreter(returner);

                 */

            }

            if (term.contains("(")) {
                if (term.contains(")")) {

                    boolean malVorKlammer = true;
                    char[] zeichenVorKlammer = {'+', '-', '*', '/', '^', '(', '\\'};

                    if (term.indexOf("(") == 0) {
                        malVorKlammer = false;
                    } else {
                        for (char zeichen : zeichenVorKlammer) {
                            int indexOfChar = term.indexOf('(') - 1;

                            if (term.charAt(indexOfChar) == zeichen) {
                                malVorKlammer = false;
                                break;
                            }
                        }
                    }


                    if (malVorKlammer) {
                        term = term.substring(0, term.indexOf("("))
                                + "*" + interpreter(term.substring(term.indexOf("(") + 1, term.lastIndexOf(")")))
                                + term.substring(term.lastIndexOf(")") + 1);
                    } else {
                        term = term.substring(0, term.indexOf("("))
                                + interpreter(term.substring(term.indexOf("(") + 1, term.lastIndexOf(")")))
                                + term.substring(term.lastIndexOf(")") + 1);
                    }

                } else {
                    throw new ArithmeticException("Die Klammer muss geschlossen werden!");
                }
            }
            if (term.contains(")") && !term.contains(")")) {
                throw new ArithmeticException("Die Klammer muss geöffnet werden!");
            }

            if (term.contains("^")) {
                String[] arr_num1 = num_before(term, "^");
                String[] arr_num2 = num_after_root(term);
                double num1 = Double.parseDouble(arr_num1[0]);
                double result = 0.0;
                if (Boolean.parseBoolean(arr_num2[2])) {
                    result = wurzel(num1, arr_num2[0]);
                } else {
                    int num2;
                    double num2_double;

                    try {
                        num2 = Integer.parseInt(arr_num2[0]);
                        result = potenzieren(num1, num2);
                    } catch (NumberFormatException e) {
                        num2_double = Double.parseDouble(arr_num2[0]);
                        result = potenzieren_ungenau(num1, num2_double);
                    }
                }

                String returner = String.valueOf(result);


                if (!(Integer.parseInt(arr_num2[1]) + 1 == term.length())) {
                    returner = returner + term.substring(Integer.parseInt(arr_num2[1]));
                }
                if (!(Integer.parseInt(arr_num1[1]) == 0)) {
                    returner = term.substring(0, Integer.parseInt(arr_num1[1]) + 1) + returner;
                }
                return interpreter(returner);
            }

            if (term.contains("*") || term.contains("/")) {
                String Rechenzeichen = "";
                if (term.contains("*") && term.contains("/")) {
                    Rechenzeichen = term.indexOf("*") < term.indexOf("/") ? "*" : "/";
                } else if (term.contains("*")) {
                    Rechenzeichen = "*";
                } else if (term.contains("/")) {
                    Rechenzeichen = "/";
                }
                String[] arr_num1 = num_before(term, Rechenzeichen);
                String[] arr_num2 = num_after(term, Rechenzeichen);
                double num1 = Double.parseDouble(arr_num1[0]);
                double num2 = Double.parseDouble(arr_num2[0]);
                double result = 0;
                switch (Rechenzeichen) {
                    case "*" -> result = multiplizieren(num1, num2);
                    case "/" -> result = dividieren(num1, num2);
                }
                String returner = String.valueOf(result);
                if (!(Integer.parseInt(arr_num2[1]) + 1 == term.length())) {
                    returner = returner + term.substring(Integer.parseInt(arr_num2[1]));
                }
                if (!(Integer.parseInt(arr_num1[1]) == 0)) {
                    returner = term.substring(0, Integer.parseInt(arr_num1[1]) + 1) + returner;
                }
                return interpreter(returner);
            }

            if (term.contains("+") || (term.contains("-") && term.indexOf("-") > 0)) {
                String Rechenzeichen = "";
                if (term.contains("+") && term.contains("-")) {
                    Rechenzeichen = term.indexOf("+") < term.indexOf("-") ? "+" : "-";
                } else if (term.contains("+")) {
                    Rechenzeichen = "+";
                } else if (term.contains("-")) {
                    Rechenzeichen = "-";
                }
                String[] arr_num1 = num_before(term, Rechenzeichen);
                String[] arr_num2 = num_after(term, Rechenzeichen);
                double num1 = Double.parseDouble(arr_num1[0]);
                double num2 = Double.parseDouble(arr_num2[0]);
                double result = 0;
                switch (Rechenzeichen) {
                    case "+" -> result = addieren(num1, num2);
                    case "-" -> result = subtrahieren(num1, num2);
                }
                String returner = String.valueOf(result);
                if (!(Integer.parseInt(arr_num2[1]) + 1 == term.length())) {
                    returner = returner + term.substring(Integer.parseInt(arr_num2[1]));
                }
                if (!(Integer.parseInt(arr_num1[1]) == 0)) {
                    returner = term.substring(0, Integer.parseInt(arr_num1[1]) + 1) + returner;
                }
                return interpreter(returner);
            }


            return term;

        } catch (NumberFormatException e) {
            return "Error: Unerwartetes Zahlenformat.\n\n" + e.getMessage();
        } catch (ArithmeticException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: Unerwarteter Fehler.\n\n" + e.getMessage();
        }
    }

}