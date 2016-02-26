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
			
			ArrayList<String> teste = new ArrayList<String>(lex.removeComentario(codigos));
			
			//String a = lex.separar(teste.toString());
			
			//faz a analise lexica
			resultadoAnaliseLexica = lex.analiseLexica(teste, alfabeto);
			
			LinkedHashMap<Integer, ArrayList<MToken>> linhasToken = new LinkedHashMap<Integer, ArrayList<MToken>>();
			
			//tokens formatados na mesma linha
			linhasToken = funcao.agrupaTokensPorLinha(resultadoAnaliseLexica.tokens);
			
			//cria variavel que sera usada para identificar execucao de uma condicao
			boolean condicional = false;
			
			//cria variavel que sera usada para identificar a declaracao de parametro
			boolean parametro = false;
			
			//cria variavel para controle de inicio e fim de um bloco
			boolean bloco  = false;

			Stack<MToken> retornoSintax = new Stack<MToken>();
			
			//se o primeiro token for diferente de simbolo inicial, imprime erro, e continua a execuao
			if(!resultadoAnaliseLexica.tokens.get(0).chave.equals("simbolo_inicial"))
			{
				//relata erro de faltando simbolo inicial
				System.out.println("Erro sintatico na linha: " + resultadoAnaliseLexica.tokens.get(0).linha + ". Faltando simbolo inicial 'Program'");
			}
			
			retornoSintax = sintax.analiseSintatica(resultadoAnaliseLexica.tokens, new Stack<MToken>(), condicional, parametro, bloco, resultadoAnaliseLexica.alfabeto);
			
			funcao.imprimePilhaErro(retornoSintax);
			
			funcao.imprimeTokens(resultadoAnaliseLexica.tokens);
			
//			AnalisadorSemantico semantico = new AnalisadorSemantico();
			
			//vincula cada variavel ao seu escopo
//			semantico.constroiDeclaracaoBlocos(alfabeto, linhasToken);
			
			//acrescenta as declaracoes na tabela de simbolos
//			semantico.incrementaTabela(alfabeto, linhasToken);
			
		} catch (Exception e) {
			System.console().writer().println(e + "cannot open this file");
		}		
		
	}

}
