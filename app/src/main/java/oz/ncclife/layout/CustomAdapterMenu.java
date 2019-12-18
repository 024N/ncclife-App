package oz.ncclife.layout;


public class CustomAdapterMenu // extends BaseAdapter
{


	//public ListView mylistview;

	/*
	public CustomAdapterMenu(List<RowItemMenuHeader> rowItemMenus)
	{
		this.rowItemMenus = rowItemMenus;
	}

	public CustomAdapterMenu(Context context, List<RowItemMenuHeader> rowItemMenus)
	{
		this.context = context;
		this.rowItemMenus = rowItemMenus;
	}

	@Override
	public int getCount()
	{
		return rowItemMenus.size();
	}

	@Override
	public Object getItem(int position)
	{
		return rowItemMenus.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return rowItemMenus.indexOf(getItem(position));
	}

	// private view holder class
	private class ViewHolder
	{
		TextView name;
		TextView foodName;
		TextView foodPrice;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.list_row_menu_header, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name = (TextView) convertView.findViewById(R.id.menu_name);
		holder.foodName = (TextView) convertView.findViewById(R.id.food_name);
		holder.foodPrice = (TextView) convertView.findViewById(R.id.food_price);

		RowItemMenuHeader row_pos = rowItemMenus.get(position);

		holder.name.setText(row_pos.getName());
		//holder.foodName.setText(row_pos.getFoodName());
		//holder.foodPrice.setText(row_pos.getFoodPrice());
*/
/*
    public void setData(List<RowItemMenuHeader> data)
	{
		this.data=data;
		notifyDataSetChanged();
	}
	*/
}

