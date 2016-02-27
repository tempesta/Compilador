import java.beans.FeatureDescriptor;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
/*
 * Classe que transforma a saida do analisador semantico em codigo de tres enderecos
 */

public class TresEnderecos {

	private String labelRepeticao, labelRepeticaoOut, labelCondicional; //atributos que controlam os labels para identificar os rotulos
	private String ifLabel, elseLabel, atribuicao; // labels para identificacao de rotulos
	private int contadorRepeticao, contadorCondicional, contadorTemporarios; //contadores de cada label
	private StringBuilder saidaCodigo; // codigo de tres enderecos
	private Stack<String> pilhaFechaChaves; //pilha que auxilia no processo de equivalencia entre as chaves para abrir e fechar blocos
	
	//construtor padrao: inicia os valores nos respectivos atributos
	public TresEnderecos() {
		super();
		this.atribuicao = new String(":=");
		this.elseLabel = new String("ifnot");
		this.ifLabel = new String("if");
		this.labelCondicional = new String("_I");
		this.labelRepeticao = new String("_W");
		this.labelRepeticaoOut = new String("_Wout");
		this.contadorCondicional = 1;
		this.contadorRepeticao = 1;
		this.contadorTemporarios = 1;
		this.saidaCodigo = new StringBuilder();
		this.pilhaFechaChaves = new Stack<String>();
	}
	
	//metodo acessor: retorna o codigo de tres enderecos
	public StringBuilder getSaidaCodigo() {
		return saidaCodigo;
	}
	
	//metodo acessor: retorna o label de repeticao
	public String getLabelRepeticao() {
		return labelRepeticao;
	}

	//metodo acessor: retorna o label condicional
	public String getLabelCondicional() {
		return labelCondicional;
	}

	//metodo acessor: retorna if para auxiliar na construcao dos codigos
	public String getIfLabel() {
		return ifLabel;
	}

	//metodo acessor: retorna else para auxiliar na construcao dos codigos
	public String getElseLabel() {
		return elseLabel;
	}

	//retorna simbolo :=
	public String getAtribuicao() {
		return atribuicao;
	}

	// retorna o contador que representa a proxima iteracao
	public int getContadorRepetica() {
		return contadorRepeticao;
	}

	//altera valor para contador de repeticoes (iteracoes)
	public void setContadorRepetica(int contadorRepetica) {
		this.contadorRepeticao = contadorRepetica;
	}

	//retorna valor para contador condicional
	public int getContadorCondicional() {
		return contadorCondicional;
	}

	//seta valor no atributo contador inicial
	public void setContadorCondicional(int contadorCondicional) {
		this.contadorCondicional = contadorCondicional;
	}

	//retorna valor do contador temporario
	public int getContadorTemporarios() {
		return contadorTemporarios;
	}

	//seta valor no atributo contador de temporarios
	public void setContadorTemporarios(int contadorTemporarios) {
		this.contadorTemporarios = contadorTemporarios;
	}

	//seta valor no atributo labelRepeticao
	public void setLabelRepeticao(String labelRepeticao) {
		this.labelRepeticao = labelRepeticao;
	}

	//seta valor no atributo labelCondicional
	public void setLabelCondicional(String labelCondicional) {
		this.labelCondicional = labelCondicional;
	}

	//seta valor no label if
	public void setIfLabel(String ifLabel) {
		this.ifLabel = ifLabel;
	}

	//seta valor no label else
	public void setElseLabel(String elseLabel) {
		this.elseLabel = elseLabel;
	}
	
	/*
	 * Metodo que gera o codigo de tres enderecos
	 */
	public void geraCodigo(final LinkedHashMap<Integer, ArrayList<MToken>> linhasToken) {
		
		//percorre todos os tokens
		for (Entry<Integer, ArrayList<MToken>> entry : linhasToken.entrySet()) {
			
			ArrayList<MToken> value = entry.getValue();
			boolean flag = false;
			
			for (int i = 0; i < value.size() && !flag; i++) {
				
				MToken token = new MToken(value.get(i));
					
					//identifica as peculiaredades de cada trecho de codigo para criar o codigo de tres enderecos
					//declaracao ou declaracao seguida de expressao				
					if (token.valor.equals("int") || token.valor.equals("float") || token.valor.equals("void") || token.valor.equals("char")) {
						constroiExpressao(value);
						flag = true;
					} else if (token.chave.equals("letra")) { //variavel
						constroiExpressao(value);
						flag = true;
					} else if (token.chave.equals("palavras_reservadas") && token.valor.equals("if")) { //condicional
						flag = true;
						pilhaFechaChaves.push(labelCondicional + contadorCondicional); //empilha o fecha chaves para o comando condicional
						contadorCondicional++; //incrementa contador de comandos condicionais
						constroiExpressao(value);//constroi expressao equivalente
					} else if (token.chave.equals("palavras_reservadas") && token.valor.equals("while")) { //while
						flag = true;
						pilhaFechaChaves.push(labelRepeticaoOut + contadorRepeticao); //empilha o fecha chaves para o comando de iteracao
						constroiExpressao(value); //constroi expressao para while
					} else if (token.valor.equals("}")) { //identifica fecha chaves
						if (pilhaFechaChaves.lastElement().contains("W")) {//verifica se o topo da pilha de labes eh referente ao while
							this.saidaCodigo.append("\tGOTO _W" + pilhaFechaChaves.lastElement().substring(5) + "\n");//acrescenta topo da pilha (fecha chaves do while) ao codigo de tres enderecos
						} 
						this.saidaCodigo.append(pilhaFechaChaves.pop() + ":\n"); //acrescenta label ao codigo de tres enderecos
						
					}
			}		
		}			
	}
	

	/*
	 * Metodo responsavel por converter as expressoes do codigo fonte para o codigo de tres enderecos
	 */
	private void constroiExpressao(ArrayList<MToken> value) {
	
		//declaracao comum, to tipo int a;
		if (value.size() == 3) {
			if (value.get(0).valor.equals("int")) {
				this.saidaCodigo.append("\t" + value.get(1).valor + " " + this.atribuicao + " 0\n"); // lida com declaracoes do tipo int a = 0;
			} else if (value.get(0).valor.equals("float")) {
				this.saidaCodigo.append("\t" + value.get(1).valor + " " + this.atribuicao + " 0.0\n"); //lida com declaracoes do tipo float a = 0.0;
			} else if (value.get(0).valor.equals("char")) {
				this.saidaCodigo.append("\t" + value.get(1).valor + " " +this.atribuicao + " \''\n");//lida com declaracoes do tipo char a = ''
			}			
		}
		
		//expressao de soma, divisao, subtracao e multiplicacao
		if (value.get(0).chave.equals("identificadores")) {
			//verifica tamanho da expressao
			if (value.size() == 7) {
				//expressao da forma int x = y + z;
				this.saidaCodigo.append("\t" + value.get(1).valor + " " + this.atribuicao + " " + value.get(3).valor +  " "  + value.get(4).valor + " " + value.get(5).valor + "\n");	
			} else if (value.size() == 5) {
				//int a = b ou int a = 0
				this.saidaCodigo.append("\t" + value.get(1).valor + " " + this.atribuicao + " " + value.get(3).valor + "\n");
			}
		} else if (value.get(0).chave.equals("letra")) {
			if (value.size() == 6) {
				//expressao da forma x = y + z;
				this.saidaCodigo.append("\t" + value.get(0).valor + this.atribuicao + value.get(2).valor + " " + value.get(3).valor + " " + value.get(4).valor + "\n");
			} else if (value.size() == 4) {
				//a = b ou a = 0
				this.saidaCodigo.append("\t" + value.get(0).valor + " " + this.atribuicao + value.get(2).valor + "\n");
			} 
		} else if (value.get(0).chave.equals("palavras_reservadas") && value.get(0).valor.equals("if")) {
			//gera codigo para if
			this.saidaCodigo.append("\tifnot " +value.get(2).valor + " " + value.get(3).valor + " " + value.get(4).valor + " GOTO " + pilhaFechaChaves.lastElement() + "\n"); //insere if no codigo de tres enderecos
		} else if (value.get(0).chave.equals("palavras_reservadas") && value.get(0).valor.equals("while")) {
			//gera codigo para while
			this.saidaCodigo.append(labelRepeticao + contadorRepeticao + ":\n");
			this.saidaCodigo.append("\tifnot " +value.get(2).valor + " " + value.get(3).valor + " " + value.get(4).valor + " GOTO " + pilhaFechaChaves.lastElement() + "\n"); //insere while no codigo de tres enderecos
			contadorRepeticao++;
		}
		
	}
	
}
