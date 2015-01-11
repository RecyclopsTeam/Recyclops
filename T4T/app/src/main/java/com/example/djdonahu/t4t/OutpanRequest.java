package com.example.djdonahu.t4t;


import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OutpanRequest
{
    private static AsyncTask currRequest = null;
    private static String gatewayUrl
            = "http://www.outpan.com/get-product.php?apikey=ac75f6821225d0efc22cb8cb9cfd294b";

    public static void getProduct(String barcode, FetchUrlCallback callback)
    {
        request( RequestType.GET_PRODUCT, barcode, callback );

    }

    private static void request(RequestType type, String args, FetchUrlCallback callback)
    {
        StringBuilder urlStr = new StringBuilder( gatewayUrl );
        Class<?> objectType = ProductResult.class;
        try
        {
            switch (type)
            {
                case GET_PRODUCT:
                    urlStr.append("&barcode=")
                            .append(args);
                    objectType = ProductResult.class;
                    break;

            }

            System.out.println( "URL: " + urlStr.toString());

            currRequest = new FetchRequestAsync( urlStr.toString(),
                    callback, objectType ).execute();

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }
}
