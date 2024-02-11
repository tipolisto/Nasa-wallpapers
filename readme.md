# ¿Que es NASA wallpapers? / What is NASA wallpapers?

Es un programa que se descarga las últimas imágenes de la nasa y la va mostrando en el fondo de tu escritorio.

También puedes manejar una base de datos con tus wallpaperes preferidos y seleccionar algunas fotos con los botones de cambios rápidos.

-------------------------------------

It is a program that downloads the latest NASA images and displays them on your desktop background.

You can also manage a database with your favorite wallpapers and select some photos with the quick change buttons.

# Instalación / installation

Puedes descargar el programa pinchando aquí:

You can download the program by clicking here:

https://github.com/kikemadrigal/Java-desktop-nasa-wallpapers/releases/download/v1.0.0/Nasa-wallpapers.exe






haz doble click sobre el ejecutable, el programa se ocultará e la parte inferior derecha del escritorio:

double click on the executable, the program will be hidden in the lower right part of the desktop:

<img src=docs/2.png width=300px />

Pincha con el botón derecho para ver el menú

Right-click to view the menu

<img src=docs/3.png width=300px />

Pincha en maximizar y ya está:

Click on maximize and that's it:

<img src=docs/4.PNG width=300px />

# Developers

Utiliza la librería https://mvnrepository.com/artifact/net.java.dev.jna/jna que proporciona un facil acceso a las librerías compartidas del sistema operativo.

Para compilar el proyecto utiliza maven:

1. En eclipse, abre tu programa desde File->Import->Existing Maven Projects y selecciona el archivo pong.xml.
2. Para solo compilar el proyecto y ejecutar, pincha botón derecho sobre la carpeta del proyecto->Run as->Maven build... y escribe en Goals: compile exec:java -Dexec.mainClass="wallpaper.Main" -Djava.io.tmpdir=C:\\javatmpdir
   
-Dexec.mainClass="wallpaper.Main" es para decirle donde está la clase principal

-Djava.io.tmpdir=C:\\javatmpdir es para cambiar el directorio temporal que utiliza windows por defecto para la librería y que si no lo cambias te da error por falta de permisos.

Pincha en Run.

Recuerda que esto es posible por la configuración que le hemos puesto en el archivo pong.xml

<img src=docs/5.PNG width=400px />

1. Para crear el jar, utiliza el comando mvn package.

<img src=docs/6.PNG width=400px />

4. Para crear el .exe de windows necesitas el launch4java ( https://sourceforge.net/projects/launch4j/files/launch4j-3/3.50/ ), un icono, un archivo manifest(en realidad es un archivo de texto con xml):


   1. .[ !IMPORTANT] Para que te pida ejecutar cmo administrador al ejecutar el programa, crea un archivo llamado "Nasa-wallpapers.manifest" (lo tienes en la carpeta assets), con este contenido:

    ```
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <assembly xmlns="urn:schemas-microsoft-com:asm.v1" manifestVersion="1.0">
    <trustInfo xmlns="urn:schemas-microsoft-com:asm.v3">
    <security>
    <requestedPrivileges>
    <requestedExecutionLevel level="highestAvailable"   uiAccess="False" />
    </requestedPrivileges>
    </security>
    </trustInfo>
    </assembly>
    ```
    <img src=docs/6.PNG width=300px />

    2. Instala y abre laun4java y escribe lo siguiente:
    
    <img src=docs/7.PNG width=300px />

    <img src=docs/8.PNG width=300px />
    
    Al pichar el el engraje, te sale una ventana, elige su lugar de destino y ponle un nombre al archivo:
    
    Es una copia de respaldo de la configuración para que puedas abrirla desde Open configuration.

    <img src=docs/9.PNG width=300px />

    y ya:

    <img src=docs/10.PNG width=300px />

    




