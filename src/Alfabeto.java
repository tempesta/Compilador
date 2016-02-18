import java.util.HashMap;

public class Alfabeto {
	//alfabeto(tabela de simbolos
	public HashMap<String, String> geraClasses()
	{
		//cria a estrutura para guardar as classes contendo palavras reservadas, letras, digitos, etc
		HashMap<String, String> hash = new HashMap<String, String>();
		
		//cria o alfabeto
		String letras = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
		//cria os digitos
		String digitos = "0,1,2,3,4,5,6,7,8,9";
		//cria palavras reservadas
		String palavras_reservadas = ",struct,if,else,while,void,return,";
		//cria identificadores
		String identificadores = ",int,float,real,char,array,register,";
		//cria operadores relacionais
		String relacional = "=,<,>,<=,>=,==,!=";		
		
		//relaciona o alfabeto ao token letra
		hash.put("letra", letras);		
		//adicona a classe digitos
		hash.put("digitos", digitos);
		//adiciona simbolo multiplicacao
		hash.put("mult", "*,/,");
		//adiciona soma e subtracao
		hash.put("soma", "+,-,");
		//adiciona abre-colchete
		hash.put("abre_colchete", "[");
		//adiciona fecha-colchete
		hash.put("fecha_colchete", "]");
		//adiciona abre-chave
		hash.put("abre_chave", "{");
		//adiciona fecha-chave
		hash.put("fecha_chave", "}");
		//adiciona abre-parenteses
		hash.put("abre_parenteses", "(");
		//adiciona fecha-parenteses
		hash.put("fecha_parenteses", ")");
		//adciona ponto e virgula
		hash.put("ponto_virgula", ";");
		//adiciona palavras reservadas
		hash.put("palavras_reservadas", palavras_reservadas);
		//adiciona identificadores
		hash.put("identificadores", identificadores);
		//adiciona identificador de funcao
		hash.put("funcao", "function");
		//adciona comnetarios
		hash.put("comentario", "/*,*/,");
		//adiciona operadores relacionas
		hash.put("relacional", relacional);
		//adciona simbolo inicial
		hash.put("simbolo_inicial", ",program,");	
		//adiciona simbolo virgula
		hash.put("virgula", ",");
		//adiciona simbolo ponto
		hash.put("ponto", ".");
		//adiciona simbolo aspas
		hash.put("aspas", "\"");
		//adiciona nome das variaveis declaradas
		hash.put("variaveis_declaradas", ",");
		
		return hash;
	}
}
