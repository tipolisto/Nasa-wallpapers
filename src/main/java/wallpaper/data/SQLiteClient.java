package wallpaper.data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;



public class SQLiteClient {
	private String dbName;
	private String user;
	private String password;
	private float version;
	private Connection connection;
	private float VERSION_SQLITE=2.0f;

	public SQLiteClient() {
		this.version=version;

		//File fileDB=new File("target\\tools\\sqlite\\msxtools.db");
		File fileDB=new File("wallpapers.db");
		if(fileDB.length()==0)VERSION_SQLITE=1.0f;
		/*URL resource = getClass().getClassLoader().getResource("MSXTools.db");
		URI path=null;
		File fileDB=null;
		try {
			path = resource.toURI();
			fileDB = new File(resource.toURI());			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//String url="jdbc:sqlite:MSXTools.db";
		String url="jdbc:sqlite:"+fileDB.getAbsolutePath();

		try {	
			Class.forName("org.sqlite.JDBC");
			connection=DriverManager.getConnection(url);

			if(VERSION_SQLITE==1.0f) {
				Statement statement=connection.createStatement();
				statement.addBatch(SQL.createTableWallpaper);


				statement.addBatch(SQL.insertWallpaper0);
				statement.addBatch(SQL.insertWallpaper1);
				statement.addBatch(SQL.insertWallpaper2);
				
				statement.executeBatch();
			}
		} catch (  Exception ex) {
			//System.out.println("No se pudo conectar a la base de datos: "+fileDB.getAbsolutePath());
			System.out.println(ex.getMessage().toString());
			JOptionPane.showMessageDialog(null, "Not found database");
		}
	}
	
	public void SQLiteClose() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public float getVersion() {
		return this.version;
	}

	/********************************COLORS*********************************/
	/*public ColorPalette getColor(int idColor) {
		ColorPalette colorPalette=null;
		try {
			Statement statementSelectAllFromPalettes=connection.createStatement();
			//select * from colors where id='2';
			ResultSet resulset=statementSelectAllFromPalettes.executeQuery("select * from colors where id='"+idColor+"';");

			while(resulset.next()) {
				//(int id, String name, String hexadecimal, String msx, int r, int g, int b) 
				colorPalette=new ColorPalette(resulset.getInt("id"),resulset.getString("name"),resulset.getInt("r"),resulset.getInt("g"),resulset.getInt("b"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return colorPalette;
	}
	
	public void insertColor(ColorPalette color) {
		Statement statementInsertColor;
		try {
			statementInsertColor = connection.createStatement();
			statementInsertColor.executeUpdate("insert into colors values(null,'"+color.getName()+"',"+color.getR()+","+color.getG()+","+color.getB()+");");
			//System.out.println("resulset "+resulset.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getLastIDInsertColor() {
		int lastId=0;
		 try {
			 	Statement statementLastId = connection.createStatement();
				ResultSet resulset=statementLastId.executeQuery("SELECT max(id) AS max_id FROM colors LIMIT 1;");
				//System.out.println(resulset.toString());
				while(resulset.next()) {
					lastId=resulset.getInt("max_id");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return lastId;
	}
	
	public void updateColor(ColorPalette colorPalette) {
		
			try {
				Statement statementUpdatePalette=connection.createStatement();
				//System.out.println("Actualizado color : "+colorPalette.getId()+" r "+colorPalette.getR()+" g "+colorPalette.getG()+" b "+colorPalette.getB());
				statementUpdatePalette.executeUpdate("update colors set r="+colorPalette.getR()+", g="+colorPalette.getG()+", b="+colorPalette.getB()+" where id="+colorPalette.getId()+";");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}*/
}
