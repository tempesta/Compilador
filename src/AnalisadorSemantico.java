import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalisadorSemantico {

	private Map<String, ArrayList<String>> declaracoesBloco;

	public AnalisadorSemantico() {
		super();
		this.declaracoesBloco = new HashMap<String, ArrayList<String>>();
	}

	public AnalisadorSemantico(Map<String, ArrayList<String>> declaracoesBloco) {
		super();
		this.declaracoesBloco = declaracoesBloco;
	}

	public Map<String, ArrayList<String>> getDeclaracoesBloco() {
		return declaracoesBloco;
	}

	public void setDeclaracoesBloco(Map<String, ArrayList<String>> declaracoesBloco) {
		this.declaracoesBloco = declaracoesBloco;
	}
	
	//constroi a hash relativa aos escopos, classificando cada variavel em determinado escopo 
	public void constroiDeclaracaoBlocos(final HashMap<String, String> alfabeto, HashMap<Integer, ArrayList<MToken>> tokens) {
		
		//variavel que sinaliza se o escopo eh o principal ou nao
		boolean escopoPrincipal = true;
		
		//variavel que acumula o numero do escopo
		int contadorEscopos = 0;
		
		//percorre a lista de tokens para realizar a classificacao
		for (int i = 0; i < tokens.size(); i++) {
			
			
			
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
