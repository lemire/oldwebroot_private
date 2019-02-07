/*	Ondecolle.java
	Applet donnant un environnement flexible
	pour l'étude des données par ondelettes. */

import java.applet.*;
import java.awt.*;

public class Ondecolle extends Applet
{
	String str=new String();
	TextArea tx;
	TextArea txc;
	int Max=64; /* Attention, si Max est trop important,
				on risque de manque de mémoire ! */
	
	int Hauteur=30;
	int Largeur=150; // Il faut Max<Largeur.
	float[] v0=new float[Max];
	float[] v1=new float[Max];
	float[] w1=new float[Max];
	float[] v2=new float[Max];
	float[] w2=new float[Max];
	/* Attention, on ne peut pas faire 
	float[] v0,v1,w1,v2,w2=new float[Max];*/
	float Maxv0,Minv0;
	int indice=0;	
	
	public void init()
	{
    	resize(600,350);
		tx=new TextArea(15,15);
		txc=new TextArea("les résultats\napparaissent ici\n",15,15);
		setLayout(new BorderLayout(25,25));
		add("West",tx);				
		Button b=new Button("Calcule !");				
		add("South",b);
		add("East",txc);		
		for(int i=0;i<Max;i++) {
			v0[i]=0;
			v1[i]=0;
			v2[i]=0;
			w1[i]=0;
			w2[i]=0;
		}
	}


	public boolean action (Event e, Object o) {
		String[] Ligne=new String[Max];	
		for(int i=0;i<Max;i++){
			Ligne[i]="";
		}
		String Contenu=new String();
		Contenu=tx.getText();
		Contenu=Contenu.trim();
		int Fin=Contenu.length();
		indice=0;
		for(int i=0;i<Fin && indice<Max;i++) {
			Character Carac=new Character(Contenu.charAt(i));
			String caractere=new String (Carac.toString());
			if (Character.isSpace(Contenu.charAt(i))||caractere==null) {
				indice=indice+1;
			} else {				
				Ligne[indice]=Ligne[indice]+caractere;
			}
		}

		float[] x=new float[64];
		for (int i=0;i<=indice;i++) {
			try {
				Float X=new Float(Ligne[i]);
				x[i]=X.floatValue();
			} catch (NumberFormatException erreur) {
				x[i]=0;
			}
		}
		Minv0=x[0];Maxv0=x[0];
		Contenu="\n*******\ndonnées\n*******\n";
		for (int i=0;i<=indice;i++) {
			v0[i]=x[i];
			Float X=new Float(v0[i]);						
			Contenu=Contenu+X.toString()+"\n";			
			if(x[i]<Minv0) {Minv0=x[i];}
			if(x[i]>Maxv0) {Maxv0=x[i];}
		}	
		Contenu=Contenu+"\n*******\nV1\n*******\n";
		for (int i=0;i<=Math.floor(indice/2);i++) {
			v1[i]=(v0[2*i]+v0[2*i+1])/2f;
			Float X=new Float(v1[i]);						
			Contenu=Contenu+X.toString()+"\n";			
		}	
		Contenu=Contenu+"\n*******\nW1\n*******\n";
		for (int i=0;i<=Math.floor(indice/2);i++) {
			w1[i]=(v0[2*i]-v0[2*i+1])/2f;
			Float X=new Float(w1[i]);						
			Contenu=Contenu+X.toString()+"\n";			
		}	
		Contenu=Contenu+"\n*******\nV2\n*******\n";
		for (int i=0;i<=Math.floor(indice/4);i++) {
			v2[i]=(v1[2*i]+v1[2*i+1])/2f;
			Float X=new Float(v2[i]);						
			Contenu=Contenu+X.toString()+"\n";			
		}	
		Contenu=Contenu+"\n*******\nW2\n*******\n";
		for (int i=0;i<=Math.floor(indice/4);i++) {
			w2[i]=(v1[2*i]-v1[2*i+1])/2f;
			Float X=new Float(w2[i]);						
			Contenu=Contenu+X.toString()+"\n";			
		}	
		Contenu=Contenu+"***\nfin\n***\n";
		txc.appendText(Contenu);
		repaint();
		return true;
	}

	public void paint(Graphics g)
	{
		int dh=Math.round(Hauteur/2f);
		int PosX=225;
		int PosYv0=20;
		int PosYv1=40+Hauteur;
		int PosYw1=60+2*Hauteur;
		int PosYv2=80+3*Hauteur;
		int PosYw2=100+4*Hauteur;
		g.setColor(Color.red);
		g.drawString("V0",PosX,PosYv0-5);
		g.drawString("V1",PosX,PosYv1-5);
		g.drawString("W1",PosX,PosYw1-5);
		g.drawString("V2",PosX,PosYv2-5);
		g.drawString("W2",PosX,PosYw2-5);
				
		float deltax= Largeur/(indice+1);
		Float Deltax=new Float(deltax);
		int intdeltax=Deltax.intValue();

		g.setColor(Color.black);
		g.drawRect(PosX,PosYv0,intdeltax*(indice+1),Hauteur);
		g.drawRect(PosX,PosYv1,intdeltax*(indice+1),Hauteur);
		g.drawRect(PosX,PosYw1,intdeltax*(indice+1),Hauteur);
		g.drawRect(PosX,PosYv2,intdeltax*(indice+1),Hauteur);
		g.drawRect(PosX,PosYw2,intdeltax*(indice+1),Hauteur);
		
		if (intdeltax==0 || indice<3) {
		
			return;
		}// On quitte !

		

		if(Maxv0==Minv0){Maxv0=Minv0+1;}
		int[] iv0=new int[indice+1];
		int[] iv1=new int[indice+1];
		int[] iw1=new int[indice+1];
		int[] iv2=new int[indice+1];
		int[] iw2=new int[indice+1];
		long v;
		float echelle=dh/Math.max(Math.abs(Maxv0),Math.abs(Minv0));
		for(int i=0;i<=indice;i++) {
			System.out.println("v0:"+v0[i]);	
			v=Math.round(v0[i]*echelle);
			Long V0=new Long(v);
			iv0[i]=V0.intValue();		
			System.out.println("iv0:"+iv0[i]);	
		}
		for(int i=0;i<=Math.floor(indice/2);i++) {
			v=Math.round(v1[i]*echelle);
			Long V1=new Long(v);
			iv1[i]=V1.intValue();
			v=Math.round(w1[i]*echelle);
			Long W1=new Long(v);
			iw1[i]=W1.intValue();
		}
		for(int i=0;i<=Math.floor(indice/4);i++) {
			v=Math.round(v2[i]*echelle);
			Long V2=new Long(v);
			iv2[i]=V2.intValue();
			v=Math.round(w2[i]*echelle);
			Long W2=new Long(v);
			iw2[i]=W2.intValue();
		}
		int intcurseurX=0;
		g.setColor(Color.blue);
		for(int i=0;i<=indice;i++) {
			float curseurX=deltax*i+PosX;
			Float CurseurX=new Float(curseurX);
			intcurseurX=CurseurX.intValue();
			if (iv0[i]>=0) {
				g.fillRect(intcurseurX,PosYv0+dh-iv0[i],intdeltax,iv0[i]+1);
			} else {
				g.fillRect(intcurseurX,PosYv0+dh,intdeltax,-iv0[i]);	
			}
			
		}	
		g.setColor(Color.blue);
		for(int i=0;i<=Math.floor(indice/2);i++) {
			double curseurX=deltax*2*i+PosX+1;
			Double CurseurX=new Double(curseurX);
			intcurseurX=CurseurX.intValue();			
			if (iv1[i]>=0) {
				g.fillRect(intcurseurX,PosYv1+dh-iv1[i],intdeltax,iv1[i]+1);
			} else {
				g.fillRect(intcurseurX,PosYv1+dh,intdeltax,-iv1[i]);	
			}
			if (iw1[i]>=0) {
				g.fillRect(intcurseurX,PosYw1+dh-iw1[i],intdeltax,iw1[i]+1);
			} else {
				g.fillRect(intcurseurX,PosYw1+dh,intdeltax,-iw1[i]);	
			}
		}
		g.setColor(Color.blue);
		for(int i=0;i<=Math.floor(indice/4);i++) {
			double curseurX=deltax*4*i+PosX+1;
			Double CurseurX=new Double(curseurX);
			intcurseurX=CurseurX.intValue();
			if (iv2[i]>=0) {
				g.fillRect(intcurseurX,PosYv2+dh-iv2[i],intdeltax,iv2[i]+1);
			} else {
				g.fillRect(intcurseurX,PosYv2+dh,intdeltax,-iv2[i]);	
			}
			if (iw2[i]>=0) {
				g.fillRect(intcurseurX,PosYw2+dh-iw2[i],intdeltax,iw2[i]+1);
			} else {
				g.fillRect(intcurseurX,PosYw2+dh,intdeltax,-iw2[i]);	
			}
		}		
	}

	public String getAppletInfo()
	{
		return "Nom: Ondecolle\r\n" +
		       "Auteur: Daniel Lemire";
	}



}
