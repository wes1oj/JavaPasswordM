package infobiszt;

public class Data {

	public  String weBName;
	public  String weBurl;
	public  String weBpass;


	Data(String name,String url, String pass) {
		weBName = name;
		weBurl=url;
		weBpass = pass;
	}

	public void setWeBName(String name) {
		weBName = name;
	}

	public void setWeBurl(String url) {
		weBurl=url;
	}

	public void setWeBpass(String pass) {
		weBpass = pass;
	}

	public String getWeBName() {
		return weBName;
	}

	public  String getWeBurl() {
		return weBurl;
	}

	public  String getWeBpass() {
		return weBpass;
	}

	public  String toString() {
		return "Data [weBName=" + weBName + ", weBurl=" + weBurl + ", weBpass=" + weBpass + "]";
	}
	
}
