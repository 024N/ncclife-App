package oz.ncclife.interfaces;

import oz.ncclife.model.Restaurant;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestaurantInterface
{
    @GET("restaurantsandroid.json")
    Call<Restaurant[]> getJsonValues();
}
