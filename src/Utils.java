import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Utils {
	
	//Funcao criada para ler o arquivo com codigo fonte
	public ArrayList<String> leituraArquivo(String nomeArquivo)
	{
		//armazena o diretorio corrente da aplicacao, onde deve estar o arquivo a ser lido
		String path = Paths.get("").toAbsolutePath().toString();
		
		try {
			//cria um buffer de leitura a partir do diretorio corrente da aplicacao + o nome do arquivo
			BufferedReader reader = new BufferedReader(new FileReader(path + "/src/" + nomeArquivo));
			
			//inicializa o array com as linhas de codigo a serem retornados
			ArrayList<String> codigos = new ArrayList<>();
			
			//inicializa a string com a primeira linha de codigo do arquivo
			String linha = reader.readLine();
			
			//enquanto nao encontrar o fim do arquivo continua lendo e adicionando as linhas de codigo
			while(linha != null)
			{
				//adicona as linhas de cogido ao array 'codigos'
				codigos.add(linha);
				
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
