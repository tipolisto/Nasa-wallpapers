package wallpaper.data;

import java.net.URL;
//https://api.nasa.gov/planetary/apod?api_key=BTsFn3rER4vAddg5ZzrZcaoIC7Ld0jhyAWA6tgmp
//para ver por fechas: https://api.nasa.gov/planetary/apod?api_key=BTsFn3rER4vAddg5ZzrZcaoIC7Ld0jhyAWA6tgmp&start_date=2017-07-08&end_date=2017-07-10
public class NasaResponse {
	//private String copyright;
	private String date;
	private String explanation;
	private URL hdurl;
	private String media_type;
	private String service_version;
	private String title;
	private URL url;
	public NasaResponse( String date, String explanation, URL hdurl, String media_type,
			String service_version, String title, URL url) {
		super();
		//this.copyright = copyright;
		this.date = date;
		this.explanation = explanation;
		this.hdurl = hdurl;
		this.media_type = media_type;
		this.service_version = service_version;
		this.title = title;
		this.url = url;
	}
	/*public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}*/
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public URL getHdurl() {
		return hdurl;
	}
	public void setHdurl(URL hdurl) {
		this.hdurl = hdurl;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public String getService_version() {
		return service_version;
	}
	public void setService_version(String service_version) {
		this.service_version = service_version;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "NasaResponse [date=" + date + ", explanation=" + explanation + ", hdurl="
				+ hdurl + ", media_type=" + media_type + ", service_version=" + service_version + ", title=" + title
				+ ", url=" + url + "]";
	}
	

}
