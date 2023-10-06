public class Taschenrechner {
    public int[] split(double num) {
        String str_floaz = Double.toString(num);
        int int_floaz1 = Integer.parseInt(str_floaz.substring(0, str_floaz.indexOf(".")));
        int int_floaz2 = Integer.parseInt(str_floaz.substring(str_floaz.indexOf(".") + 1));
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
    public double combine(int num_vor, int num_nach, int num_len) {
        return num_vor + (num_nach / Math.pow(10, num_len));
    }

    public int getMaxAfterDigit (double... numbers){
        int maxAfterDigit = 0;

        for (double num : numbers){
            int[] splitTemp = split(num);
            if (splitTemp[2] > maxAfterDigit){
                maxAfterDigit = splitTemp[2];
            }
        }
        return maxAfterDigit;
    }

    public double addieren(double... numbers) {
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
    public double subtrahieren(double... numbers) {

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

    public double multiplizieren(double... numbers) {
        if (numbers.length == 0) {
            return 0.0;
        }

        double product = 1.0;

        for (double num : numbers) {
            if (num == 0.0) {
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

    public double dividieren(double... numbers) {
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

}