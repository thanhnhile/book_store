package com.example.book_store.ui;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public final class FormatCurrency {
    public static String formatVND(int value){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str = currencyVN.format(value);
        return str;
    }
    public static int formatInt(String currency){
        int result = 0;
        currency.substring(0,currency.length()-2);
        String [] arr = currency.split(".");
        int dem = 0;
        for(int i=arr.length - 1;i>=0;i--){
            result += Integer.parseInt(arr[i])*Math.pow(10,dem);
            dem++;
        }
        //15200
        //5.000d
        //50.000d
        //100.000d
        return result;
    }

}
