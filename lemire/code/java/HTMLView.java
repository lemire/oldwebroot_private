package Applet;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.net.*;

public class HTMLView extends JPanel {
  public static int WIDTH = 600;  public static int HEIGHT = 400;
  private JEditorPane _view;
  private JTextField _commandLine;
  private JLabel status;
  private Vector oldURLs=new Vector(0,1);
  private int pos=0;
  private JMenuItem reculerMenuItem,avancerMenuItem,quitmenu;
  private JMenuBar menubar;

  public HTMLView(String initialURL){     _view = new JEditorPane();    _view.setEditable(false);
    _view.addHyperlinkListener(new HyperlinkListener(){
      public void hyperlinkUpdate(HyperlinkEvent event){
      setURL(event.getURL());
      }
      });
    setLayout(new BorderLayout());
    add(new JScrollPane(_view),BorderLayout.CENTER);
    //Add the location line
    _commandLine = new JTextField(initialURL);
    add(_commandLine,BorderLayout.NORTH);
    status = new JLabel ("Contenu : "+initialURL+".");
    add(status,BorderLayout.SOUTH);
    menubar=new JMenuBar();
    JMenu movemenu=new JMenu("Navigation");
    movemenu.add(reculerMenuItem=new JMenuItem("Reculer"));
    movemenu.add(avancerMenuItem=new JMenuItem("Avancer"));
    movemenu.addSeparator();
    movemenu.add(quitmenu=new JMenuItem("Quitter"));
    menubar.add(movemenu);

    reculerMenuItem.addActionListener(
      new ActionListener() {
 	      public void actionPerformed (ActionEvent e) {
           if(pos==oldURLs.size())
              oldURLs.add(_view.getPage());
            pos--;
            memorylessSetURL((URL)oldURLs.elementAt(pos));
        }
      }
    );
    avancerMenuItem.addActionListener(new ActionListener() {
 	    public void actionPerformed (ActionEvent e) {
          pos++;
          memorylessSetURL((URL)oldURLs.elementAt(pos));
      }
    });
    quitmenu.addActionListener(new ActionListener() {
 	    public void actionPerformed (ActionEvent e) {
        System.exit(0);
      }
    });

    _commandLine.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        try{
          URL newURL = new URL(ae.getActionCommand());
          setURL(newURL);
        }catch (MalformedURLException mue){
          System.out.println("L'URL est incorrect :"+mue);
          status.setText ("L'URL est incorrect :"+mue);
          status.repaint();
        }
      }
    });

  }

  public void setURL(URL newURL){    try{

      if(pos==oldURLs.size()) {

          oldURLs.add(_view.getPage());
          System.out.println("added 1 element "+oldURLs.size());

        pos++;

       } else if (pos<oldURLs.size()-1) {
        pos++;
        oldURLs.setElementAt(_view.getPage(),pos-1);
        oldURLs.setSize(pos);
        System.out.println("set size to pos+1 "+oldURLs.size());
        reculerMenuItem.setEnabled(true);
       }
      if (pos>0)
        reculerMenuItem.setEnabled(true);
      else
        reculerMenuItem.setEnabled(false);

      avancerMenuItem.setEnabled(false);

      _view.setPage(newURL);
      _commandLine.setText(newURL.toExternalForm());
      status.setText("Contenu : "+newURL.toExternalForm()+".");
      status.repaint();
    }catch (IOException ioe){
      System.err.println("Erreur :"+ioe);
      status.setText("Erreur :"+ioe);
      status.repaint();
    }
      System.out.println("mem size "+oldURLs.size()+" pos "+pos+" avancer "+(pos<oldURLs.size()));

      for(int k=0;k<oldURLs.size();k++) {
        System.out.println(oldURLs.elementAt(k));
      }

  }

  public void memorylessSetURL (URL newURL){
    try{
      _view.setPage(newURL);
      _commandLine.setText(newURL.toExternalForm());
      status.setText("Contenu : "+newURL.toExternalForm()+".");
      status.repaint();
      reculerMenuItem.setEnabled(((pos>1)&&(oldURLs.size()>0)));
      avancerMenuItem.setEnabled((pos<oldURLs.size()));
    }catch (IOException ioe){
      System.err.println("Erreur :"+ioe);
      status.setText("Erreur :"+ioe);
      status.repaint();
    }
      System.out.println("size "+oldURLs.size()+" pos "+pos+" avancer "+(pos<oldURLs.size()));
      for(int k=0;k<oldURLs.size();k++) {
        System.out.println(oldURLs.elementAt(k));
      }

  }

  public static void main(String s[]) {    String init="file:/C:/jdk1.2/docs/index.html";    HTMLView panel = new HTMLView(init);
    JFrame frame = new JFrame("Mozarella 0.2 : le mini-fureteur (c) Daniel Lemire");
    frame.getContentPane().setLayout(new BorderLayout());
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {System.exit(0);}
      });
    frame.getContentPane().add(panel,BorderLayout.CENTER);
    frame.setJMenuBar(panel.menubar);
    frame.pack();
    frame.setSize(WIDTH,HEIGHT);
    frame.setVisible(true);
      try{
      URL initURL=new URL(init);
      panel.memorylessSetURL(initURL);
      } catch(MalformedURLException e){}
  }
}


