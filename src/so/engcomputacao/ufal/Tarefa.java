package so.engcomputacao.ufal;

public class Tarefa implements Comparable<Tarefa> {
	
	private int id;
	private int tempChegada;
	private int prioridade;
	private int duracao;
	private int tempoExecutado = 0;
	
	/**
	 * Retorna o tempo total que a tarefa executou*/
	public int getTempoExecutado() {
		return tempoExecutado;
	}
	
	/**
	 * Incrementa o tempo executado de acordo com o valor passado no parâmetro*/
	public void setTempoExecutado(int tempoExecutado) {
		this.tempoExecutado += tempoExecutado;
	}

	private Estado estado = Estado.NOVO;
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTempChegada() {
		return tempChegada;
	}
	public void setTempChegada(int tempChegada) {
		this.tempChegada = tempChegada;
	}
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	public int getTempSaida(){
		return tempChegada+duracao;
	}
	
	@Override
	public int compareTo(Tarefa outro) {
		
	    if((this.tempChegada == outro.getTempChegada())){
	    	if(this.prioridade > outro.getPrioridade()){
	    		return -1;
	    	}else{
	    		return 1;
	    	} 	
	    }

		if (this.tempChegada < outro.getTempChegada()) {
	            return -1;
	    }
		
	    if (this.tempChegada > outro.getTempChegada()) {
	           return 1;
	    }
	    
	    return 0;
	}
	
	

}
