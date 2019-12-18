package oz.ncclife.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import oz.ncclife.R;
import oz.ncclife.layout.Adapter;
import oz.ncclife.layout.RowItemMenu;
import oz.ncclife.layout.RowItemMenuHeader;
import oz.ncclife.model.Restaurant;

public class MenuFragment extends Fragment
{
    SimpleRecyclerView simpleRecyclerView;
    public List<Restaurant.Menu> menus;
    public List<String> phones = new ArrayList<String>();

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public CharSequence phoneNumb[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_row_menu_main, container, false);
        setHasOptionsMenu(true);
        //Toast.makeText(getActivity(), "This! " + menus.get(1) , Toast.LENGTH_LONG).show();

        simpleRecyclerView = view.findViewById(R.id.recyclerView);
        this.addRecyclerHeaders();
        this.bindData();

        return view;
    }

    private void addRecyclerHeaders()
    {
        SectionHeaderProvider<RowItemMenu> sh=new SimpleSectionHeaderProvider<RowItemMenu>()
        {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull RowItemMenu rowItemMenu, int i) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_row_menu_header, null, false);
                TextView textView =  view.findViewById(R.id.menu_header);
                textView.setText(rowItemMenu.getRowItemMenuHeaderName());
                return view;
            }

            @Override
            public boolean isSameSection(@NonNull RowItemMenu rowItemMenu, @NonNull RowItemMenu nextRowItemMenu) {
                return rowItemMenu.getRowItemMenuHeaderId() == nextRowItemMenu.getRowItemMenuHeaderId();
            }
            // Optional, whether the header is sticky, default false
            @Override
            public boolean isSticky() {
                return true;
            }
        };
        simpleRecyclerView.setSectionHeader(sh);
    }

    private void bindData()
    {
        List<RowItemMenu> rowItemMenus = getData();
        //CUSTOM SORT ACCORDING TO CATEGORIES
        Collections.sort(rowItemMenus, new Comparator<RowItemMenu>(){
            public int compare(RowItemMenu RowItemMenu, RowItemMenu nextRowItemMenu) {
                return RowItemMenu.getRowItemMenuHeaderId() - nextRowItemMenu.getRowItemMenuHeaderId();
            }
        });
        List<Adapter> cells = new ArrayList<>();

        //LOOP THROUGH GALAXIES INSTANTIATING THEIR CELLS AND ADDING TO CELLS COLLECTION
        for (RowItemMenu rowItemMenu : rowItemMenus) {
            Adapter cell = new Adapter(rowItemMenu);
            // There are two default cell listeners: OnCellClickListener<CELL, VH, T> and OnCellLongClickListener<CELL, VH, T>

            cell.setOnCellClickListener2(new SimpleCell.OnCellClickListener2<Adapter, Adapter.ViewHolder, RowItemMenu>() {
                @Override
                public void onCellClicked(Adapter Adapter, Adapter.ViewHolder viewHolder, RowItemMenu item) {
                    //Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            cell.setOnCellLongClickListener2(new SimpleCell.OnCellLongClickListener2<Adapter, Adapter.ViewHolder, RowItemMenu>() {
                @Override
                public void onCellLongClicked(Adapter Adapter, Adapter.ViewHolder viewHolder, RowItemMenu item) {
                    //Toast.makeText(getActivity(), item.getPrice(), Toast.LENGTH_SHORT).show();

                }
            });
            cells.add(cell);
        }
        simpleRecyclerView.addCells(cells);
    }

    private ArrayList<RowItemMenu> getData()
    {
        ArrayList<RowItemMenu> menuPage = new ArrayList<>();

        //CREATE CATEGORIES
        for(int i = 0; i<menus.size(); i++)
        {
            RowItemMenuHeader header = new RowItemMenuHeader(i,menus.get(i).name);
            for(int j = 0; j < menus.get(i).foods.size(); j++)
            {
                RowItemMenu row = new RowItemMenu(menus.get(i).foods.get(j).name, "₺ " + menus.get(i).foods.get(j).price, header);
                menuPage.add(row);
            }
        }
        return menuPage;
    }
    //Json data bu metot'a geliyor
    public void setData(List<Restaurant.Menu> menus, List<String> phones)
    {
        this.menus = menus;
        this.phones = phones;
        //Log.i(" XXX ", this.phones.get(0));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.add("").setIcon(android.R.drawable.ic_menu_call).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS))
            {
            }
            else
            {
                //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                //MenuFragment.requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        //Toast.makeText(getActivity(), ""+phones.size(), Toast.LENGTH_LONG).show();
        phoneNumb = new CharSequence[phones.size()];
        for(int i=0; i<phones.size(); i++)
            phoneNumb[i] = phones.get(i);

        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MyTheme);
        final AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Lütfen aramak istediğiniz numarayı seçin:");
        builder.setItems(phoneNumb, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Toast.makeText(getActivity(), ""+which, Toast.LENGTH_LONG).show();
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+phones.get(which)));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                {
                    startActivity(phoneIntent);
                }
                startActivity(phoneIntent);
            }
        });


        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    builder.show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                }
                else
                {
                    Toast.makeText(getActivity(), "Arama için izin verilmedi!!! ", Toast.LENGTH_LONG).show();
                    builder.show().dismiss();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}