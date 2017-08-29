import java.io.FileWriter;
import java.io.IOException;


/**
 * 
 */

/**
 * @author lgm
 *
 */
public class CodeGen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String rules[] = {"cout<<\"white space\\n\";","cout<<\"right para\\n\";" };
//		String defPart = "using namespace std;\n";
//		String userSubroutine = "int main() { seuLex(); return 0;}\n";
//		
//		NFA n1 = new NFA(" |\t|\n");
//		NFA n2 = new NFA(")");
//		NFA nfas[] = {n1,n2 };
//		MergedNFA mn = new MergedNFA(nfas);
//		DFA dfa = new DFA(mn);
//		dfa.print();
//		CodeGen cg = new CodeGen();
//		cg.write(defPart);
//		cg.genTable(dfa.getTable(), "TABLE");
//		cg.genVector(dfa.getStatePattern(), "STATE_PATTERN");
//		cg.genDriver(rules);
//		cg.write(userSubroutine);
//		cg.close();
	}
	
	public CodeGen() {
		try {
			fileWriter = new FileWriter("temp.data");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		genInclude("iostream");
		genInclude("stack");
		genInclude("deque");
		genInclude("cassert");
		genInclude("string");
	}
	
	public void genDriver(String rules[]) {
		writeln("std::string seuLexLastLex;");
		writeln("int seuLex(std::istream& cin = std::cin, std::ostream& cout = std::cout)");
		writeln("{");
		writeln("while ( true )");
		writeln("{");
		writeln("int currentState = 0;");
		writeln("int matchedState = 0;");
		writeln("int currentLength = 0;");
		writeln("int matchedLength = 0;");
		writeln("seuLexLastLex.clear();");
		writeln("char c;");
		writeln("std::stack<int> s;");
		writeln("std::deque<char> q;");
		writeln("while ( currentState!=-1 && cin.get(c) )");
		writeln("{");
		writeln("q.push_back(c);");
		writeln("currentLength++;");
		writeln("currentState = TABLE[currentState][c];");
		writeln("if ( STATE_PATTERN[currentState] != -1 )");
		writeln("{");
		writeln("matchedState = currentState;");
		writeln("matchedLength = currentLength;");
		writeln("}");
		writeln("}");
		writeln("if ( matchedLength>0 )");
		writeln("{");
		writeln("while ( currentLength>matchedLength )");
		writeln("{");
		writeln("cin.putback(q.back());");
		writeln("q.pop_back();");
		writeln("currentLength--;");
		writeln("}");
		writeln("while ( !q.empty() )");
		writeln("{");
		writeln("seuLexLastLex += q.front();");
		writeln("q.pop_front();");
		writeln("}");
		writeln("switch ( STATE_PATTERN[matchedState] )");
		writeln("{");
		for ( int i=0; i<rules.length; i++ ) {
			write("case "+ i +":\n");
			write(rules[i]+"\n");
			write("break;\n");
		}
		writeln("default:");
		writeln("assert(false);");
		writeln("}");
		writeln("}");
		writeln("else ");
		writeln("{");
		writeln("return -1;");
		writeln("}");
		writeln("}");
		writeln("return 0;");
		writeln("}");
	}
	
	public void genInclude(String head) {
		write("#include <"+head+">\n");
	}
	
	public void genConstant(int c, String name ) {
		write("const int "+name+" = "+c+";\n");
	}
	
	public void genTable( int t[][], String name) {
		write("const int "+name+"[" + t.length + "][" + t[0].length + "] = {\n");
		for ( int i=0; i<t.length; i++ ) {
			write("\t");
			for ( int j=0; j<t[i].length; j++ ) {
				write(t[i][j] + ",");
			}
			write("\n");
		}
		write("};\n");
	}
	
	public void genVector( int v[], String name ) {
		write("const int "+name+"["+ v.length + "] = {");
		for ( int i=0; i<v.length; i++ ) {
			write(v[i] + ",");
		}
		write("};\n");
	}
	
	public void close() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeln( String str ) {
		write(str+"\n");
	}
	public void write( String str ) {
		try {
			fileWriter.write(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private FileWriter fileWriter;
}
