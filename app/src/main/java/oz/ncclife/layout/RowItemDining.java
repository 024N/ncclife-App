package oz.ncclife.layout;

public class RowItemDining
{
	private String date;
	private String soup;
	private String mainDinner;
	private String thirdKind;
	private String fourthKind;
	private String fifthKind;

	public RowItemDining(String date, String soup, String mainDinner, String thirdKind, String fourthKind, String fifthKind)
	{
		this.date = date;
		this.soup = soup;
		this.mainDinner = mainDinner;
		this.thirdKind = thirdKind;
		this.fourthKind = fourthKind;
		this.fifthKind = fifthKind;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSoup() {
		return soup;
	}

	public void setSoup(String soup) {
		this.soup = soup;
	}

	public String getMainDinner() {
		return mainDinner;
	}

	public void setMainDinner(String mainDinner) {
		this.mainDinner = mainDinner;
	}

	public String getThirdKind() {
		return thirdKind;
	}

	public void setThirdKind(String thirdKind) {
		this.thirdKind = thirdKind;
	}

	public String getFourthKind() {
		return fourthKind;
	}

	public void setFourthKind(String fourthKind) {
		this.fourthKind = fourthKind;
	}

	public String getFifthKind() {
		return fifthKind;
	}

	public void setFifthKind(String fifthKind) {
		this.fifthKind = fifthKind;
	}
}