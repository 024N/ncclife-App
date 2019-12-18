package oz.ncclife.layout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import oz.ncclife.R;

public class CustomAdapterRest extends BaseAdapter
{
	Context context;
	List<RowItemRest> rowItemRests;

	List<RowItemRest> data;

	public ListView mylistview;

	public CustomAdapterRest(List<RowItemRest> rowItemRests)
	{
		this.rowItemRests = rowItemRests;
	}

	public CustomAdapterRest(Context context, List<RowItemRest> rowItemRests)
	{
		this.context = context;
		this.rowItemRests = rowItemRests;
	}

	@Override
	public int getCount()
	{
		return rowItemRests.size();
	}

	@Override
	public Object getItem(int position)
	{
		return rowItemRests.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return rowItemRests.indexOf(getItem(position));
	}

	/* private view holder class */
	private class ViewHolder
	{
		TextView name;
		TextView desc;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.list_row_rest, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name = (TextView) convertView.findViewById(R.id.rest_name);
		holder.desc = (TextView) convertView.findViewById(R.id.rest_desc);

		RowItemRest row_pos = rowItemRests.get(position);

		holder.name.setText(row_pos.getName());
		holder.desc.setText(row_pos.getDesc());

		return convertView;
	}

	public void setData(List<RowItemRest> data)
	{
		this.data=data;
		notifyDataSetChanged();
	}
}