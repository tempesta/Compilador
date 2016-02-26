import java.util.ArrayList;
//import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {

		try {
			//URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
			
			//inicializa a variavel com funcoes uteis (leitura e impressao, entre outras nao especificas do trabalho)
			Utils funcao = new Utils();
			
			//cria variavel que chamara a funcao analiseLexica da classe AnalisadorLexico
			AnalisadorLexico lex = new AnalisadorLexico();
			
			//cria variavel que chamara a funcao analiseSintatica da classe AnalisadorSintatico
			AnalisadorSintatico sintax = new AnalisadorSintatico();
			
			//cria a variavel que recebera o resultado da analise lexica (tokes e alfabeto atualizado)
			ObjRetornoAnaliseLexica resultadoAnaliseLexica = new ObjRetornoAnaliseLexica();
			
			//cria a funcao para gerar o alfabeto
			Alfabeto criaAlfabeto = new Alfabeto();
			
			//variavel para receber o alfabeto criado
			HashMap<String, String> alfabeto = new HashMap<>();
			
			//gera o alfabeto
			alfabeto = criaAlfabeto.geraClasses();
			
			//cria o array que recebera as linhas de codigo contidas no arquivo a ser lido
			ArrayList<String> codigos = new ArrayList<>();
			
			//executa a leitura do arquivo
			codigos = funcao.leituraArquivo("teste.txt");
			
			
			//faz a analise lexica
			resultadoAnaliseLexica = lex.analiseLexica(codigos, alfabeto);
			
			LinkedHashMap<Integer, ArrayList<MToken>> linhasToken = new LinkedHashMap<Integer, ArrayList<MToken>>();
			
			//tokens formatados na mesma linha
			linhasToken = funcao.agrupaTokensPorLinha(resultadoAnaliseLexica.tokens);

			sintax.analiseSintatica(resultadoAnaliseLexica.tokens, new Stack<MToken>(), false, resultadoAnaliseLexica.alfabeto);
			
			funcao.imprimeTokens(resultadoAnaliseLexica.tokens);
			
			AnalisadorSemantico semantico = new AnalisadorSemantico();
			
			//vincula cada variavel ao seu escopo
			semantico.constroiDeclaracaoBlocos(alfabeto, linhasToken);
			
			//acrescenta as declaracoes na tabela de simbolos
			semantico.incrementaTabela(alfabeto, linhasToken);
			
			//realiza a verificacao de tipos
			semantico.verificaTipo(alfabeto, linhasToken);
			
			TresEnderecos codigo = new TresEnderecos();
			codigo.geraCodigo(linhasToken);
			
		} catch (Exception e) {
			System.console().writer().println(e + "cannot open this file");
		}		
		
	}

}
