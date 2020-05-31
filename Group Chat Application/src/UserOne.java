import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class UserOne extends JFrame implements ActionListener,Runnable {
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JTextArea textArea;
    static Socket socketClient;
    
    BufferedWriter writer;
    BufferedReader reader;
    UserOne()
    {
      
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground( new Color (57, 50, 92));
        p1.setBounds(0,0,400,50);
        add(p1);
        
       //icon
       ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
       Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
       ImageIcon i3 = new ImageIcon(i2);
       JLabel l1 = new JLabel(i3);
       l1.setBounds(5,15,25,25 );
       p1.add(l1);
       l1.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              System.exit(0);
          }
       });
       
       //dp
       ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/iconGrp1.png"));
       Image i5 = i4.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
       ImageIcon i6 = new ImageIcon(i5);
       JLabel l2 = new JLabel(i6);
       l2.setBounds(30,3,60,45 );
       p1.add(l2);
       
       //icon1 : video
       ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
       Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
       ImageIcon i9= new ImageIcon(i8);
       JLabel l3 = new JLabel(i9);
       l3.setBounds(250,10,30,30 );
       p1.add(l3);
       //icon2:phone
       ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
       Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
       ImageIcon i12 = new ImageIcon(i11);
       JLabel l4 = new JLabel(i12);
       l4.setBounds(300,10,35,30 );
       p1.add(l4);
       //icon3:3dot
       ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
       Image i14 = i13.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
       ImageIcon i15 = new ImageIcon(i14);
       JLabel l5 = new JLabel(i15);
       l5.setBounds(360,3,10,45 );
       p1.add(l5);
       
       //Name
       JLabel lname = new JLabel("Schools Friends");
       lname.setFont(new Font("SAN_SERIF",Font.BOLD,18));
       lname.setForeground(Color.WHITE);
       lname.setBounds(90,5,150 ,20);
       p1.add(lname);
       
       //Active Status
       JLabel lstatus = new JLabel("Alice, Bob, Raj, Kriston, David, Smith");
       lstatus.setFont(new Font("SAN_SERIF",Font.PLAIN,11));
       lstatus.setForeground(Color.WHITE);
       lstatus.setBounds(95,21,100 ,20);
       p1.add(lstatus);
       
       //Message Typing
       t1 = new JTextField();
       t1.setBounds(2,560,300,40);
       t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
       add(t1);
       
       //send Button
       b1 = new JButton("Send");
       b1.setBounds(305,560,90,39);
       b1.setBackground(new Color(57, 50, 92));
       b1.setForeground(Color.WHITE);
       b1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
       add(b1);
       b1.addActionListener(this);
       
       //textArea
       textArea = new JTextArea();
       textArea.setBounds(0,50,395,510);
       textArea.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
       textArea.setEditable(false);
       //textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);//Right me krne ke liye
       textArea.setLineWrap(true);
       textArea.setWrapStyleWord(true);
       //textArea.setBackground(Color.pink);
       add(textArea);
       
       
       
       setSize(400,600);
       setLocation(20,100);
       setLayout(null);
       //getContentPane().setBackground(Color.yellow);
       setUndecorated(true);
       setVisible(true);
       
       
       try{
         socketClient = new Socket("localhost",2001);
         writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
         reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
       }
       catch(Exception ae)
       {}
       
    }
    public void actionPerformed(ActionEvent e)
    {
       String str = "David\n"+t1.getText();
        try{
            writer.write(str);
            writer.write("\r\n");
            writer.flush();
        }catch(Exception ae){}
        t1.setText("");
        
    }
    //beacause we have to keep receiving a msg
    public void run()
    {
        try{
            String data = "";
            while((data= reader.readLine()) != null){
                textArea.append(data + "\n");
            }
        }
        catch(Exception e){}
    }
    public static void main(String args[])
    {
       UserOne one = new UserOne();
       one.setVisible(true);
       
       Thread t1 = new Thread(one);
       t1.start();
    }
}
