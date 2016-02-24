import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalisadorSemantico {

	private Map<String, String> declaracoesBloco;

	public AnalisadorSemantico() {
		super();
		this.declaracoesBloco = new HashMap<String, String>();
	}

	public AnalisadorSemantico(Map<String, String> declaracoesBloco) {
		super();
		this.declaracoesBloco = declaracoesBloco;
	}

	public Map<String, String> getDeclaracoesBloco() {
		return declaracoesBloco;
	}

	public void setDeclaracoesBloco(Map<String, String> declaracoesBloco) {
		this.declaracoesBloco = declaracoesBloco;
	}
	
	//constroi a hash relativa aos escopos, classificando cada variavel em determinado escopo 
	public void constroiDeclaracaoBlocos(final HashMap<String, String> alfabeto, HashMap<Integer, ArrayList<MToken>> tokens) {
		
		//variavel que sinaliza se o escopo eh o principal ou nao
		boolean escopoPrincipal = true;
		
		//variavel que acumula o numero do escopo
		int contadorEscopos = 0;
		int contadorEscoposSecundarios = 0;
		
		//percorre a lista de tokens para realizar a classificacao
		for (int i = 0; i <= tokens.size(); i++) {
			
			//pega linha de tokens
			ArrayList<MToken> linhaDeTokens = new ArrayList<MToken>(tokens.get(i+1));
			
			//percorre linha de tokens, acrecentando-os ao hash declaracoesBloco
			for (int j = 0; j < linhaDeTokens.size(); j++) {
				
				//verifica entrada em escopo dentro de escopo secundario
				if ( linhaDeTokens.get(j).valor.equals( "{" ) && !escopoPrincipal ) {
					contadorEscoposSecundarios++;
				}
				
				//verifica saida de escopo dentro de escopo secundario
				if ( linhaDeTokens.get(j).valor.equals( "}" ) && !escopoPrincipal ) {
					contadorEscoposSecundarios--;
				}
				
				//verifica inicio de outro escopo a partir do escopo principal
				if ( linhaDeTokens.get(j).valor.equals( "{" )) {
					escopoPrincipal = false;
					contadorEscopos++;
				}
				
				//verifica se voltou para o escopo principal
				if ( linhaDeTokens.get(j).valor.equals( "}" ))
					escopoPrincipal = true;				
				
				if ( linhaDeTokens.get(j).chave.equals("letra")) {
					
					//confere se a variavel foi declarada
					if ( (j - 1 >= 0) && (linhaDeTokens.get(j - 1).chave.equals("identificadores")) ) {
					
						//salva no hash de acordo com o escopo
						if (escopoPrincipal) {						
							//verifica se ja existe bloco
							if (this.declaracoesBloco.get("EscopoPrincipal") == null) {
								//cria primeiro bloco
								this.declaracoesBloco.put("EscopoPrincipal", linhaDeTokens.get(j).valor + ",");		
							} else {
								//acrescenta demais variaveis ao bloco principal
								String temp = this.declaracoesBloco.get("EscopoPrincipal");
								this.declaracoesBloco.put("EscopoPrincipal", temp + linhaDeTokens.get(j).valor + ",");
							}						
						} else {
							//trabalho nos demais escopos
							if (this.declaracoesBloco.get("Escopo_" + contadorEscopos + "." + contadorEscoposSecundarios) == null) {
								//cria primeiro bloco
								this.declaracoesBloco.put("Escopo_" + contadorEscopos + "." + contadorEscoposSecundarios, linhaDeTokens.get(j).valor + ",");		
							} else {
								//acrescenta demais variaveis ao bloco principal
								String temp = this.declaracoesBloco.get("Escopo_" + contadorEscopos + "." + contadorEscoposSecundarios);
								this.declaracoesBloco.put("Escopo_" + contadorEscopos + "." + contadorEscoposSecundarios, temp + linhaDeTokens.get(j).valor + ",");
							}							
						}
						
					}				
					
				}		
				
			}
			
			
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
