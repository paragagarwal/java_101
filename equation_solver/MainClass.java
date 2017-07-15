
import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

/***
* Main Class
*/
public class MainClass {
	/***
	 * Constructor
	*/
	public MainClass()
	{	
	}
	/***
	 * Method to read input file and generate a list of lines
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private List<String> readFile(String path) throws IOException 
	{
		List<String> list=new ArrayList<String>();
	    File file = new File(path);
	    FileReader fr = new FileReader(file);
	    BufferedReader br = new BufferedReader(fr);
	    String line;
	    while((line = br.readLine()) != null){
	    		if(line.length() != 0){
	            	list.add(line);
	            }
	    }
	    br.close();
	    fr.close();
	    return list;
	}
	/****
	 * Method to check if input string is a valid string
	 * which contains characters which satisfy isAlpha(c)
	 * @param str
	 * @return
	 */
	private boolean checkVariable(String str){
		return str.matches("[a-zA-Z]+");
	}
	/***
	 * Method to check if input string is a valid unsigned integer
	 * @param s
	 * @return
	 */
	private boolean isUnsignedInteger(String s)
	{
		try
		{
			 Integer.parseUnsignedInt(s);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/***
	 * Method to parse equation and create object Equation 
	 * else it will return null if this is not a valid equation
	 * @param str
	 * @return
	 */
	public Equation parseEquation(String str)
	{
		Equation eq=new Equation(str);
		String[] tokens = str.trim().split("=");
		if(tokens.length != 2)
		{
			return null;
		}
		if(!checkVariable(tokens[0].trim()))
		{
			return null;
		}
		eq.lhs=tokens[0].replace(" ","");
		boolean seenPlus=false;
		int count=0;
		try{
			for(String s:tokens[1].trim().split(" ")){
				boolean checkVar=checkVariable(s);
				boolean checkUnSignedInteger=isUnsignedInteger(s);
				if(s.trim().equals("")) continue;
				if(!seenPlus && s.equals("+"))
				{
					if(count == 0){
						return null;
					}
					seenPlus=true;
				}else if(seenPlus && s.equals("+"))
				{
					return null;
				}else if(seenPlus && !checkVar && !checkUnSignedInteger)
				{
					return null;
				}else if(checkVar)
				{
					if(seenPlus)
					{
						seenPlus=false;
					}
					++count;
					if(eq.rhs.containsKey(s))
					{
						int v=eq.rhs.get(s);
						eq.rhs.put(s,v+1);
					}else{
						eq.rhs.put(s,1);
					}
				}else if(checkUnSignedInteger){
					if(seenPlus){
						seenPlus=false;
					}
					++count;
					eq.rhsSum+=Integer.parseUnsignedInt(s);
				}else if(!s.trim().equals("")){
						return null;
				}
			}
		}catch(Exception e){
			return null;
		}
		if(seenPlus){
			return null;
		}
		if(eq.rhs.containsKey(eq.lhs)){
			return null;
		}
		return eq;
	}

	/****
	 * Method to solve equations given a list of parsed equations
	 * @param equations
	 * @return
	 */
	public List<Equation> solveEquation(List<Equation> equations)
	{
		List<Equation> unsolved=new ArrayList<Equation>();
		List<Equation> solved=new ArrayList<Equation>();
		List<Equation> newvals=new ArrayList<Equation>();
		for(Equation e:equations)
		{
			if(e.rhs.size() == 0)
			{
				solved.add(e);
				newvals.add(e);
			}else{
				unsolved.add(e);
			}
		}
		while(unsolved.size() >  0)
		{
			if(solved.size() == 0 && unsolved.size() > 0)
			{
				return null;
			}
			List<Equation> col=new ArrayList<Equation>();
			for(Equation val:solved)
			{
				String k=val.lhs;
					for(Equation e:unsolved){
						if(e.rhs.containsKey(k)){
							e.rhsSum+=val.rhsSum*e.rhs.get(k);
							e.rhs.remove(k);
						}
					}
				col.add(val);
			}

			for(Equation e:col)
			{
				solved.remove(e);
			}
			col=new ArrayList<Equation>();
			for(Equation e:unsolved)
			{
				if(e.rhs.size() == 0){
					col.add(e);
				}
			}
			for(Equation e:col){
				unsolved.remove(e);
				solved.add(e);
				newvals.add(e);
			}

			col=null;

		}

		if(unsolved.size() !=  0)
		{
			return null;
		}else{
			return newvals;
		}
	}
	/****
	 * Method to print equations from a list of solved equations
	 * @param equations
	 */
	public void printEquations(List<Equation> equations)
	{
		Map<String, String> treeMap = new TreeMap<String, String>();
		for(Equation eq:equations)
		{
			treeMap.put(eq.lhs, eq.lhs+" = "+eq.rhsSum);
			eq=null;
		}
		for(String k:treeMap.keySet())
		{
			System.out.println(treeMap.get(k));
		}
		treeMap=null;
	}
	/***
	 * Method to prints input equations
	 * @param list
	 */
	public void printInput(List<String> list)
	{
		for(String s:list){
			System.out.println(s);
		}
	}
	/****
	 * Main Class
	 * @param args
	 */
	public static void main(String[] args)
	{
		MainClass eq=new MainClass();
		List<Equation> array=new ArrayList<Equation>();
		if(args.length == 0){
			System.out.println("input file missing");
			return;
		}
		String file=args[0];
		try{
			List<String> list=eq.readFile(file); 
			for(String e:list){
				Equation e1 = eq.parseEquation(e);
				if(e1 != null){
					array.add(e1);
				}else{
					eq.printInput(list);
					return;
				}
			}
			List<Equation> newVals  = eq.solveEquation(array);
			if(newVals != null){
				eq.printEquations(newVals);
			}else{
				eq.printInput(list);
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}
}

/***
* Class to represent equation
*/
class Equation {
	String lhs;
	Map<String, Integer> rhs;
	int rhsSum=0;
	String equation;
	public Equation(String equation)
	{
		this.equation=equation;
		this.rhs=new HashMap<String,Integer>();
	}
	@Override
	public String toString() {
		return "Equation [lhs=" + lhs + ", rhs=" + rhs + ", rhsSum=" + rhsSum + ", equation=" + equation + "]";
	}
}
