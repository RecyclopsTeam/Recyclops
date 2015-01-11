package com.example.djdonahu.t4t;
import android.os.AsyncTask;
import org.json.JSONObject;

public class AsyncFetchUrl extends AsyncTask<String,Void,String>
{
    private FetchUrl fetchUrl;
    private FetchUrlCallback fetchCallback;

    public AsyncFetchUrl(String url, FetchUrlCallback callback)
    {
        // For debug. Take out later.
        System.out.println("New asyncfetch");
        this.fetchUrl = new FetchUrl();
        this.fetchCallback = callback;

    }

    protected String doInBackground(String ... urls)
    {
        return this.fetchUrl.fetch(urls[0]);

    }

    protected void onPostExecute(JSONObject result)
    {
        // Debug statement.
        System.out.println("Post execute");
        if (result != null)
        {
            try
            {
                if (this.fetchCallback != null)
                    this.fetchCallback.execute(result);
                else
                    throw new FetchCallbackException("eeee");

            }
            catch (FetchCallbackException e)
            {
                e.printStackTrace();

            }
        }
    }



}
