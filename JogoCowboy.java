import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import javax.sound.sampled.*;


public class JogoCowboy extends JFrame implements Runnable {

  public static  void music() {

      try {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
          JogoCowboy.class.getResourceAsStream("music.wav"));
        clip.open(inputStream);
        clip.start();
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }


  static PrintStream os = null;
  JTextField textField;
  JTextArea textArea;
  JLabel lb;

  public int bx,by;
  public boolean readyToFire, shot = false;
  public Rectangle bullet;

  //private Controller c = new Controller();
  public int cont_acertos = 0;
  Image[] img = new Image[8];
  Image[] img2 = new Image[8];
  Image[] imgbala = new Image[1];
  Desenho des = new Desenho();
  final int FUNDO = 0;
  final int PARADO = 1;
  final int ANDA1 = 2;
  final int ANDA2 = 3;
  final int ANDA3 = 4;
  final int TIRO1 = 5;
  final int ANDAVERT1 = 6;
  final int ANDAVERT2 = 7;
  final int PARADO2 = 1;
  final int ANDA12 = 2;
  final int ANDA22 = 3;
  final int ANDA32 = 4;
  final int TIRO12 = 5;
  final int ANDAVERT12 = 6;
  final int ANDAVERT22 = 7;


  int posX1 = 10;
  int posY1 = getSize().height+img[PARADO].getHeight(this)+30;
  int posX2 = 850;
  int posY2 = getSize().height+img[PARADO2].getHeight(this)+30;
  int estado1 = PARADO;
  int estado2 = PARADO2;
  int dir1 = 1;
  int dir2 = 1;
  int dx1 = 0;
  int dx2 = 0;



  boolean continua = true;
  public class Desenho extends JPanel {

    Desenho() {
      try {
        setPreferredSize(new Dimension(1000, 600)); // tamanho da tela
        imgbala[0] = ImageIO.read(new File("bala.png"));
        img[FUNDO] = ImageIO.read(new File("fundo1.png")); //background
        img[PARADO] = ImageIO.read(new File("parado.png"));
        img[ANDA1] = ImageIO.read(new File("anda1.png"));
        img[ANDA2] = ImageIO.read(new File("anda2.png"));
        img[ANDA3] = ImageIO.read(new File("anda3.png"));
        img[TIRO1] = ImageIO.read(new File("tiro1.png"));
        img[ANDAVERT1] = ImageIO.read(new File("anda1.png"));
        img[ANDAVERT2] = ImageIO.read(new File("anda1.png"));
        img2[PARADO2] = ImageIO.read(new File("parado2.png"));
        img2[ANDA12] = ImageIO.read(new File("anda12.png"));
        img2[ANDA22] = ImageIO.read(new File("anda22.png"));
        img2[ANDA32] = ImageIO.read(new File("anda32.png"));
        img2[TIRO12] = ImageIO.read(new File("tiro2.png"));
        img2[ANDAVERT12] = ImageIO.read(new File("anda12.png"));
        img2[ANDAVERT22] = ImageIO.read(new File("anda12.png"));
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "A imagem não pode ser carregada!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img[FUNDO], 0, 0, getSize().width, getSize().height, this);
      g.drawImage(img[estado1],dx1+posX1,posY1 , dir1*img[estado1].getWidth(this), img[estado1].getHeight(this), this); //desenha a imagem com tamanho da tela
      g.drawImage(img[estado2],dx2+posX2,posY2 , dir2*img[estado2].getWidth(this), img[estado2].getHeight(this), this);
      Toolkit.getDefaultToolkit().sync(); //tipo um flush
      if(shot){

        g.drawImage(imgbala[0],bx, by, imgbala[0].getWidth(this)-350, imgbala[0].getHeight(this)-350,this);
      }

    }
  }

  public void shoot(int bx1){


    new Thread() {
      public void run() {

        while(shot){
            try{
                bx+=25;
                Thread.sleep(50);
                if(((bx>= posX2 && (by>=posY2 && by<=posY2+200)))){
                shot = false;
                cont_acertos++;}
                if(bx >= 999)
                shot = false;
                if(cont_acertos == 3){
                  JOptionPane.showMessageDialog(new JFrame(), "Player 1 Venceu");
                  cont_acertos = 0;
                }
              }catch(InterruptedException e){System.out.println(e);}

       }

      }
}.start();


}

public void shoot2(int bx1){


    new Thread() {
      public void run() {

        while(shot){
            try{


                bx-=25;
                Thread.sleep(50);
                if(((bx<= posX1 && (by>=posY1 && by<=posY1+200)))){
                shot = false;
                cont_acertos++;}
                if(bx<=1)
                shot = false;
                 if(cont_acertos == 3){
                  JOptionPane.showMessageDialog(new JFrame(), "Player 2 Venceu");
                  cont_acertos = 0;
                }
              }catch(InterruptedException e){System.out.println(e);}

       }

      }
}.start();


}

  JogoCowboy() {
    super("Trabalho");

    new Thread(){
      public void run(){
        try{
          while(continua){

            switch(estado1){
              case ANDA1:
              estado1 = ANDA2;
              if(posX1<0) posX1 = 0;
              if(posX1>420) posX1 = 420;
              else posX1+=20*dir1;
              break;

              case ANDA2:
              estado1 = ANDA3;
              if(posX1<0) posX1 = 0;
              if(posX1>420) posX1 = 420;
              else posX1+=20*dir1;
              break;

              case ANDAVERT1:

              estado1 = ANDA3;
              if(posY1<=0) posY1 = 0;
              else posY1-=20;
              break;

              case ANDAVERT2:
              estado1 = ANDA3;
              if(posY1>=330) posY1 = 330;
              else posY1+=20;
              break;
             }


              switch(estado2){
              case ANDA12:
              estado2 = ANDA22;
              //if(posX2<=150) posX2 = 150;
              //if(posX2>=850) posX2 = 850;
              //else
              posX2+=20*dir2;
              break;

              case ANDA22:
              estado2 = ANDA3;
              //if(posX2<=150) posX2 = 150;
              //if(posX2>=850) posX2 = 850;
              //else
              posX2+=20*dir2;
              break;

              case ANDAVERT12:
              estado2 = ANDA3;
              if(posY2<=0) posY2 = 0;
              else posY2-=20;
              break;

              case ANDAVERT22:
              estado2 = ANDA3;
              if(posY2>=330) posY2 = 330;
              else posY2+=20;
              break;
            }
            repaint();
            sleep(100);

          }
        }catch(InterruptedException ex){}
      }
    }.start();

    addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
          case KeyEvent.VK_D:
            os.println("D");
            break;

          case KeyEvent.VK_A:
            os.println("A");
            break;

          case KeyEvent.VK_SPACE:
            //c.render(g);
            os.println("SP");

            break;

          case KeyEvent.VK_W:
            os.println("W");
            break;

          case KeyEvent.VK_S:
            os.println("S");
            break;
        }
      }


    });
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(des);
    pack();
    setVisible(true);
  }



  static public void main(String[] args) {

    music();
    new Thread(new JogoCowboy()).start();
  }

  public void run() {
    Socket socket = null;
    Scanner is = null;

    try {
      socket = new Socket("127.0.0.1", 1025);
      os = new PrintStream(socket.getOutputStream(), true);
      is = new Scanner(socket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host.");
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to host");
    }

    try {
      String inputLine;

      do {

        switch (inputLine=is.nextLine()){
          case "W1":
          estado1 = ANDAVERT1;
          repaint();
          if(posX1 >= 500)
            posX1 = 500;
          break;

          case "W2":
          estado2 = ANDAVERT12;
          repaint();
          break;

          case "A1":
          estado1 = ANDA1;
          dir1 = -1;
          dx1 = img[estado1].getWidth(null);
          repaint();
          if(posX1 >= 500)
            posX1 = 500;
          break;

          case "A2":
          estado2 = ANDA12;
          dir2 = -1;
          dx2 = img[estado2].getWidth(null);
          repaint();
          break;

          case "S1":
          //posY1+=10;
          estado1 = ANDAVERT2;
          repaint();
          break;

          case "S2":
          //posY2+=10;
          estado2 = ANDAVERT22;
          repaint();
          break;

          case "D1":
          dx1 = 0;
          estado1 = ANDA1;
          dir1 = 1;
            repaint();
          break;

          case "D2":
          dx2 = 0;
          estado2 = ANDA12;
          dir2 = 1;
            repaint();
          break;

          case "SP1":
          estado1 = TIRO1;
          if (bullet == null)
            readyToFire = true;
          if(readyToFire==true){
            bx = posX1+100;
            by = posY1+105;
            bullet = new Rectangle(bx, by, 60, 30);
            shot = true;
            if(shot){
            shoot(bx);

          }
          }

        repaint();
          break;

          case "SP2":
          estado2 = TIRO12;
          if (bullet == null)
            readyToFire = true;
          if(readyToFire==true){
            bx = posX2-100;
            by = posY2+105;
            bullet = new Rectangle(bx, by, 30, 50);
            shot = true;
            shoot2(bx);
          }

        repaint();
          break;
        }
      } while (!(is.nextLine()).equals("")); //

      os.close();
      is.close();
      socket.close();
    } catch (UnknownHostException e) {
      System.err.println("Trying to connect to unknown host: " + e);
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}
