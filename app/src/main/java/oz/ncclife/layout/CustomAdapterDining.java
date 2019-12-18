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

public class CustomAdapterDining extends BaseAdapter
{
	Context context;
	List<RowItemDining> rowItemDinings;

	List<RowItemDining> data;

	public ListView mylistview;

	public CustomAdapterDining( List<RowItemDining> rowItemDinings)
	{
		this.rowItemDinings = rowItemDinings;
	}

	public CustomAdapterDining(Context context, List<RowItemDining> rowItemDinings)
	{
		this.context = context;
		this.rowItemDinings = rowItemDinings;
	}

	@Override
	public int getCount()
	{
		return rowItemDinings.size();
	}

	@Override
	public Object getItem(int position)
	{
		return rowItemDinings.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return rowItemDinings.indexOf(getItem(position));
	}

	/* private view holder class */
	private class ViewHolder
	{
		TextView date;
		TextView soup;
		TextView mainDinner;
		TextView thirdKind;
		TextView fourthKind;
		TextView fifthKind;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.list_row_dining, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.date = (TextView) convertView.findViewById(R.id.date);
		holder.soup = (TextView) convertView.findViewById(R.id.soup);
		holder.mainDinner = (TextView) convertView.findViewById(R.id.main_dinner);
		holder.thirdKind = (TextView) convertView.findViewById(R.id.third_kind);
		holder.fourthKind = (TextView) convertView.findViewById(R.id.fourth_kind);
		holder.fifthKind = (TextView) convertView.findViewById(R.id.fifth_kind);

		RowItemDining row_pos = rowItemDinings.get(position);

		holder.date.setText(row_pos.getDate());
		holder.soup.setText(row_pos.getSoup());
		holder.mainDinner.setText(row_pos.getMainDinner());
		holder.thirdKind.setText(row_pos.getThirdKind());
		holder.fourthKind.setText(row_pos.getFourthKind());
		holder.fifthKind.setText(row_pos.getFifthKind());

		return convertView;
	}

	public void setData(List<RowItemDining> data)
	{
		this.data=data;
		notifyDataSetChanged();
	}
}