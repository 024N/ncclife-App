package oz.ncclife.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import oz.ncclife.MainActivity;
import oz.ncclife.R;
import oz.ncclife.interfaces.DiningInterface;
import oz.ncclife.interfaces.RetroClient;
import oz.ncclife.layout.CustomAdapterDining;
import oz.ncclife.layout.RowItemDining;
import oz.ncclife.model.Dining;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiningFragment extends Fragment
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Dining> dinings = new ArrayList<>();

    public String cacheVersion, types;
    public ArrayList<Integer> type = new ArrayList<Integer>();
    public ArrayList<String> date = new ArrayList<String>();
    public ArrayList<String> soup = new ArrayList<String>();
    public ArrayList<String> mainDinner = new ArrayList<String>();
    public ArrayList<String> thirdKind = new ArrayList<String>();
    public ArrayList<String> fourthKind = new ArrayList<String>();
    public ArrayList<String> fifthKind = new ArrayList<String>();

    public List<RowItemDining> rowItemDinings;
    public ListView mylistview;

    public String day;
    public String month;
    public int index = 0;
    public int temp = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dining, container, false);

        date.clear();
        soup.clear();
        mainDinner.clear();
        thirdKind.clear();
        fourthKind.clear();
        fifthKind.clear();
        dinings.clear();

        final TinyDB tinydb = new TinyDB(getActivity());

        //Log.i("XXXVersion2: ", tinydb.getInt("UpdateDining") + " " + tinydb.getInt("UpdateDining"));

        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        if(c.get(Calendar.DAY_OF_MONTH)<10)
            day = "0"+c.get(Calendar.DAY_OF_MONTH);
        else
            day = ""+c.get(Calendar.DAY_OF_MONTH);

        if(c.get(Calendar.MONTH)+1<10)
            month = "0"+(c.get(Calendar.MONTH)+1);
        else
            month = ""+(c.get(Calendar.MONTH)+1);

        rowItemDinings = new ArrayList<RowItemDining>();
        rowItemDinings.clear();

        if(isNetworkAvailable() && tinydb.getInt("UpdateDining") == 1)
        {
            //final SharedPreferences.Editor myPrefsEditor = mySharedPrefs.edit();

            DiningInterface diningInterface = RetroClient.getClient().create(DiningInterface.class);
            Call<Dining[]> call = diningInterface.getJsonValues();

            //Call<Dining[]> call = ((MainActivity) getActivity()).DiningJson();

            mylistview = (ListView) view.findViewById(R.id.dining_list);
            final CustomAdapterDining adapter = new CustomAdapterDining(getActivity(), rowItemDinings);
            mylistview.setAdapter(adapter);
            call.enqueue(new Callback<Dining[]>()
            {
                @Override
                public void onResponse(Call<Dining[]> call, Response<Dining[]> response)
                {
                    dinings = Arrays.asList(response.body());

                    //cacheVersion = dinings.get(0).cacheVersion;
                    for(int i=0; i<dinings.size(); i++)
                    {
                        if(dinings.get(i).type == 1)
                            types = "ÖĞLEN";
                        else
                            types = "AKŞAM";
                        date.add(dinings.get(i).date + " - " + types);
                        soup.add(dinings.get(i).soup);
                        mainDinner.add(dinings.get(i).mainDinner);
                        thirdKind.add(dinings.get(i).thirdKind);
                        fourthKind.add(dinings.get(i).fourthKind);
                        fifthKind.add(dinings.get(i).fifthKind);

                        //listrow'da o gunun yerini bulması icin
                        if((dinings.get(i).date.substring(0,dinings.get(i).date.indexOf(".20")).equals(day+"."+month)) && (temp == 0))
                        {
                            index = i;
                            temp = 1;
                        }

                        RowItemDining item = new RowItemDining(date.get(i),  soup.get(i),  mainDinner.get(i),  thirdKind.get(i),  fourthKind.get(i),  fifthKind.get(i));
                        rowItemDinings.add(item);
                    }
                    tinydb.putListString("date",date);
                    tinydb.putListString("soup",soup);
                    tinydb.putListString("mainDinner",mainDinner);
                    tinydb.putListString("thirdKind",thirdKind);
                    tinydb.putListString("fourthKind",fourthKind);
                    tinydb.putListString("fifthKind",fifthKind);
                    //mylistview.smoothScrollToPosition(7);

                    mylistview.setSelection(index);
                    adapter.setData(rowItemDinings);
                    if(temp == 0)
                        Toast.makeText(getActivity(), "Bugün kapalı ya da menüye eklenmemiş!" , Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Call<Dining[]> call, Throwable t)
                {
                    Log.e(TAG, "Error: " + t.toString());
                }
            });

            tinydb.putInt("UpdateDining",0);
        }

        else
        {
            mylistview = (ListView) view.findViewById(R.id.dining_list);
            final CustomAdapterDining adapter = new CustomAdapterDining(getActivity(), rowItemDinings);

            date = tinydb.getListString("date");
            soup = tinydb.getListString("soup");
            mainDinner = tinydb.getListString("mainDinner");
            thirdKind = tinydb.getListString("thirdKind");
            fourthKind = tinydb.getListString("fourthKind");
            fifthKind = tinydb.getListString("fifthKind");

            for(int i=0; i<date.size(); i++)
            {
                //listrow'da o gunun yerini bulması icin .2018 den onceki bolumu alır
                if((date.get(i).substring(0,date.get(i).indexOf(".20")).equals(day+"."+month)) && (temp == 0))
                {
                    index = i;
                    temp = 1;
                }
                RowItemDining item = new RowItemDining(date.get(i),  soup.get(i),  mainDinner.get(i),  thirdKind.get(i),  fourthKind.get(i),  fifthKind.get(i));
                rowItemDinings.add(item);
            }
            //mylistview.smoothScrollToPosition(7);
            adapter.setData(rowItemDinings);
            mylistview.setAdapter(adapter);
            mylistview.setSelection(index);

            if(temp == 0)
                Toast.makeText(getActivity(), "Bugün kapalı ya da menüye eklenmemiş!" , Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}