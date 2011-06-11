import java.awt.*;				// Package(that contains classes) for Graphical components
import java.awt.event.*;		// Package(that contains classes) for Events like mouse click, key press, etc.
import java.net.*;				// Package(that contains classes) for networking
import java.io.*;				// Package(that contains classes) for input & output




//-------------------- FRAME(CHAT WINDOW) CLASS --------------------------------

class G_Caprice_LAN_1_1 extends Frame implements ActionListener,ItemListener,KeyListener
{

	//****************** FRAME COMPONENTS *******************

	Label l1 = new Label("Enter computer name to connect to:",Label.RIGHT);
	Label l2 = new Label("Type here:",Label.RIGHT);
	Label l3 = new Label("LAN LIST:",Label.LEFT);
	Label l4 = new Label("Enter nickname for it: ");
	TextField f2 = new TextField("My PC");
	TextField f1 = new TextField("localhost");
	TextArea t1 = new TextArea("------Your chat with "+f2.getText()+"------",5,5,TextArea.SCROLLBARS_VERTICAL_ONLY);
	TextArea t2 = new TextArea("hello",5,5,TextArea.SCROLLBARS_NONE);
	Button b1 = new Button("Add");
	Button b2 = new Button("Send");
	List l = new List();
	Socket s;
	PrintWriter pw;
	String comps[] = new String[254];
	Font f = new Font("SansSerif",Font.BOLD|Font.ITALIC,16);
	Font fo2 = new Font("SansSerif",Font.BOLD,17);
	Font f3 = new Font("SansSerif",Font.ITALIC,17);
	Color c1 = new Color(0,0,0);
	Color c2 = new Color(200,200,200);
	Color c3 = new Color(255,110,110);
	Color c4 = new Color(255,200,0);

	//Cur c = new Cur("Type here");

	//*******************************************************


	G_Caprice_LAN_1_1(String title)			//	Constructor
	{
		super(title);

		add(l1);
		add(l2);
		add(l3);
		add(l4);							// Adding Components to chat window
		add(f1);
		add(f2);
		add(t1);
		add(t2);
		add(b1);
		add(b2);
		add(l);

		addWindowListener(new WL_1_1(this));
		b2.addActionListener(this);			// Registering events like Mouse click,
		b1.addActionListener(this);			// List Item Selection, etc.
		l.addItemListener(this);
		t2.addKeyListener(this);

		t1.setEditable(false);				// Don't let user edit chat area

	}

	void init()
	{

	}

	public void paint(Graphics g)							//	AESTHETICS FUNCTION
	{

		b1.setCursor(new Cursor(Cursor.HAND_CURSOR));		//	SETTING CURSOR
		b2.setCursor(new Cursor(Cursor.HAND_CURSOR));		//	FOR BUTTONS

		l1.setSize(new Dimension(275,25));
		l2.setSize(new Dimension(100,25));
		l3.setSize(new Dimension(100,25));					//
		l4.setSize(new Dimension(170,25));
		f1.setSize(new Dimension(250,25));
		f2.setSize(new Dimension(250,25));					//	SETTING SIZE
		t1.setSize(new Dimension(600,300));
		t2.setSize(new Dimension(450,75));
		b1.setSize(new Dimension(75,30));					//
		b2.setSize(new Dimension(75,40));
		l.setSize(new Dimension(200,400));


		l1.setLocation(10,60);
		l2.setLocation(10,460);
		l3.setLocation(750,75);								//
		l4.setLocation(10,100);
		f1.setLocation(315,60);
		f2.setLocation(315,100);							//	SETTING LOCATION
		t1.setLocation(50,140);
		t2.setLocation(125,460);
		b1.setLocation(630,80);								//
		b2.setLocation(590,475);
		l.setLocation(750,100);


		l1.setFont(f);
		l2.setFont(f);
		l3.setFont(f);										//
		l4.setFont(f);
		f1.setFont(fo2);
		f2.setFont(fo2);										//	SETTING FONT
		t1.setFont(fo2);
		t2.setFont(fo2);
		b1.setFont(f);										//
		b2.setFont(f);
		l.setFont(fo2);


		l1.setBackground(c1);
		l2.setBackground(c1);
		l3.setBackground(c1);								//
		l4.setBackground(c1);
		f1.setBackground(Color.gray);
		f2.setBackground(Color.gray);						//	SETTING BACKGROUND COLOR
		t1.setBackground(c1);
		t2.setBackground(c1);
		b1.setBackground(Color.darkGray);					//
		b2.setBackground(Color.darkGray);
		l.setBackground(Color.gray);


		l1.setForeground(c2);
		l2.setForeground(c2);
		l3.setForeground(c2);								//
		l4.setForeground(c2);
		f1.setForeground(Color.white);
		f2.setForeground(Color.white);						//	SETTING FOREGROUND COLOR
		t1.setForeground(c3);
		t2.setForeground(c3);
		b1.setForeground(c4);								//
		b2.setForeground(c4);
		l.setForeground(Color.white);

		g.setColor(c2);
		g.drawLine(627,95,570,72);
		g.drawLine(627,95,570,112);

		g.drawLine(575,82,570,72);
		g.drawLine(580,68,570,72);

		g.drawLine(582,118,570,112);
		g.drawLine(578,102,570,112);

	}


	//************************** MAIN() ******************************

	public static void main(String args[])
	{
		G_Caprice_LAN_1_1 g = new G_Caprice_LAN_1_1("G-Caprice LAN chat(Beta) by Mohit Gvalani");	//	Creating a chat window
		g.setSize(new Dimension(1000,600));															//	and setting its dimension,
		g.setVisible(true);																			//	making it visible,
		g.setBackground(new Color(0,0,0));															//	setting its background color
		g.setResizable(false);																		//	and not allowing the user to resize it.

		ServerSocket ss = null;
		try
		{
			ss = new ServerSocket(9999);				// ServerSocket for receiving messages at Port no. 9999
		}
		catch(IOException e)
		{
			System.out.println(e);
		}


		//############## MESSAGE RECEIVING PROCESS ###############

		while(true)
		{
			Socket s;						//	Server Side normal Socket that acts as Client socket
			BufferedReader br;				//	Reader Object
			String str,user = null;

			try
			{
				s = ss.accept();			//	Accept connection from ANY client
				br= new BufferedReader (new InputStreamReader(s.getInputStream()));		// Reader Object that reads from the Socket Connection

				if((g.f2.getText()=="")||(g.f2.getText()==null))		//	Deciding chat name
					user = g.f1.getText();							//	as comp name	[aliter: s.getInetAddress().getHostName()]
				else												//	OR
					user = g.f2.getText();							//	as nick name

				g.t1.append("\n\n"+user+":");

				while ((str=br.readLine ())!=null)		//	Reading Line typed
				{										//	by Client & storing it in "str"
					g.t1.append(str+"\n");					//	and then appending it to
				}										//	the chat area.

				br.close ();		//	close the
				s.close ();			//	connection
			}

			catch (UnknownHostException e)
			{
				System.err.println ("Could not resolve hostname:" + e.getMessage ());
			}

			catch (IOException e)
			{
				System.out.println ("Connection error and failed");
			}
		}

		//###################################################
	}

	// ***************************************************************



	//****************** EVENT LISTENER FOR SEND & ADD (MOUSE) *********************

	public void actionPerformed(ActionEvent ae)		// This function caters to the interrupt generated by mouse clicked on "Send" or "Add" Button
	{
		if(ae.getActionCommand()=="Send")			// If "Send" Button was clicked
		{
			try
			{
				s = new Socket(f1.getText(),9999);										// 	Creates a Socket at Port no. 9999
				pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));		//	Creates a Writer Object
				t1.append("\n\nMe: ");													//	Updates the
				t1.append(t2.getText());												//	chat area
				pw.println(t2.getText());												//	Sends the typed data to the other end with the help of Writer object

				t2.selectAll();

				if (s !=null)
				{
					pw.close();						// Close the
					s.close();						// connection
				}

			}

			catch (UnknownHostException e)
			{
				System.err.println ("Could not resolve hostname:" + e.getMessage ());
				t1.append("\n\nComp: "+f1.getText()+"\nNickname: "+f2.getText()+"\nDoesn't exist or is OFF LAN\n");
			}

			catch (IOException e)
			{
				System.out.println ("connect error and failed");
			}
		}

		if(ae.getActionCommand()=="Add")				// If "Add" Button was clicked
		{
			boolean flag = false;
			int co = l.getItemCount();					// "co" contains no.of items in "LAN LIST" List

			for(int i=0; i<co; i++)
			{
				if(f2.getText().compareTo(l.getItem(i))==0)			// "for" loop checks if nickname already exists
				{
					flag = true;
					f2.setText("Nickname already exists");
					f2.selectAll();
					break;
				}
			}

			if(flag == false)							//	If nickname doesn't exists
			{
				l.add(f2.getText());					//	Add it to the list
				comps[co] = f1.getText();				// 	Add corresponding comp name to array
			}
		}

	}

	// ****************** EVENT LISTENER FOR ITEM SELECTION ***********************

	public void itemStateChanged(ItemEvent e)		// This function caters to the interrupt generated by selection of a "LAN LIST" List Item
	{
		Object st = e.getItem();					//	Gets the Item no. in the List selected in "Object" form
		int no = Integer.parseInt(st.toString());	//	Converts the "Object" into "integer"
		f1.setText(comps[no]);						//	Sets comp name for selected item (i.e. nickname)
		f2.setText(l.getItem(no));					// 	Sets the nickname as selected by user
		t2.requestFocus();							// Transfer the typing cursor('I' cursor) to the area where user types his/her message

		t1.append("\n\n------Your chat with "+f2.getText()+"------");
	}


	//****************** EVENT LISTENER FOR SEND (KEYBOARD) *********************
	public void keyTyped(KeyEvent ke)
	{
		//String test="\nSee\nMohit\n",temp = null;

		if(ke.getKeyChar()=='\n')				//	If key typed is 'Enter'
		{
			if(ke.isShiftDown()==true)			//	If 'Shift+Enter', go to next line
			{
			}
			else								// If only 'Enter', then SEND
			{
				try
				{
					/*temp = t2.getText();									//	Remove the character
					System.out.println("temp before: "+temp);
					int len = temp.length();								//	that represents 'Enter'
					temp = temp.substring(0,len-1);							//	from the message
					t2.setText(temp);										// 	to be sent

					System.out.println("temp after: "+temp+test+"length of test: "+test.length());*/

					s = new Socket(f1.getText(),9999);										// 	Creates a Socket at Port no. 9999
					pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));		//	Creates a Writer Object
					t1.append("\n\nMe: ");													//	Updates the
					t1.append(t2.getText());												//	chat area
					pw.println(t2.getText());												//	Sends the typed data to the other end with the help of Writer object

					t2.selectAll();

					if (s !=null)
					{
						pw.close();						// Close the
						s.close();						// connection
					}

				}

				catch (UnknownHostException e)
				{
					System.err.println ("Could not resolve hostname:" + e.getMessage ());
					t1.append("\n\nComp: "+f1.getText()+"\nNickname: "+f2.getText()+"\nDoesn't exist or is OFF LAN\n");
				}

				catch (IOException e)
				{
					System.out.println ("connect error and failed");
				}
			}
		}
	}

	public void keyPressed(KeyEvent ke)
	{}

	public void keyReleased(KeyEvent ke)
	{}

}





//----------------------- CLASS for CLOSING & OPENING FRAME (CHAT WINDOW) ---------------------


class WL_1_1 extends WindowAdapter //pg 681
{

	G_Caprice_LAN_1_1 g;

	WL_1_1(G_Caprice_LAN_1_1 g)
	{
		this.g = g;
	}

	//******************** WHEN WINDOW IS CLOSED *************************

	public void windowClosing(WindowEvent we)
	{
		System.out.println("Reached here!");
		try
		{
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("LAN list.txt")));	//	Get a writer object for text file - LAN list.txt
			for(int j = 0; j<g.l.getItemCount(); j++)
			{
				pw.println(g.comps[j]);					//	Transfer all the LIST & array contents
				pw.println(g.l.getItem(j));				//	to the text file "LAN list.txt"
			}
			pw.close();
		}
		catch(Exception e)
		{}


		System.exit(0);							// STOP PROGRAM EXECUTION

	}



	//******************** WHEN WINDOW IS OPENED *************************

	public void windowOpened(WindowEvent we)
	{
		FileInputStream f1 =null;
		try
		{
			f1 = new FileInputStream("LAN list.txt");		//	Open file "LAN list.txt" for manipulation
			int i=0;
			String name;
			BufferedReader obj = new BufferedReader ( new InputStreamReader (f1));		// Get a reader object for text file - LAN list.txt
			while((name=obj.readLine())!=null)
			{
				g.comps[i] = name;				//	Transfer the file contents to
				g.l.add(obj.readLine());		//	the LIST and array
				i++;

			}
		}
		catch(Exception e)
		{}
	}

}

//--------------------------------------------------------------------------------------------
