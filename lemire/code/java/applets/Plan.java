/***********************************************/
// Fichier Plan.java
// auteur: Daniel Lemire
// lemire@mathappl.polymtl.ca
// École Polytechnique de Montréal
/**********************************************/


import java.applet.Applet;	// par défault pour une "applet"
import java.awt.*;			// Utilisation des fonctions graphiques

public class Plan extends Applet {
/*************************/
// déclarations Globales
/*************************/
final int N=17;
float[][] y=new float[N][N];
TextArea tx;
final float a=9/16f;
final float b=-1/16f;
final float c=0;
final float d=0;
final float e=81/256f;
final float f=-9/256f;
final float g=1/256f;

    public void init() {	
	showStatus("initialisation ...");
	for(int i=0;i<=N-1;i++){
		for(int j=0;j<=N-1;j++) {
			if((i==0) && (j==floor(N/2))) {y[i][j]=1;}
			else {y[i][j]=0;}

		}
	}
	add (new Button("Calcule!"));
	tx=new TextArea(20,20);
	add(tx);
	affichage();	
	showStatus("initialisation terminée");
   }

		

    public boolean action (Event e, Object arg) {
	iteration();
	affichage();
	float Ecartx=0;
	float Ecarty=0;
	for(int i=0;i<=N-1;i++){
		for(int j=0;j<=N-1;j++) {
			if(i>0) {
				if(Ecartx<Math.abs(y[i][j]-y[i-1][j])) {Ecartx=Math.abs(y[i][j]-y[i-1][j]);}
			}
			if(j>0) {
				if(Ecarty<Math.abs(y[i][j]-y[i][j-1])) {Ecarty=Math.abs(y[i][j]-y[i][j-1]);}
			}

		}
	}
	showStatus(String.valueOf(Ecartx)+" "+String.valueOf(Ecarty));
	repaint();
	return true;
    }

	public void affichage () {
		showStatus("Procédure d'affichage en cours ...");
		String Contenu=new String("{");
		for(int i=0;i<=N-1;i++){
			Contenu=Contenu+"{";
			for(int j=0;j<=N-1;j++) {
				Contenu=Contenu+ /* String.valueOf(i)+", "+String.valueOf(j)+", "+ */ String.valueOf(y[i][j]);
				if (j<N-1){Contenu=Contenu+",";}
				// Contenu=Contenu+"\n";
			}
			Contenu=Contenu+"}";
			if(i<N-1) {Contenu=Contenu+",";}
			Contenu=Contenu+"\n";
		}
		Contenu=Contenu+"}";
		tx.insertText(Contenu,0);
		showStatus("Procédure d'affichage terminée");
	}

	public void iteration () {     
		showStatus("Calculs en cours ...");
		InsertionDesZeros();
		ApplicationDuFiltre();
		showStatus("Fin des calculs");
	}

	public int floor (float f) {
		Long l=new Long(Math.round(Math.floor(f)));
		int i=l.intValue();
		return(i);
	}

	public void InsertionDesZeros () {
		float[][] z=new float[N][N];
		for(int i=0;i<=N-1;i++){
			for(int j=0;j<=N-1;j++) {
				z[i][j]=0;
			}
		}
		for(int i=0;2*i<=N-1;i++){
			for(int j=floor(N/4);j<=floor((3*N-2)/4);j++) {
				z[2*i][2*j-floor(N/2)]=y[i][j];
			}
		}
		y=z;
	}

	public void ApplicationDuFiltre () {
		float[][] z=new float[N+4][N+4];	
		for(int i=0;i<=N-1;i++){
			for(int j=0;j<=N-1;j++) {
				z[i+2][j+2]=y[i][j];
			}
		}
		for(int i=0;i<=N+3;i++){
			z[i][0]=0;
			z[i][N+3]=0;
			z[i][1]=0;
			z[i][N+2]=0;
		}
		for(int j=0;j<=N+3;j++){
			if(j==2*Math.round(j/2)) 
				z[0][j]=4*z[2][j]-6*z[4][j]+4*z[6][j]-z[8][j];
		
			
			else 
				z[0][j]=0;
			z[N+3][j]=0;

			z[N+2][j]=0;
			z[1][j]=0;

		}
		float Somme=0;
		for(int i=0;i<=N-1;i++){
			for(int j=0;j<=N-1;j++) {
				Somme=0;
				if((j!=2*Math.round(j/2)) && (i!=2*Math.round(i/2))) {
					for(int k=-3;k<=3;k++){for(int l=-3;l<=3;l++){
						if(k*k+l*l==2)
							Somme=Somme+z[i+k+2][j+l+2]*e;
						if(k*k+l*l==10)
							Somme=Somme+z[i+k+2][j+l+2]*f;
						if(k*k+l*l==18)
							Somme=Somme+z[i+k+2][j+l+2]*g;
					}}
				}
		
				else if((j!=2*Math.round(j/2)) && (i==2*Math.round(i/2))) {
					for(int k=-2;k<=2;k++){for(int l=-3;l<=3;l++){
						if(k*k+l*l==1)
							Somme=Somme+z[i+k+2][j+l+2]*a;
						if(k*k+l*l==9)
							Somme=Somme+z[i+k+2][j+l+2]*b;
						if(k*k+l*l==5)
							Somme=Somme+z[i+k+2][j+l+2]*d;
						if(k*k+l*l==13)
							Somme=Somme+z[i+k+2][j+l+2]*c;
					}}
				}
				else if((j==2*Math.round(j/2)) && (i!=2*Math.round(i/2))) {
					for(int k=-3;k<=3;k++){for(int l=-2;l<=2;l++){
						if(k*k+l*l==1)
							Somme=Somme+z[i+k+2][j+l+2]*a;
						if(k*k+l*l==9)
							Somme=Somme+z[i+k+2][j+l+2]*b;
						if(k*k+l*l==5)
							Somme=Somme+z[i+k+2][j+l+2]*d;
						if(k*k+l*l==13)
							Somme=Somme+z[i+k+2][j+l+2]*c;
					}}

				}
				else if((j==2*Math.round(j/2)) && (i==2*Math.round(i/2))) {
					Somme=y[i][j];
				}
				y[i][j]=Somme;
			}
		}
	}


	public void paint (Graphics g){
		
	}

	public String getAppletInfo()
	{
		return "fichier : Plan\r\n" +
		       "Auteur : Daniel Lemire\r\n" +
		       "École Polytechnique de Montréal\r\n" +
		       "juillet 1997\r\n" +
		       "\r\n" +
		       "";
	}
}
/***********/
// fin
/***********/

