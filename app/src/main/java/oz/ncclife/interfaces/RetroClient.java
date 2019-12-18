package oz.ncclife.interfaces;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient
{
    public static final String BASE_URL = "http://ncclife.net/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}



    
    try:
        with connection.cursor() as cursor:
            # Read a single record
            sql = "SELECT `id` FROM `users` WHERE `email`=%s"
            cursor.execute(sql, ('email133f'))
    finally:
        connection.close()