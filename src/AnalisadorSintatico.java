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
	
	//Funcao que verifica sintaxe 'if' e 'while'
	//Parametros: pilha -> pilha, 
	//			  tokenAtual -> token que esta sendo verificado no metodo analiseSintatica,
	//            listaTokens -> lista de tokens para caso seja necessario mais iteracoes,
	//			  index -> retorna a posicao atual de leitura da lista caso seja necessario mais iteracoes
	//retorno : pilha apos casar ou nao com proximo elemento,
	//			
	
	private Stack<MToken> verificaSintaxeIFeWHILE(Stack<MToken> pilha, MToken tokenAtual, ArrayList<MToken> listaTokens, int index)
	{
		return pilha;
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
