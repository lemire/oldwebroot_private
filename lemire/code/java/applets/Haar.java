//******************************************************************************
// Haar.java:	Applet
//
//******************************************************************************
import java.applet.*;
import java.awt.*;

public class Haar extends Applet
{
	int Nombre=32; // Choisir un nombre divisible par 2
	// et positif !
	int [] donnees=new int [Nombre];
	int [] V=new int [Nombre];
	int [] W=new int [Nombre];
	int [] Compress=new int[Nombre];
	int [] Debruit=new int[Nombre];
	int deltay,deltax,demideltay;
	Dimension Taille;
	int Espace=30;

	public void init()
	{
		for(int k=0;k<Nombre;k++) {
			donnees[k]=0;
			V[k]=0;
			W[k]=0;
		}
		setBackground(Color.white);
		Taille=size();

		Long Deltay=new Long(Math.round((Taille.height-6*Espace)/5));
		deltay=Deltay.intValue();
		Long Deltax=new Long(Math.round((Taille.width-40)/Nombre));
		deltax=Deltax.intValue();
		Long Demideltay=new Long(Math.round(deltay/2));
		demideltay=Demideltay.intValue();
		
	}

	public void destroy()
	{
		
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		g.drawRect(20,Espace,Nombre*deltax,deltay);		
		g.drawRect(20,2*Espace+deltay,Nombre*deltax,deltay);		
		g.drawRect(20,3*Espace+2*deltay,Nombre*deltax,deltay);		
		g.drawRect(20,4*Espace+3*deltay,Nombre*deltax,deltay);		
		g.drawRect(20,5*Espace+4*deltay,Nombre*deltax,deltay);		
		g.drawString("Données (cliquez pour modifier)",20,20);
		g.setColor(Color.blue);
		g.drawString("filtre passe-bas",20,Espace+deltay+20);
		g.setColor(Color.red);
		g.drawString("filtre passe-haut",20,2*Espace+2*deltay+20);
		g.setColor(Color.darkGray);
		g.drawString("signal compressé",20,3*Espace+3*deltay+20);
		g.setColor(Color.orange);
		g.drawString("signal débruité",20,4*Espace+4*deltay+20);		

		for(int k=0;k<Nombre;k++) {

			g.setColor(Color.black);

			if(donnees[k]>=0) {
				g.fillRect(20+deltax*k,Espace+demideltay-donnees[k],deltax,donnees[k]+1);
			} else {
				g.fillRect(20+deltax*k,Espace+demideltay,deltax,-donnees[k]);
			}
			
			g.setColor(Color.blue);

			if (V[k]>=0) {
				g.fillRect(20+deltax*k,2*Espace+deltay+demideltay-V[k],deltax,V[k]+1);
			} else {
				g.fillRect(20+deltax*k,2*Espace+deltay+demideltay,deltax,-V[k]);
			}

			g.setColor(Color.red);

			if (W[k]>=0) {
				g.fillRect(20+deltax*k,3*Espace+2*deltay+demideltay-W[k],deltax,W[k]+1);
			} else {
				g.fillRect(20+deltax*k,3*Espace+2*deltay+demideltay,deltax,-W[k]);
			}
			g.setColor(Color.darkGray);
			if (Compress[k]>=0) {
				g.fillRect(20+deltax*k,4*Espace+3*deltay+demideltay-Compress[k],deltax,Compress[k]+1);
			} else {
				g.fillRect(20+deltax*k,4*Espace+3*deltay+demideltay,deltax,-Compress[k]);
			}

			g.setColor(Color.orange);
		
			if (Debruit[k]>=0) {
				g.fillRect(20+deltax*k,5*Espace+4*deltay+demideltay-Debruit[k],deltax,Debruit[k]+1);
			} else {
				g.fillRect(20+deltax*k,5*Espace+4*deltay+demideltay,deltax,-Debruit[k]);
			}

		}
	}

	public boolean mouseDown(Event evt, int xPos, int yPos) {
		if(xPos>20 && xPos<Nombre*deltax+20 && yPos > Espace && yPos<Espace+deltay) {
			Double K=new Double(Math.floor((xPos-20)/deltax));
			int k=K.intValue();
			int y=-yPos+(Espace+demideltay);
			donnees[k]=y;
			/* Application des filtres passe-bas et
			passe-haut. On recalcule tout en entier 
			alors que l'on devrait utiliser le fait
			que le filtre est un FIR. */
			for(int m=0;2*m+1<Nombre;m++) {
				Long VLong=new Long(Math.round((donnees[2*m]+donnees[2*m+1])/2));
				Long WLong=new Long(Math.round((-donnees[2*m]+donnees[2*m+1])/2));
				// le signe est arbitraire
				// la normalisation aussi ...
				V[2*m]=VLong.intValue();
				V[2*m+1]=VLong.intValue();//Downsampling
				W[2*m]=WLong.intValue();
				W[2*m+1]=WLong.intValue();//Downsampling
				/* Le downsampling n'est pas nécessaire, mais
				c'est plus clair et versatile ...*/			
			}
			int MaxCoef=Math.abs(W[0]);
			for(int m=1;2*m+1<Nombre;m++) {
				MaxCoef=Math.max(MaxCoef,Math.abs(W[2*m]));
			}
			for(int m=0;2*m+1<Nombre;m++) {
				if (2*Math.abs(W[2*m])>MaxCoef) {
					Compress[2*m]=V[2*m]-W[2*m];
					Compress[2*m+1]=V[2*m]+W[2*m];
					Debruit[2*m]=V[2*m];
					Debruit[2*m+1]=V[2*m];
				} else {
					Compress[2*m]=V[2*m];
					Compress[2*m+1]=V[2*m];
					Debruit[2*m]=V[2*m]-W[2*m];
					Debruit[2*m+1]=V[2*m]+W[2*m];
				}
			}
			repaint();
			return true;
		}
		return false;
	}
	public void start()
	{
	}
	public void stop()
	{
	}

	public String getAppletInfo()
	{
		return "Nom: Haar\r\n" +
		       "Auteur: Daniel Lemire\r\n" +
		       "École Polytechnique de Montréal";
	}



}
