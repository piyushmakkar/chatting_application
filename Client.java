package chat;
import  javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import java.awt.*;
import java.awt.event.*;

import java.net.*;
import java.io.*;

public class Client implements ActionListener{
	static JFrame f1 = new JFrame();
	JPanel p1; //panel act same as div tag of html.
	JTextField t1;
	JButton b1;
	static JPanel a1;
	
	static Box vertical = Box.createVerticalBox();//align messages
	
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	
	Boolean type;//false initially
	
	Client(){
		
		p1 = new JPanel();
		p1.setLayout(null); 
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0, 0, 285, 50);
		f1.add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chat/icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5,10,30,30);
		p1.add(l1);
		l1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chat/icons/2.png"));
		Image i5 = i4.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40,7,35,35);
		p1.add(l2);
		
		JLabel l3 = new JLabel("Raghav");
		l3.setFont(new Font("SAN_SERIF",Font.BOLD,14));
		l3.setForeground(Color.WHITE);
		l3.setBounds(80,0,100,30);
		p1.add(l3);
		
		JLabel l4 = new JLabel("Active Now");
		l4.setFont(new Font("SAN_SERIF",Font.PLAIN,10));
		l4.setForeground(Color.WHITE);
		l4.setBounds(80,10,100,40);
		p1.add(l4);
		
		Timer t = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(!type) //if not true i.e false hence false = false results into true 
				{
					l4.setText("Active Now");
				}
			}
		});
		
		t.setInitialDelay(1000); // initial delay of changing the status
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chat/icons/video.png"));
		Image i8 = i7.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel l5 = new JLabel(i9);
		l5.setBounds(170,15,20,20);
		p1.add(l5);
		
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chat/icons/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel l6 = new JLabel(i12);
		l6.setBounds(210,15,20,20);
		p1.add(l6);
		
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chat/icons/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel l7 = new JLabel(i15);
		l7.setBounds(250,17,15,15);
		p1.add(l7);
		
		a1 = new JPanel();
		//a1.setBounds(0,50,285,270);
		a1.setFont(new Font("SAN_SERIF",Font.PLAIN,14));;
		//f1.add(a1);
		
		JScrollPane sp = new JScrollPane(a1);// object of class is called in which we have to add the scroll bar
		sp.setBounds(0,50,285,270);
		sp.setBorder(BorderFactory.createEmptyBorder());
		
		ScrollBarUI ui = new BasicScrollBarUI()
		{
			protected JButton createDecreaseButton (int orientation) // to change color of decrease button
			{
				JButton button = super.createDecreaseButton(orientation); // calling constructor of super class
				button.setBackground(new Color(7,94,84));
				button.setForeground(Color.WHITE);
				this.thumbColor = new Color(7,94,84); // to change the color of thumb i.e scroll line
				return button;
			}
			
			protected JButton createIncreaseButton (int orientation) // to change color of increase button
			{
				JButton button = super.createIncreaseButton(orientation);
				button.setBackground(new Color(7,94,84));
				button.setForeground(Color.WHITE);
				this.thumbColor = new Color(7,94,84);
				return button;
			}
		};
		
		sp.getVerticalScrollBar().setUI(ui);
		f1.add(sp);
		
		t1 = new JTextField();
		t1.setBounds(5,325,180,30);
		t1.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
		f1.add(t1);
		
		t1.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke) 
			{
				l4.setText("Typing...");//when key is pressed this is called
				t.stop();
				type = true;
			}
			public void keyReleased(KeyEvent ke)
			{
				type = false;  //called after pressed is called
				if(!t.isRunning())
				{
					t.start();
				}
			}
		});
		
		b1 = new JButton("Send");
		b1.setBounds(195,325,80,30);
		b1.setBackground(new Color(7,94,84));
		b1.setForeground(Color.WHITE);
		b1.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
		b1.addActionListener(this);
		f1.add(b1);
		
		f1.setLayout(null); //if pre-made layouts not needed.
		f1.setSize(300,400); //defines size of the frame.
		f1.setLocation(800,100); //defines position of frame on the screen.
		f1.setVisible(true); //default visibility is false. written in the end as its position matter the most.
	}
	
	public void actionPerformed(ActionEvent ae)  
	{
	
		try {
			String out = t1.getText();
			
			JPanel p2 = formatLabel(out);
			a1.setLayout(new BorderLayout());
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2,BorderLayout.LINE_END);//to align in right
			vertical.add(right);
			a1.add(vertical ,BorderLayout.PAGE_START);
			
			dout.writeUTF(out);
			t1.setText("");
	}catch(Exception e) {}
		}
	
	public static JPanel formatLabel(String out)//to format label on new messages {
	{
		JPanel p3 = new JPanel();
		
		JLabel l1 = new JLabel("<html><p style = \"width : 70px\">"+out+"</p></html>"); // to add label on message
		l1.setFont(new Font("Tahoma",Font.PLAIN,14));
		l1.setBackground(new Color(37,211,102));
		l1.setOpaque(true);// for color to be visible
		l1.setBorder(new EmptyBorder(10,10,10,10));//empty border dimensions
		
		p3.add(l1);
		return p3;
	}
	
	public static void main(String[] args)
	{
		new Client().f1.setVisible(true);
		
		String mssgin = "";
		
		try {
			s = new Socket("127.0.0.1",3000);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			while(true)
			{
				mssgin=din.readUTF();
				JPanel p2 = formatLabel(mssgin);
				
				JPanel left = new JPanel(new BorderLayout());
				left.add(p2, BorderLayout.LINE_START);
				vertical.add(left);
				f1.validate();
			}
			
			//s.close();
			
		}catch(Exception e) {}
		
	}
}
