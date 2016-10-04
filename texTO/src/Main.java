

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class Main extends MIDlet implements CommandListener {

	/* 
	 * PDAP DEMO
	 */
    /* special string denotes upper directory */
    private static final String UP_DIRECTORY = "..";

    /* special string that denotes upper directory accessible by this browser.
     * this virtual directory contains all roots.
     */
    private static final String MEGA_ROOT = "/";

    /* separator string as defined by FC specification */
    private static final String SEP_STR = "/";

    /* separator character as defined by FC specification */
    private static final char SEP = '/';
    
    private String currDirName;
	
    private Image dirIcon;
    private Image fileIcon;
    private Image[] iconList;
    
    private Command view = new Command("View", Command.ITEM, 1);
    private Command creat = new Command("New", Command.ITEM, 2);

    //add delete file functionality
    private Command delete = new Command("Delete", Command.ITEM, 3);
    private Command exitbrowser = new Command("Exit", Command.EXIT, 3);
    
	//end PDAP DEMO
	
	//set tools
	public TextBox tb;
	public Command liscommand,
	               ok,exit,baru,buka,insert,setting,
	               backtb,hapustb,
	               oktf,back,
	               backt,
	               oksetting,backsetting,
	               okc,backc, //backt for tentang form
	               okfopen,backfopen, //fopen
	               okfproject,backfproject; //fproject
	public Command cproject;
	public Display dis;
	public TextField tf,tf2,fopendir,fopenfile,tfproject;
	public Form form,tentang,fchoice,fopen,fproject,fsetting,fsplash;
	public List lis;
	//Choice HTML,CSS,JS
	public ChoiceGroup coice,coicext,coiceopen,coicesetting;
	//LANGUAGE
	private String LANGUAGE[][] = {
			{// 0         1              2              3            4          5     6
			 "New File","Open File","Insert Script","Make Project","Setting","About","Exit", //all command
		//	     7              8           9           10
			 "Save File","Folder Default","File Name","Extension",  //main
		//	    11     12         13               14               15                          16
			 "Succes","Failed","Make new file.","Opened file.",",maybe file is nothing",",please try again.",  //alert
	    //     17            18
			 "Languages","Programming",
       //     19  
			 "Created by De\nVersion 1.0.0\n"+                  // about
						"Description:\ntextTO direct for learning Language HTML,CSS,JavaScript on Handphone "+
						"type OS Java.\nEmail : kinoe48@gmail.com.",
       //     20                        21
			"Description:","-css -> make default.\n-images -> make default."+
								"\n-js -> make default.",
	 //        22                                            23
			 "Please insert \"/\" in end name folder.","Name file is nothing",
			 //     24              25
			 "Make new Project.","Remove All"," already exists.\nDo you want to replace it?"
			},
			{
			  "File Baru","Buka File","Masukan Skrip","Buat Projek","Pengaturan","Tentang","Keluar",
			  "Simpan File","Folder Utama","Nama File","Ektensi",
			  "Berhasil","Gagal","Membuat file baru","File telah di buka",",mungkin file tidak ada",",mohon coba lagi.",
			  "Bahasa","Programming",
			  "Di buat oleh De\nVersion 1.0.0\n"+
						"Deskripsi:\ntextTO bertujuan untuk belajar Bahasa HTML,CSS,JavaScript di Handphone "+
						"bertipe OS Java.\nEmail : kinoe48@gmail.com.",
			  "Deskripsi:","-css -> dibuat secara default.\n-images -> dibuat secara default."+
			             "\n-js -> dibuat secara default.",
			   "Mohon masukan \"/\" di akhir nama folder.","Nama file tidak ada",
			   "Membuat Projek baru.","Hapus semua"," sudah ada.\nApa kamu ingin menempatkan kembali?"
			}
	};
	//END
	public int pilihan;// 0 = default indonesia
	
	//Data
	public Data data;
	public Skrip skrip;
	public String TITLE_APP = "texTO";
	String loc;
	
	public Main() {
		// TODO Auto-generated constructor stub 
		/*
		 *  source PDAP DEMO
		 */
		currDirName = MEGA_ROOT; 
		//end
		data = new Data(); // ini haru di call agar bisa menggunakan semua fungsi di Data class
		skrip = new Skrip();
		// en
		// id-ID
		loc = System.getProperty("microedition.locale");
		pilihan = 1;
		
		dis = Display.getDisplay(this);
		
		lis = new List(TITLE_APP,List.IMPLICIT);
		lis.append(LANGUAGE[pilihan][0], null);
		lis.append(LANGUAGE[pilihan][1], null);
		//lis.append(LANGUAGE[pilihan][2], null);
		lis.append(LANGUAGE[pilihan][3], null);
		lis.append(LANGUAGE[pilihan][4], null);
		lis.append(LANGUAGE[pilihan][5], null);
		lis.append(LANGUAGE[pilihan][6], null);
		liscommand = new Command("Ok",Command.ITEM,1);
		lis.setSelectCommand(liscommand);
		lis.setCommandListener(this);
		
		
		tb = new TextBox(TITLE_APP, null, 5000, TextField.ANY);
		tf2 = new TextField(LANGUAGE[pilihan][8]+" E:/","texTO/",100,TextField.ANY);
		tf = new TextField(LANGUAGE[pilihan][9],null,100,TextField.ANY);
		//choice ext
		coicext = new ChoiceGroup(LANGUAGE[pilihan][10],Choice.EXCLUSIVE);
		coicext.append("Default",null);coicext.append(".css",null);coicext.append(".html",null);
		coicext.append(".js", null);coicext.append(".txt", null);
		//end
		form = new Form(LANGUAGE[pilihan][7]);
		form.append(tf2);
		form.append(tf);
		form.append(coicext);
		//set Command on TextBox
		ok = new Command("Ok", Command.OK,1);
		
		hapustb = new Command(LANGUAGE[pilihan][25],Command.SCREEN,1);
		insert = new Command(LANGUAGE[pilihan][2],Command.SCREEN,2);
		
		backtb = new Command("Kembali",Command.SCREEN,3);
		//
		oktf = new Command("Ok",Command.OK,1);
		back = new Command("Kembali",null,Command.BACK,2);
		okc = new Command("Ok",null,Command.OK,1);
		backc = new Command("Kembali",null,Command.BACK,2);
		//add command to TextBox
		tb.addCommand(ok);
		tb.addCommand(backtb); tb.addCommand(hapustb); tb.addCommand(insert);
		tb.setCommandListener(this);
		//
		form.addCommand(oktf);
		form.addCommand(back);
		form.setCommandListener(this);
		//form setting
		fsetting = new Form(LANGUAGE[pilihan][4]);
		coicesetting = new ChoiceGroup(LANGUAGE[pilihan][17],Choice.EXCLUSIVE);
		//coicesetting.append("English", null);
		coicesetting.append("Indonesia", null);
		oksetting = new Command("Ok",Command.OK,1);backsetting = new Command("Kembali",Command.BACK,2);
		fsetting.append(coicesetting);
		fsetting.addCommand(oksetting);fsetting.addCommand(backsetting);
		fsetting.setCommandListener(this);
		//form about
		tentang = new Form(LANGUAGE[pilihan][5]);
		StringItem si = new StringItem(null,LANGUAGE[pilihan][19]);
		tentang.append(si);
		backt = new Command("Kembali",null,Command.BACK,1);
		tentang.addCommand(backt);
		tentang.setCommandListener(this);
		//fchoice
		fchoice = new Form(LANGUAGE[pilihan][17]);
		//choice group
		coice = new ChoiceGroup(LANGUAGE[pilihan][18],Choice.EXCLUSIVE);
		coice.append("HTML",null);coice.append("CSS", null);coice.append("JavaScript", null);
		//form ->choice
		fchoice.append(coice);
		fchoice.addCommand(okc);fchoice.addCommand(backc);
		fchoice.setCommandListener(this);
		//form ->open
		fopen = new Form(LANGUAGE[pilihan][1]);
		fopendir = new TextField(LANGUAGE[pilihan][8]+" =>E:/","texTO/",100,TextField.ANY);
		fopenfile = new TextField(LANGUAGE[pilihan][9],null,100,TextField.ANY);
		//choice group
		coiceopen = new ChoiceGroup(LANGUAGE[pilihan][10],Choice.EXCLUSIVE);
		coiceopen.append("Default",null);coiceopen.append(".css",null);coiceopen.append(".html",null);
		coiceopen.append(".js", null);coiceopen.append(".txt", null);
		//end
		fopen.append(fopendir);fopen.append(fopenfile);fopen.append(coiceopen);
		okfopen = new Command("Ok",Command.OK,1);
		backfopen = new Command("Kembali",Command.BACK,2);
		fopen.addCommand(okfopen);fopen.addCommand(backfopen);
		fopen.setCommandListener(this);
		//fproject
		fproject = new Form(LANGUAGE[pilihan][3]);
		tfproject = new TextField(LANGUAGE[pilihan][8]+" =>E:/texTO/",null,100,TextField.ANY);
		StringItem info = new StringItem(LANGUAGE[pilihan][20],LANGUAGE[pilihan][21]);
		okfproject = new Command("Ok",Command.OK,1); backfproject = new Command("Kembali",Command.BACK,2);
		fproject.addCommand(okfproject);fproject.addCommand(backfproject);
		fproject.append(tfproject);
		fproject.append(info);
		fproject.setCommandListener(this);
		//fsplash
		fsplash = new Form(TITLE_APP);
		Image img;
		try {
			img = Image.createImage("/textTO.png");
			ImageItem ii = new ImageItem(null,img,ImageItem.LAYOUT_CENTER|ImageItem.LAYOUT_VCENTER,"");
			fsplash.append(ii);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(LANGUAGE[pilihan][12],"Image tdk ada",null,AlertType.WARNING);
			dis.setCurrent(alert,fsplash);
		}
		
	}

	protected void startApp(){
		// TODO Auto-generated method stub
		dis.setCurrent(fsplash);
		try{
			Thread.sleep(3000);
			//dis.setCurrent(fsplash);
		}catch(Exception e){
			
		}
		dis.setCurrent(lis);
		//dis.setCurrent(tb);
	}
	
	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void destroyApp(boolean a) {
		// TODO Auto-generated method stub
		
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		/*
		if(c == exit){
			destroyApp(false);
			notifyDestroyed();
		}
		else
			*/
		if(c == liscommand){
			int idx = lis.getSelectedIndex();
			switch (idx){
			case 0:
				tb.setTitle(TITLE_APP);
				tf2.setString(TITLE_APP+"/");
				tb.setString(null);
				dis.setCurrent(tb); break;
			case 1: //open file
				showDirektory();break;
			case 2: //make new project
				dis.setCurrent(fproject); break;
			case 3: //setting
				dis.setCurrent(fsetting); break;
			case 4: //about
				dis.setCurrent(tentang);break;
			case 5: //exit
				destroyApp(false);
				notifyDestroyed();
			default:
				//dis.setCurrent(lis);
					
			}
		}
		else if(c == backtb){
			tb.setString(TITLE_APP);
			dis.setCurrent(lis);
		}
		else if(c == cproject){
			dis.setCurrent(fproject);
		}
		else if(c == insert){
			dis.setCurrent(fchoice);
		}
		else if(c == ok){
			String value = tb.getTitle();
			if(value == TITLE_APP){
				dis.setCurrent(form);
			}else{
				String value2 = new String(value);
				String dir = value2.substring(9);
				tf2.setString(dir);
				dis.setCurrent(form);
			}
		}
		else if(c == setting){
			dis.setCurrent(fsetting);
		}
		else if(c == baru){
			tb.setTitle(TITLE_APP);
			tf2.setString(TITLE_APP+"/");
			tb.setString(null);  //make new file
		}else if(c == buka){
			dis.setCurrent(fopen);
		}
		//command all from Form
		else if(c == oktf){  //save file
			// TODO Auto-generated catch block
			String dir = tf2.getString().toString(); //get name place dir
			String file = tf.getString().toString(); //get name file
			String isi = tb.getString().toString();  //get value/data from textbox
			//choice
			int index = coicext.getSelectedIndex();
			String ext = coicext.getString(index);
	//		choice
			byte value[] = isi.getBytes();
			int length = dir.length();
			char slash = 0;
			if(length>0){
				slash = dir.charAt(length-1);
			}else{
				slash = dir.charAt(length);
			}
			/*
			 * Alert untuk nama sama :
			 *   bla.bl.txt already exists.
			 *   Do you want to replace it?
			 */
			if(slash == '/'){
				boolean namefile = data.checkFile(dir,file,ext);
				if(namefile == true){
					Alert alert = new Alert(LANGUAGE[pilihan][11],LANGUAGE[pilihan][26],null,AlertType.INFO);
				}
				try{				
					data.createFile(dir,file,ext,value);
					Alert alert = new Alert(LANGUAGE[pilihan][11],LANGUAGE[pilihan][13],null,AlertType.INFO);
					dis.setCurrent(alert,tb);
				}catch(Exception e){
					e.printStackTrace(); //never show
					Alert alert = new Alert(LANGUAGE[pilihan][12],LANGUAGE[pilihan][13]+LANGUAGE[pilihan][16],null,AlertType.WARNING);
					dis.setCurrent(alert,tb);
				}
				
			}else{
				Alert alert = new Alert(LANGUAGE[pilihan][12],LANGUAGE[pilihan][22],null,AlertType.WARNING);
				dis.setCurrent(alert,form);
			}
		}
		else if(c == back){  //back at form txtfield
			dis.setCurrent(tb);
		}else if(c == hapustb){
			tb.setString(null);
		}
		else if(c == oksetting){ //fsetting
			int apa = coicesetting.getSelectedIndex();
			this.pilihan = apa;
			// en
			// id-ID
			//String loc = System.getProperty("microedition.locale");
		    //tb.setString("nilai = pilihan "+loc+" , = "+apa);
			dis.setCurrent(tb);
		}
		else if(c == backsetting){
			dis.setCurrent(lis);
		}
		else if(c == backt){//form tentang command
			dis.setCurrent(lis);
		}
		else if(c == okc){
			int idx = coice.getSelectedIndex();
			String ins = skrip.getLang(idx);
			tb.setString(ins);
			dis.setCurrent(tb);
		}else if(c == backc){
			dis.setCurrent(tb);
		}
		else if(c == okfopen){
			String dir = fopendir.getString().toString();
			String file = fopenfile.getString().toString();
			//choice group
			int index = coiceopen.getSelectedIndex();
			String ext = coiceopen.getString(index);
			//end
			InputStream is = null;
			FileConnection fc;
			//
			String LOCALHOST = "file:///";
			String HANDPHONE = "C:/";
			String MEMORY = "E:/";
			try{
				if(ext == "Default"){
					fc = (FileConnection)Connector.open(LOCALHOST+MEMORY+dir+file);
				}else{
					fc = (FileConnection)Connector.open(LOCALHOST+MEMORY+dir+file+ext);
				}
				//check nameFile is there or nothing
				if(fc.isDirectory() == true && fc.exists() == false){
					Alert alert = new Alert(LANGUAGE[pilihan][12],LANGUAGE[pilihan][23]+LANGUAGE[pilihan][16],null,AlertType.WARNING);
					dis.setCurrent(alert,tb);
				}else{
					is = fc.openInputStream();
				    byte[] value = new byte[5000]; // 5000 max textbox
				    int length = is.read(value);
				    if(length > 0){
				    	tb.setString(new String(value,0,length));
				    }
				    is.close();
				    fc.close();
				}
				Alert alert = new Alert(LANGUAGE[pilihan][11],LANGUAGE[pilihan][14],null,AlertType.INFO);
				//set title by dir
				tb.setTitle(TITLE_APP+" -> "+dir); //8
				dis.setCurrent(alert,tb);
			}catch(Exception e){
				Alert alert = new Alert(LANGUAGE[pilihan][12],LANGUAGE[pilihan][15]+LANGUAGE[pilihan][16],null,AlertType.WARNING);
				dis.setCurrent(alert,tb);
			}
		}
		else if(c == backfopen){
			dis.setCurrent(lis);
		}
		else if(c == okfproject){
			String dir = tfproject.getString().toString();
			try{
				data.createProject(dir);
				Alert alert = new Alert(LANGUAGE[pilihan][11],LANGUAGE[pilihan][24],null,AlertType.INFO);
				dis.setCurrent(alert,tb);
			}catch(Exception e){
				Alert alert = new Alert(LANGUAGE[pilihan][12],LANGUAGE[pilihan][24]+LANGUAGE[pilihan][16],null,AlertType.WARNING);
				dis.setCurrent(alert,tb);
			}
		}else if(c == backfproject){
			dis.setCurrent(lis);
		}
			//Display Browser and this all command on Browser
		else if(c == view){
			List curr = (List)d;
            final String currFile = curr.getString(curr.getSelectedIndex());
            
            new Thread(new Runnable() {
                    public void run() {
                        if (currFile.endsWith(SEP_STR) || currFile.equals(UP_DIRECTORY)) {
                            traverseDirectory(currFile);
                        } else {
                            // Show file contents
                           //showFile(currFile);
                        	try {
                        		FileConnection fc = (FileConnection)Connector.open("file://localhost/" + currDirName + currFile);
                        		if (!fc.exists()) {
                                    throw new IOException("File does not exists");
                                }
                        		InputStream is = fc.openInputStream();
            				    byte[] value = new byte[5000]; //5000 max textbox
            				    int length = is.read(value);
            				    if(length > 0){
            				    	tb.setString(new String(value,0,length));
            				    }
            				    is.close();
            				    fc.close();
            				    Alert alert = new Alert(LANGUAGE[pilihan][11],LANGUAGE[pilihan][14],null,AlertType.INFO);
            					tb.setTitle(TITLE_APP+" -> "+currDirName); 
            					dis.setCurrent(alert,tb);
            				    
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Alert alert = new Alert(LANGUAGE[pilihan][12],LANGUAGE[pilihan][15]+LANGUAGE[pilihan][16],null,AlertType.WARNING);
								dis.setCurrent(alert,tb);
							}
                        }
                    }
                }).start();
		}
		else if(c == delete){
			List curr = (List)d;
            String currFile = curr.getString(curr.getSelectedIndex());
            executeDelete(currFile);
		}
		else if(c == creat){
			dis.setCurrent(tb);
		}
		else if(c == exitbrowser){
			dis.setCurrent(lis);
		}
	}

	private void showDirektory() {
		// TODO Auto-generated method stub
		Enumeration e;
        FileConnection currDir = null;
        List browser;
        
        try {
            if (MEGA_ROOT.equals(currDirName)) {
                e = FileSystemRegistry.listRoots();
                browser = new List(currDirName, List.IMPLICIT);
            } else {
                currDir = (FileConnection)Connector.open("file://localhost/" + currDirName);
                e = currDir.list();
                browser = new List(currDirName, List.IMPLICIT);
                // not root - draw UP_DIRECTORY
                browser.append(UP_DIRECTORY, null);
            }

            while (e.hasMoreElements()) {
                String fileName = (String)e.nextElement();

                if (fileName.charAt(fileName.length() - 1) == SEP) {
                    // This is directory
                    browser.append(fileName, null);
                } else {
                    // this is regular file
                    browser.append(fileName, null);
                }
            }

            browser.setSelectCommand(view);

            //Do not allow creating files/directories beside root
            if (!MEGA_ROOT.equals(currDirName)) {
                //browser.addCommand(prop);
                browser.addCommand(creat);
                browser.addCommand(delete);
            }

            browser.addCommand(exitbrowser);

            browser.setCommandListener(this);

            if (currDir != null) {
                currDir.close();
            }

            Display.getDisplay(this).setCurrent(browser);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
		
	}
	
	void traverseDirectory(String fileName) {
        /* In case of directory just change the current directory
         * and show it
         */
        if (currDirName.equals(MEGA_ROOT)) {
            if (fileName.equals(UP_DIRECTORY)) {
                // can not go up from MEGA_ROOT
                return;
            }

            currDirName = fileName;
        } else if (fileName.equals(UP_DIRECTORY)) {
            // Go up one directory
            int i = currDirName.lastIndexOf(SEP, currDirName.length() - 2);

            if (i != -1) {
                currDirName = currDirName.substring(0, i + 1);
            } else {
                currDirName = MEGA_ROOT;
            }
        } else {
            currDirName = currDirName + fileName;
        }

        showDirektory();
    }
	
	private void executeDelete(String currFile) {
        final String file = currFile;
        new Thread(new Runnable() {
                public void run() {
                    delete(file);
                }
            }).start();
    }

	protected void delete(String file) {
		// TODO Auto-generated method stub
		try {
            FileConnection fc = (FileConnection)Connector.open("file:///" + currDirName + file);
            fc.delete();
        } catch (Exception e) {
            Alert alert =
                new Alert("Error!",
                    "Can not access/delete file " + file + " in directory " + currDirName +
                    "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            Display.getDisplay(this).setCurrent(alert);
        }
	}

}
