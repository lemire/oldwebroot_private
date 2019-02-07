
//Title:       JavaSci
//Version:	0.5
//Copyright:   Copyright (c) 1998
//Author:      Daniel Lemire
//Company:     Institut de génie biomédical
//Description:
package Applet.morse;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
//import com.sun.java.swing.*;
//import java.lang.Runnable;

//import com.sun.java.swing.UIManager;
public class Morse extends Applet implements Runnable {
  boolean isStandalone = false;
  String forecolor;
  String background;
  String soundname;
  int delaimot;
  int delailigne;
  int delaipage;
  int lignes;
  boolean center;
  String[] ligne=new String[16];
  //Font police;
  int w;
  FontMetrics fm;
  Thread machine, triangle;
  String[] lignesString;
//Get a parameter value
  int dist;
  AudioClip clip;

  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet

  public Morse() {
  }
//Initialize the applet

  public void init() {
    try { forecolor = this.getParameter("Couleur du Texte", "#00FF00"); } catch (Exception e) { e.printStackTrace(); }
    try { background = this.getParameter("Couleur du fond", "#000000"); } catch (Exception e) { e.printStackTrace(); }
    try { soundname = this.getParameter("son", ""); } catch (Exception e) { e.printStackTrace(); }
    try { delaimot = Integer.parseInt(this.getParameter("delai-mot", "100")); } catch (Exception e) { e.printStackTrace(); }
    try { delailigne = Integer.parseInt(this.getParameter("delai-ligne", "500")); } catch (Exception e) { e.printStackTrace(); }
    try { delaipage = Integer.parseInt(this.getParameter("delai-page", "1000")); } catch (Exception e) { e.printStackTrace(); }
    try { lignes = Integer.parseInt(this.getParameter("lignes", "4")); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[0] = this.getParameter("ligne1", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[1] = this.getParameter("ligne2", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[2] = this.getParameter("ligne3", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[3] = this.getParameter("ligne4", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[4] = this.getParameter("ligne5", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[5] = this.getParameter("ligne6", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[6] = this.getParameter("ligne7", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[7] = this.getParameter("ligne8", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[8] = this.getParameter("ligne9", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[9] = this.getParameter("ligne10", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[10] = this.getParameter("ligne11", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[11] = this.getParameter("ligne12", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[12] = this.getParameter("ligne13", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[13] = this.getParameter("ligne14", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[14] = this.getParameter("ligne15", ""); } catch (Exception e) { e.printStackTrace(); }
    try { ligne[15] = this.getParameter("ligne16", ""); } catch (Exception e) { e.printStackTrace(); }
    int r=Integer.valueOf(forecolor.substring(1,3),16).intValue();
    int g=Integer.valueOf(forecolor.substring(3,5),16).intValue();
    int b=Integer.valueOf(forecolor.substring(5,7),16).intValue();
//    System.out.println(r);
//    System.out.println(g);
//    System.out.println(b);
    this.setForeground(new Color(r,g,b));
    r=Integer.valueOf(background.substring(1,3),16).intValue();
    g=Integer.valueOf(background.substring(3,5),16).intValue();
    b=Integer.valueOf(background.substring(5,7),16).intValue();
    this.setBackground(new Color(r,g,b));
   // try {
    //jbInit();
    //}
    //catch (Exception e) {
    //e.printStackTrace();
    //}
    //police=Font.getFont("Monospaced");
    //this.setFont(police);
    fm=Toolkit.getDefaultToolkit().getFontMetrics(this.getFont());
    int haut=this.getSize().height;
    double distdouble=Math.round((haut)/((double) lignes)-(double)fm.getHeight());
    dist=new Double(distdouble).intValue();
	/*************************
	* on suppose que l'applet garde
	* toujours la même dimension
	***************************/
	//clip.loop();
	clip=getAudioClip(getCodeBase(), soundname);
	center=true;
  }

  //static {
  //  try {
  //    //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.metal.MetalLookAndFeel());
  //    //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.motif.MotifLookAndFeel());
  //    UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
  //  }
  //  catch (Exception e) {}
  //}
//Component initialization

//  private void jbInit() throws Exception {
   // this.setSize(400,300);
 // }
//Start the applet

  public void start() {
	triangle=new Thread(this);
	triangle.start();
    machine=new Thread(this);
    machine.start();
  }
//Stop the applet

  public void stop() {
	triangle.stop();
	triangle=null;
    machine.stop();
    machine=null;
  }
//Destroy the applet

  public void destroy() {
	  clip=null;
  }
//Get Applet information

  public void paint(Graphics g) {
    if(lignesString!=null) {
		if(center) {
  		    for(int k=0;k<lignesString.length;k++) {
  		      g.drawString(lignesString[k],new Double(Math.round((this.getSize().width-fm.stringWidth(lignesString[k]))/(double)2)).intValue(),(dist+fm.getHeight())*k+fm.getHeight());
  		    }

		} else {
  		    for(int k=0;k<lignesString.length;k++) {
  		      g.drawString(lignesString[k],0,(dist+fm.getHeight())*k+fm.getHeight());
  		    }
  		}
    }

  }

  public String getAppletInfo() {
    return "(c) 1998 Daniel Lemire version 0.5";
  }
//Get parameter info

  public String[][] getParameterInfo() {
    String pinfo[][] =
    {
      {"Couleur du Texte", "String", "Couleur du texte"},
      {"Couleur du fond", "String", "Couleur du fond de l'écran"},
      {"son", "String", "Nom de fichier de son"},
      {"delai-mot", "int", "délai entre chaque lettres"},
      {"delai-ligne", "int", "délai entre chaque ligne"},
      {"delai-page", "int", "délai entre chaque page"},
      {"lignes", "int", "nombre de lignes par page"},
      {"ligne1", "String", "Contenu de la ligne 1"},
      {"ligne2", "String", "Contenu de la ligne 2"},
      {"ligne3", "String", "Contenu de la ligne 3"},
      {"ligne4", "String", "Contenu de la ligne 4"},
      {"ligne5", "String", "Contenu de la ligne 5"},
      {"ligne6", "String", "Contenu de la ligne 6"},
      {"ligne7", "String", "Contenu de la ligne 7"},
      {"ligne8", "String", "Contenu de la ligne 8"},
      {"ligne9", "String", "Contenu de la ligne 9"},
      {"ligne10", "String", "Contenu de la ligne 10"},
      {"ligne11", "String", "Contenu de la ligne 11"},
      {"ligne12", "String", "Contenu de la ligne 12"},
      {"ligne13", "String", "Contenu de la ligne 13"},
      {"ligne14", "String", "Contenu de la ligne 14"},
      {"ligne15", "String", "Contenu de la ligne 15"},
      {"ligne16", "String", "Contenu de la ligne 16"},
    };
    return pinfo;
  }

  public void run() {
    Thread me=Thread.currentThread();
	while (me==machine) {
      me.setPriority(Thread.MAX_PRIORITY);
      lignesString=new String[lignes];
      for(int page=0;page<16/(double)lignes;page++) {
        for(int k=0;k<lignes;k++) {
          lignesString[k]=new String();
        }
        for(int k=0;k<lignes;k++) {
          for(int l=0;l<ligne[k+page*lignes].length();l++) {
			  if (l<ligne[k+page*lignes].length()-1) {
		  	  	synchronized(this) {
              		lignesString[k]=ligne[k+page*lignes].substring(0,l+1).concat("_");
              		repaint();
		  	  	}
		  	  }
			  if((clip!=null)&&(!ligne[k+page*lignes].substring(l,l+1).equals(" "))) {
				  	synchronized(this) {
						clip.stop();
						clip.play();
					}
		      }
			  try {
			    Thread.currentThread().sleep(delaimot);
			  } catch(InterruptedException e) {}
			  synchronized(this) {
              	lignesString[k]=ligne[k+page*lignes].substring(0,l+1);
              	repaint();
		  	  }
          }
          try {
            Thread.currentThread().sleep(delailigne);
          } catch(InterruptedException e) {}
        }
		System.gc();
        try {
          Thread.currentThread().sleep(delaipage);
        } catch(InterruptedException e) {}
      }
    }
    //TODO: implement this java.lang.Runnable method;
  }
}

