import java.net.*;
import java.io.*;
import java.util.*;

class Servidor1 {
  public static void main(String[] args) {
    ServerSocket serverSocket=null;

    try {
      serverSocket = new ServerSocket(1025);
    } catch (IOException e) {
      System.out.println("Could not listen on port: " + 1025 + ", " + e);
      System.exit(1);
    }

    for (int i=0; i<2; i++) {
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
        System.out.println("Nova conexão com o cliente " +
    clientSocket.getPort()
);

       
      //  System.out.println(clientSocket);
     // System.out.println(player[i]);
  		
      } catch (IOException e) {
        System.out.println("Accept failed: " + 1025 + ", " + e);
        System.exit(1);
      }

      System.out.println("Accept Funcionou!");

      new Servindo(clientSocket , i).start();

    }

    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  		
  	
  					
  }
}


class Servindo extends Thread {
  Socket clientSocket;
  int id = 0;
  static PrintStream os[] = new PrintStream[2];
  static int cont=0;

  Servindo(Socket clientSocket , int id) {
    this.clientSocket = clientSocket;
    this.id = id;
  }

  public void run() {
    try {
      Scanner is = new Scanner(clientSocket.getInputStream());
      os[cont++] = new PrintStream(clientSocket.getOutputStream());
      String inputLine, outputLine;

      do {
        inputLine = is.nextLine();
        for (int i=0; i<cont; i++) {
         
         new Thread(){
          public void run(){
        		switch (inputLine){

        			case "W" :  
        			if(id == 0){
        			os[i].println("W1");
        			os[i].flush();		
        		   }
        		    else {
        			os[i].println("W2");
        			os[i].flush();		
        		   }

        		break;

				case "A" :  
        			if(id == 0){
        			os[i].println("A1");
        			os[i].flush();	
        				
        		}
        		    else {
        			os[i].println("A2");
        			os[i].flush();		
        		   }

        		break;	
					
					case "S" :  
        			if(id == 0){
        			os[i].println("S1");
        			os[i].flush();	
        			
        		}
        			else{
        			os[i].println("S2");
        			os[i].flush();		
        		   }	

        		break;	
        		
        		case "D" :  
        			if(id == 0){
        			os[i].println("D1");
        			os[i].flush();	
        				
        		}
        		    else{
        			os[i].println("D2");
        			os[i].flush();		
        		   }

        		break;	

            case "SP" :  
              if(id == 0){
              os[i].println("SP1");
              os[i].flush();  
                
            }
                else{
              os[i].println("SP2");
              os[i].flush();    
               }

            break;  
			}

         // os[i].println(inputLine);
          //os[i].flush();
        }
          }
        }.start();
      } while (!inputLine.equals(""));

      for (int i=0; i<cont; i++)
        os[i].close();
      is.close();
      clientSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchElementException e) {
      System.out.println("Conexacao terminada pelo cliente");
    }
  }
};
