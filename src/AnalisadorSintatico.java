import java.util.ArrayList;
import java.util.Stack;

import javax.security.auth.Subject;
import javax.swing.text.html.HTMLDocument.Iterator;

public class AnalisadorSintatico {
	
	//Funcao que verifica se o valor do token do tipo identificador esta relacionado com o ultimo elemento da pilha de tokens
	private Stack<MToken> verificaIdentificador(Stack<MToken> pilha, MToken token)
	{
		try {
			switch (token.valor)
			{
				case "int":
					if(pilha.isEmpty())
					{
						return pilha;
					}
				case "float":
					break;
				case "real":
					break;	
				case "char":
				default:
					break;
			}		
			return pilha;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao verificar valor do identificador " + e);
			return pilha;
		}
	}
	
	//Funcao que sera chamada recursivamente para analisar os tokens da lista de tokens em relacao aos objetos da pilha
	private ObjAnaliseSintatica verificaPilhaListaDeTokens(ArrayList<MToken> listaTokens, Stack<MToken> pilha)
	{
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao relacionar os tokens lidos com a pilha. " + e);
			//obj vazio
			return new ObjAnaliseSintatica();
		}
	}
	
	//Funcao que verifica proximo token para token do tipo identificador, retorna true(identificador seguido de token letra) ou false
	private boolean verificaTokenIdentificador(MToken pilha, MToken token)
	{
		try {
			//caso a chave do ultimo elemento da pilha seja identificador
			if(pilha.chave.equals("identificador"))
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
					}
				}
			}
			
			//caso a chave do ultimo elemento da pilha seja letra
			if(pilha.chave.equals("letra"))
			{
				if(token.valor.equals("="))
				{
					return true;
				}
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
	
	//Funcao que verifica proximo token para token do tipo palavra_reservada de valor 'if' ou 'while', retorna true(if ou while seguido de token abre parentese) ou false
	private boolean verificaTokenIfOuWhile(MToken pilha, MToken token)
	{
		try {
			//caso a chave do ultimo elemento da pilha seja if
			if(pilha.valor.equals("if"))
			{
				if(token.valor.equals("("))
				{
					return true;
				}
			}
			
			//caso a chave do ultimo elemento da pilha seja while
			if(token.valor.equals("while"))
			{
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
	
	//Funcao que faz a analise sintatica
	public void analiseSintatica(ArrayList<MToken> listaTokens)
	{
		try {
			
			AnalisadorLexico lex = new AnalisadorLexico();
			
			//cria o objeto pilha, para armazenar o ultimo token lido, e serve como referecia para avaliar o proximo token
			Stack<MToken> pilha = new Stack<>();
			
			if(listaTokens.get(0).chave.equals("simbolo_inicial"))
			{
				listaTokens.remove(0);
			}
			
			int index = 0;
			
			while(index <= listaTokens.size())
			{
				pilha.push(listaTokens.get(index));
				
				index += 1;	
				
				//caso a chave do ultimo elemento da pilha seja palavra reservada
				if(pilha.lastElement().chave.equals("palavra_reservada"))
				{
					//verifica o valor do token de chave palavra reservada
					//se for if, o token da lista na posico atual deve ter abre_parentese
					if(pilha.lastElement().valor.equals("if"))
					{		
						//se token da posicao atual da lista de tokens for abre parentese, desempilha
						if(listaTokens.get(index).chave.equals("abre_parenteses"))
						{
							//desempilha token de chave 'palavra_reservada' e valor 'if'
							pilha.pop();
							
							//empilha token de valor 'abre parenteses', que esta na posicao atual da lista de tokens
							pilha.push(listaTokens.get(index));
						}
					}
					
					//se for while, o token da lista na posico atual deve ser abre_parentese
					if(pilha.lastElement().valor.equals("while"))
					{		
						//se token da posicao atual da lista de tokens for abre parentese, desempilha
						if(listaTokens.get(index).chave.equals("abre_parenteses"))
						{
							//desempilha token de chave 'palavra_reservada' e valor 'while'
							pilha.pop();
							
							//empilha token de valor 'abre parenteses', que esta na posicao atual da lista de tokens
							pilha.push(listaTokens.get(index));
						}
					}
					
					//se for void, o token da lista na posico atual deve ser abre_parentese
					if(pilha.lastElement().valor.equals("void"))
					{		
						//se token da posicao atual da lista de tokens for abre parentese, desempilha
						if(listaTokens.get(index).chave.equals("abre_parenteses"))
						{
							//desempilha token de chave 'palavra_reservada' e valor 'void'
							pilha.pop();
							
							//empilha token de valor abre parenteses, que esta na posicao atual da lista de tokens
							pilha.push(listaTokens.get(index));
						}
					}
					
					//se for else, o token da lista na posico atual deve ser abre_chaves
					if(pilha.lastElement().valor.equals("else"))
					{		
						//se token da posicao atual da lista de tokens for abre chaves, desempilha
						if(listaTokens.get(index).chave.equals("abre_chave"))
						{
							//desempilha token de chave 'palavra_reservada' e valor 'else'
							pilha.pop();
							
							//empilha token de valor abre chave, que esta na posicao atual da lista de tokens
							pilha.push(listaTokens.get(index));
						}
					}
					
					//se for struct, o token da lista na posico atual deve ser abre_chaves
					if(pilha.lastElement().valor.equals("struct"))
					{		
						//se token da posicao atual da lista de tokens for abre chaves, desempilha
						if(listaTokens.get(index).chave.equals("abre_chave"))
						{
							//desempilha token de chave 'palavra_reservada' e valor 'struct'
							pilha.pop();
							
							//empilha token de valor abre chave, que esta na posicao atual da lista de tokens
							pilha.push(listaTokens.get(index));
						}
					}
				}
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao executar analise Sitatica " + e);
		}
	}
}
