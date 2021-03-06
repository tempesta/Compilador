import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

public class AnalisadorLexico {	

	//Funcao que verifica se uma substring � um identificador ou uma funcao, e retorna o que foi identificado
	public MToken verificaIdentificadorOuPalavraReservada(String codigos, int linha)
	{
		try {
			
			///////  IDENTIFICADORES  ////////
			
			//verifica identificador do tipo int
			if(codigos.matches("^int"))
			{
				return new MToken("identificadores", codigos, linha);
			}
			
			//verifica identificador do tipo float
			if(codigos.matches("^float"))
			{
				return new MToken("identificadores", codigos, linha);
			}
			
			//verifica identificador do tipo real
			if(codigos.matches("^real"))
			{
				return new MToken("identificadores", codigos, linha);
			}
			
			//verifica identificador do tipo char
			if(codigos.matches("^char"))
			{
				return new MToken("identificadores", codigos, linha);
			}
			
			//verifica identificador do tipo array
			if(codigos.matches("^array"))
			{
				return new MToken("identificadores", codigos, linha);
			}
			
			//verifica identificador do tipo register
			if(codigos.matches("^register"))
			{
				return new MToken("identificadores", codigos, linha);
			}
			
			///////  FUNCOES ////////
			
			//verifica palavras_reservadas do tipo struct
			if(codigos.matches("^struct"))
			{
				return new MToken("palavras_reservadas", codigos, linha);
			}
			
			//verifica palavras_reservadas do tipo if 
			if(codigos.matches("^if"))
			{
				return new MToken("palavras_reservadas", codigos, linha);
			}
			
			//verifica palavras_reservadas do tipo else
			if(codigos.matches("^else"))
			{
				return new MToken("palavras_reservadas", codigos, linha);
			}
			
			//verifica palavras_reservadas do tipo while
			if(codigos.matches("^while"))
			{
				return new MToken("palavras_reservadas", codigos, linha);
			}
			
			//verifica palavras_reservadas do tipo void
			if(codigos.matches("^void"))
			{
				return new MToken("palavras_reservadas", codigos, linha);
			}
			
			//verifica palavras_reservadas do tipo return
			if(codigos.matches("^return"))
			{
				return new MToken("palavras_reservadas", codigos, linha);
			}
			
			//verifica simbolo_inicial
			if(codigos.matches("^Program"))
			{
				return new MToken("simbolo_inicial", codigos, linha);
			}
						
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao verificar identificador ou palavra reservada " + e);
			return null;
		}
	}
	
	//Funcao para verificar tipo caracter(char)
	public boolean verificaChar(String codigos)
	{
		//cria a regex para sequencia de numeros inteiros
        Pattern p = Pattern.compile("^'[a-zA-Z]'");
        //verifica de acordo com a regex criada a string 'strCodigo'
        Matcher m = p.matcher(codigos);
        
        if(m.matches())
        {
        	return true;
        }
        
		return false;
	}
	
	//Funcao para verificar abre parenteses 
	public boolean verificaAbreParenteses(String codigos)
	{
		try {
			//caso especal pois ) � considerado caracter especial em java, � necessario uma regex diferente
			if(codigos.equals("("))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar abre parenteses " + e);
			return false;
		}
	}
	
	//Funcao que verifica fecha parenteses
	public boolean verificaFechaParenteses(String codigos)
	{
		try {
			//caso especal pois ( � considerado caracter especial em java, � necessario uma regex diferente
			if(codigos.equals(")"))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar fecha parenteses " + e);
			return false;
		}
	}
	
	//Funcao que verifica chaves
	public boolean verificaAbreChaves(String codigos)
	{
		try {
			//caso especal pois { � considerado caracter especial em java, � necessario uma regex diferente
			if(codigos.equals("{"))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar abre chaves " + e);
			return false;
		}
	}
	
	//Funcao que verifica fecha chaves
	public boolean verificaFechaChaves(String codigos)
	{
		try {
			//caso especal pois } � considerado caracter especial em java, � necessario uma regex diferente
			if(codigos.equals("}"))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar fecha chaves " + e);
			return false;
		}
	}
	
	//Funcao que verifica abre colchetes
	public boolean verificaAbreColchete(String codigos)
	{
		try {
			if(codigos.equals("["))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar abre colchete " + e);
			return false;
		}
	}
	
	//Funcao que verifica fecha colchete
		private boolean verificaFechaColchete(String codigos)
		{
			try {
				if(codigos.equals("]"))
				{
					return true;
				}
				return false;
				
			} catch (Exception e) {
				System.out.println("Erro ao verificar fecha colchete " + e);
				return false;
			}
		}
	
	//Funcao que verifica ponto e virgula
	public boolean verificaPontoVirgula(String codigos)
	{
		try {
			if(codigos.equals(";"))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar ponto e virgula " + e);
			return false;
		}
	}
	
	//Funcao que verifica sinal de soma e subtracao
	public boolean verificaSinalSomaSubtracao(String codigos)
	{
		try {
			if(codigos.equals("+") || codigos.equals("-"))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar sinal de soma ou subtracao " + e);
			return false;
		}
	}
	
	//Funcao que verifica multiplicacao e divisao
	public boolean verificaMultiplicacaoDivisao(String codigos)
	{
		try {
			if(codigos.equals("/") || codigos.equals("*"))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar sinal de multiplicacao ou divisao " + e);
			return false;
		}
	}
	
	//Funcao que verifica operadores relacionais
	public boolean verificaRecalional(String codigos)
	{
		try {
			if(codigos.equals("="))
			{
				return true;
			}
			if(codigos.equals("==")){
				return true;
			}
			if(codigos.equals("!=")){
				return true;
			}
			if(codigos.equals("<"))
			{
				return true;
			}
			if(codigos.equals("<=")){
				return true;
			}
			if(codigos.equals(">"))
			{
				return true;
			}
			if(codigos.equals(">="))
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar operadores relacionais " + e);
			return false;
		}
	}
	
	//Funcao que verifica numeros Inteiros
	public boolean verificaNumerosInteiros(String codigos)
	{
		//cria a regex para sequencia de numeros inteiros
        Pattern p = Pattern.compile("^[+-]?\\d+");
        //verifica de acordo com a regex criada a string 'strCodigo'
        Matcher m = p.matcher(codigos);
        
		try {
			//caso a string 'strCodigo esteja de acordo('case') com a regex, retorna true'. Eh uma sequencia de numeros inteiros
			if(m.matches()){
				return true;
			}			
			return false;			
		} catch (Exception e) {
			System.out.println("Erro ao verificar sequencia de numeros inteiros" + e);	
			return false;
		}
	}
	
	//Funcao que verifica numeros flutuantes
	public boolean verificaNumerosFlutuantes(String codigos)
	{
		//cria a regex para sequencia de numeros flutuantes
        Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)");
        //verifica de acordo com a regex criada a string 'strCodigo'
        Matcher m = p.matcher(codigos);
        
		try {
			//caso a string 'strCodigo esteja de acordo('case') com a regex, retorna true'. Eh uma sequencia de numeros flutuantes
			if(m.matches()){
				return true;
			}			
			return false;			
		} catch (Exception e) {
			System.out.println("Erro ao verificar sequencia de numeros flutuantes" + e);	
			return false;
		}
	}
	
	//Funcao que verifica nome da Variavel, se esta de acordo. Nao pode comecar com digito, nao pode ter caracter special(',', '.', '()', etc)
	public boolean verificaString(String codigos)
	{	
		//cria a regex para sequencia de numeros flutuantes
		Pattern p = Pattern.compile("^([a-zA-Z])+(\\w+)?");
        //verifica de acordo com a regex criada a string 'strCodigo'
        Matcher m = p.matcher(codigos);		
		
		try {
			if(m.matches())
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar variavel ja declarada " + e);
			return false;
		}
	}
	
	//Funcao para verificar a o token 'funcao = function'
	public boolean verificaFuncao(String codigos)
	{
		//cria a regex para sequencia de numeros flutuantes
		Pattern p = Pattern.compile("^function");
        //verifica de acordo com a regex criada a string 'strCodigo'
        Matcher m = p.matcher(codigos);	

        //caso o codigo(substring) seja igual a palavra funcao, retorna true
        if(m.matches())
        {
        	return true;
        }
        
        return false;
	}
	
	//Funcao que verifica se o nome do identificador(variavel) ja foi declarada
	public boolean verificaVariavelDeclarada(String codigo, HashMap<String, String> alfabeto)
	{	
		
		String[] variaveisDeclaradas = alfabeto.get("variaveis_declaradas").split(",");
		
		try {
			for (String variavel : variaveisDeclaradas)
			{
				if(!variavel.isEmpty())
				{
					if(codigo.matches(variavel))
					{
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erro ao verificar variavel ja declarada " + e);
			return false;
		}
	}
	
	//Funcao recursiva que separa caracteres concatenados sem espaco. Usado no metodo separarCadeiaCarecter
	public String separar(String subCadeias)
	{
		try {
			//string auxiliar
			String strCadeiasConcatenadas = "";
			
			//caso a string str nao esteja separada por espaco, isso ocorre no retorno de uma string separada ja, para verificar se nao restou nenhuma string sem separar
			if(subCadeias.split(" ").length > 1)
			{	
				//separa em substring por espaco 
				for (String str : subCadeias.split(" "))
				{
					//para cada substring chama o metodo separar passando a substring gerada, se for diferente de vazio
					if(!str.isEmpty())
					{
						strCadeiasConcatenadas += " " + separar(str);
					}
				}
				
				return strCadeiasConcatenadas;
			}
			
			//caso nao esteja separado por abre parentese
			if(subCadeias.split("\\(").length > 1)
			{
				//iteracao para concatenar as subcadeias em uma soh string e separadas por espaco
				for (String strings : subCadeias.split("\\(")) {
					//se houver concatenas as sub cadeias. Exemplo: 'substring' + '(espaco)'
					strCadeiasConcatenadas += " " + strings + " ("; 
				}
				//usado para pegar o index do ultimo caracter
				int lastIndexAbreParentese = strCadeiasConcatenadas.length();
				
				//caso o ultimo caracter seja diferente de abre parentese '(', pega o index do ultimo abre parentese para retira-lo da string
				if(!subCadeias.split("")[subCadeias.length()].equals("("))
				{
					lastIndexAbreParentese = strCadeiasConcatenadas.lastIndexOf("(");
				}
				//caso o ultimo parentese tenha sido gerado na concatenacao e nao pertenca a string original, remove
				String strComEspaco = strCadeiasConcatenadas.substring(0, lastIndexAbreParentese);
				
				return separar(strComEspaco);
			}
			
			//caso nao esteja separado por fecha parentese, ou o ultimo caracter seja parentese(o split nao gera dois itens quando o termo esta no final)
			if(subCadeias.split("\\)").length > 1 || (subCadeias.split("\\)").length > 0 && subCadeias.split("")[subCadeias.length()].equals(")")))
			{
				//iteracao para concatenar as subcadeias em uma soh string e separadas por espaco
				for (String strings : subCadeias.split("\\)")) {
					//se houver concatenas as sub cadeias. Exemplo: 'substring' + '(espaco)'
					strCadeiasConcatenadas += " " + strings + " )"; 
				}
				//usado para pegar o index do ultimo caracter
				int lastIndexParentese = strCadeiasConcatenadas.length();
				
				//caso o ultimo caracter seja diferente de fecha parentese ')', pega o index do ultimo fecha parentese para retira-lo da string
				if(!subCadeias.split("")[subCadeias.length()].equals(")"))
				{
					lastIndexParentese = strCadeiasConcatenadas.lastIndexOf(")");
				}
				//caso o ultimo parentese tenha sido gerado na concatenacao e nao pertenca a string original, remove
				String strComEspaco = strCadeiasConcatenadas.substring(0, lastIndexParentese);
				
				return separar(strComEspaco);
			}
			
			//caso nao esteja separado por ponto e virgula
			if(subCadeias.split(";").length > 1 || (subCadeias.split(";").length > 0 && subCadeias.split("")[subCadeias.length()].equals(";")))
			{
				//iteracao para concatenar as subcadeias em uma soh string e separadas por espaco
				for (String strings : subCadeias.split(";")) {
					//se houver concatenas as sub cadeias. Exemplo: 'substring' + '(espaco)'
					strCadeiasConcatenadas += " " + strings + " ;"; 
				}
				//usado para pegar o index do ultimo caracter
				int lastIndexPontoVirgula = strCadeiasConcatenadas.length();
				
				//caso o ultimo caracter seja diferente de ';', pega o index do ultimo ';' para retira-lo da string
				if(!subCadeias.split("")[subCadeias.length()].equals(";"))
				{
					lastIndexPontoVirgula = strCadeiasConcatenadas.lastIndexOf(";");
				}
				//caso o ultimo ponto e virgula tenha sido gerado na concatenacao e nao pertenca a string original, remove
				String strComEspaco = strCadeiasConcatenadas.substring(0, lastIndexPontoVirgula);
				
				return separar(strComEspaco);
			}
			
			//caso nao haja nenhuma subcadeia nao separada por espaco retorna a string original
			return " " + subCadeias;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao separar subcadeis de caracter " + e);
			return "";
		}
	}
	
	//Funcao criada separar as cadeias de caracteres
	private String[] separarCadeiaCaracter(String strLinha)
	{
		try {
			//Gera array com as substrings separadas por espaco 
			String strSeparadoPorEspaco = "";
			
			if(strLinha.split(" ").length > 1)
			{
				//para cada substring verifica se existe algum caracter possivelmente concatenado que deveria estar separado por espaco
				for (String subString : strLinha.split(" ")) {
					//concatena as subsequencias de string agora separadas por espaco
					strSeparadoPorEspaco += separar(subString);
				}
				
				//retorna array formatado, sem elementos vazios
				return formataArrayComVazio(strSeparadoPorEspaco.split(" "));
			}
			else
			{
				strSeparadoPorEspaco += separar(strLinha);
			}
			
			return strLinha.split(" ");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao separar cadeia de caracteres " + e);
			return null;
		}
	}
	
	//Funcao criada para retirar elementos vazios do array
	private String[] formataArrayComVazio(String[] arCodigo)
	{
		try 
		{
			
			String[] arAuxiliar = new String[arCodigo.length];
			
			int j = 0;
			
			for (int i = 0; i < arCodigo.length; i++) {
				if(!arCodigo[i].trim().isEmpty())
				{
					//adiciona subCadeia no array arResposta na posicao 'j'. 
					//Obs.:'j' so sera incrementado quando arCodigo[i] for diferente de vazio, assim eliminando possivel elementos 'nullssss'
					arAuxiliar[j] = arCodigo[i];
					j++;
				}
			}
			
			//cria outro array para as substrings de tamanho = ao array auxiliar - numero elementos nulos
			String[] arResposta = new String[j];
			
			//removendo elementos 'null'
			for (int i = 0; i < j; i++) {
				arResposta[i] = arAuxiliar[i];
			}
			
			return arResposta;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao retirar elementos vazios. Metodo: formatarArrayComVazio " + e);
			return null;
		}
	}
	
	//Funcao para remover comentario (/*comentario*/)
	public ArrayList<String> removeComentario(ArrayList<String> blocoCodigo)
	{
		//encontra o index de inicio de comentario na string codigo
		int indexInicioComentario = blocoCodigo.toString().indexOf("/*");
		//encontra o index de fim de comentario na string codigo e soma 2, para que seja conseiderado o restante da strin apos o comentario
		int indexFimComentario = blocoCodigo.toString().indexOf("*/") + 2;
		
		//caso nao tenha comentario no codigo(index = -1), returna o bloco de codigo
		if(indexInicioComentario < 0)
		{
			return blocoCodigo;
		}
		
		//caso o comentario tenha sido iniciado e nao tenha terminado, indica que o comentario vai ate o fim do codigo
		//verifica < 2 pois eh somado 2 no indexFimComentario a fim de que o simbolo '*/' nao faca parte do codigo
		if(indexFimComentario < 2)
		{
			//atribui indexFimComentario ao final da string de codigo
			indexFimComentario = blocoCodigo.toString().length();
		}
		
		//exclui a string entre comentario
		String strSemComentario = blocoCodigo.toString().substring(0, indexInicioComentario) + blocoCodigo.toString().substring(indexFimComentario, blocoCodigo.toString().length());
		
		//cria um array separado por ',' que indica onde a linha foi quebrada, e formata removendo elementos vazios
		String[] arraySemComentarioEespaco = formataArrayComVazio(strSemComentario.split(","));
		
		//cria lista para recebeer a resposta separada por linha, como no arquivo original
		ArrayList<String> resposta = new ArrayList<String>();
		
		//itera sobre o array formatado para transforma-lo novamente em uma lista separado pela linha do codigo no arquivo original
		for (int i = 0; i < arraySemComentarioEespaco.length; i++)
		{
			//na primeira posicao, remove o caracter '[' gerado pelo metodo .split()
			if(i == 0)
			{
				//remove '['
				resposta.add(arraySemComentarioEespaco[i].replace("[", ""));
			}
			else
			{
				//na ultima posicao, remove o caracter ']' gerado pelo metodo .split()
				if( i == arraySemComentarioEespaco.length-1)
				{
					//remove ']'
					resposta.add(arraySemComentarioEespaco[i].replace("]", ""));
				}
				else
				{
					//adiciona linha de codigo
					resposta.add(arraySemComentarioEespaco[i]);
				}
			}
		}
		
		//retorna a lista de linhas de codigo sem comentario
		return resposta;
	}
	
	//Funcao que identifica o token a partir de uma string
	public MToken identificaToken(String substring, int linha)
	{
		try {
			MToken token = new MToken();
			
			//verifica abre parentese
			if(verificaAbreParenteses(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("abre_parenteses", substring, linha);
			}
			
			//verifica fecha parentese
			if(verificaFechaParenteses(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("fecha_parenteses", substring, linha);
			}
			
			//verifica abre chaves
			if(verificaAbreChaves(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("abre_chave", substring, linha);
			}
			
			//verifica fecha chaves
			if(verificaFechaChaves(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("fecha_chave", substring, linha);
			}
			
			//verifica abre colchetes
			if(verificaFechaColchete(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("abre_colchete", substring, linha);
			}
			
			//verifica fecha colchetes
			if(verificaAbreColchete(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("fecha_colchete", substring, linha);
			}
			
			//verifica ponto e virgula
			if(verificaPontoVirgula(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("ponto_virgula", substring, linha);
			}
			
			//verifica sinal de soma ou subtracao
			if(verificaSinalSomaSubtracao(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("soma", substring, linha);
			}
			
			//verifica sinal de multiplicacao ou divisao
			if(verificaMultiplicacaoDivisao(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("mult", substring, linha);
			}
			
			//verifica simbolos relacionais
			if(verificaRecalional(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("relacional", substring, linha);
			}
			
			//verifica numeros inteiros
			if(verificaNumerosInteiros(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("int", substring, linha);
			}
			
			//verifica numeros flutuantes
			if(verificaNumerosFlutuantes(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("float", substring, linha);
			}
			
			//verifica valor tipo char(unico caracter entre aspas simples)
			if(verificaChar(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("char", substring, linha);
			}
			
			//verifica valor do tipo function, que indica uma funcao
			if(verificaFuncao(substring))
			{
				//Instancia novo objeto Mtoken e adiciona a lista de tokens(chave, valor)
				return token = new MToken("funcao", substring, linha);
			}
			
			//verifica sequencia de caracter, deve iniciar com letra para ser valido
			if(verificaString(substring))
			{
				MToken tokenPalavra = new MToken();
				tokenPalavra = verificaIdentificadorOuPalavraReservada(substring, linha);
				
				//caso seja verificado um identificador ou uma palavra reservada adiciona o token encontrado entre as opcoes
				if(tokenPalavra != null)
				{
					//adiciona token palavra reservada ou identificador
					return token = tokenPalavra;
				}
				else
				{
					return token = new MToken("letra", substring, linha);
				}
				
			}
			
			return token;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao identificar token com a sequencia: " + substring + ", " + e);
			return null;
		}
	}
	
	public ObjRetornoAnaliseLexica analiseLexica(ArrayList<String> linhas, HashMap<String, String> alfabeto)
	{
		//cria uma lista para guardar os tokens
		ArrayList<MToken> tokens = new ArrayList<>();
		
		try 
		{	
//			//cria uma lista para guardar os tokens
//			ArrayList<MToken> tokens = new ArrayList<>();
			
			//cria um array de string para guardar as sequencias de caracter contidas em cada linha
			String[] strLinhaComEspaco;
			
			MToken tokenIndentificado = new MToken();
			
			int contaLinha = 1;
			
			//para cada linha chama o metodo separarCadeiaCaracter que retorna um array de substrings
			for (String strLinha : linhas) 
			{
				
				//obtem o array de substring
				strLinhaComEspaco = separarCadeiaCaracter(strLinha);
				
				//itera sobre cada substring para verificar a qual token perntence
				for (String substring : strLinhaComEspaco) 
				{
					tokenIndentificado = identificaToken(substring, contaLinha);
					if(tokenIndentificado.chave != null)
					{
						tokens.add(tokenIndentificado);
					
						if(tokenIndentificado.chave.equals("letra"))
						{					
							//primeiro verifica se o ultimo token que foi adicionado na lista de tokens � um identificador,
							//o que diz se a proxima sequencia e nome de uma variavel
							if(tokens.size() > 1 && tokens.get(tokens.size() - 2).chave.equals("identificadores"))
							{
								//verifica se a sequencia de caracter pode ser nome de uma variavel e se ja foi declarada
								if(!verificaVariavelDeclarada(substring, alfabeto))
								{
									//caso nao tenha sido declarada � adicionada ao alfabeto na chave variaveis_declaradas
									alfabeto.put("variaveis_declaradas", alfabeto.get("variaveis_declaradas") + substring + ",");
								}
								
								//adiciona o token com chave = tipo do identificador e valor = substring
								//tokens.add(new MToken(tokens.get(tokens.size() - 1).chave + " " + tokens.get(tokens.size() - 1).valor, substring));
							}
						}
					}
					else
					{
						System.out.println("Token " + substring + " nao identificado");
					}
				}
				//incrementa contador linha
				contaLinha++;
			}
			
			return new ObjRetornoAnaliseLexica(tokens, alfabeto); 
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("Erro ao executar o metodo analiseLexica " + e);
			return new ObjRetornoAnaliseLexica(tokens, alfabeto);
		} 
	}
}

