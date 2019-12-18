package oz.ncclife;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.mukesh.tinydb.TinyDB;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oz.ncclife.fragments.DiningFragment;
import oz.ncclife.fragments.RestFragment;
import oz.ncclife.interfaces.RetroClient;
import oz.ncclife.interfaces.VersionInterface;
import oz.ncclife.model.Version;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private List<Version> cacheVersion = new ArrayList<>();
    public String version;

    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId())
            {
                case R.id.navigation_dining:
                    setTitle("Yemekhane");
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);//fragmentleri temizler
                    transaction.replace(R.id.content, new DiningFragment(), "Dining").commit();
                    return true;
                case R.id.navigation_rest:
                    setTitle("Restoranlar");
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);//fragmentleri temizler
                    transaction.replace(R.id.content, new RestFragment(), "Rest").commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        setContentView(R.layout.activity_main);

        if(isNetworkAvailable())
        {
            final TinyDB tinydb = new TinyDB(this);

            VersionInterface versionInterface = RetroClient.getClient().create(VersionInterface.class);
            Call<Version> call = versionInterface.getJsonValues();
            call.enqueue(new Callback<Version>()
            {
                @Override
                public void onResponse(Call<Version> call, Response<Version> response)
                {
                    cacheVersion = Arrays.asList(response.body());
                    version = cacheVersion.get(0).cacheVersion;
                    if(tinydb.getString("Version").isEmpty() || tinydb.getString("Version") == null || !version.equals(tinydb.getString("Version")))
                    {
                        tinydb.putString("Version",version);
                        tinydb.putInt("UpdateDining",1);
                        tinydb.putInt("UpdateRest",1);
                    }
                    else
                    {
                        tinydb.putInt("UpdateDining",0);
                        tinydb.putInt("UpdateRest",0);
                    }
                }
                @Override
                public void onFailure(Call<Version> call, Throwable t)
                {
                    Log.e(TAG, "Error: " + t.toString());
                }
            });
        }

        setTitle("Yemekhane");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Baslangıcta yemekhane(diningfragment) bolumunu direk acilmasi icin.
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); //fragmentleri temizler
        transaction.replace(R.id.content, new DiningFragment()).commit();
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}