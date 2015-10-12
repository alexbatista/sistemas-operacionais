package so.engcomputacao.ufal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-seconds

public class SistemaOperacional {
	
	private List<Tarefa> filaDeTarefas = new ArrayList<Tarefa>();
	
	public SistemaOperacional(List<Tarefa>  tarefas){
		this.filaDeTarefas = tarefas;
	}
	public SistemaOperacional(){
		
	}
	
	public void escalonarTarefasFCFS(){
		Collections.sort(filaDeTarefas);
		
		printCabecalho(filaDeTarefas.size());
		
		int j = 0;
		int timer = 0;
		while(j < filaDeTarefas.size()){
			try { 
				timer +=1;
				filaDeTarefas.get(j).setEstado(Estado.EXECUTANDO);
				printTarefas(timer, filaDeTarefas);
				Thread.sleep(1000); 
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}finally{
				if(filaDeTarefas.get(j).getTempSaida() <= timer){
					filaDeTarefas.get(j).setEstado(Estado.CONCLUIDA);
					j++;	
				}
			}
		}
		
		System.out.println("----------- Fim da execução do FCFS -------------------");
	}
	
	public void escalonarTarefasRR(){
		int timer = 0;

		//ORDENAÇÃO E IMPRESSÃO NA TELA
		Collections.sort(filaDeTarefas);
		System.out.print("tempo ");
		for(int i = 0; i < filaDeTarefas.size(); i++){
			System.out.print("P"+i+" ");
		}
		
		System.out.println("");

		
		Tarefa tarefaExecutando = filaDeTarefas.get(0);
		filaDeTarefas.remove(tarefaExecutando);
		System.out.println("Timer: "+timer);
		
		while(!filaDeTarefas.isEmpty()){
			try {
				tarefaExecutando.setEstado(Estado.EXECUTANDO);
				System.out.println("A tarefa P"+tarefaExecutando.getId()+" está sendo executada. Cujo tempo de entrada é: "+tarefaExecutando.getTempChegada()+" e prioridade: "+tarefaExecutando.getPrioridade()+" e dura: "+tarefaExecutando.getDuracao());
				//Quantum de 2 segundos
				timer += 2;
				Thread.sleep(2*1000); 
				System.out.println("Timer: "+timer);
				
				if(tarefaExecutando.getTempSaida() > timer){
					tarefaExecutando.setDuracao(tarefaExecutando.getDuracao()-(timer-tarefaExecutando.getTempChegada()));
					tarefaExecutando.setEstado(Estado.AGUARDANDO);
					filaDeTarefas.add(tarefaExecutando);
				}else{
					System.out.println("Tarefa P"+tarefaExecutando.getId()+" foi concluída.");
				}
				
				Collections.sort(filaDeTarefas);
				
//				if(filaDeTarefas.get(0).getTempChegada() <= timer){
					tarefaExecutando = filaDeTarefas.get(0);
					tarefaExecutando.setTempChegada(timer);
					filaDeTarefas.remove(0);
//				}
								
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}finally{
				//verifico se minha tarefa terminou, se sim, removo da lista, se não, verifico quais tarefas estão na pilha durante esse tempo e verifio a prioridade (CRIAR LISTA ADICIONAL APENAS PARA ESSA EXECUÇÃO)
			}
		}
		
		System.out.println("-------------- Fim da Execução do RR ------------------------");
	}
	
	public void printCabecalho(int numTarefas){
		System.out.print("tempo   ");
		for(int i = 1; i<= numTarefas; i++){
			System.out.print("P"+i+"  ");
		}
		System.out.println("");
	}
	
	public void printTarefas(int timer,List<Tarefa> tarefas){
		
		List<String> estados = new ArrayList<String>(tarefas.size());
		
		if(timer < 10){
			System.out.print(" "+(timer-1)+"-"+timer+"   ");
		}else if (timer == 10){
			System.out.print(" "+(timer-1)+"-"+timer+"  ");
		}
		else{
			System.out.print(" "+(timer-1)+"-"+timer+" ");
		}
		for(Tarefa tarefa : tarefas){
			
			if(tarefa.getEstado() == Estado.NOVA)
				estados.add(tarefa.getId(),"    ");

			if(tarefa.getEstado() == Estado.EXECUTANDO)
				estados.add(tarefa.getId()," ## ");

			if(tarefa.getEstado() == Estado.AGUARDANDO)
				estados.add(tarefa.getId()," -- ");

			if(tarefa.getEstado() == Estado.CONCLUIDA)
				estados.add(tarefa.getId(),"    ");

		}
		
		for(int i = 0; i< estados.size(); i++){
			System.out.print(estados.get(i));
		}
		
		System.out.println("");
	}

}
