package oz.ncclife.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Restaurant
{
    @SerializedName("cacheVersion")
    @Expose
    public String cacheVersion;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("desc")
    @Expose
    public String desc;
    @SerializedName("phones")
    @Expose
    public List<String> phones = null;
    @SerializedName("menus")
    @Expose
    public List<Menu> menus = null;
    @SerializedName("image")
    @Expose
    public String image;


    public static class Menu
    {
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("foods")
        @Expose
        public List<Food> foods = new ArrayList<>();

    }


    public static class Food
    {
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("desc")
        @Expose
        public String desc;
        @SerializedName("price")
        @Expose
        public String price;
    }
}