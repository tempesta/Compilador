public class MToken{
	
	public String chave;
	public String valor;
	public int linha;
		
	public MToken(String nomeclasse, String palavra, int linha)
	{
		this.chave = nomeclasse;
		this.valor = palavra;
		this.linha = linha;
		
	}
	
	public MToken()
	{
		
	}

	public MToken(MToken mToken) {
		this(mToken.chave, mToken.valor, mToken.linha);
	}
}