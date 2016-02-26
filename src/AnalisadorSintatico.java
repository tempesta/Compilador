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
			//caso a chave do ultimo elemento da pilha seja identificador
			if(pilha.chave.equals("identificadores"))
			{
				//verifica se a chave do token da posicao atual é nome de uma variavel(nao inicia com digito e nao contem caracter especial)
				if(token.chave.equals("letra"))
				{
					//caso seja(identificador reconhecido), desempilha identificador
					return true;
				}
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
			}	
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao identidicar proximo token para token tipo letra. " + e);
			return false;
		}
	}
	
	//Funcao que verifica proximo token para token do tipo identificador, retorna true(letra seguido de token relacional de valor '=') ou false
	private boolean verificaTokenLetra(MToken pilha, MToken token, boolean condicional)
	{
		try {
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
							//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
							System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
							return false;
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
				
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
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
						//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
						System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
						
						//retorna false para que a execucao continue
						return false;
					}
					//se o token lido for o esperado(';' neste caso) retorna true
					if(token.valor.equals(";") || token.valor.equals("+") || token.valor.equals("-") || token.valor.equals("*") || token.valor.equals("/"))
					{
						return true;
					}
					//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
//					System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
					
					//caso nao encontre o token esperado, apos relatar o erro na tela retorna false para que a execucao continue
					return false;
				default:
					//retorna false caso o token analisado(ultima posicao da pilha) nao for um valor
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
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
			}
			
			//caso a chave do ultimo elemento da pilha seja while
			if(pilha.valor.equals("while"))
			{
				//se o token atual da lista de token for abre parentese o inicio do while esta valido. Obs.: sera verificado o restante em outro metodo
				if(token.valor.equals("("))
				{
					//retorna verdadeiro para inicio correto de uma condicao while
					return true;
				}
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
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
				//caso o valor do token seja '{', retorna true
				if(token.valor.equals("{"))
				{
					return true;
				}
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
			}
			
			//caso a chave do ultimo elemento da pilha seja struct
			if(pilha.valor.equals("struct"))
			{
				//caso o valor do token seja '{'
				if(token.valor.equals("{"))
				{
					//retorna true para inicio correto da declaracao de um struct
					return true;
				}
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
			}
			//retorna falso caso o elemento da pilha nao seja do tipo else ou struct
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
					//retorna true caso o proximo token lido for compativel com o esperado pelo token na ultima posicao da pilha
					return true;
				}
				//verifica se o proximo elemento apos o return eh um numero que representa true ou false, caso sim, retorna true
				if(token.valor.equals("0") || token.valor.equals("1"))
				{
					return true;
				}
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
			}
			//retorna falso caso o elemento em analise da pilha nao for do tipo palavra reservada de valor 'return'
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
					//retorna true caso o proximo token lido for compativel com o esperado pelo token na ultima posicao da pilha, no caso '('
					return true;
				}	
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
			}
			//retorna false caso o ultimo elemento da pilha nao for do uma palavras reservada de valor = 'void'
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
					if(token.chave.equals("char"))
					{
						return true;
					}
					if(token.chave.equals("letra"))
					{
						/*AnalisadorLexico lex = new AnalisadorLexico();
						if(lex.verificaVariavelDeclarada(token.valor, alfabeto))
						{
							return true;
						}*/
						
						return true;
					}
					
					//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
					System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
					
					//retorna falso apos relatar mensagem de erro, para que a execucao continue
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
					//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
					System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
					
					//retorna falso apos relatar mensagem de erro, para que a execucao continue
					return false;
				default:
					
					//retorna false caso o token da pilha analisado nao for do tipo especifico ao qual a funcao verifica
					return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica o proximo token do tipo '('
	private Boolean verificaTokenAbreParentese(MToken pilha, MToken token, boolean parametro)
	{
		try 
		{
			//caso a pilha seja o inicio da declaracao de um parametro(void), o proximo token deve ser um identificador
			if(parametro)
			{
				//o proximo token deve ter chave = 'identificadores'
				if(token.chave.equals("identificadores"))
				{
					//caso for um identificador, returna true
					return true;
				}
			}			
			//caso o ultimo token na pilha for '('
			if(pilha.valor.equals("("))
			{
				//se o token atual seja fecha parentese, indica que esta fechado alguma condicao
				if(token.valor.equals(")"))
				{
					return true;
				}
			}
			//retorna false caso o token da pilha analisado nao for do tipo especifico ao qual a funcao verifica
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	
	//Funcao que verifica o proximo token do tipo '{'
	private Boolean verificaTokenAbreChave(MToken pilha, MToken token)
	{
		try 
		{
			//caso o ultimo token na pilha for '{'
			if(pilha.valor.equals("{"))
			{
				//se o token atual for fecha chave, indica o fim de um bloco de comando, if, else, while, struct ou de uma funcao
				if(token.valor.equals("}"))
				{
					return true;
				}
				
			}
			//retorna false caso o token da pilha analisado nao for do tipo especifico ao qual a funcao verifica
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica o proximo token do tipo '}'
	private Boolean verificaTokenFechaChave(MToken pilha, MToken token)
	{
		try 
		{
			//caso o ultimo token na pilha for '}'
			if(pilha.valor.equals("}"))
			{
				//se o token atual for fecha chave, indica o inicio de um bloco de comando, if, else, while ou de uma funcao
				if(token.chave.equals("letra") || token.chave.equals("identificadores") || token.chave.equals("palavras_reservadas") || token.chave.equals("funcao"))
				{
					return true;
				}
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
				
				//retorna falso apos relatar mensagem de erro, para que a execucao continue
				return false;
			}
			//retorna false caso o token da pilha analisado nao for do tipo especifico ao qual a funcao verifica
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica o proximo token do tipo ')'. O parametro sera o token anterior da lista, pois os simbolos ')' e '}' nao sao empilhados
	private Boolean verificaTokenFechaParentese(MToken tokenAnterior, MToken token)
	{
		try 
		{
			//caso o ultimo token na pilha for ')' indica fim de uma condicao, que deve vir a abertura de um bloco de codigo em seguida ('{')
			if(tokenAnterior.valor.equals(")"))
			{
				//se o token atual for fecha chave, indica o inicio de um bloco de comando, if, else, while ou de uma funcao
				if(token.valor.equals("{"))
				{
					return true;
				}
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + tokenAnterior.valor + "  na linha: " + tokenAnterior.linha);
				
				//retorna falso apos relatar mensagem de erro, para que a execucao continue
				return false;
			}
			
			//retorna false caso o token da pilha analisado nao for do(s) tipo(s) especifico ao qual a funcao verifica
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que verifica o proximo token do tipo ';'.
	private Boolean verificaTokenPontoVirgula(MToken pilha, MToken token)
	{
		try 
		{
			//caso o ultimo token na pilha for ';' indica fim de um comando ou atribuicao
			if(pilha.valor.equals(";"))
			{
				//se o token atual for ';', indica o inicio de um bloco de comando, if, else, while ou de uma funcao
				if(token.chave.equals("letra") || token.chave.equals("identificadores") || token.chave.equals("palavras_reservadas") || token.chave.equals("funcao") || token.valor.equals("}"))
				{
					return true;
				}
				//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
				System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
				
				//retorna falso apos relatar mensagem de erro, para que a execucao continue
				return false;
			}
			
			//retorna false caso o token da pilha analisado nao for do(s) tipo(s) especifico ao qual a funcao verifica
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
					//Em caso de erro(caso nao encontre o proximo token compativel) imprime na tela uma mensagem de erro e faz referencia a linha onde ocorreu
					System.out.println("Erro sintatico apos o token " + pilha.valor + "  na linha: " + pilha.linha);
					
					//retorna falso apos relatar mensagem de erro, para que a execucao continue
					return false;
				default:
					//retorna false caso o token da pilha analisado nao for do(s) tipo(s) especifico ao qual a funcao verifica
					return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	//Funcao que faz a analise sintatica
	public Stack<MToken> analiseSintatica(ArrayList<MToken> listaTokens, Stack<MToken> pilha, boolean condicional, boolean parametro, boolean bloco, HashMap<String, String> alfabeto)
	{
		try {
			
			//verifica se o primeiro token inicia um programa, ou seja, tem chave = 'simbolo_inicial' ou valor = 'Progam' 
			if(listaTokens.get(0).chave.equals("simbolo_inicial"))
			{
				listaTokens.remove(0);
			}
			
			//pilha.push(listaTokens.get(0));
			
			int index = 0;
			
			while (index < listaTokens.size())
			{		
				MToken tokenLista = new MToken();
				MToken tokenPilha = new MToken();
				
				if(index < listaTokens.size())
				{
					tokenLista = listaTokens.get(index);
				}
				
				//caso a pilha estiver vazia empilha o primeiro objeto
				if(pilha.isEmpty())
				{
					//empliha token
					pilha.push(tokenLista);
					
					//atualiza o index para ler o proximo elemento da lista
					index++;
					
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
				}
				else
				{
					tokenPilha = pilha.lastElement();
				}
				
				//caso o ultimo elemento da pilha for ';', verifica proximo elemento compativel
				if(verificaTokenPontoVirgula(tokenPilha, tokenLista))
				{
					//desempilha o ultimo elemento, no caso ';'
					pilha.pop();
					
					//caso o token faca parte de uma condicao(condicional = true) nao empilha, somente na proxima iteracao
					if(!bloco)
					{
						//empilha token atual da lista
						pilha.push(tokenLista);
						//atualiza o index para o proximo elemento
						index++;
					}
					
					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
				}				
				
				//caso o ultimo elemento da pilha for '(' e o proximo elemento for ')', desempilha
				if(verificaTokenAbreParentese(tokenPilha, tokenLista, parametro))
				{
					//o valor ')' indica fim de uma condicao ou parametro
					//atualiza a flag parametro e condicional para false
					parametro = false;
					condicional = false;
					
					//deseimpilha, o que indica que a condicao foi fechada com sucesso
					pilha.pop();
					
					//empilha o prioximo token
					pilha.push(tokenLista);
					
					//atualiza o index para montar a proxima sublista
					index++;
					
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
					
				}
				
				//caso o ultimo elemento da pilha for ')' e o proximo for '{', desempilha
				if(verificaTokenFechaParentese(tokenPilha, tokenLista))
				{
					//desempilha o ultimo elemento, no caso ')'
					pilha.pop();
					
					//empilha token index atual da lista
					pilha.push(tokenLista);
					
					//atualiza index para proximo elemento
					index++;
					
					//atualiza a variavel bloco para true, pois o elemento lido e '{'
					bloco = true;
					
					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
					
				}
				
				//caso o ultimo elemento da pilha for '{' e o proximo for '}', desempilha
				if(verificaTokenAbreChave(tokenPilha, tokenLista))
				{
					//desempilha o ultimo elemento. co caso '}'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;
					
					//indica que o bloco foi finalizado
					bloco = false;

					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
					
				}
				
				//caso o ultimo elemento da pilha for '}' e o proximo elementos for um identificador ou uma variavel(token de chave = letra), desempilha
				if(verificaTokenFechaChave(tokenPilha, tokenLista))
				{
					//desempilha o ultimo elemento, no caso '}'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;

					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'identificador', chama funcao que verifica se o atual elemento da lista e uma variavel, sim = desempilha
				if(verificaTokenIdentificador(tokenPilha, tokenLista, condicional))
				{
					//desempilha o ultimo elemento, no caso um identificador
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;

					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}		
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'if' ou 'while', chama funcao que verifica se o atual elemento da lista de tokens e = '(', sim chama a funcao novamente
				if(verificaTokenIfOuWhile(tokenPilha, tokenLista))
				{
					//desempilha o ultimo elemento, no caso o valor 'if' ou 'while'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;

					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//seta a variavel que indica que esta dentro de uma condicao para true
						condicional = true;
						
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}	
					
					/*//desempilha a palavra reservada, pois o proximo token corresponde
					pilha.pop();
					
					//se depois do if for '('
					if(tokenLista.valor.equals("("))
					{
						//adiciona '(' na pilha
						pilha.push(tokenLista);
					}
						
					//le o proximo elemento depois do '('
					index++;

					//cria uma sublita a partir do index atual, a frente do elemento '('
					ArrayList<MToken> subLista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
					
					//indica para a funcao que os proximos tokens estao dentro de uma condicao
					condicional = true;
					
					return analiseSintatica(subLista, pilha, condicional, alfabeto);*/
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'else' ou 'struct', chama funcao que verifica se o atual elemento da lista de tokens e = '{', se sim chama funcao novamente
				if(verificaTokenElseOuStruct(tokenPilha, tokenLista))
				{
					//desempilha o ultimo elemento, no caso o valor 'else' ou 'struct'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;

					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}	
					
//						ArrayList<MToken> subLista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
//						
//						return analiseSintatica(subLista, pilha, condicional, alfabeto);
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'void', chama a funcao que verifica se o atual elemento da lista de tokens e = '(', se sim, chama funcao analiseSintataica novamente
				if(verificaTokenVoid(tokenPilha, tokenLista))
				{
					
					//desempilha o ultimo elemento, no caso o valor 'void'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;

					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//atualiza a flag para true, pois apos o void deve vir a declaracao de um parametro
						parametro = true;
						
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}	
					
//						//cria sublista de tokens a partir do index atual ate o fim da lista
//						ArrayList<MToken> subLista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
//						
//						//indica que entrou em uma condicao, ou declaracao de parametros
//						condicional = true;
//						
//						return analiseSintatica(subLista, pilha, condicional, alfabeto);
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'return', chama funcao que verifica se o atual elemento da lista de tokens e = variavel ou valor
				if(verificaTokenReturn(tokenPilha, tokenLista))
				{
					
					//desempilha o ultimo elemento, no caso o valor 'void'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;
					
					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
				}
				
				//caso o ultimo elemento da pilha tenha cahve = 'letra', chama funcao que verifica se o atual elemento da lista de tokens e = ';', ',', valor
				if(verificaTokenLetra(tokenPilha, tokenLista, condicional))
				{
					//desempilha o ultimo elemento, no caso o valor 'letra'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;
					
					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
					//caso o index for maior ou igual ao tamanho da lista, indica que todos os tokens ja foram lidos e verificados
					else
					{
						//se apos ler e verificar todos os tokens e o ultimo elemento da pilha(empilhado nesta mesma iteracao), for ';', desempilha e retorna a pilha no seu atual estado
						if(tokenLista.valor.equals(";"))
						{
							//desempilha ';' ultimo elemento
							pilha.pop();
							
							//retorna pilha, vazia ou com elementos nao desempilhados devido a erros sintaticos
							return pilha;
						}
					}
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'relacional', chama funcao que verifica se o proximo elemento eh um valor valido de acordo com o relacional lido
				if(verificaTokenRelacional(tokenPilha, tokenLista, alfabeto))
				{
					//desempilha o ultimo elemento, no caso o token 'relacional'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;
					
					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
				}
				
				//caso o ultimo elemento da pilha tenha chave = 'soma', '/'(divisao) ou 'mult' chama funcao que verifica se o proximo elemento eh um valor valido de acordo com a chave
				if(verificaTokenSomaSubMultDiv(tokenPilha, tokenLista))
				{
					//desempilha o ultimo elemento, no caso o token 'soma', '/'(divisao) ou 'mult'
					pilha.pop();
					
					//empilha token atual da lista
					pilha.push(tokenLista);
					
					//atualiza o index para o proximo elemento
					index++;
					
					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
				}
				
				//caso o ultimo elemento da pilha tenha chave = valor(int, float ou char), chama funcao que verifica se o proximo elemento da lista de token eh ';' ou ')'(caso estiver terminando uma condicao)
				if(verificaTokenValor(tokenPilha, tokenLista, condicional))
				{
					//desempilha o ultimo elemento, no caso o valor 'letra'
					pilha.pop();
					
					//caso o token faca parte de uma condicao(condicional = true) nao empilha, somente na proxima iteracao
					if(!condicional)
					{
						//empilha token atual da lista
						pilha.push(tokenLista);
						//atualiza o index para o proximo elemento
						index++;
					}
					
					//verifica se o index eh menor que o numero de elementos restantes na lista de tokens, para nao acessar posicao inexistente, e parar apos ler o ultimo token
					if(index < listaTokens.size())
					{
						//cria a sublista sem o token empilhado
						ArrayList<MToken> sublista = new ArrayList<>(listaTokens.subList(index, listaTokens.size()));
						
						//chama o metodo novamente sem a partir de uma sublista sem p token da posicao 0(que esta na pilha)
						return analiseSintatica(sublista, pilha, condicional, parametro, bloco, alfabeto);
					}
					//caso o index for maior ou igual ao tamanho da lista, indica que todos os tokens ja foram lidos e verificados
					else
					{
						//se apos ler e verificar todos os tokens e o ultimo elemento da pilha(empilhado nesta mesma iteracao), for ';', desempilha e retorna a pilha no seu atual estado
						if(tokenPilha.valor.equals(";"))
						{
							//desempilha o ultimo elemento da lista
							pilha.pop();
							
							//retorna pilha, vazia ou com elementos nao desempilhados devido a erros sintaticos
							return pilha;
						}
					}
				}
				
				pilha.push(tokenLista);
				index++;
			}
			
			return pilha;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao executar analise Sitatica " + e);
			return pilha;
		}
	}
}
