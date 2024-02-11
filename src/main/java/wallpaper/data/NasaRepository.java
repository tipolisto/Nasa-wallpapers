package wallpaper.data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NasaRepository {
	private LocalDateTime localDateTime,nowDateTime, repeatLastlocalDateTime;
	
	public NasaRepository(LocalDateTime localDateTime) {
		this.localDateTime=localDateTime;
		this.nowDateTime=LocalDateTime.now();
		this.nowDateTime=nowDateTime.minusDays(1);
		this.repeatLastlocalDateTime=LocalDateTime.now();
	}
	
	
	/**
	 * 
	 * @param condition=0 para ver la foto del día de hoy, 1 paa ver la foto de mañana, -1 para ver la foto del día anterioe
	 * @param repeatLast para repetir en número de días indicados desde hoy hacia atrás
	 * @return
	 * @throws IOException
	 */
	public NasaResponse getNasaResponse(int condition, int repeatLast) throws IOException {
		if(condition==0) {
			localDateTime= LocalDateTime.now();
			localDateTime=localDateTime.minusDays(1);
		}else if(condition==-1) {
			localDateTime=localDateTime.minusDays(1);
			//System.out.println(""+repeatLast);
			if(repeatLast!=0) {
				//1.Obtenemos el límite inferior
				String stringDateTime=localDateTime.format(DateTimeFormatter.ISO_DATE);
				repeatLastlocalDateTime=nowDateTime.minusDays(repeatLast);
				String stringRepeatLast=repeatLastlocalDateTime.format(DateTimeFormatter.ISO_DATE);
				//System.out.println("rsepeatLast: "+stringRepeatLast+" localTime: "+stringDateTime);
				if(stringRepeatLast.equals(stringDateTime)) {
					localDateTime= LocalDateTime.now();
					localDateTime=localDateTime.minusDays(1);
				}
			}
		}else if(condition==1) {
			String stringNowDateTime=nowDateTime.format(DateTimeFormatter.ISO_DATE);
			String stringDateTime=localDateTime.format(DateTimeFormatter.ISO_DATE);
			if(stringDateTime.equals("2020-01-01")) localDateTime=LocalDateTime.now();	
			if(!stringNowDateTime.equals(stringDateTime)) {
				localDateTime=localDateTime.plusDays(1);
			}
		}

		String start_date=localDateTime.format(DateTimeFormatter.ISO_DATE);
		String end_date=localDateTime.format(DateTimeFormatter.ISO_DATE);
		NasaResponse nasaResponse=null;

		//System.out.println("comienzo: "+start_date+", final: "+end_date);
		//start_date=2017-07-08&end_date=2017-07-10
		//https://api.nasa.gov/planetary/apod?api_key=BTsFn3rER4vAddg5ZzrZcaoIC7Ld0jhyAWA6tgmp&start_date=2022-11-08&end_date=2022-11-08
		URL url=new URL("https://api.nasa.gov/planetary/apod?api_key=BTsFn3rER4vAddg5ZzrZcaoIC7Ld0jhyAWA6tgmp&start_date="+start_date+"&end_date="+end_date);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		//Chequeo si se hizo la conexci�n
		int responseCode=conn.getResponseCode();
		
		if(responseCode!=200)
			throw new RuntimeException("HttpResponseCode: "+responseCode);
		else {
			StringBuilder informationString=new StringBuilder();
			Scanner scanner=new Scanner(url.openStream());
			while(scanner.hasNext()) {
				informationString.append(scanner.nextLine());
			}
			scanner.close();

			Gson g = new Gson();
			final java.lang.reflect.Type tipoListaNasaResponse = new TypeToken<List<NasaResponse>>(){}.getType();
			List<NasaResponse> nasaRequests=  g.fromJson(informationString.toString(), tipoListaNasaResponse);
			if(nasaRequests!=null)  {
				nasaResponse=nasaRequests.get(0);
				if(nasaResponse==null || !nasaResponse.getMedia_type().equals("image")) {
					getNasaResponse(-1, repeatLast);
					nasaResponse=null;
				}
			}
		}
		conn.disconnect();
		return nasaResponse;
	}

}
