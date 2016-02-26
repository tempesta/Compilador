import java.lang.reflect.Array;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

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
		boolean escopoSecundario = false;
		
		//variavel que acumula o numero do escopo
		int contadorEscopos = 0;
		int contadorEscoposSecundarios = 0;
		
		//percorre a lista de tokens para realizar a classificacao
		for (int i = 0; i < tokens.size(); i++) {
			
			//pega linha de tokens
			ArrayList<MToken> linhaDeTokens = new ArrayList<MToken>(tokens.get(i+1));
			
			//percorre linha de tokens, acrecentando-os ao hash declaracoesBloco
			for (int j = 0; j < linhaDeTokens.size(); j++) {
				
				//verifica se eh o escopo principal
				if (escopoPrincipal) {
					
					//verifica inicio de outro escopo a partir do escopo principal
					if ( linhaDeTokens.get(j).valor.equals( "{" ) ) {
						escopoPrincipal = false;
						contadorEscopos++;
					}
					
				} else {
					
					//verifica entrada em escopo dentro de escopo secundario
					if ( linhaDeTokens.get(j).valor.equals( "{" ) ) {
						contadorEscoposSecundarios++;
						escopoSecundario = true;
					}
					
					//verifica saida de escopo dentro de escopo secundario
					if ( linhaDeTokens.get(j).valor.equals( "}" )  && escopoSecundario) {
						contadorEscoposSecundarios--;
						escopoSecundario = false;
					} else if ( linhaDeTokens.get(j).valor.equals( "}" ) && !escopoSecundario )
						escopoPrincipal = true;	
				}
							
				
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
//		for (java.util.Map.Entry<String, String> entry : this.declaracoesBloco.entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//			System.out.println(key + " = " + value);
//		}		
	}
	
	public void incrementaTabela(final HashMap<String, String> alfabeto, LinkedHashMap<Integer, ArrayList<MToken>> linhasToken) {
		
		for (java.util.Map.Entry<Integer, ArrayList<MToken>> entry : linhasToken.entrySet()) {
			ArrayList<MToken> linha = new ArrayList<MToken>(entry.getValue());
			
			for (int i = 0; i < linha.size(); i++) {
				
				MToken token = new MToken( linha.get(i) );
				if (token.valor.equals("int") || token.valor.equals("char") || token.valor.equals("float") || token.valor.equals("void")) {
					if (i + 1 <= linha.size()) {
						if (alfabeto.get(token.valor) == null) {
							alfabeto.put(token.valor, linha.get(i + 1).valor);
						} else {
							String valor = alfabeto.get(token.valor);
							alfabeto.put(token.valor, valor + "," + linha.get(i + 1).valor);
						}
					}					
				}
				
			}		
		}
		
//		for (java.util.Map.Entry<String, String> entry : alfabeto.entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//			System.out.println(key + " = " + value);
//		}		
		
	}
	
	private String getTipoDeToken(final String variavel, HashMap<String, String> alfabeto) {
		
		String tipoToken = new String();
		String variaveisPorTipo = alfabeto.get("int");
		StringTokenizer tokenizer = new StringTokenizer(variaveisPorTipo,",");
		
		while (tokenizer.hasMoreTokens()) {
			String token =  (String) tokenizer.nextElement();
			if (token.equals(variavel)) {
				tipoToken = "int";
			}
		}
		
		if (tipoToken.isEmpty()) {
			variaveisPorTipo = alfabeto.get("float");
			tokenizer = new StringTokenizer(variaveisPorTipo,",");
			
			while (tokenizer.hasMoreTokens()) {
				String token =  (String) tokenizer.nextElement();
				if (token.equals(variavel)) {
					tipoToken = "float";
				}
			}
		}
		
		if (tipoToken.isEmpty()) {
			variaveisPorTipo = alfabeto.get("char");
			tokenizer = new StringTokenizer(variaveisPorTipo,",");
			
			while (tokenizer.hasMoreTokens()) {
				String token =  (String) tokenizer.nextElement();
				if (token.equals(variavel)) {
					tipoToken = "char";
				}
			}
		}
		
		if (tipoToken.isEmpty()) {
			variaveisPorTipo = alfabeto.get("void");
			tokenizer = new StringTokenizer(variaveisPorTipo,",");
			
			while (tokenizer.hasMoreTokens()) {
				String token =  (String) tokenizer.nextElement();
				if (token.equals(variavel)) {
					tipoToken = "void";
				}
			}
		}	
		
		return tipoToken;
	}
	
	private String getEscopoVariavel(String valor) {
		String escopo = new String();
		
		for (java.util.Map.Entry<String, String> entry : this.declaracoesBloco.entrySet()) {
			String chave = entry.getKey();
			String variaveis = entry.getValue();
			StringTokenizer tokenizer = new StringTokenizer(variaveis, ",");
			while (tokenizer.hasMoreTokens()) {
				String object = (String) tokenizer.nextElement();
				if (object.equals(valor)) {
					escopo = chave;
				}
			}
		}		
		return escopo;
	}
	
	public void verificaTipo(final HashMap<String, String> alfabeto, LinkedHashMap<Integer, ArrayList<MToken>> linhasToken) {
		
		Set<String> variaveis = new HashSet<String>();
		
		for (java.util.Map.Entry<Integer, ArrayList<MToken>> entry : linhasToken.entrySet()) {
			ArrayList<MToken> linha = new ArrayList<MToken>(entry.getValue());
			
			for (int i = 0; i < linha.size(); i++) {
				
				if (linha.get(i).valor.equals("int") || linha.get(i).valor.equals("float") || linha.get(i).valor.equals("char") || linha.get(i).valor.equals("void")) {
					MToken variavel = linha.get(i + 1);
					if (!variaveis.contains(variavel.valor)) {
						variaveis.add(variavel.valor);
					}
				}
					
				if (linha.get(i).valor.equals("=") || linha.get(i).valor.equals("==") || linha.get(i).valor.equals(">=") || linha.get(i).valor.equals("<=") || linha.get(i).valor.equals("!=") ||
						linha.get(i).valor.equals("<") || linha.get(i).valor.equals(">") || linha.get(i).valor.equals("+") || linha.get(i).valor.equals("-") || linha.get(i).valor.equals("*")
						|| linha.get(i).valor.equals("/")) {
					
					MToken tokenAnterior = new MToken(linha.get(i - 1));
					MToken tokenPosterior = new MToken(linha.get(i + 1));
					
					String tipoTokenAnterior = new String(getTipoDeToken(tokenAnterior.valor, alfabeto));
					String escopoPrimeiraVariavel = new String(getEscopoVariavel(tokenAnterior.valor));
					
					if (tokenPosterior.chave.equals("int") || tokenPosterior.chave.equals("float") || tokenPosterior.chave.equals("char")) {
						//relacao de variaveis com numeros
						if (!variaveis.contains(tokenAnterior.valor)) {
							//lanca excecao para variavel nao declarada
							System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": Variavel '" + tokenAnterior.valor + "' nao declarada!");
						} else if (!tipoTokenAnterior.equals(tokenPosterior.chave)) {
							//lanca excecao, operacoes entre tipos incompativeis
							System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": Tipos incompativeis entre '" + tokenAnterior.valor + "' e " + tokenPosterior.valor + "!");
						} 						
					} else {
						//relacao de variaveis com variaveis
						String tipoTokenPosterior = new String(getTipoDeToken(tokenAnterior.valor, alfabeto));
						String escopoSegundaVariavel = new String(getEscopoVariavel(tokenPosterior.valor));
						
						if (!variaveis.contains(tokenAnterior.valor)) {
							//lanca excecao para variavel nao declarada
							System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": Variavel '" + tokenAnterior.valor + "' nao declarada!");
						} else if (!variaveis.contains(tokenPosterior.valor)) {
							//lanca excecao para variavel nao declarada
							System.out.println("Erro Semantico na linha " + tokenPosterior.linha + ": Variavel '" + tokenPosterior.valor + "' nao declarada!");
						}
						
						if (!escopoPrimeiraVariavel.equals(escopoSegundaVariavel)) {
							System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": As variaveis '" +tokenAnterior.valor + "' e '" + tokenPosterior.valor + "' nao pertencem ao mesmo escopo!");
						} else {
							if (!tipoTokenAnterior.equals(tipoTokenPosterior)) {
								System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": Tipos incompativeis entre " + tokenAnterior.valor + " e " + tokenPosterior.valor + "!");
							}
						}						
					}					
				}				
			}
		
		}
		
	}

		
}
