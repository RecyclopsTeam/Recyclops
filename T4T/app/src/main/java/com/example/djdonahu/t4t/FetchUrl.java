package com.example.djdonahu.t4t;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class FetchUrl
{
    URL url;

    public String fetch(String urlStr)
    {
        StringBuilder pageContents = new StringBuilder();
        String s;
        try {
            this.url = new URL(urlStr);
            InputStream inputStream;
            inputStream = this.url.openStream();

            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
            while ((s = dataInputStream.readLine()) != null) {
                pageContents.append(s);

            }
        }
        catch ( UnknownHostException e )
        {
            System.err.println( "Could not reach hostname" );


        }
        catch ( MalformedURLException me )
        {
            // TODO: Change this to actually catch this.
            me.printStackTrace();

        }
        catch ( IOException ioe )
        {
            // TODO: Change this to something good.
            ioe.printStackTrace();

        }

        return pageContents.toString();

    }
}
