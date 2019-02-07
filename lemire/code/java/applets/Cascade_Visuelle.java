/***********************************************/
// Fichier Cascade_Visuelle.java
// auteur: Daniel Lemire
// lemire@mathappl.polymtl.ca
// �cole Polytechnique de Montr�al
// Applet pour l'algorithme en cascade dans
// le cas dyadique
/**********************************************/


import java.applet.Applet;	// par d�fault pour une "applet"
import java.awt.*;			// Utilisation des fonctions graphiques

public class Cascade_Visuelle extends Applet {
/*************************/
// d�clarations Globales
/*************************/
int jmax=5;
int N=32;//2^jmax
float f [] = new float [6*N+1];
int PointZero =Math.round(f.length/2f)-1;
Dimension Taille; 
TextField tx []=new TextField[7];

/****************************/
// Initialisation de l'applet
/****************************/
    public void init() {
	/*************************/
	// mise en place de l'interface
	/*************************/
	setBackground(Color.white);
	tx[0]=new TextField("-0.0625",7);
	tx[1]=new TextField("0",7);
	tx[2]=new TextField("0.5625",7);
	tx[3]=new TextField("1",7);
	tx[4]=new TextField("0.5625",7);
	tx[5]=new TextField("0",7);
	tx[6]=new TextField("-0.0625",7);
	for(int k=0;k<7;k++)
		add(tx[k]);
	add(new Button("Dessine"));
	/********************************/
	// Initialisation des variables
	/********************************/
	Taille=size(); // prise en compte de l'espace graphique disponible
	for(int i=0;i<f.length;i++)
		f[i]=0;
	f[PointZero]=1;
    }

	/************************************************/
	// m�thode particuli�re cr��e de toute pi�ce pour
	// effectuer les calculs dans le cas dyadique
	/************************************************/
    public float[] Cascade(float[] donnees, float[] filtre,int NbreIterations) {
    // la variable filtre prendra, par exemple, la forme {-1/16,0,9/16,1,9/16,0,-1/16}
	float g0[]=new float[donnees.length];
	for(int i=0;i<donnees.length;i++) g0[i]=donnees[i];
	int DemiLargeur = Math.round(filtre.length/2f)-1;
	int QuartDonnees = Math.round(donnees.length/4f);
	int Correction = 2*QuartDonnees-PointZero;
	float g[] = new float [donnees.length]; 
	for(int j=1;j<=NbreIterations;j++) {
		/*************************/
		// insertion des z�ros
		/*************************/
		/*************************************************************/
		// Il va sans dire que l'on pourrait faire mieux, ou du moins,
		// plus �l�gant.
		/*************************************************************/
		for(int i = QuartDonnees-Correction;3*i<4*donnees.length;i++) {
			if (2*(i-QuartDonnees)+Correction<donnees.length)
				g[2*(i-QuartDonnees)+Correction]=g0[i];
			if (2*(i-QuartDonnees)+1+Correction<donnees.length) 
				g[2*(i-QuartDonnees)+1+Correction]=0;
		         }
		/*************************/
		// calculs
		/*************************/
		for(int i=0;i<donnees.length;i++)  {
			g0[i]=0;
			for(int k=0;k<filtre.length;k++) {
				int indice=i+k-DemiLargeur;
				if (indice>=0 && indice<donnees.length)
					g0[i]+=g[indice]*filtre[k]; // "zero-padding"
			}
		}
	}
	return g0;
    }

	/*************************/
	// gestion des �v�nements
	/*************************/
    public boolean action (Event e, Object arg) {
	float w[]=new float[7];
	if(e.target instanceof Button) {
		for(int k=0;k<7;k++) {
			String str=tx[k].getText();
			Float W= new Float (str);
			w[6-k]=W.floatValue();
		}
       	for(int i=0;i<f.length;i++)
			f[i]=0;	
		f[PointZero]=1;
		f=Cascade(f,w,jmax);
		repaint();
	}
	return true;
    }
     
    public void start() {
    }

	/***********/
	//Graphisme
	/***********/
    public void paint (Graphics g){
	g.setColor(Color.blue);
	int PaletteSup = Taille.height/3;
	int PaletteInf =Taille.height/10;
	int EspaceDisp=Taille.height-PaletteSup-PaletteInf;
	float x=0;
	float pas = Taille.width/(f.length-1);
	float max=f[0];
	float min=f[0];
	for (int i=0;i<f.length;i++) {
		if(f[i]>max) max=f[i];
		if(f[i]<min) min=f[i];
	}
	for (int i=0;i<f.length-1;i++,x+=pas) {
		int y1 = Math.round((max-f[i])/(max-min)*EspaceDisp)+PaletteSup;
		int y2 = Math.round((max-f[i+1])/(max-min)*EspaceDisp)+PaletteSup;
		g.drawLine(Math.round(x),y1,Math.round(x+pas),y2);		
	}
	g.setColor(Color.red);
	// ligne y = 0
	long finlong = Math.round((PointZero+3*N)*pas);
	Long finLong = new Long(finlong);
	int fin= finLong.intValue();
	int Zero=Math.round(max/(max-min)*EspaceDisp)+PaletteSup;
	g.drawLine(0,Zero,fin,Zero);	
	// lignes verticales
	g.setColor(Color.red);
	for(int k=-2;k<=2;k++) {
		long Grillelong = Math.round((PointZero+k*N)*pas);
		Long GrilleLong = new Long(Grillelong);
		int Grille= GrilleLong.intValue();
		g.drawLine(Grille,PaletteSup,Grille,EspaceDisp+PaletteSup);
		}
    }

	/**********************************************************/
	// Proc�dures de "nettoyage" que l'on ignore pour l'instant
	/**********************************************************/
    public void stop() {
    }
    public void destroy() {
    }
	/*****************************************/
	// Proc�dure d'identification de l'Applet
	/*****************************************/

	public String getAppletInfo()
	{
		return "fichier : Cascade_Visuelle\r\n" +
		       "Auteur : Daniel Lemire\r\n" +
		       "�cole Polytechnique de Montr�al\r\n" +
		       "mai 1997\r\n" +
		       "\r\n" +
		       "Applet pour dessiner le r�sultat de l'algorithme en cascades\r\n" +
			   " dans le cas dyadique\r\n" +
		       "";
	}
}
/***********/
// fin
/***********/

