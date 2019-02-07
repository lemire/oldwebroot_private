/************
* on s'assure de ne pas mélanger des composantes
* heavyweight (java.awt) avec des composantes swing
* en nommant explicitement toutes les classes java.awt
* utilisée.
************/
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.datatransfer.*;

/******************************
* JConsole
* Usage / utilisation :
*   new JConsole("myApp");
*
* Redirige automatiquement System.out et System.err vers la
* console.
*
* Will automatically reset System.out and System.err to
* the newly created console. The application is in French
* but could be easily translated upon request.
*
* @author Daniel Lemire
************************************/
public final class JConsole extends PrintStream implements Runnable, ActionListener{

  private JMenuItem EnregistrerItem, CopierItem;
  private static final String copyleft="JConsole version 0.3 © 1999 Daniel Lemire, Ph.D.";
  private JTextArea console=new JTextArea();
  private StringBuffer sb=new StringBuffer();
  private JFrame frame=new JFrame();
  private Thread writer;
  private boolean mError = false;
  private JLabel status = new JLabel(copyleft);


  public JConsole() {
    super(System.out);
    System.setOut(this);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(new JScrollPane(console),BorderLayout.CENTER);
    frame.getContentPane().add(status,BorderLayout.SOUTH);
    frame.pack();
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    console.setBackground(Color.white);
    frame.setState(JFrame.ICONIFIED);
    setupMenu();
    wireEvents();
    writer=new Thread(this);
    writer.start();
  }

  public JConsole(String s) {
    this();
    setTitle(s);
  }

  public void setTitle(String s) {
    frame.setTitle(s);
  }
  public void setVisible(boolean b) {
    frame.setVisible(b);
  }

  private void setupMenu () {
    JMenuBar menubar=new JMenuBar();
    JMenu FichierMenu=new JMenu("Fichier");
    FichierMenu.add(EnregistrerItem = new JMenuItem ("Enregistrer"));
    JMenu PressePapierMenu = new JMenu ("Presse-papier");
    PressePapierMenu.add(CopierItem = new JMenuItem("Copier"));
    menubar.add(FichierMenu);
    menubar.add(PressePapierMenu);
    frame.setJMenuBar(menubar);
  }

  private void wireEvents() {
    EnregistrerItem.addActionListener(this);
    CopierItem.addActionListener(this);
  }

  public void pack() {
    frame.pack();
  }

  public int getHeight() {
    return(frame.getHeight());
  }

  public Point getLocation () {
    return(frame.getLocation());
  }

  public Dimension getSize() {
    return(frame.getSize());
  }

  public Image getIconImage() {
    return(frame.getIconImage());
  }

  public int getWidth() {
    return(frame.getWidth());
  }

  public void setBounds(int x, int y, int width, int height) {
    frame.setBounds(x, y, width, height);
  }
  public void setBounds(Rectangle r) {
    frame.setBounds(r);
  }
  public void setIconImage(Image icon) {
    frame.setIconImage(icon);
  }

  public void setSize(int width, int height) {
    frame.setSize(width, height);
  }

  public void setSize(Dimension d) {
    frame.setSize(d);
  }

  public void setState (int i) {
    frame.setState(i);
  }

  public void setLocation (int x, int y) {
    frame.setLocation(x,y);
  }

  public void setLocation (Point p) {
    frame.setLocation(p);
  }

  public void print(String s) {
    sb.append(s);
  }

  public void println(String s) {
    sb.append(s);
    sb.append("\n");
  }

  public void print(int i) {
    sb.append(i);
  }

  public void println(int i) {
    sb.append(i);
    sb.append("\n");
  }

  public String getText() {
    return(console.getText());
  }

  public void run() {
    Thread me=Thread.currentThread();
    me.setPriority(Thread.MIN_PRIORITY);
    while (me==writer) {
      try {
        me.sleep(1000);
      } catch(InterruptedException ie) {}
      if(sb.length()>0) {
        flush();
        if(!frame.isShowing()) {
          frame.pack();
          frame.setVisible(true);
        }

      }
    }
  }

  public void close() {
    flush();
    writer=null;
    sb=null;
  }

  public boolean checkError() {
    return(mError);
  }

  protected void setError() {
    mError = true;
  }

  public void flush() {
    synchronized (this) {
      console.append(sb.toString());
      sb.setLength(0);
    }
  }

  public void print(Object parm1) {
    sb.append(parm1);
  }

  public void print(boolean parm1) {
    sb.append(parm1);

  }

  public void print(char parm1) {
    sb.append(parm1);

  }

  public void print(char[] parm1) {
    sb.append(parm1);

  }

  public void print(double parm1) {
    sb.append(parm1);

  }

  public void print(float parm1) {
    sb.append(parm1);

  }

  public void println() {
    sb.append("\n");
  }

  public void print(long parm1) {
    sb.append(parm1);

  }

  public void println(Object parm1) {
    sb.append(parm1);
    sb.append("\n");
  }

  public void println(boolean parm1) {
    sb.append(parm1);
    sb.append("\n");
  }

  public void println(char parm1) {
    sb.append(parm1);
    sb.append("\n");
  }

  public void println(char[] parm1) {
    sb.append(parm1);
    sb.append("\n");
  }

  public void println(double parm1) {
    sb.append(parm1);
    sb.append("\n");
  }

  public void println(float parm1) {
    sb.append(parm1);
    sb.append("\n");
  }

  public void println(long parm1) {
    sb.append(parm1);
    sb.append("\n");
  }


  public void write(char[] parm1, int parm2, int parm3) {
    sb.append(parm1,parm2,parm3);
  }

  public void write(int parm1) {
    sb.append(parm1);
  }

  public void actionPerformed(ActionEvent e) {
    status.setText(copyleft);
    if(e.getSource() == EnregistrerItem) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Enregister le contenu de la console...");
        int returnVal = chooser.showSaveDialog(frame);
        if (returnVal != JFileChooser.APPROVE_OPTION)
          return;
        File outputfile= chooser.getSelectedFile();
        try {
          BufferedWriter br= new BufferedWriter(new FileWriter(outputfile));
          br.write(console.getText());
          br.close();
        } catch( IOException ioe){
          status.setText(ioe.toString());
 