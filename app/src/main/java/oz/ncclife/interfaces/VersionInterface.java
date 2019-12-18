package oz.ncclife.interfaces;

import oz.ncclife.model.Version;
import retrofit2.Call;
import retrofit2.http.GET;

public interface VersionInterface
{
    @GET("api/v1/dininghall-cache.json")
    Call<Version> getJsonValues();
}