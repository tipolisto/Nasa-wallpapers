package wallpaper;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import wallpaper.data.NasaRepository;
import wallpaper.data.NasaResponse;
import wallpaper.interfaces.User32;

public class ThreadChanger extends Thread{

	private NasaRepository nasaRepository;
	private NasaResponse nasaResponse;
	private JCheckBox chckbxAutomaticChange,chckbxRepeatLast;
	private JComboBox comboBoxSeconds,comboBoxRepeatLast;
	private JLabel lblLabelImage,lblLabelTitle,lblLabelDate,lblLabelURL;
	private JTextArea textAreaEplanation;
	private File file;
	public ThreadChanger(NasaRepository nasaRepository,
						JCheckBox chckbxAutomaticChange,
						JCheckBox chckbxRepeatLast,
						JComboBox comboBoxSeconds,
						JComboBox comboBoxRepeatLast,
						JLabel lblLabelImage,JLabel lblLabelTitle,JLabel lblLabelDate, JLabel lblLabelURL,
						JTextArea textAreaEplanation) {
		this.nasaRepository=nasaRepository;
		this.nasaResponse=null;
		this.chckbxAutomaticChange=chckbxAutomaticChange;
		this.chckbxRepeatLast=chckbxRepeatLast;
		this.comboBoxSeconds=comboBoxSeconds;
		this.comboBoxRepeatLast=comboBoxRepeatLast;
		this.lblLabelImage=lblLabelImage;
		this.lblLabelTitle=lblLabelTitle;
		this.lblLabelDate=lblLabelDate;
		this.lblLabelURL=lblLabelURL;
		this.textAreaEplanation=textAreaEplanation;
		this.file=null;
	}
	
	
	/*
	if(chckbxRepeatLast.isSelected()) {
	String stringMinusDays=(String)comboBoxSeconds.getSelectedItem();
	int minusDays=Integer.valueOf(stringMinusDays);
	dateTimeLastRepeat=nowDateTime.minusDays(minusDays);
	String stringDateTimeLastRepeat=dateTimeLastRepeat.format(DateTimeFormatter.ISO_DATE);
	if(stringDateTime.equals(stringDateTimeLastRepeat)) dateTime=LocalDateTime.now();	
	System.out.println("Activado repeat "+stringDateTimeLastRepeat+" - "+stringDateTime);
	 */
	@Override
	public void run() {
		while(chckbxAutomaticChange.isSelected()) {
			try {
				//hacemos la pausa según lo seleccionado en el comboBox
				String stringSeconds=(String)comboBoxSeconds.getSelectedItem();
				int seconds=Integer.valueOf(stringSeconds)*100;
				Thread.sleep(seconds);
				
				int repeatlast=0;
				if(chckbxRepeatLast.isSelected()) {
					String stringRepeatLast=(String)comboBoxRepeatLast.getSelectedItem();
					repeatlast=Integer.valueOf(stringRepeatLast);
				}

				nasaResponse=nasaRepository.getNasaResponse(-1,repeatlast);	
				if(nasaResponse!=null && nasaResponse.getMedia_type().equals("image")) {
					createFileImage(nasaResponse);
					fillFields(nasaResponse);
					changer();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	
	public void createFileImage(NasaResponse nasaResponse)  {
		String imageUrl=nasaResponse.getUrl().toString();
	    URL url;
		try {
			url = new URL(imageUrl);
			InputStream is = url.openStream();
		    OutputStream os = new FileOutputStream("temp.jpg");

		    byte[] b = new byte[2048];
		    int length;

		    while ((length = is.read(b)) != -1) {
		        os.write(b, 0, length);
		    }
		    is.close();
		    os.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fillFields(NasaResponse nasaResponse) {
		//5.Le ponemos la imagen al label
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("temp.jpg"));
			Image img= bufferedImage.getScaledInstance(400, 200, Image.SCALE_DEFAULT);
			lblLabelImage.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//6.Ponemos los demás campos
		lblLabelDate.setText(nasaResponse.getDate());
		lblLabelURL.setText(nasaResponse.getHdurl().toString());
	    lblLabelTitle.setText(nasaResponse.getTitle());
	    textAreaEplanation.setText(nasaResponse.getExplanation());				
	}
	
	public void changer() {
		File file=new File("temp.jpg");
		file.setReadable(true, false);
		file.setExecutable(true, false);
		file.setWritable(true, false);
		String stringFile=file.getAbsolutePath();
		if(file!=null)
			User32.INSTANCE.SystemParametersInfo(0x0014, 0, stringFile, 1);	
	}
	public void changerWithFile(String file) {
		User32.INSTANCE.SystemParametersInfo(0x0014, 0, file, 1);	
	}

	
	
	
	
	
	
	/*
	//Eemplo con cáse anónima
	private void runThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(bucleCheckBox==true) {
					try {
						String stringSeconds=(String)comboBoxSeconds.getSelectedItem();
						int seconds=Integer.valueOf(stringSeconds)*100;
						Thread.sleep(seconds);
						NasaResponse nasaResponse=nasaRepository.getNasaResponse(-1);
						saveImage(nasaResponse);	
						llenarCampos(nasaResponse);
						if (file!=null)changer(file.getAbsolutePath());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		}).start();
	}
	*/
	
	/*
	//Ejemplo de trabajar con la librería JSON-simple
	private void workWtihJSONLibrary() {
		JSONParser parse=new JSONParser();
		JSONArray dataObject;
		try {
			dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
			JSONObject jsonObject=(JSONObject) dataObject.get(0);
			NasaResponse nasaRequest=(NasaResponse) jsonObject.get(0);
			String stringUrlImage=nasaRequest.getUrl().toString();
			saveImage(stringUrlImage);
		    lblLabelTitle.setText(nasaRequest.getTitle());
			file=new File("temp.jpg");
			System.out.println(stringUrlImage);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	*/
}
