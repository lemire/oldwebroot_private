/////////////////////////////////////////////////////////////
//Title:       	Vague2
//Version:     	0.4
//Copyright:   	Copyright (c) 1998
//Author:      	Daniel Lemire
//				docteur en mathématiques de l'ingénieur
//				lemire@techelements.com
/////////////////////////////////////////////////////////////
//
// Vous pouvez utiliser librement cette applet sans royalties,
// mais pour les applications commerciales, j'exige qu'on demande
// mon accord au préalable.
//
// Vous pouvez modifier le code à loisir, mais vous êtes tenu
// de me retransmettre le code amélioré.
//
// Vous ne pouvez pas retirer cette note du code, même si
// vous le modifiez.
//
// Merci!
//
//////////////////////////////////////////////////////////////
// http://cafe.rapidus.net/danilemi/
// http://www.techelements.com/
// http://www.techelements.com/lemire/
//////////////////////////////////////////////////////////////

package Applet.image;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.image.*;
import java.net.*;
import java.lang.Runnable;
import java.io.*;

public class vague2 extends Applet implements Runnable {

	Image originalImage, topImage, bottomImage, offscreen;
	MemoryImageSource topmis, bottommis;
	int[][] sineArray;
	int imw, imh;
	int[] toppix, bottompix, textpix;
	Thread animator;
	int delai;
	String imagename;
	double amplitude, vitesse;
	final static int bound=5;
	int alphabound;
	Graphics offg;
	Thread Animator;
	boolean isStandalone=true;
	MediaTracker tracker;
	String javaver, javavendor, javavendorurl;
	boolean animating;
	int fontheight;

  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

	public void update(Graphics g) {
	    paint(g);
	}

  public void init() {
  	super.init();
  	javaver=System.getProperty("java.version");
    System.out.println("Java version "+javaver+" détectée");
   // System.out.println(System.getProperty("java.class.path"));
	//System.out.println(System.getProperty("java.class.path"));
	setLayout(null);
	addNotify();
	animating=false;
    javavendor=System.getProperty("java.vendor");
    javavendorurl=System.getProperty("java.vendor.url");
	isStandalone=false;
	tracker=new MediaTracker(this);
	imw=getSize().width;
	imh=(int) Math.round(getSize().height/2.0);
	offscreen=createImage(imw,imh*2);
    try { imagename = this.getParameter("image", ""); } catch (Exception e) { e.printStackTrace(); }
    try { vitesse = Double.valueOf(this.getParameter("vitesse", "0.25")).doubleValue() ; } catch (Exception e) { e.printStackTrace(); }
    try { amplitude = Double.valueOf(this.getParameter("amplitude", "5.0")).doubleValue() ; } catch (Exception e) { e.printStackTrace(); }
    try { delai = Integer.valueOf(this.getParameter("delai", "100")).intValue() ; } catch (Exception e) { e.printStackTrace(); }
	//originalImage=getImage(getDocumentBase(),imagename);
	//originalImage=getImage(imagename);
 	try {

		URL url=new URL(getCodeBase(),imagename);
		System.out.println(url.toString());
    //originalImage=this.getToolkit().prepareImage(        (url);


    try {
    InputStream is=url.openStream();
    if(is==null)
      System.err.println("Je ne trouve pas l'image "+imagename);
    ByteArrayOutputStream baos=new ByteArrayOutputStream();

      int c;
      while((c=is.read())>=0)
        baos.write(c);
      originalImage=getToolkit().createImage(baos.toByteArray());
    } catch(IOException e) {
      e.printStackTrace();
    }
	//	originalImage=this.getImage(this.getDocumentBase(),imagename);
		System.out.println("Image en cours de chargement...");
	} catch (MalformedURLException e) {
	 	e.printStackTrace();
	}
	if(originalImage==null)
		System.err.println("Image est nulle!!! ;-)");



	alphabound=(int) Math.round(bound/vitesse);
	sineArray=new int[2*alphabound][imh];
	for(int alpha=-alphabound;alpha<alphabound;alpha+=1) {
		try {
		for(int l=0;/*l<imh*/;l++) {
			sineArray[alpha+alphabound][l]=(int) Math.round(l+amplitude*(Math.cos(Math.PI*(alpha*vitesse+(imh-1-l)*(imh-1-l)/(4*(double)(imh-1))))));
			if(sineArray[alpha+alphabound][l]<0)
				sineArray[alpha+alphabound][l]=0;
			else if(sineArray[alpha+alphabound][l]>imh-1)
				sineArray[alpha+alphabound][l]=imh-1;
			sineArray[alpha+alphabound][l]=(imh-sineArray[alpha+alphabound][l]-1)*imw;
		}
		} catch (IndexOutOfBoundsException iobe) {}
	}
	toppix=new int[imw*imh];
	bottompix=new int[imw*imh];
	topmis=new MemoryImageSource(imw, imh, toppix, 0, imw);
	topmis.setAnimated(true);
	bottommis=new MemoryImageSource(imw, imh, bottompix, 0, imw);
	bottommis.setAnimated(true);
	FontMetrics fm=this.getFontMetrics(this.getFont());
	fontheight=fm.getHeight();

  }
	public void start() {
		Animator=new Thread(this);
		Animator.start();
	}
	public void stop() {
		Animator.interrupt();
		Animator=null;
	}
	public void paint(Graphics g) {
		try {
			if(animating==false) {
				offg=offscreen.getGraphics();
				offg.drawString("Lecture de l'image en cours...",5,fontheight);
				offg.drawString("Si rien ne se passe en moins ",5,2*fontheight);
				offg.drawString("de 5 secondes, votre fureteur",5,3*fontheight);
				offg.drawString("n'est pas compatible.",5,4*fontheight);
				offg.drawString("java version "+javaver+" détecté",5,5*fontheight);
				offg.drawString(javavendor,5,6*fontheight);
				offg.drawString(javavendorurl,5,7*fontheight);
				g.drawImage(offscreen,0,0,this);
			} else {
				offg=offscreen.getGraphics();
				offg.drawImage(topImage,0,0,this);
				offg.drawImage(bottomImage,0,imh,this);
				g.drawImage(offscreen,0,0,this);
			}
		}
		finally {
			offg.dispose();
		}
	}

	public void run() {
	  Thread me=Thread.currentThread();
    while(originalImage==null) {
		  try {
        System.out.println("Image nulle...");
        me.sleep(100);

			} catch (InterruptedException e) {
				e.printStackTrace();
		  }
    }

	  tracker.addImage(originalImage,0,imw,imh);

		try {
			tracker.waitForID(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
		}
		try {

		PixelGrabber pg=new PixelGrabber(originalImage, 0, 0, imw, imh, toppix, 0, imw);



            pg.grabPixels();

        if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
            System.err.println("image fetch aborted or errored");
            return;
        }
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	wave(0);
	topImage = createImage(topmis);
	bottomImage = createImage(bottommis);

	repaint();

	animating=true;

		while(me==Animator) {
			for(int alpha=-alphabound;alpha<alphabound;alpha+=1) {
				wave(alpha);
				try {
					Thread.sleep(delai);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

  public void wave(int alpha) {
    for(int l=0;l<imh;l++) {
	System.arraycopy(toppix,sineArray[alpha+alphabound][l],bottompix,l*imw,imw);
    }
    bottommis.newPixels();
  }

  public String getAppletInfo() {
     String ans="Applet Vague\nVersion:     0.4\nCopyright (c) 1998\n\n";
     ans=ans.concat("Daniel Lemire\nDaniel.Lemire@Tintin.net\n\n");
     ans=ans.concat("Ceci est un partagiciel que vous devez enregistrer\n");
     ans=ans.concat("en m'écrivant un mot contenant vos noms et adresses.\n");
     ans=ans.concat("Ce faisant vous serez automatiquement averti des éventuelles\n");
     ans=ans.concat("mises à jour et vous pourrez en profiter pour me faire des\n");
     ans=ans.concat("suggestions.\n\n");
     ans=ans.concat("Si vous le voulez bien, vous pouvez aussi mettre un lien\n");
     ans=ans.concat("vers ma page personnelle que je vous invite à visiter souvent.\n\n");
     ans=ans.concat("http://technopole.le-village.com/Lemire/\n\n");
     ans=ans.concat("Merci!\n");
     ans=ans.concat("Note: Je tiens à remercier Alain Béliveau et Nathalie Lampron pour leurs idées.\n");
     return(ans);
  }
}
