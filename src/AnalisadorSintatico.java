import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.security.auth.Subject;
import javax.swing.text.html.HTMLDocument.Iterator;

public class AnalisadorSintatico {

	//Funcao que verifica proximo token para token do tipo identificador, retorna true(identificador seguido de token letra) ou false
	private boolean verificaTokenIdentificador(MToken pilha, MToken token, Boolean condicional)
	{
		try {			
			//verifica se o identificador esta dentro de uma condicao
			if(condicional)
			{
				//verifica se a chave do ultimo token na pilha é um identificador
				if(pilha.chave.equals("identificador"))
				{
					//caso seja um identificador e esteja dentro de um loop, qualquer operador relacional diferente de '=' é valido
					if(!token.valor.equals("="))
					{
						return true;
					}
				}
			}
			
			//caso a chave do ultimo elemento da pilha seja identificador
			if(pilha.chave.equals("identificadores"))
			{
				//verifica se a chave do token da posicao atual é nome de uma variavel(nao inicia com digito e nao contem caracter especial)
				if(token.chave.equals("letra"))
				{
					//caso seja(identificador reconhecido), desempilha identificador
					return true;
				}
			}	
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao identidicar proximo token para token tipo letra. " + e);
			return false;
		}
	}
	
	//Funcao que verifica proximo token para token do tipo identificador, retorna true(letra seguido de token relacional de valor '=') ou false
	private boolean verificaTokenLetra(MToken pilha, MToken token, Boolean condicional)
	private boolean verificaTokenLetra(MToken pilha, MToken token, boolean condicional)
	{
		try {
			
			//verifica se o identificador esta dentro de alguma condicao
			if(condicional)
			{
				//caso a chave do ultimo elemento da pilha seja letra
				if(pilha.chave.equals("letra"))
				{
					if(token.valor.equals("<="))
					{
						return true;
					}
					if(token.valor.equals("=="))
					{
						return true;
					}
					if(token.valor.equals(">="))
					{
						return true;
					}
					if(token.valor.equals("!="))
					{
						return true;
			//caso a variavel for um parametro de uma funcao
			if(condicional)
			{
				//e caso a chave do ultimo elemento da pilha seja letra
				if(pilha.chave.equals("letra"))
				{
					//se a chave do ultimo token na pilha for letra, o token atual da lista deve ser um operador relacional de valor ')', '>', '<', '<=', '>=', '==' ou '!='
					switch (token.valor) 
					{
						case ")":
						case ">":
						case "<":
						case "<=":
						case ">=":
						case "==":
						case "!=":
							return true;
						default:
							return false;
					}					
				}
			}
					}
				}
			}
			
			//caso a chave do ultimo elemento da pilha seja letra
			if(pilha.chave.equals("letra"))
			{
				//se a chave do ultimo token na pilha for letra, o token atual da lista deve ser um operador relacional de valor '='
				if(token.valor.equals("="))
				{
					return true;
				}
				if(token.valor.equals(";"))
				if(token.valor.equals("+"))
				{
					return true;
				}
				if(token.valor.equals("-"))
				{
					return true;
				}
				if(token.valor.equals("*"))
				{
					return true;
				}
				if(token.valor.equals("/"))
				{
					return true;
				}
				//ou se o token atual for ';' identifica a declaracao de uma variavel, valido tambem
				if(token.valor.equals(";"))
				{
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica proximo token para token que indica um valor(chaves : int, float, char), retorna true(valor seguido de ';' ou valor seguido de ')' final de condicao
	private boolean verificaTokenValor(MToken pilha, MToken token, boolean condicional)
	{
		try {
			switch (pilha.chave)
			{
				case "int":
				case "float":
				case "char":
					//se estiver dentro de uma condicao
					if(condicional)
					{
						//dentro de condicao o proximo token deve ser ')', se encontrar ')' retorna true
						if(token.valor.equals(")"))
						{
							return true;
						}
						return false;
					}
					if(token.valor.equals(";"))
					{
						return true;
					}
				default:
					return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica proximo token para token do tipo palavra_reservada de valor 'if' ou 'while', retorna true(if ou while seguido de token abre parentese) ou false
	private boolean verificaTokenIfOuWhile(MToken pilha, MToken token)
	{
		try {
			//caso a chave do ultimo elemento da pilha seja if
			if(pilha.valor.equals("if"))
			{
				//se o token atual da lista de token for abre parentese o inicio do if esta valido. Obs.: sera verificado o restante em outro metodo
				if(token.valor.equals("("))
				{
					return true;
				}
			}
			
			//caso a chave do ultimo elemento da pilha seja while
			if(token.valor.equals("while"))
			{
				//se o token atual da lista de token for abre parentese o inicio do while esta valido. Obs.: sera verificado o restante em outro metodo
				if(token.valor.equals("("))
				{
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica proximo token para token do tipo palavra_reservada de valor 'else' ou 'struct', retorna true(else ou struct seguido de token abre chave) ou false
	private boolean verificaTokenElseOuStruct(MToken pilha, MToken token)
	{
		try {
			//caso a chave do ultimo elemento da pilha seja else
			if(pilha.valor.equals("else"))
			{
				//caso o valor do token seja '{'
				if(token.valor.equals("{"))
				{
					return true;
				}
			}
			
			//caso a chave do ultimo elemento da pilha seja struct
			if(pilha.valor.equals("struct"))
			{
				//caso o valor do token seja '{'
				if(token.valor.equals("{"))
				{
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica proximo token para token do tipo palavra_reservada de valor 'return', retorna true(return seguido de token letra) ou false
	private boolean verificaTokenReturn(MToken pilha, MToken token)
	{
		try {
			//caso a chave do ultimo elemento da pilha seja return
			if(pilha.valor.equals("return"))
			{
				//se o proximo token apos o return for letra retorna true
				if(token.chave.equals("letra"))
				{
					return true;
				}				
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica proximo token para token do tipo palavra_reservada de valor 'void', retorna true(void seguido de abre parentese) ou false
	private boolean verificaTokenVoid(MToken pilha, MToken token)
	{
		try {
			//caso a chave do ultimo elemento da pilha seja void
			if(pilha.valor.equals("void"))
			{
				//se o proximo token apos o void for abre parentese retorna true
				if(token.valor.equals("("))
				{
					return true;
				}				
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica proximo token para token do tipo relacional, retorna true(relacional seguido de valor(int, float, char)) ou false
	private boolean verificaTokenRelacional(MToken pilha, MToken token, HashMap<String, String> alfabeto)
	{
		try {
			switch (pilha.valor) 
			{
				case "=":
				case "!=":
				case "==":
					//caso a chave do ultimo elemento da pilha seja um relacional de valor '=', '!=' ou '=='
					//se o proximo token for um valor(int, float, char) retorna true
					if(token.chave.equals("int"))
					{
						return true;
					}				
					if(token.chave.equals("float"))
					{
						return true;
					}
					if(token.valor.equals("char"))
					{
						return true;
					}
					if(token.chave.equals("letra"))
					{
						AnalisadorLexico lex = new AnalisadorLexico();
						if(lex.verificaVariavelDeclarada(token.valor, alfabeto))
						{
							return true;
						}
					}
					return false;
				case "<=":
				case ">=":
				case "<":
				case ">":
					//caso a chave do ultimo elemento da pilha seja um relacional de valor '<', '>', '<=' ou '>='
					//se o proximo token for um valor(int, float) retorna true
					if(token.chave.equals("int"))
					{
						return true;
					}				
					if(token.chave.equals("float"))
					{
						return true;
					}
				default:
					return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica o proximo token do tipo ')'
	private Boolean verificaTokenFechaParentese(MToken pilha, MToken token)
	{
		try {
				//caso o ultimo token na pilha for '('
				if(pilha.valor.equals("("))
				{
					//se o token atual seja fecha parentese, indica que esta fechado alguma condicao
					if(token.valor.equals(")"))
					{
						return true;
					}
				}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica o proximo token do tipo '}'
	private Boolean verificaTokenFechaChave(MToken pilha, MToken token)
	{
		try {
				//caso o ultimo token na pilha for '{'
				if(pilha.valor.equals("{"))
				{
					//se o token atual for fecha chave, indica o fim de um bloco de comando, if, else, while ou de uma funcao
					if(token.valor.equals("}"))
					{
						return true;
					}
				}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica o proximo token para o tipo '+', '-', '*' ou '/'
	private boolean verificaTokenSomaSubMultDiv(MToken pilha, MToken token)
	{
		try {
			switch (pilha.chave) 
			{
				case "soma":
				case "mult":
					if(token.chave.equals("int"))
					{
						return true;
					}
					if(token.chave.equals("float"))
					{
						return true;
					}
					return false;
				default:
					return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que faz a analise sintatica
	public void analiseSintatica(ArrayList<MToken> listaTokens, Stack<MToken> pilha, boolean condicional, HashMap<String, String> alfabeto)
	{
		try {
//			Stack<MToken> pilha = new Stack<>();
			
			//verifica se o primeiro token inicia um programa, ou seja, tem chave = 'simbolo_inicial' ou valor = 'Progam' 
			if(listaTokens.get(0).chave.equals("simbolo_inicial"))
			{
				listaTokens.remove(0);
			}
			
			pilha.push(listaTokens.get(0));
			
			int index = 1;
			
			while (index < listaTokens.size())
			{				
				//caso o ultimo elemento da pilha for '(' e o proximo elemento for ')', desempilha
				if(verificaTokenFechaParentese(pilha.lastElement(), listaTokens.get(index)))
				{
					//seta para false a flag utilizada para controle se o token esta ou nao dentro de uma condicao
					condicional = false;
					pilha.pop();
				}
				
				//caso o ultimo elemento da pilha for '{' e o proximo for '}', desempilha
				if(verificaTokenFechaChave(pilha.lastElement(), listaTokens.get(index)))
				{
					pilha.pop();
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'identificador', chama funcao que verifica se o atual elemento da lista e uma variavel, sim = desempilha
				if(verificaTokenIdentificador(pilha.lastElement(), listaTokens.get(index), condicional))
				{
					pilha.pop();
					
					analiseSintatica(new ArrayList<>(listaTokens.subList(index, listaTokens.size())), pilha, condicional, alfabeto);			
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'if' ou 'while', chama funcao que verifica se o atual elemento da lista de tokens e = '(', sim chama a funcao novamente
				if(verificaTokenIfOuWhile(pilha.lastElement(), listaTokens.get(index)))
				{
					
					//desempilha a palavra reservada, pois o proximo token corresponde
					pilha.pop();
					
					//se depois do if for '('
					if(listaTokens.get(index).valor.equals("("))
					{
						//adiciona '(' na pilha
						pilha.push(listaTokens.get(index));
					}
						
					//le o proximo elemento depois do '('
					index++;

					//cria uma sublita a partir do index atual, a frente do elemento '('
					ArrayList<MToken> subLista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
					
					//indica para a funcao que os proximos tokens estao dentro de uma condicao
					condicional = true;
					
					analiseSintatica(subLista, pilha, condicional, alfabeto);
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'else' ou 'struct', chama funcao que verifica se o atual elemento da lista de tokens e = '{', se sim chama funcao novamente
				if(verificaTokenElseOuStruct(pilha.lastElement(), listaTokens.get(index)))
				{
					ArrayList<MToken> subLista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
					
					analiseSintatica(subLista, pilha, condicional, alfabeto);
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'void', chama a funcao que verifica se o atual elemento da lista de tokens e = '(', se sim, chama funcao analiseSintataica novamente
				if(verificaTokenVoid(pilha.lastElement(), listaTokens.get(index)))
				{
					//cria sublista de tokens a partir do index atual ate o fim da lista
					ArrayList<MToken> subLista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
					
					//indica que entrou em uma condicao, ou declaracao de parametros
					condicional = true;
					
					analiseSintatica(subLista, pilha, condicional, alfabeto);
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'return', chama funcao que verifica se o atual elemento da lista de tokens e = variavel ou valor
				if(verificaTokenReturn(pilha.lastElement(), listaTokens.get(index)))
				{
					pilha.pop();
					
					ArrayList<MToken> subLista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
					
					analiseSintatica(subLista, pilha, condicional, alfabeto);
				}
				
				//caso o ultimo elemento da pilha tenha cahve = 'letra', chama funcao que verifica se o atual elemento da lista de tokens e = ';', ',', valor
				if(verificaTokenLetra(pilha.lastElement(), listaTokens.get(index), condicional))
				{
					//desempilha letra
					pilha.pop();
					
					if(listaTokens.get(index).valor.equals(";"))
					{
						//o token esta correto, pois indica o fim de uma declaracao ou atribuicao. Porem nao deve ser adicionado na pilha na proxima iteracao
						index++;
					}
					
					if(index >= listaTokens.size())
					{
						break;
					}
					//cria sublista de tokens da lista de tokens a partir do index atual ate o fim da lista
					ArrayList<MToken> subLista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
					
					//chama novamente a analise sintatica para a sublista
					analiseSintatica(subLista, pilha, condicional, alfabeto);
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'relacional', chama funcao que verifica se o proximo elemento eh um valor valido de acordo com o relacional lido
				if(verificaTokenRelacional(pilha.lastElement(), listaTokens.get(index), alfabeto))
				{
					pilha.pop();
					
					analiseSintatica(new ArrayList<>(listaTokens.subList(index, listaTokens.size())), pilha, condicional, alfabeto);		
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'soma' ou 'mult' chama funcao que verifica se o proximo elemento eh um valor valido de acordo com a chave
				if(verificaTokenSomaSubMultDiv(pilha.lastElement(), listaTokens.get(index)))
				{
					pilha.pop();
					
					analiseSintatica(new ArrayList<>(listaTokens.subList(index, listaTokens.size())), pilha, condicional, alfabeto);		
				}
				
				//caso o ultimo elemento da pilha tenha chave = valor(int, float ou char), chama funcao que verifica se o proximo elemento da lista de token eh ';' ou ')'(caso estiver terminando uma condicao)
				if(verificaTokenValor(pilha.lastElement(), listaTokens.get(index), condicional))
				{
					pilha.pop();
					
					if(listaTokens.get(index).valor.equals(";"))
					{
						//o token esta correto, pois indica o fim de uma declaracao ou atribuicao. Porem nao deve ser adicionado na pilha na proxima iteracao
						index++;
					}
					
					if(!pilha.isEmpty())
					{
						if(verificaTokenFechaParentese(pilha.lastElement(), listaTokens.get(index)))
						{
							pilha.pop();
							//indica que e o fim da condicional ou declaracao de parametro
							condicional = false;
							
							index++;
						}
						
						if(!pilha.isEmpty())
						{
						
							if(verificaTokenFechaChave(pilha.lastElement(), listaTokens.get(index)))
							{
								pilha.pop();
								//indica que e o fim da do bloco entre chaves
								condicional = false;
								
							}
						}
						
						//se o proximo elemento de pois da condicao do if for '{'
						if(listaTokens.get(index).valor.equals("{"))
						{
							//empilha
							pilha.push(listaTokens.get(index));
						}
						
						index++;
					}
					analiseSintatica(new ArrayList<>(listaTokens.subList(index, listaTokens.size())), pilha, condicional, alfabeto);
				}
				
				index++;
			}
			
			if(!pilha.empty())
			{
				System.out.println("Erro sintatico: " + pilha.toArray() + " elementos com erro");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao executar analise Sitatica " + e);
		}
	}
}
