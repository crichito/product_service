package com.nttdata.product.app.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.nttdata.product.app.document.Card;

public class GenericFunction {
    
    public static Card generateCard() {

        Calendar cal = Calendar.getInstance();//
        cal.add(Calendar.YEAR, 8);
        Date date = cal.getTime();

        return new Card(GenericFunction.getNumericString(16),  date, GenericFunction.getNumericString(3), GenericFunction.getNumericString(4));
    }

    public static String getNumericString(int n)
    {
        String AlphaNumericString = "0123456789";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length()* Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
