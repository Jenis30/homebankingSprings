package mindhub.HomebankingSprings.Utils;

public class AccountsUtil {
    public static String generateAccountNumber(int max, int min){
       int number = (int) ((Math.random() * (max - min)) + min);
        String numberFormat = String.format("%03d", number);
       return "VIN-"+ numberFormat;
    }
}
