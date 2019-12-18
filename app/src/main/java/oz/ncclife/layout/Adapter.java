package oz.ncclife.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import oz.ncclife.R;

public class Adapter extends SimpleCell<RowItemMenu,Adapter.ViewHolder>
{
    public Adapter(@NonNull RowItemMenu item)
    {
        super(item);
    }

    @Override
    protected int getLayoutRes()
    {
        return R.layout.list_row_menu_food;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, View cellView)
    {
        return new ViewHolder(cellView);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Context context, Object o)
    {
        viewHolder.foodName.setText(getItem().getName());
        viewHolder.foodPrice.setText(getItem().getPrice());
    }

    static public class ViewHolder extends SimpleViewHolder
    {
        TextView foodName,foodPrice;
        ViewHolder(View itemView)
        {
            super(itemView);
            foodName=itemView.findViewById(R.id.food_name);
            foodPrice=itemView.findViewById(R.id.food_price);
        }
    }
}