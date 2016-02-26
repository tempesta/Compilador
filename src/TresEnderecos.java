import java.beans.FeatureDescriptor;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;


public class TresEnderecos {

	private String labelRepeticao, labelRepeticaoOut, labelCondicional;
	private String ifLabel, elseLabel, atribuicao;
	private int contadorRepeticao, contadorCondicional, contadorTemporarios;
	private StringBuilder saidaCodigo;
	private Stack<String> pilhaFechaChaves;
	
	
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
	
	public StringBuilder getSaidaCodigo() {
		return saidaCodigo;
	}
	
	public String getLabelRepeticao() {
		return labelRepeticao;
	}


	public String getLabelCondicional() {
		return labelCondicional;
	}


	public String getIfLabel() {
		return ifLabel;
	}


	public String getElseLabel() {
		return elseLabel;
	}


	public String getAtribuicao() {
		return atribuicao;
	}


	public int getContadorRepetica() {
		return contadorRepeticao;
	}


	public void setContadorRepetica(int contadorRepetica) {
		this.contadorRepeticao = contadorRepetica;
	}


	public int getContadorCondicional() {
		return contadorCondicional;
	}


	public void setContadorCondicional(int contadorCondicional) {
		this.contadorCondicional = contadorCondicional;
	}


	public int getContadorTemporarios() {
		return contadorTemporarios;
	}


	public void setContadorTemporarios(int contadorTemporarios) {
		this.contadorTemporarios = contadorTemporarios;
	}


	public void setLabelRepeticao(String labelRepeticao) {
		this.labelRepeticao = labelRepeticao;
	}


	public void setLabelCondicional(String labelCondicional) {
		this.labelCondicional = labelCondicional;
	}


	public void setIfLabel(String ifLabel) {
		this.ifLabel = ifLabel;
	}


	public void setElseLabel(String elseLabel) {
		this.elseLabel = elseLabel;
	}
	
	
	public void geraCodigo(final LinkedHashMap<Integer, ArrayList<MToken>> linhasToken) {
		
		for (Entry<Integer, ArrayList<MToken>> entry : linhasToken.entrySet()) {
			
			ArrayList<MToken> value = entry.getValue();
			boolean flag = false;

			
			for (int i = 0; i < value.size() && !flag; i++) {
				
				MToken token = new MToken(value.get(i));
				System.out.println(token.valor);
				
				//declaracao ou declaracao seguida de expressao				
					if (token.valor.equals("int") || token.valor.equals("float") || token.valor.equals("void") || token.valor.equals("char")) {
						constroiExpressao(value);
						flag = true;
					} else if (token.chave.equals("letra")) {
						constroiExpressao(value);
						flag = true;
					} else if (token.chave.equals("palavras_reservadas") && token.valor.equals("if")) {
						flag = true;
						pilhaFechaChaves.push(labelCondicional + contadorCondicional); 
						contadorCondicional++;
						constroiExpressao(value);
					} else if (token.chave.equals("palavras_reservadas") && token.valor.equals("while")) {
						flag = true;
						pilhaFechaChaves.push(labelRepeticaoOut + contadorRepeticao); 
						constroiExpressao(value);
					} else if (token.valor.equals("}")) {
						if (pilhaFechaChaves.lastElement().contains("W")) {
							this.saidaCodigo.append("\tGOTO _W" + pilhaFechaChaves.lastElement().substring(5) + "\n");
						} 
						this.saidaCodigo.append(pilhaFechaChaves.pop() + ":\n");
						
					}
			}		
		}			
	}
	
	/**
	 * refatora a expressao
	 * @param value
	 * @return
	 */
	private String refatoraExpressao(final ArrayList<MToken> value) {
		String expressao = new String();
		int contadorTemporarios = 1;
		boolean precedencia = false;
		boolean igualdade = false;
		//verifica se existe precedencia nos operadores
		for (int i = 0; i < value.size() && !precedencia; i++) {
			if (value.get(i).valor.equals("*") || value.get(i).valor.equals("'/") || value.get(i).valor.equals("(") || value.get(i).valor.equals(")")) {
				precedencia = true;
			}
		}
		
		//nao ha precedencia, refatoracao simples
		
		
		
		return null;
	}

	
	/*public void teste(ArrayList<MToken> linha, HashMap<String, String> hashExpressao, String strExpressao)
	{
		int indexIgual, indexSoma, indexSubtracao, indexAbreParentese, indexMult, indexDivisao,indexFechaParentese;
		
		int contaNovaExpressao = 0;
		
		if(linha.indexOf("=") > 0)
		{
			teste(new ArrayList<MToken>(linha.subList(linha.indexOf("="), linha.size())), hashExpressao, strExpressao);
		}		
		if(linha.indexOf("+") > 0)
		{
			indexSoma = linha.indexOf("+");
			if(hashExpressao.get(linha.get(indexSoma - 1).valor + "+" + linha.get(indexSoma + 1).valor) == null)
			{
				hashExpressao.put(linha.get(indexSoma - 1).valor + "+" + linha.get(indexSoma + 1).valor, "T_" + contaNovaExpressao);
				contaNovaExpressao++;
			}
			strExpressao += "T_" + contaNovaExpressao;
			teste(new ArrayList<MToken>(linha.subList(indexSoma + 1, linha.size())), hashExpressao, strExpressao);
		}
		if(linha.indexOf("-") > 0)
		{
			indexSubtracao = lista.indexOf("-");
		}
		if(linha.indexOf("*") > 0)
		{
			indexMult = lista.indexOf("*");
		}
		if(linha.indexOf("/") > 0)
		{
			indexDivisao = lista.indexOf("/");
		}
		if(linha.indexOf("(") > 0)
		{
			indexAbreParentese = lista.indexOf("(");
			if(lista.indexOf(")") > 0)
			{
				indexFechaParentese = lista.indexOf(")");
			}
		}
		
		ArrayList<MToken> sublista = new ArrayList<MToken>(lista.subList(indexIgual + 1, lista.size()));
		
		teste(sublista, objTresEndereco, listaObjEndereco);
	}*/
	


	private void constroiExpressao(ArrayList<MToken> value) {
	
		//declaracao comum
		if (value.size() == 3) {
			if (value.get(0).valor.equals("int")) {
				this.saidaCodigo.append("\t" + value.get(1).valor + " " + this.atribuicao + " 0\n");
			} else if (value.get(0).valor.equals("float")) {
				this.saidaCodigo.append("\t" + value.get(1).valor + " " + this.atribuicao + " 0.0\n");
			} else if (value.get(0).valor.equals("char")) {
				this.saidaCodigo.append("\t" + value.get(1).valor + " " +this.atribuicao + " \''\n");
			}			
			System.out.println(this.saidaCodigo);
		}
		
		//expressao
		if (value.get(0).chave.equals("identificadores")) {
			//verifica tamanho da expressao
			if (value.size() == 7) {
				//expressao da forma int x = y + z;
				this.saidaCodigo.append("\t" + value.get(1).valor + " " + this.atribuicao + " " + value.get(3).valor +  " "  + value.get(4).valor + " " + value.get(5).valor + "\n");
			} else if (value.size() > 7) {
				//expressao que precisa ser refatorada
				//this.saidaCodigo.append(refatoraExpressao(value));				
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
			this.saidaCodigo.append("\tifnot " +value.get(2).valor + " " + value.get(3).valor + " " + value.get(4).valor + " GOTO " + pilhaFechaChaves.lastElement() + "\n");
		} else if (value.get(0).chave.equals("palavras_reservadas") && value.get(0).valor.equals("while")) {
			//gera codigo para while
			this.saidaCodigo.append(labelRepeticao + contadorRepeticao + ":\n");
			this.saidaCodigo.append("\tifnot " +value.get(2).valor + " " + value.get(3).valor + " " + value.get(4).valor + " GOTO " + pilhaFechaChaves.lastElement() + "\n");
			contadorRepeticao++;
		}
		
		System.out.print(this.saidaCodigo);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
