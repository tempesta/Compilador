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
				
				//caso a chave do ultimo elemento da pilha seja identificador
				if(pilha.lastElement().chave.equals("identificador"))
				{
					//verifica se a chave do token da posicao atual é nome de uma variavel(nao inicia com digito e nao contem caracter especial)
					if(listaTokens.get(index).chave.equals("letra"))
					{
						//caso seja(identificador reconhecido), desempilha identificador
						pilha.pop();
					}
				}				
				
				//caso a chave do ultimo elemento da pilha seja letra
				if(pilha.lastElement().chave.equals("letra"))
				{
					switch (listaTokens.get(index).chave) {
					case "relacional":
					case "ponto_virgula":
						pilha.pop();
						break;
					default:
						break;
					}
				}
				
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
							pilha.pop();
						}
					}
					
					//se for while, o token da lista na posico atual deve ter abre_parentese
					if(pilha.lastElement().valor.equals("while"))
					{		
						//se token da posicao atual da lista de tokens for abre parentese, desempilha
						if(listaTokens.get(index).chave.equals("abre_parenteses"))
						{
							pilha.pop();
						}
					}
					
					//se for void, o token da lista na posico atual deve ter abre_parentese
					if(pilha.lastElement().valor.equals("void"))
					{		
						//se token da posicao atual da lista de tokens for abre parentese, desempilha
						if(listaTokens.get(index).chave.equals("abre_parenteses"))
						{
							pilha.pop();
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
