package oz.ncclife.layout;

public class RowItemMenu
{

    private String name,price;
    private RowItemMenuHeader rowItemMenuHeader;

    public RowItemMenu(String name, String price, RowItemMenuHeader rowItemMenuHeader)
    {
        this.name = name;
        this.price = price;
        this.rowItemMenuHeader = rowItemMenuHeader;
    }

    public String getName()
    {
        return name;
    }

    public String getPrice()
    {
        return price;
    }

    public int getRowItemMenuHeaderId()
    {
        return rowItemMenuHeader.getId();
    }

    public String getRowItemMenuHeaderName()
    {
        return rowItemMenuHeader.getName();
    }
}