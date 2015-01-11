package com.example.djdonahu.t4t;


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

        try
        {
            this.url = new URL(urlStr);
            InputStream inputStream;
            inputStream = this.url.openStream();

        }
        catch (UnknownHostException uhe)
        {
            // Should probably deal with this error. Not now! (Stretch goal?)
            System.err.println("Could not reach hostname.");

        }
        catch(MalformedURLException mue)
        {
            mue.printStackTrace();

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();

        }

        return pageContents.toString();

    }
}
