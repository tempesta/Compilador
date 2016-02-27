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

/*
 * Classe que realiza as operacoes de um analisador sintatico
 */
public class AnalisadorSemantico {

	// estrutura que armazena o escopo de cada variavel
	private Map<String, String> declaracoesBloco;

	// construtor padrao: inicializa o atributo alocando memoria para o mesmo
	public AnalisadorSemantico() {
		super();
		this.declaracoesBloco = new HashMap<String, String>();
	}

	// construtor parametrico: inicializa o atributo com a estrutura passada
	// como parametro
	public AnalisadorSemantico(Map<String, String> declaracoesBloco) {
		super();
		this.declaracoesBloco = declaracoesBloco;
	}

	// acessor do atributo: retorna o valor da estrutura declaracoesBloco
	public Map<String, String> getDeclaracoesBloco() {
		return declaracoesBloco;
	}

	// acessor do atributo: altera o valor da estrutura declaracoesBloco
	public void setDeclaracoesBloco(Map<String, String> declaracoesBloco) {
		this.declaracoesBloco = declaracoesBloco;
	}

	// constroi a hash relativa aos escopos, classificando cada variavel em
	// determinado escopo
	public void constroiDeclaracaoBlocos(
			final HashMap<String, String> alfabeto,
			HashMap<Integer, ArrayList<MToken>> tokens) {

		// variaveis que sinalizam se o escopo eh o principal ou nao
		boolean escopoPrincipal = true;
		boolean escopoSecundario = false;

		// variaveis que acumulam o numero dos escopos
		int contadorEscopos = 0;
		int contadorEscoposSecundarios = 0;

		// percorre a lista de tokens para realizar a classificacao
		for (int i = 0; i < tokens.size(); i++) {

			// pega linha de tokens
			ArrayList<MToken> linhaDeTokens = new ArrayList<MToken>(tokens.get(i + 1));

			// percorre linha de tokens, acrecentando-os ao hash declaracoesBloco
			for (int j = 0; j < linhaDeTokens.size(); j++) {

				// verifica se eh o escopo principal
				if (escopoPrincipal) {

					// verifica inicio de outro escopo a partir do escopo
					// principal
					if (linhaDeTokens.get(j).valor.equals("{")) {
						escopoPrincipal = false;
						contadorEscopos++;
					}

				} else {

					// verifica entrada em escopo dentro de escopo secundario
					if (linhaDeTokens.get(j).valor.equals("{")) {
						contadorEscoposSecundarios++;
						escopoSecundario = true;
					}

					// verifica saida de escopo dentro de escopo secundario
					if (linhaDeTokens.get(j).valor.equals("}") && escopoSecundario) {
						contadorEscoposSecundarios--;
						escopoSecundario = false;
					} else if (linhaDeTokens.get(j).valor.equals("}") && !escopoSecundario)
						escopoPrincipal = true;
				}

				if (linhaDeTokens.get(j).chave.equals("letra")) {

					// confere se a variavel foi declarada
					if ((j - 1 >= 0) && (linhaDeTokens.get(j - 1).chave.equals("identificadores"))) {

						// salva no hash de acordo com o escopo
						if (escopoPrincipal) {
							// verifica se ja existe bloco
							if (this.declaracoesBloco.get("EscopoPrincipal") == null) {
								// cria primeiro bloco
								this.declaracoesBloco.put("EscopoPrincipal",
										linhaDeTokens.get(j).valor + ",");
							} else {
								// acrescenta demais variaveis ao bloco
								// principal
								String temp = this.declaracoesBloco.get("EscopoPrincipal");
								this.declaracoesBloco.put("EscopoPrincipal", temp + linhaDeTokens.get(j).valor + ",");
							}
						} else {
							// trabalho nos demais escopos
							if (this.declaracoesBloco.get("Escopo_"	+ contadorEscopos + "."	+ contadorEscoposSecundarios) == null) {
								// cria primeiro bloco
								this.declaracoesBloco.put("Escopo_"	+ contadorEscopos + "."	+ contadorEscoposSecundarios, linhaDeTokens.get(j).valor + ",");
							} else {
								// acrescenta demais variaveis ao bloco principal
								String temp = this.declaracoesBloco.get("Escopo_" + contadorEscopos + "." + contadorEscoposSecundarios);
								this.declaracoesBloco.put("Escopo_"	+ contadorEscopos + "." + contadorEscoposSecundarios, temp 	+ linhaDeTokens.get(j).valor + ",");
							}
						}

					}

				}

			}

		}
	}

	/*
	 * Metodo que acrescenta aa tabela de simbolos os tipos especificos de cada variavel
	 * A partir dessa operacao, podera ser realizada mais adiante a verificacao de tipos 
	 */
	public void incrementaTabela(final HashMap<String, String> alfabeto, LinkedHashMap<Integer, ArrayList<MToken>> linhasToken) {
		//percorre-se o conjunto de todos os tokens para a veficacao dos tipos de cada identificador
		for (java.util.Map.Entry<Integer, ArrayList<MToken>> entry : linhasToken.entrySet()) {
			//linha = conjunto de instrucoes (tokens)
			ArrayList<MToken> linha = new ArrayList<MToken>(entry.getValue());

			//percorre o conjunto de instrucoes por linha
			for (int i = 0; i < linha.size(); i++) {

				//cria token auxiliar para guardar valor, ira auxiliar no processo
				MToken token = new MToken(linha.get(i));
				
				//verifica o tipo da variavel
				if (token.valor.equals("int") || token.valor.equals("char")	|| token.valor.equals("float")	|| token.valor.equals("void")) {
					if (i + 1 <= linha.size()) {
						//verifica ja existe o tipo de dados (int, float, char, void) inserido na tabela de simbolos
						//caso exista, o atualiza, caso contrario, acrescenta-o
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
	}

	/*
	 * Metodo que consulta a tabela de simbolos para consultar o tipo de cada variavel previamente declarada
	 */
	private String getTipoDeToken(final String variavel, HashMap<String, String> alfabeto) {

		String tipoToken = new String();
		String variaveisPorTipo = alfabeto.get("int");
		StringTokenizer tokenizer = new StringTokenizer(variaveisPorTipo, ",");

		//verifica se a variavel esta contida no conjunto de variaveis inteiras na tabela de simbolos
		while (tokenizer.hasMoreTokens()) {
			String token = (String) tokenizer.nextElement();
			if (token.equals(variavel)) {
				tipoToken = "int";
			}
		}
		
		//verifica se ja encontrou o tipo da variavel
		if (tipoToken.isEmpty()) {
			variaveisPorTipo = alfabeto.get("float");
			tokenizer = new StringTokenizer(variaveisPorTipo, ",");

			//verifica se a variavel esta contida no conjunto de variaveis ponto flutuante na tabela de simbolos
			while (tokenizer.hasMoreTokens()) {
				String token = (String) tokenizer.nextElement();
				if (token.equals(variavel)) {
					tipoToken = "float";
				}
			}
		}

		//verifica se ja encontrou o tipo da variavel
		if (tipoToken.isEmpty()) {
			variaveisPorTipo = alfabeto.get("char");
			tokenizer = new StringTokenizer(variaveisPorTipo, ",");

			//verifica se a variavel esta contida no conjunto de variaveis char na tabela de simbolos
			while (tokenizer.hasMoreTokens()) {
				String token = (String) tokenizer.nextElement();
				if (token.equals(variavel)) {
					tipoToken = "char";
				}
			}
		}

		//verifica se ja encontrou o tipo da variavel
		if (tipoToken.isEmpty()) {
			variaveisPorTipo = alfabeto.get("void");
			tokenizer = new StringTokenizer(variaveisPorTipo, ",");

			//verifica se a variavel esta contida no conjunto de variaveis void na tabela de simbolos
			while (tokenizer.hasMoreTokens()) {
				String token = (String) tokenizer.nextElement();
				if (token.equals(variavel)) {
					tipoToken = "void";
				}
			}
		}

		//retorna o tipo da variavel encontrada
		return tipoToken;
	}
	
	/*
	 * Metodo que consulta a tabela de simbolos com a finalidade de acessar o escopo de cada variavel
	 * O resultado desse metodo eh usado para verificar o escopo das variaveis no momento da analise semantica
	 */
	private String getEscopoVariavel(String valor) {
		String escopo = new String();
		
		//percorre toda a hash que armazena todo o escopo das variaveis
		for (java.util.Map.Entry<String, String> entry : this.declaracoesBloco.entrySet()) {
			String chave = entry.getKey(); // pega elemento chave
			String variaveis = entry.getValue(); // pega valor
			StringTokenizer tokenizer = new StringTokenizer(variaveis, ","); //separa o retorno dos valores em tokens (facilita a iteracao)
			while (tokenizer.hasMoreTokens()) {
				String object = (String) tokenizer.nextElement();
				if (object.equals(valor)) {//verifica se o token eh do tipo esperado
					escopo = chave;//caso positivo, seleciona o escopo
				}
			}
		}
		//retorna o escopo
		return escopo;
	}

	/*
	 * Metodo que realiza a verificacao de tipo
	 */
	public void verificaTipo(final HashMap<String, String> alfabeto, LinkedHashMap<Integer, ArrayList<MToken>> linhasToken) {

		Set<String> variaveis = new HashSet<String>();

		//percorre todos os tokens para obter os tipos dos mesmos
		for (java.util.Map.Entry<Integer, ArrayList<MToken>> entry : linhasToken.entrySet()) {
			
			ArrayList<MToken> linha = new ArrayList<MToken>(entry.getValue());

			for (int i = 0; i < linha.size(); i++) {

				//verifica se o token obtido eh de um dos tipos a seguir
				if (linha.get(i).valor.equals("int") || linha.get(i).valor.equals("float") || linha.get(i).valor.equals("char") || linha.get(i).valor.equals("void")) {
					MToken variavel = linha.get(i + 1);
					if (!variaveis.contains(variavel.valor)) {
						variaveis.add(variavel.valor); //pega o valor da variavel apos confirmar seu tipo
					}
				}

				//verifica se o token obtido eh um relacional, aritmetico ou atribuicao
				if (linha.get(i).valor.equals("=") || linha.get(i).valor.equals("==")|| linha.get(i).valor.equals(">=")	|| linha.get(i).valor.equals("<=")
						|| linha.get(i).valor.equals("!=") || linha.get(i).valor.equals("<") || linha.get(i).valor.equals(">") || linha.get(i).valor.equals("+")
						|| linha.get(i).valor.equals("-") || linha.get(i).valor.equals("*") || linha.get(i).valor.equals("/")) {

					//realiza processo de separacao de expressao utilizando operador como raiz
					MToken tokenAnterior = new MToken(linha.get(i - 1)); //pega token a esquerda do operador
					MToken tokenPosterior = new MToken(linha.get(i + 1)); //pega token a direita do operador

					String tipoTokenAnterior = new String(getTipoDeToken(tokenAnterior.valor, alfabeto)); //pega tipo do token a esquerda do operador
					String escopoPrimeiraVariavel = new String(getEscopoVariavel(tokenAnterior.valor)); // pega escopo da variavel a direta do operador

					//verifica o tipo da variavel
					if (tokenPosterior.chave.equals("int") || tokenPosterior.chave.equals("float") || tokenPosterior.chave.equals("char")) {
						// relacao de variaveis com numeros
						if (!variaveis.contains(tokenAnterior.valor)) {//verifica se variavel foi previamente declarada
							// lanca excecao para variavel nao declarada
							System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": Variavel '" + tokenAnterior.valor + "' nao declarada!");
						} else if (!tipoTokenAnterior.equals(tokenPosterior.chave)) { //verifica se os tipos sao compativeis
							// lanca excecao, operacoes entre tipos/ incompativeis
							System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": Tipos incompativeis entre '" + tokenAnterior.valor + "' e " + tokenPosterior.valor + "!");
						}
					} else {
						// relacao de variaveis com variaveis
						String tipoTokenPosterior = new String(getTipoDeToken(tokenAnterior.valor, alfabeto)); //pega tipo do token a direita do operador
						String escopoSegundaVariavel = new String(getEscopoVariavel(tokenPosterior.valor)); // pega escopo da segunda variavel

						if (!variaveis.contains(tokenAnterior.valor)) { //verifica se a primeira variavel foi previamente declarada
							// lanca excecao para variavel nao declarada
							System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": Variavel '" + tokenAnterior.valor + "' nao declarada!");
						} else if (!variaveis.contains(tokenPosterior.valor)) { //verifica se a segunda variavel foi previamente declarada
							// lanca excecao para variavel nao declarada
							System.out.println("Erro Semantico na linha " + tokenPosterior.linha + ": Variavel '" + tokenPosterior.valor + "' nao declarada!");
						}

						//checa os escopos das variaveis
						if (!escopoPrimeiraVariavel.equals(escopoSegundaVariavel)) {
							//lanca excecao para escopos diferentes
							System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": As variaveis '" + tokenAnterior.valor + "' e '" + tokenPosterior.valor + "' nao pertencem ao mesmo escopo!");
						} else {
							//verifica se os tipos sao compativeis
							if (!tipoTokenAnterior.equals(tipoTokenPosterior)) {
								//lanca excessao se tipos forem incompativeis
								System.out.println("Erro Semantico na linha " + tokenAnterior.linha + ": Tipos incompativeis entre " + tokenAnterior.valor + " e " 	+ tokenPosterior.valor + "!");							}
						}
					}
				}
			}

		}

	}

}
