import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.channels.Pipe;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;

public class Utils {
	
	//Funcao para agrupar tokens por linha
	public  LinkedHashMap<Integer,ArrayList<MToken>> agrupaTokensPorLinha(ArrayList<MToken> listaTokens)
	{
		LinkedHashMap<Integer,ArrayList<MToken>> linhasToken = new LinkedHashMap<Integer, ArrayList<MToken>>();
		
		int aux = 1;
		
		ArrayList<MToken> arrayToken = new ArrayList<MToken>();
		
		for (int index = 0; index < listaTokens.size(); index++) 
		{
			if(listaTokens.get(index).linha != aux)
			{
				ArrayList<MToken> ar = new ArrayList<MToken>(arrayToken);			
				
				linhasToken.put(aux, ar);
				aux++;
				arrayToken.clear();
			}
			arrayToken.add(listaTokens.get(index));	
		}
		
		linhasToken.put(aux, arrayToken);
		
		return linhasToken;
	}
	
	//Funcao criada para ler o arquivo com codigo fonte
	public ArrayList<String> leituraArquivo(String nomeArquivo)
	{
		//armazena o diretorio corrente da aplicacao, onde deve estar o arquivo a ser lido
		String path = Paths.get("").toAbsolutePath().toString();
		
		try {
			//cria um buffer de leitura a partir do diretorio corrente da aplicacao + o nome do arquivo
			BufferedReader reader = new BufferedReader(new FileReader(path + "/testes/" + nomeArquivo));
			
			//inicializa o array com as linhas de codigo a serem retornados
			ArrayList<String> codigos = new ArrayList<>();
			
			//inicializa a string com a primeira linha de codigo do arquivo
			String linha = reader.readLine();
			
			//enquanto nao encontrar o fim do arquivo continua lendo e adicionando as linhas de codigo
			while(linha != null)
			{
				//remove espa�o e tabulacao do inicio e fim da linha
				linha = linha.trim();
				
				if(!linha.isEmpty())
				{
					//adicona as linhas de cogido ao array 'codigos'
					codigos.add(linha);
				}
				//le a proxima linhas
				linha = reader.readLine();
			}
			
			//fecha o buffer de leitura
			reader.close();
			
			//retorna o array com as linhas de codigo do arquivo lido
			return codigos;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao tentar ler o arquivo " + nomeArquivo + ", " + e);
			return null;
		}
	}

	//Funcao criada para imprimir a pilha com erros da analise sinstatica
	public void imprimePilhaErro(Stack<MToken> pilha)
	{
		while(!pilha.isEmpty())
		{
			System.out.println("Erro sintatico apos o token " + pilha.lastElement().valor + "  na linha: " + pilha.lastElement().linha);
			pilha.pop();
		}
	}
	
	//Funcao criada para imprimir os tokens da analise lexica
	public void imprimeTokens(ArrayList<MToken> tokens)
	{
		try {
			for (MToken item : tokens) {
				System.out.println("<" + item.chave + "> <" + item.valor + ">" );
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro ao imprimir tokens " + e);
		}
	}
}
