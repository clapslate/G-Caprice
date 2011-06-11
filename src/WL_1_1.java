//------------------- CLASS for CLOSING FRAME ----------------------


class WL_1_1 extends WindowAdapter //pg 681
{

	//String s;
	G_Caprice_LAN_1_1 g;
	//Add a;

	WL_1_1(G_Caprice_LAN_1_1 g)
	{
		//this.s = s;
		this.g = g;
		//this.a = a;
	}


	public void windowClosing(WindowEvent we)
	{
		System.out.println("Reached here!");
		//FileOutputStream f2 = new FileOutputStream("LAN list");
		try
		{
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("LAN list.txt")));
			for(int j = 0; j<g.l.getItemCount(); j++)
			{
				pw.println(g.comps[j]);
				pw.println(g.l.getItem(j));
			}
			pw.close();
		}
		catch(Exception e)
		{}


		System.exit(0);							// Stop Program Execution

	}

	public void windowOpened(WindowEvent we)
	{
		FileInputStream f1 =null;
		try
		{
			f1 = new FileInputStream("LAN list.txt");
			int i=0;
			String name;
			BufferedReader obj = new BufferedReader ( new InputStreamReader (f1));
			while((name=obj.readLine())!=null)
			{
				g.comps[i] = name;
				g.l.add(obj.readLine());
				i++;

			}
			System.out.println("Window Opened!");
		}
		catch(Exception e)
		{}
	}

}

//-----------------------------------------------------------------
