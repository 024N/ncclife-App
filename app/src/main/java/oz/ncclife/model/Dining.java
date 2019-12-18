package oz.ncclife.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dining
{
    @SerializedName("cacheVersion")
    @Expose
    public String cacheVersion;
    @SerializedName("type")
    @Expose
    public Integer type;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("soup")
    @Expose
    public String soup;
    @SerializedName("main_dinner")
    @Expose
    public String mainDinner;
    @SerializedName("third_kind")
    @Expose
    public String thirdKind;
    @SerializedName("fourth_kind")
    @Expose
    public String fourthKind;
    @SerializedName("fifth_kind")
    @Expose
    public String fifthKind;
}