/**
 * 
 */

/**
 * @author lgm
 *
 */
public class SeuLex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "Cminus.l";
		if ( s.length()==0 )
		{
			//System.err.println("Lex File Name not Given!");
			//return;
			
		}
		LexFileReader lexFileReader = new LexFileReader(s);
		CodeGen cg  = new CodeGen();
		cg.write(lexFileReader.getCDefPart()+ "\n");
		NFA nfas[] = new NFA[lexFileReader.getRegularExpressionNumber()];
		System.out.println("Contructing NFAs...");
//		for ( int i=lexFileReader.getRegularExpressionNumber()-1; i>=0; i-- ) {
//			String regExpr = lexFileReader.getRegularExpression()[i];
//			System.out.println(regExpr);
//			nfas[i] = new NFA(regExpr, lexFileReader);
//		}		
		for ( int i=0; i<lexFileReader.getRegularExpressionNumber(); i++ ) {
			String regExpr = lexFileReader.getRegularExpression()[i];
			nfas[i] = new NFA(regExpr, lexFileReader);
			System.out.println("Job Done! Got NFA with "+nfas[i].numState+" States ---- "+regExpr);
		}
		System.out.println("Merging NFAs...");
		MergedNFA mn = new MergedNFA(nfas);
		mn.print();
		
		System.out.println("Job Done! Got Merged NFA with "+mn.numState+" States");		
		System.out.println("Constructing DFA...");
		DFA dfa = new DFA (mn);
		System.out.println("Job Done! Got DFA with "+dfa.getNumStates()+" States");
		System.out.println("Minimizing DFA...");
		dfa.minimize();
		System.out.println("Job Done! Got Minimized DFA with "+dfa.getNumStates()+" States");
		System.out.println("Generating Code...");
		if (Debug.TRUE)
			dfa.print();
		cg.genTable(dfa.getTable(), "TABLE");
		int a[] = dfa.getStatePattern();
		cg.genVector(dfa.getStatePattern(), "STATE_PATTERN");
		cg.genDriver(lexFileReader.getCCode());
		cg.write(lexFileReader.getCSubRoutine()+"\n");
		cg.close();
		System.out.println("Job Done! Got Origin C++ Code in temp.data");
		System.out.println("Formatting Code...");
		Formatter f = new Formatter("temp.data", "SeuLex_Generated_Code.cpp");
		f.format();
		System.out.println("Job Done! Got Formatted C++ Code in SeuLex_Generated_Code.cpp");
	}
	//}

	private static String lexFileName;
}
