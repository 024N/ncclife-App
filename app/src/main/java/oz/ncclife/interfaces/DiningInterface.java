package oz.ncclife.interfaces;

import oz.ncclife.model.Dining;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DiningInterface
{
    @GET("dininghallandroid.json")
    Call<Dining[]> getJsonValues();
}
