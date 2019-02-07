/************************************
* Applet EchelleCDF2_3Applet1_0
* @author Daniel Lemire
* Daniel.Lemire@Videotron.ca
*************************************/

import java.awt.event.*;
import java.applet.*;
import java.awt.*;
import polymtl.magi.awt.*;
import polymtl.magi.math.*;
import polymtl.magi.math.Ondelettes.*;
import polymtl.magi.math.Ondelettes.CDF2_4.*;
import polymtl.magi.math.Ondelettes.Splines.*;
public class EchelleCDF2_4Applet1_0 extends Applet  {
	
	int n0=12;
	int j0=7;
	int k=0;
     	double pas=1;
     	double[][] nul={{},{}};
	Graphique2D1_0 Graph=new Graphique2D1_0(nul);
	int up=20;
	int border=1;
	
        public void init()
        {
		setBackground(Color.white);
		double[][] graph=new double[3][Cascades.longueur(n0,j0)];
		for(int K=0;K<Cascades.longueur(n0,j0);K++) {
			graph[0][K]=K*pas;
		}
            graph[1]=donnee1(n0,0,j0);
            graph[2]=donnee2(n0,0,j0);       	
		Graph.setData(graph);
		Graph.setExtremas(-4,4);
		Graph.setNumbering(false);
		Graph.setColor(0,Color.blue);
		Graph.setColor(1,Color.red);
	}
	
	public void paint(Graphics g)
        {
        	Dimension Taille=size();
                g.setColor(Color.red); 
        	g.drawRect(border,border,Taille.width-2*border,up-1); 
                g.setColor(Color.blue);         
                g.drawString("cliquez ici",border,border+g.getFontMetrics().getHeight());
        	Graph.reshape(border,border+up,Taille.width-2*border,Taille.height-2*border-up);
		Graph.paint(g);
        }

	private double[] donnee1(int n0, int k,int j0) {
		EchelleDuale2_4 Eche=new EchelleDuale2_4(n0,k);
		return(Eche.evaluation(j0,j0));
	}
	
	private double[] donnee2(int n0, int k, int j0) {
		SplineLineaire Eche=MultiSpline2_4.chapeau(n0,k);
		return(Eche.interpole(j0));
	}




        public boolean mouseDown(Event evt, int xPos, int yPos) {
                if(k<2*(n0-1)-1) {k++;} else {k=0;}
       		int L=n0-1-Math.abs(n0-1-k);
		double[][] graph=new double[3][Cascades.longueur(n0,j0)];
   		for(int K=0;K<Cascades.longueur(n0,j0);K++) {
			graph[0][K]=K*pas;
		}
		graph[1]=donnee1(n0,L,j0);
		graph[2]=donnee2(n0,L,j0);
		Graph.setData(graph);
		Graph.setExtremas(-4,4);
		repaint();
		return(true);
    }
}