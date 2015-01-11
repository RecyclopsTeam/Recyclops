package com.example.djdonahu.t4t;
import android.os.AsyncTask;

import com.google.gson.Gson;

public class FetchRequestAsync extends AsyncTask<String,Void,String>
{
    Gson gson;
    String url;
    FetchUrl fetchUrl;
    FetchUrlCallback fetchCallback;
    Class<?> objectType;

    public FetchRequestAsync( String _url, FetchUrlCallback callback, Class<?> _objectType )
    {
        System.out.println( "New AsyncFetch" );
        this.url = _url;
        this.fetchUrl = new FetchUrl();
        this.fetchCallback = callback;
        this.objectType = _objectType;

    }


    protected String doInBackground( String ... urls )
    {
        return this.fetchUrl.fetch( this.url );

    }


    protected void onPostExecute( String result )
    {
        this.gson = new Gson();
        System.out.println( "Postexecute" );
        //Class json;

        if ( result != null )
        {
            try
            {
                System.out.println( result );
                Object object = gson.fromJson( result, this.objectType );

                System.out.println( object );
                if ( this.fetchCallback != null )
                    this.fetchCallback.execute( object );
                else
                    throw new FetchCallbackException( "Invalid fetch callback." );

            }
            catch ( com.google.gson.JsonSyntaxException e )
            {
                System.out.println( "Invalid JSON received from gateway." );
                System.out.println( e.getMessage() );

            }
            catch ( FetchCallbackException e )
            {
                e.printStackTrace();

            }
        }
    }
}
