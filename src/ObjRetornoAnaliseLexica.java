import java.util.ArrayList;
import java.util.HashMap;

//classe usada para montar objeto de retorno da analise lexica
public class ObjRetornoAnaliseLexica {

	//lista de objetos tokens
	public ArrayList<MToken> tokens;
	
	//hash contendo o alfabeto
	public HashMap<String, String> alfabeto;
	
	//constutor 
	public ObjRetornoAnaliseLexica(ArrayList<MToken> tokens, HashMap<String, String> alfabeto) {
		this.tokens = tokens;
		this.alfabeto = alfabeto;
	}
	
	//construtor vazio
	public ObjRetornoAnaliseLexica(){}
	
}
