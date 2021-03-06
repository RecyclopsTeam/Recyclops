package com.example.djdonahu.t4t;
import android.os.AsyncTask;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/*

    getProduct (or another callback) is another method within the calling class,
    or any other method you wish to call. Blame the really stupid structure on
    Java's lack of function pointers.

    Example of product fetch:
    OutpanRequest.getProduct( "barcodehere",
        new FetchUrlCallback() {

        @Override
        public void execute(Object result) {
            getProduct(result);
        }
    });

    Here is how your getProduct(result) might work if result is a Product object.
    public Product getProduct(Object result)
    {
        Product product = null;
        try {
        product = (Product) result;
        // now do something with product result.
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return product;
    }

 */

public class OutpanRequest
{
    private static AsyncTask currRequest = null;
    private static String gatewayUrl
            = "http://www.outpan.com/api/get-product.php?apikey=ac75f6821225d0efc22cb8cb9cfd294b";

    public static Product castProduct(Object result)
    {
        Product product = null;
        try {
            if ( result != null ) {
                product = (Product) result;
                // The initialization step could null out the product for invalid data
                product = product.initialize();
            } else {
                product = null;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return product;

    }
    
    public static void getProduct(String barcode, FetchUrlCallback callback)
    {
        request( RequestType.GET_PRODUCT, barcode, callback );

    }


    public static void cancelRequest()
    {
        if ( currRequest != null )
        {
            System.out.println( "ATTEMPT CANCEL" );
            currRequest.cancel( true );

        }
    }


    private static void request(RequestType type, String args, FetchUrlCallback callback)
    {
        StringBuilder urlStr = new StringBuilder(gatewayUrl);
        Class<?> objectType = Product.class;
        try
        {
            switch (type)
            {
                case GET_PRODUCT:
                    urlStr.append("&barcode=")
                            .append(args);
                    objectType = Product.class;
                    break;

            }

            System.out.println("URL: " + urlStr.toString());

            currRequest = new FetchRequestAsync( urlStr.toString(),
                    callback, objectType ).execute();

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }
}
