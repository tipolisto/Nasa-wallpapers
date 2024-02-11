package wallpaper;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;



import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;

import java.net.URI;
import java.time.LocalDateTime;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;

import wallpaper.data.NasaRepository;
import wallpaper.data.NasaResponse;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.Box;
import javax.swing.Icon;

public class NasaWallpaperFrame extends JFrame {

	private NasaRepository nasaRepository;
	private JPanel contentPane;
	private File files[];
	private File file;
	private int index=-1;
	private LocalDateTime localDateTime ;
	private ThreadChanger threadChanger;
	
	private JLabel lblLabelImage,lblLabelTitle,lblLabelDate,lblLabelURL;
	private JTextArea textAreaEplanation;
	private JButton btnSetOnDesktop;
	private JCheckBox chckbxAutomaticChange,chckbxRepeatLast;
	private JComboBox comboBoxSeconds,comboBoxRepeatLast;
	
	private PopupMenu popup;
	private TrayIcon ticono;
	private MenuItem item1,item2, item3;
	private SystemTray st;
	private JLabel lblNewLabel_1;




	
	public NasaWallpaperFrame() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 744, 689);
	    ImageIcon imageIcon=new ImageIcon(getClass().getResource("/galaxia.png"));
	    setIconImage(imageIcon.getImage());

	    setResizable(false);
	    setTitle("Nasa wallpapers");
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		//contentPane.setOpaque(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.setBackground(new Color(252,199,253));
	    /*
		ImageIcon image=new ImageIcon(getClass().getResource("/background.png"));
		JLabel backgroundLabel=new JLabel("",image,JLabel.CENTER);
		backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(backgroundLabel);
		*/
		initComponents();
		
		localDateTime = LocalDateTime.now();
	    nasaRepository=new NasaRepository(localDateTime);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	           int confirm=JOptionPane.showConfirmDialog(null, "Minimize? you can exit by clicking on exit on the icon");
	            if(confirm==0) {
	            	setVisible(false);
					try {
						st.add(ticono);
					}catch(AWTException ex) {}
	            }
	        }
	    });
	    
		
		threadChanger=new ThreadChanger(nasaRepository, chckbxAutomaticChange, chckbxRepeatLast, 
										comboBoxSeconds, comboBoxRepeatLast,
										lblLabelImage,lblLabelTitle,lblLabelDate,lblLabelURL,
										textAreaEplanation);
		

		threadChanger.start();

	}
	
	
	
	
	
	private void initComponents(){
		//creando el popup de la imagen
		if(SystemTray.isSupported()) {
			st=SystemTray.getSystemTray();
			Image image=new ImageIcon(getClass().getResource("/galaxia.png")).getImage();
			popup=new PopupMenu();
			item1=new MenuItem("Salir");
			item2=new MenuItem("Maximizar");
			popup.add(item1);
			popup.add(item2);
			ticono=new TrayIcon(image,"Nasa wallpapers",popup);
			
			//Salir
			item1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int confirm=JOptionPane.showConfirmDialog(null, "Exit? ");
					if(confirm==0)System.exit(0);
				}
			});
			//Maximizar
			item2.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					toFront();
					st.remove(ticono);
				}
			});
			
			//Mensaje del icono
			ticono.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ticono.displayMessage("Nasa wallpapers", "Hello", TrayIcon.MessageType.INFO);
				}
			});

			ticono.setImageAutoSize(true);
			try {
				st.add(ticono);
			}catch(AWTException ex) {}
		}
		
		JButton btnBrowseFiles = new JButton("Browse files");
		btnBrowseFiles.setBackground(Color.WHITE);
		btnBrowseFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
				jFileChooser.setDialogTitle("Open file");
				jFileChooser.setMultiSelectionEnabled(true);
				jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result=jFileChooser.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION) {
					files=jFileChooser.getSelectedFiles();
				}
			}
		});
		btnBrowseFiles.setBounds(20, 35, 275, 23);
		contentPane.add(btnBrowseFiles);
		
		JButton btnChangeWallpaper = new JButton("Change wallpaper");
		btnChangeWallpaper.setBackground(Color.WHITE);
		btnChangeWallpaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(files!=null) {
					if (files.length>=0) {
						try {
							File file=files[++index];
							threadChanger.changerWithFile(file.getAbsolutePath());
							//BufferedImage bufferedImage = ImageIO.read(new File("temp.jpg"));
							BufferedImage bufferedImage = ImageIO.read(file);
							Image img= bufferedImage.getScaledInstance(400, 200, Image.SCALE_DEFAULT);
							lblLabelImage.setIcon(new ImageIcon(img));
							lblLabelDate.setText("");
							lblLabelTitle.setText(file.getName());
							lblLabelURL.setText("");
							textAreaEplanation.setText("");
						}catch (Exception ex) {
							index=-1;
							JOptionPane.showMessageDialog(null, "Press again");
						}
					}else {
						JOptionPane.showMessageDialog(null, "No files selected");
					}
				}else {
					JOptionPane.showMessageDialog(null, "No files selected");
				}
			}

			
		});
		btnChangeWallpaper.setBounds(20, 80, 275, 23);
		btnChangeWallpaper.setBackground(Color.WHITE);
		contentPane.add(btnChangeWallpaper);
		
		JButton btnGetNasaWallpaper = new JButton("Get nasa Wallpaper");
		btnGetNasaWallpaper.setBackground(Color.WHITE);
		btnGetNasaWallpaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxAutomaticChange.isSelected()) {
					JOptionPane.showMessageDialog(null, "Disable autoplay");
				}else {
					try {
						NasaResponse nasaResponse=nasaRepository.getNasaResponse(-1,0);
						threadChanger.createFileImage(nasaResponse);
						threadChanger.fillFields(nasaResponse);
					}catch(IOException ex) {
						JOptionPane.showMessageDialog(null, "An error has occurred:\n"+ex.toString());
					}
				}
			}
		});
		btnGetNasaWallpaper.setBounds(20, 369, 275, 23);
		contentPane.add(btnGetNasaWallpaper);
		
		lblLabelImage = new JLabel("");
		lblLabelImage.setBounds(317, 64, 401, 200);
		lblLabelImage.setIcon(new ImageIcon(getClass().getResource("/no-image.png")));
		contentPane.add(lblLabelImage);
		
		lblLabelTitle = new JLabel("");
		lblLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLabelTitle.setBounds(317, 11, 401, 39);
		contentPane.add(lblLabelTitle);
		
		JButton btnPreviusNasa = new JButton(new ImageIcon(getClass().getResource("/left-jet.png")));
		btnPreviusNasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					NasaResponse nasaResponse=nasaRepository.getNasaResponse(-1,0);
					if(nasaResponse!=null && nasaResponse.getMedia_type().equals("image")) {
						threadChanger.createFileImage(nasaResponse);
						threadChanger.fillFields(nasaResponse);
					}else {
						JOptionPane.showMessageDialog(null, "Click again");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred:\n"+e1.toString());
				}
			}
		});
		btnPreviusNasa.setBounds(20, 416, 56, 23);
		contentPane.add(btnPreviusNasa);
		
		JButton btnTodayNasa = new JButton(new ImageIcon(getClass().getResource("/start.png")));
		btnTodayNasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					NasaResponse nasaResponse=nasaRepository.getNasaResponse(0,0);
					threadChanger.createFileImage(nasaResponse);
					threadChanger.fillFields(nasaResponse);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred:\n"+e1.toString());
				}
			}
		});
		btnTodayNasa.setBounds(125, 416, 57, 23);
		contentPane.add(btnTodayNasa);
		
		JButton btnNextNasa = new JButton(new ImageIcon(getClass().getResource("/right-jet.png")));
		btnNextNasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					NasaResponse nasaResponse=nasaRepository.getNasaResponse(1,0);
					if(nasaResponse!=null && nasaResponse.getMedia_type().equals("image")) {
						threadChanger.createFileImage(nasaResponse);
						threadChanger.fillFields(nasaResponse);
					}else {
						JOptionPane.showMessageDialog(null, "Click again");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error has occurred:\n"+e1.toString());
				}
			}
		});
		btnNextNasa.setBounds(237, 416, 58, 23);
		contentPane.add(btnNextNasa);
		
		btnSetOnDesktop = new JButton("Set on desktop");
		btnSetOnDesktop.setBackground(Color.WHITE);
		btnSetOnDesktop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				threadChanger.changer();
			}
		});
		btnSetOnDesktop.setBounds(20, 463, 275, 23);
		contentPane.add(btnSetOnDesktop);
		JScrollPane jscrollPaneTextArea=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(jscrollPaneTextArea);
		jscrollPaneTextArea.setBounds(317, 329, 401, 310);
		
		textAreaEplanation = new JTextArea();
		jscrollPaneTextArea.setViewportView(textAreaEplanation);
		textAreaEplanation.setLineWrap(true);
		textAreaEplanation.setEditable(false);	
		
		lblLabelDate = new JLabel("");
		lblLabelDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelDate.setBounds(332, 275, 386, 14);
		contentPane.add(lblLabelDate);
		
		lblLabelURL = new JLabel("");
		lblLabelURL.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelURL.setBounds(332, 300, 386, 14);
		lblLabelURL.setForeground(Color.BLUE.darker());
		lblLabelURL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLabelURL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    try {
			        Desktop.getDesktop().browse(new URI(lblLabelURL.getText().toString()));
			    } catch (Exception e1) {
			        e1.printStackTrace();
			    }
			}
		});
		contentPane.add(lblLabelURL);
		
		
		
		chckbxRepeatLast = new JCheckBox("Repeat last network");
		chckbxRepeatLast.setBounds(20, 586, 275, 23);
		chckbxRepeatLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chckbxRepeatLast.isSelected())localDateTime=localDateTime.now(); 		
			}
		});	
		contentPane.add(chckbxRepeatLast);
		
		comboBoxRepeatLast = new JComboBox();
		comboBoxRepeatLast.setBounds(20, 616, 275, 23);
		comboBoxRepeatLast.addItem("10");
		comboBoxRepeatLast.addItem("20");
		comboBoxRepeatLast.addItem("50");
		comboBoxRepeatLast.addItem("100");
		comboBoxRepeatLast.addItem("1000");
		contentPane.add(comboBoxRepeatLast);
		

		
		comboBoxSeconds = new JComboBox();
		comboBoxSeconds.setBounds(20, 539, 275, 23);
		comboBoxSeconds.addItem("100");
		comboBoxSeconds.addItem("1000");
		comboBoxSeconds.addItem("10000");
		contentPane.add(comboBoxSeconds);
		
		
		chckbxAutomaticChange = new JCheckBox("Automatic change network wallpaper");
		chckbxAutomaticChange.setBounds(20, 507, 275, 23);
		chckbxAutomaticChange.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if(chckbxAutomaticChange.isSelected()) {
							threadChanger=new ThreadChanger(nasaRepository, chckbxAutomaticChange, chckbxRepeatLast, 
									comboBoxSeconds, comboBoxRepeatLast,
									lblLabelImage,lblLabelTitle,lblLabelDate,lblLabelURL,
									textAreaEplanation);
							threadChanger.start();
						}else {
							try {
								threadChanger.join();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}	
					}
				}).start();	
			}
		});
		chckbxAutomaticChange.setSelected(true);
		contentPane.add(chckbxAutomaticChange);
		
		
		/***
		 * DATABASE
		 */
		JLabel lblNewLabel = new JLabel("Quick changes");
		lblNewLabel.setBounds(20, 10, 275, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDatabaseManager = new JLabel("Database manager");
		lblDatabaseManager.setBounds(20, 114, 275, 14);
		contentPane.add(lblDatabaseManager);
		
		JButton btnDatabaseManager = new JButton("Database manager");
		btnDatabaseManager.setBackground(Color.WHITE);
		btnDatabaseManager.setBounds(20, 140, 275, 23);
		contentPane.add(btnDatabaseManager);
		btnDatabaseManager.setEnabled(false);
		
		lblNewLabel_1 = new JLabel("Network");
		lblNewLabel_1.setBounds(20, 335, 275, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnPreviusDataBase = new JButton((Icon) null);
		btnPreviusDataBase.setBounds(20, 174, 56, 23);
		btnPreviusDataBase.setEnabled(false);
		contentPane.add(btnPreviusDataBase);
		
		JButton btnTodayDatabase = new JButton((Icon) null);
		btnTodayDatabase.setBounds(125, 174, 57, 23);
		btnTodayDatabase.setEnabled(false);
		contentPane.add(btnTodayDatabase);
		
		JButton btnNextDataBase = new JButton((Icon) null);
		btnNextDataBase.setBounds(237, 174, 58, 23);
		btnNextDataBase.setEnabled(false);
		contentPane.add(btnNextDataBase);
	}
}
