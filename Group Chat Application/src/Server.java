
import java.net.*;
import java.io.*;
import java.util.*;
public class Server implements Runnable{
    Socket socket;
    public static Vector clientsVector = new Vector();
    public Server(Socket socket)
    {
       try{
            this.socket = socket;
       }
       catch(Exception e){}
    }
    public void run()
    {
        try{
           BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
           
           //add clients writer ,where we can write the msg
           clientsVector.add(writer);
           while(true)
           {
               String str = reader.readLine().trim();
               System.out.println("Received " + str);
               
               //transmit the msg to the clients
               for(int i = 0;  i < clientsVector.size(); i++)
               {
                   try{
                        BufferedWriter bw = (BufferedWriter)clientsVector.get(i);
                        bw.write(str);
                        bw.write("\r\n");
                        bw.flush();
                   }
                   catch(Exception aee){}
               }
           }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public static void main(String args[])throws IOException
    {
        ServerSocket s = new ServerSocket(2001);
        int count = 0;
        while(true)
        {
            ++count;//client count
            
            Socket socket = s.accept();//accept client Requests
            
            //show client information in terminal
            System.out.println("client  " + socket  + " is Connected to server...");
            System.out.println("No of Client : "+count);
            
            //as many client requests we have ,as many we have to make as many Server Objects
            Server myserver = new Server(socket);
            
            //as many client requests we have ,as many we have to make as many threads
            Thread t = new Thread(myserver);
            t.start();
            
        }
    }
}
