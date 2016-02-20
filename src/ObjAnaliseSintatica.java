import java.util.ArrayList;
import java.util.Stack;

//Classe criada para montar um objeto de retorno da analise sintatica, que contem a lista de tokens atualizada, e a pilha tambem atualizada
public class ObjAnaliseSintatica {

	//armazena a lista de tokens
	public ArrayList<MToken> listaTokens;
	
	//armazena a pilha com tokens ainda nao lidos na lista de tokens
	public Stack<MToken> pilha;
	
	//construtor com objetos passador como parametro
	public ObjAnaliseSintatica(ArrayList<MToken> listaTokens, Stack<MToken> pilha)
	{
		this.listaTokens = listaTokens;
		this.pilha = pilha;
	}
	
	//Construtor que inicializa os objetos vazios
	public ObjAnaliseSintatica(){}
	
}
