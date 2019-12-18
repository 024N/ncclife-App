package oz.ncclife.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oz.ncclife.MainActivity;
import oz.ncclife.R;
import oz.ncclife.interfaces.RestaurantInterface;
import oz.ncclife.interfaces.RetroClient;
import oz.ncclife.layout.CustomAdapterRest;
import oz.ncclife.layout.RowItemRest;
import oz.ncclife.model.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestFragment extends Fragment
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Restaurant> restaurants = new ArrayList<>();
    private List<List<Restaurant.Menu>> menus = new ArrayList<>();

    public String cacheVersion;
    public List<Integer> id = new ArrayList<Integer>();
    public ArrayList<String> name = new ArrayList<String>();
    public ArrayList<String> desc = new ArrayList<String>();
    public List<List<String>> phones = new ArrayList<>();

    public List<RowItemRest> rowItemRests;
    public ListView mylistview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_rest, container, false);
        rowItemRests = new ArrayList<RowItemRest>();

        rowItemRests.clear();
        restaurants.clear();
        menus.clear();
        id.clear();
        name.clear();
        desc.clear();
        phones.clear();

        final TinyDB tinydb = new TinyDB(getActivity());

        if(isNetworkAvailable() && tinydb.getInt("UpdateRest") == 1)
        {
            RestaurantInterface restaurantInterface = RetroClient.getClient().create(RestaurantInterface.class);
            Call<Restaurant[]> call = restaurantInterface.getJsonValues();

            mylistview = (ListView) view.findViewById(R.id.rest_list);
            final CustomAdapterRest adapter = new CustomAdapterRest(getActivity(), rowItemRests);
            mylistview.setAdapter(adapter);
            call.enqueue(new Callback<Restaurant[]>()
            {
                @Override
                public void onResponse(Call<Restaurant[]> call, Response<Restaurant[]> response)
                {
                    restaurants = Arrays.asList(response.body());
                    //cacheVersion = restaurants.get(0).cacheVersion;

                    if(true)
                    {
                        Gson gson = new Gson();
                        ArrayList<String> gsonString = new ArrayList<>();
                        for(int i=0; i<restaurants.size(); i++)
                            gsonString.add(gson.toJson(restaurants.get(i)));
                        tinydb.putListString("tinyRestaurant",gsonString);
                    }

                    for (int i = 0; i < restaurants.size(); i++)
                    {
                        name.add(restaurants.get(i).name);
                        desc.add(restaurants.get(i).desc);
                        menus.add(restaurants.get(i).menus);
                        phones.add(restaurants.get(i).phones);

                        RowItemRest item = new RowItemRest(name.get(i), desc.get(i));
                        rowItemRests.add(item);
                    }

                    adapter.setData(rowItemRests);
                    tinydb.putInt("UpdateRest",0);
                }

                @Override
                public void onFailure(Call<Restaurant[]> call, Throwable t) {
                    Log.e(TAG, "Error: " + t.toString());
                }
            });
        }

        else
        {
            mylistview = (ListView) view.findViewById(R.id.rest_list);
            CustomAdapterRest adapter = new CustomAdapterRest(getActivity(), rowItemRests);

            Gson gson = new Gson();
            for(int i=0; i<tinydb.getListString("tinyRestaurant").size(); i++)
                restaurants.add(gson.fromJson(tinydb.getListString("tinyRestaurant").get(i), Restaurant.class));

            //Usteki for ile birlestirilecek.
            for (int i = 0; i < restaurants.size(); i++)
            {
                name.add(restaurants.get(i).name);
                desc.add(restaurants.get(i).desc);
                menus.add(restaurants.get(i).menus);
                phones.add(restaurants.get(i).phones);

                RowItemRest item = new RowItemRest(name.get(i), desc.get(i));
                rowItemRests.add(item);
            }
            adapter.setData(rowItemRests);
            mylistview.setAdapter(adapter);
        }

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Toast.makeText(getActivity(), " XXX! " + phones.get(0).get(0) , Toast.LENGTH_LONG).show();

                MenuFragment menuFragment = new MenuFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                getActivity().setTitle(name.get(position));
                menuFragment.setData(menus.get(position), phones.get(position));

                //fragmentleri temizler
                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                transaction.replace(R.id.content, menuFragment, "MenuFragment");
                transaction.addToBackStack(null).commit();
            }
        });

        return view;
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}