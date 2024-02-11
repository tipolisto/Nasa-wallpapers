package wallpaper.data;



public class SQL {
	/***************************WALLPAPERS********************************************************************/
	//create table colors (id integer primary key autoincrement,name varchar(100),hexadecimal VARCHAR(100),msx VARCHAR(100),r INT(3),g INT(3),b INT(3));
	public static String createTableWallpaper="create table wallpapers ("+
												"id integer primary key autoincrement,"+
												"date varchar(100),"+
												"explanation TEXT,"+
												"hdurl varchar(100),,"+
												"media_type varchar(100),"+
												"service_version varchar(100),"+
												"title varchar(100),"+
												"url varchar(100)"+
											");";
	public static String deleteTableWallpaper="drop table wallpapers;";
	public static String insertWallpaper0="insert into colors values(0,'2022-10-10',"+
										"'iamgen de las mas bonitas',"+
										"'c:/windows/users/casa/desktop/bonitahd.png',"+
										"'image'"+
										"'v1'"+
										"'bonita'"+
										"'c:/windows/users/casa/desktop/bonita.png')";
	public static String insertWallpaper1="insert into colors values(0,'2022-11-050',"+
			"'Yo en la playa',"+
			"'c:/windows/users/casa/desktop/beachHD.png',"+
			"'image'"+
			"'v1'"+
			"'beach 2022'"+
			"'c:/windows/users/casa/desktop/beach.png')";
	
	public static String insertWallpaper2="insert into colors values(0,'2022-11-050',"+
			"'Yo en la montaña',"+
			"'c:/windows/users/casa/desktop/montainHD.png',"+
			"'image'"+
			"'v1'"+
			"'montai 2021'"+
			"'c:/windows/users/casa/desktop/montai.png')";
	
	/************************END WALLPAPERS********************************************************************/

	
}
