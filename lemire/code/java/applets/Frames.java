//******************************************************************************
// Frames.java:	Applet
//
//******************************************************************************
import java.applet.*;
import java.awt.*;

public class Frames extends Applet
{
	float x1,x2,x3,y1,y2,y3,a,b,v1,v2,v3,w1,w2,w3;
	TextField tx1,tx2,tx3,ty1,ty2,ty3;
	boolean ErreurDeSaisie;
	Panel pSaisie= new Panel();
	Panel pResultat= new Panel();
	List li=new List (11,true);
	public void init()
	{
		resize(480,220);
		ErreurDeSaisie=false;
		setBackground(Color.white);
		x1=0;x2=0;x3=0;y1=0;y2=0;y3=0;
		w1=0;w2=0;w3=0;w1=0;w2=0;w3=0;
		pSaisie.setLayout(new GridLayout(0,2,1,20));
		// paneau de saisies
		tx1=new TextField("2",10);
		tx2=new TextField("0",10);
		tx3=new TextField("0",10);
		ty1=new TextField("1",10);
		ty2=new TextField("-1",10);
		ty3=new TextField("1",10);
		pSaisie.add(tx1);pSaisie.add(ty1);
		pSaisie.add(tx2);pSaisie.add(ty2);
		pSaisie.add(tx3);pSaisie.add(ty3);
		pSaisie.add(new Button("Calcule"));
		pResultat.add(li);
		//mise en place des panneaux
		setLayout(new GridLayout(0,3,10,10));
		add(pSaisie);
		add(pResultat);
	}

	public boolean action (Event e, Object o) 
	{

		if(e.target instanceof Button) {
			// lecture des données
			String sx1=tx1.getText();
			String sx2=tx2.getText();
			String sx3=tx3.getText();
			String sy1=ty1.getText();
			String sy2=ty2.getText();
			String sy3=ty3.getText();
			try {
			  Float X1=new Float(sx1);
			  Float X2=new Float(sx2);
			  Float X3=new Float(sx3);
			  Float Y1=new Float(sy1);
			  Float Y2=new Float(sy2);
			  Float Y3=new Float(sy3);
			  x1=X1.floatValue();
			  x2=X2.floatValue();
			  x3=X3.floatValue();
			  y1=Y1.floatValue();
			  y2=Y2.floatValue();
			  y3=Y3.floatValue();
			  //
			  double Eigen1=(x1*x1+x2*x2+x3*x3+y1*y1+y2*y2+ 
				  y3*y3-Math.sqrt((-x1*x1-x2*x2-x3*x3-y1*y1- 
				  y2*y2-y3*y3)*(-x1*x1-x2*x2-x3*x3-y1*y1- 
				  y2*y2-y3*y3)-4*(x2*x2*y1*y1+x3*x3*y1*y1- 
				  2*x1*x2*y1*y2+x1*x1*y2*y2+x3*x3*y2*y2- 
				  2*x1*x3*y1*y3-2*x2*x3*y2*y3+x1*x1*y3*y3 + 
				  x2*x2*y3*y3)))/2;
			  double Eigen2=(x1*x1+x2*x2+x3*x3+y1*y1+y2*y2 
				  +y3*y3+Math.sqrt((-x1*x1-x2*x2-x3*x3-y1*y1- 
				  y2*y2-y3*y3)*(-x1*x1-x2*x2-x3*x3-y1*y1-y2*y2
				  -y3*y3)-4*(x2*x2*y1*y1+x3*x3*y1*y1-2*x1*x2*y1*y2
				  +x1*x1*y2*y2+x3*x3*y2*y2-2*x1*x3*y1*y3
				  -2*x2*x3*y2*y3+x1*x1*y3*y3+x2*x2*y3*y3)))/2;
			  double Atemp=Math.min(Eigen1,Eigen2);
			  Float A=new Float(Atemp);
			  a=A.floatValue();
			  double Btemp=Math.max(Eigen1,Eigen2);
			  Float B=new Float(Btemp);
			  b=B.floatValue();			
			  if(a>0) {
				  v1=(-(x2*y1*y2) + x1*y2*y2 - x3*y1*y3 + 
					  x1*y3*y3)/
					  (x2*x2*y1*y1 + x3*x3*y1*y1 - 
					  2*x1*x2*y1*y2 + x1*x1*y2*y2 + 
					  x3*x3*y2*y2 - 2*x1*x3*y1*y3 - 2*x2*x3*y2*y3 + 
					  x1*x1*y3*y3 + x2*x2*y3*y3);
				  w1=(x2*x2*y1 + x3*x3*y1 - x1*x2*y2 - x1*x3*y3)/
					  (x2*x2*y1*y1 + x3*x3*y1*y1 - 
					  2*x1*x2*y1*y2 + x1*x1*y2*y2 + 
					  x3*x3*y2*y2 - 2*x1*x3*y1*y3 - 2*x2*x3*y2*y3 + 
					  x1*x1*y3*y3 + x2*x2*y3*y3);
				  v2=(x2*y1*y1 - x1*y1*y2 - x3*y2*y3 + x2*y3*y3)/
					  (x2*x2*y1*y1 + x3*x3*y1*y1 - 
					  2*x1*x2*y1*y2 + x1*x1*y2*y2 + 
					  x3*x3*y2*y2 - 2*x1*x3*y1*y3 - 2*x2*x3*y2*y3 + 
					  x1*x1*y3*y3 + x2*x2*y3*y3);
				  w2=   (-(x1*x2*y1) + x1*x1*y2 + x3*x3*y2 - x2*x3*y3)/
				  	  (x2*x2*y1*y1 + x3*x3*y1*y1 - 
					  2*x1*x2*y1*y2 + x1*x1*y2*y2 + 
					  x3*x3*y2*y2 - 2*x1*x3*y1*y3 - 2*x2*x3*y2*y3 + 
					  x1*x1*y3*y3 + x2*x2*y3*y3);
				  v3=(x3*y1*y1 + x3*y2*y2 - x1*y1*y3 - x2*y2*y3)/
				  	  (x2*x2*y1*y1 + x3*x3*y1*y1 - 
					  2*x1*x2*y1*y2 + x1*x1*y2*y2 + 
					  x3*x3*y2*y2 - 2*x1*x3*y1*y3 - 2*x2*x3*y2*y3 + 
					  x1*x1*y3*y3 + x2*x2*y3*y3);
				  w3= (-(x1*x3*y1) - x2*x3*y2 + x1*x1*y3 + x2*x2*y3)/
					  (x2*x2*y1*y1 + x3*x3*y1*y1 - 
					  2*x1*x2*y1*y2 + x1*x1*y2*y2 + 
					  x3*x3*y2*y2 - 2*x1*x3*y1*y3 - 2*x2*x3*y2*y3 + 
					  x1*x1*y3*y3 + x2*x2*y3*y3);
			  }
			  repaint();
			  return true;}
			catch (NumberFormatException erreur){
				ErreurDeSaisie=true;
				repaint();
				return true;};
			/* Ce programme ne semble pas fonctionner sous Unix.
			La variable ErreurDeSaisie vient prendre en compte ces
			problèmes d'une façon temporaire. L'utilisateur sera
			alors tenté d'entrer ses données d'une façon différente ...
			J'espère que ça fera l'affaire ...*/
		} else {
		v1=0;w1=0;v2=0;w2=0;v3=0;w3=0;
		return false;
		}
	}

	public void paint(Graphics g)
	{
		li.clear();
		if (ErreurDeSaisie==true) {
			li.addItem ("Erreur de saisie.");
			li.addItem ("Veuillez");
			li.addItem ("recommencer !");
			ErreurDeSaisie=false;
		} else if (a==0) {
			li.addItem("pas de dual!",3);
			li.addItem("A="+a,0);
		    li.addItem("B="+b,1);
		} else	{
			li.addItem("A="+a,0);
		    li.addItem("B="+b,1);
			li.addItem("(v1,w1)",3);
			li.addItem("v1="+v1,4);
			li.addItem("w1="+w1,5);
			li.addItem("(v2,w2)",6);
			li.addItem("v2="+v2,7);
			li.addItem("w2="+w2,8);
			li.addItem("(v3,w3)",9);
			li.addItem("v3="+v3,10);
			li.addItem("w3="+w3,11);
			float maxX=Math.abs(x1);
			float maxY=Math.abs(y1);
			maxX=Math.max(maxX,Math.abs(x2));
			maxY=Math.max(maxX,Math.abs(y2));
			maxX=Math.max(maxX,Math.abs(x3));
			maxY=Math.max(maxX,Math.abs(y3));
			maxX=Math.max(maxX,Math.abs(v1));
			maxY=Math.max(maxX,Math.abs(w1));
			maxX=Math.max(maxX,Math.abs(v2));
			maxY=Math.max(maxX,Math.abs(w2));
			maxX=Math.max(maxX,Math.abs(v3));
			maxY=Math.max(maxX,Math.abs(w3));
			maxX=1.1f*maxX;
			maxY=-1.1f*maxY;
			Long Px1=new Long(Math.round(x1/maxX*75));
			int px1=Px1.intValue();
			Long Px2=new Long(Math.round(x2/maxX*75));
			int px2=Px2.intValue();
			Long Px3=new Long(Math.round(x3/maxX*75));
			int px3=Px3.intValue();
			Long Pv1=new Long(Math.round(v1/maxX*75));
			int pv1=Pv1.intValue();
			Long Pv2=new Long(Math.round(v2/maxX*75));
			int pv2=Pv2.intValue();
			Long Pv3=new Long(Math.round(v3/maxX*75));
			int pv3=Pv3.intValue();
			Long Py1=new Long(Math.round(y1/maxY*75));
			int py1=Py1.intValue();
			Long Py2=new Long(Math.round(y2/maxY*75));
			int py2=Py2.intValue();
			Long Py3=new Long(Math.round(y3/maxY*75));
			int py3=Py3.intValue();
			Long Pw1=new Long(Math.round(w1/maxY*75));
			int pw1=Pw1.intValue();
			Long Pw2=new Long(Math.round(w2/maxY*75));
			int pw2=Pw2.intValue();
			Long Pw3=new Long(Math.round(w3/maxY*75));
			int pw3=Pw3.intValue();
			int x0=75+325;
			int y0=75+15;
			g.setColor(Color.black);
			g.drawRect(325,15,150,150);

			g.setColor(Color.red);
			g.drawLine(x0,y0,x0+pv1,y0+pw1);
			g.drawLine(x0,y0,x0+pv2,y0+pw2);
			g.drawLine(x0,y0,x0+pv3,y0+pw3);
			g.drawString("1",x0+pv1,y0+pw1);
			g.drawString("2",x0+pv2,y0+pw2);
			g.drawString("3",x0+pv3,y0+pw3);
			g.setColor(Color.blue);
			g.drawLine(x0,y0,x0+px1,y0+py1);
			g.drawLine(x0,y0,x0+px2,y0+py2);
			g.drawLine(x0,y0,x0+px3,y0+py3);
			g.drawString("1",x0+px1-10,y0+py1);
			g.drawString("2",x0+px2-10,y0+py2);
			g.drawString("3",x0+px3-10,y0+py3);
		}
		
		
	}
	public void start()
	{
	}
	public void destroy()
	{
	}

	public void stop()
	{	}

	public String getAppl