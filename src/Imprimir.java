import java.util.Map;

public class Imprimir {
	
	public void imprimirToken(Map<Integer, Map<String, Map<String, String>>> map)
	{	
		System.out.println("Tokens");
		for (Map.Entry<Integer, Map<String, Map<String, String>>> hash : map.entrySet()) {
			
			for (Map.Entry<String, String> result : hash.getValue().get("tokens").entrySet()) {
				System.out.print("<" + result.getKey() + "> <" + result.getValue() + ">   ");
			}
		}
		
		System.out.println("Erros");
		for (Map.Entry<Integer, Map<String, Map<String, String>>> hash : map.entrySet()) {
			
			for (Map.Entry<String, String> result : hash.getValue().get("erros").entrySet()) {
				System.out.print("<" + result.getKey() + "> <" + result.getValue() + ">   ");
			}
		}
	}
	
	public void imprimirErro(Map<String, String> map)
	{
		System.out.println("Erros");
		for (Map.Entry<String, String> result : map.entrySet()) {
		    System.out.print("<" + result.getKey() + "> <" + result.getValue() + ">   ");
		}
	}
}
