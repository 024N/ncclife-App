package oz.ncclife.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Version
{
    @SerializedName("cacheVersion")
    @Expose
    public String cacheVersion;
}