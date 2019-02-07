//******************************************************************************
// DLGApplet1_0.java:	Applet
//
//******************************************************************************
import java.applet.*;
import java.awt.*;
import polymtl.igb.Ondelettes.*;
import polymtl.magi.awt.*;
import polymtl.magi.math.*;
import polymtl.magi.math.Ondelettes.CDF2_4.*;
import polymtl.magi.math.Ondelettes.DLG.*;

public class DLGApplet1_0 extends Applet {
	int Nombre=64; // Choisir une puissance de deux
	int [] donnees=new int [Nombre];
	int deltay,deltax,demideltay;
	Dimension Taille;
	int Espace=30;
	TextField tx=new TextField();
     	double[][] nul={{},{}};
	Graphique2D1_0 GraphW1=new Graphique2D1_0(nul);
	Graphique2D1_0 GraphW2=new Graphique2D1_0(nul);
	Graphique2D1_0 GraphW3=new Graphique2D1_0(nul);
	DLG fi=new DLG();
	public void init()
	{
		double [] W1=new double [Nombre];
		double [] W2=new double [Nombre];
		double [] W3=new double [Nombre];
		for(int k=0;k<Nombre;k++) {
			donnees[k]=0;
			W1[k]=0;
			W2[k]=0;
			W3[k]=0;
		}
		setBackground(Color.white);
		redimensionne();
		double[][] graphW1=new double[2][2*Nombre];
		double[][] graphW2=new double[2][2*Nombre];
		double[][] graphW3=new double[2][2*Nombre];
		tx=new TextField("0.2",7);
		double alpha=0.2;
		fi.fixeParametre(alpha);
		double[] doubledonnees=new double[Nombre];
		for(int k=0;k<Nombre;k++) {
			doubledonnees[k]=donnees[k];
		}
		W1=overSample(W1);
		W2=overSample(W2);
		W3=overSample(W3);
		for(int K=0;K<Nombre;K++) {
			graphW1[0][2*K]=K;
			graphW2[0][2*K]=K;
			graphW3[0][2*K]=K;
			graphW1[0][2*K+1]=K+1d-0.0001d;
			graphW2[0][2*K+1]=K+1d-0.0001d;
			graphW3[0][2*K+1]=K+1d-0.0001d;
			graphW1[1][2*K]=W1[K];
			graphW2[1][2*K]=W2[K];
			graphW3[1][2*K]=W3[K];
			graphW1[1][2*K+1]=W1[K];
			graphW2[1][2*K+1]=W2[K];
			graphW3[1][2*K+1]=W3[K];
		}
		GraphW1.setData(graphW1);
		GraphW2.setData(graphW2);
		GraphW3.setData(graphW3);
		GraphW1.setNumbering(false);
		GraphW2.setNumbering(false);
		GraphW3.setNumbering(false);
		GraphW1.setColor(0,Color.blue);
		GraphW2.setColor(0,Color.red);
		GraphW3.setColor(0,Color.darkGray);
		add(GraphW1);
		add(GraphW2);
		add(GraphW3);
		add(tx);
	}

	public void start() {
	}

	public void destroy()
	{
	}

	public void paint(Graphics g)
	{
		GraphW1.reshape(20,2*Espace+deltay,Nombre*deltax,deltay);
        	GraphW2.reshape(20,3*Espace+2*deltay,Nombre*deltax,deltay);
        	GraphW3.reshape(20,4*Espace+3*deltay,Nombre*deltax,deltay);
        	tx.move(20,5*Espace+4*deltay);

		g.setColor(Color.black);
		g.drawRect(20,Espace,Nombre*deltax,deltay);		
		g.drawString("Données (cliquez pour modifier)",20,20);
		g.setColor(Color.blue);
		g.drawString("Coefficient d'ondelettes j=1",20,Espace+deltay+20);
		g.setColor(Color.red);
		g.drawString("Coefficient d'ondelettes j=2",20,2*Espace+2*deltay+20);
		g.setColor(Color.darkGray);
		g.drawString("Coefficient d'ondelettes j=3",20,3*Espace+3*deltay+20);
		for(int k=0;k<Nombre;k++) {
			g.setColor(Color.black);
			if(donnees[k]<=0) {
				g.fillRect(20+deltax*k,Espace+demideltay+donnees[k],deltax,-donnees[k]+1);
			} else {
				g.fillRect(20+deltax*k,Espace+demideltay,deltax,donnees[k]);
			}
		}



	}

	public boolean mouseDown(Event evt, int xPos, int yPos) {
		redimensionne();
		if(xPos>20 && xPos<Nombre*deltax+20 && yPos > Espace && yPos<Espace+deltay) {
			Double K=new Double(Math.floor((xPos-20)/deltax));
			int k=K.intValue();
			int y=-yPos+(Espace+demideltay);
			donnees[k]=-y;
		}
		double [] W1=new double [Nombre];
		double [] V1=new double [Nombre];
		double [] W2=new double [div2(Nombre)];
		double [] V2=new double [div2(Nombre)];
		double [] W3=new double [div2(div2(Nombre))];
		String str=tx.getText();
		Double Alpha= new Double(str);
		double alpha=Alpha.doubleValue();
		fi.fixeParametre(alpha);
		double[] doubledonnees=new double[Nombre];
		for(int k=0;k<Nombre;k++) {
			doubledonnees[k]=donnees[k];
		}
		Signal T1=new Signal(fi,doubledonnees);
		V1=T1.ProjectionPasseBas();
		W1=T1.ProjectionPasseHaut();
		Signal T2=new Signal (fi,V1);
		V2=T2.ProjectionPasseBas();
		W2=T2.ProjectionPasseHaut();
		Signal T3=new Signal (fi,V2);
		W3=T3.ProjectionPasseHaut();
		W1=overSample(W1);
		W2=overSample(overSample(W2));
		W3=overSample(overSample(overSample(W3)));
		double[][] graphW1=new double[2][2*Nombre];
		double[][] graphW2=new double[2][2*Nombre];
		double[][] graphW3=new double[2][2*Nombre];
		for(int K=0;K<Nombre;K++) {			
			graphW1[0][2*K]=K;
			graphW2[0][2*K]=K;
			graphW3[0][2*K]=K;
			graphW1[0][2*K+1]=K+1d-0.0001d;
			graphW2[0][2*K+1]=K+1d-0.0001d;
			graphW3[0][2*K+1]=K+1d-0.0001d;
			graphW1[1][2*K]=W1[K];
			graphW2[1][2*K]=W2[K];
			graphW3[1][2*K]=W3[K];
			graphW1[1][2*K+1]=W1[K];
			graphW2[1][2*K+1]=W2[K];
			graphW3[1][2*K+1]=W3[K];		
		}
		GraphW1.setData(graphW1);
		GraphW2.setData(graphW2);
		GraphW3.setData(graphW3);
		GraphW1.setNumbering(false);
		GraphW2.setNumbering(false);
		GraphW3.setNumbering(false);
		GraphW1.setColor(0,Color.blue);
		GraphW2.setColor(0,Color.red);
		GraphW3.setColor(0,Color.darkGray);
		repaint();
		return true;
	}

	public void stop()
	{
	}

	public String getAppletInfo()
	{
		return "Nom: DLGApplet1_0\r\n" +
		       "Auteur: Daniel Lemire\r\n" +
		       "École Polytechnique de Montréal";
	}

	private double[] overSample(double[] v) {
		double[] ans=new double[v.length*2];
		for(int k=0;k<v.length;k++) {
			ans[2*k]=v[k];
			ans[2*k+1]=v[k];
		}
		return(ans);
	}
	private void redimensionne() {
		Taille=size();
		Long Deltay=new Long(Math.round((Taille.height-6*Espace)/5));
		deltay=Deltay.intValue();
		Long Deltax=new Long(Math.round((Taille.width-40)/Nombre));
		deltax=Deltax.intValue();
		Long Demideltay=new Long(Math.round(deltay/2));
		demideltay=Demideltay.intValue();

	}
	private int div2(int a) {
		Double D=new Double(a/2);
		return(D.intValue());
	}
}
