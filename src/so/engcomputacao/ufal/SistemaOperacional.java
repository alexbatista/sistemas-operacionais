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
		
		System.out.print("tempo ");
		for(int i = 0; i < filaDeTarefas.size(); i++){
			System.out.print("P"+i+" ");
		}
		System.out.println("");
		int j = 0;
		
		while(!filaDeTarefas.isEmpty()){
			Tarefa t = filaDeTarefas.get(0);
			try { 
				System.out.println("A tarefa P"+j+" está sendo executada. Cujo tempo de entrada é: "+t.getTempChegada()+" e prioridade: "+t.getPrioridade()+" e dura: "+t.getDuracao());
				Thread.sleep(t.getDuracao()*100); 
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}finally{
				filaDeTarefas.remove(0);
				j++;
			}
		}
		
		System.out.println("----------- Fim da execução do FCFS -------------------");
	}
	
	public void escalonarTarefasRR(){
		int timer = 0;

		//ORDENAÇÃO E IMPRESSÃO NA TELA
		Collections.sort(filaDeTarefas);
//		List<Tarefa> tarefasExecutando = new ArrayList<Tarefa>();
		
		System.out.print("tempo ");
		for(int i = 0; i < filaDeTarefas.size(); i++){
			System.out.print("P"+i+" ");
		}
		
		System.out.println("");
		
		//INICIO A FILA DE TAREFAS A SEREM EXECUTADAS
//		tarefasExecutando.add(tarefas.get(0));
//		tarefas.remove(0);
		
		Tarefa tarefaExecutando = filaDeTarefas.get(0);
		filaDeTarefas.remove(tarefaExecutando);
		
		while(!filaDeTarefas.isEmpty()){
			try {
				System.out.println("A tarefa P"+tarefaExecutando.getId()+" está sendo executada. Cujo tempo de entrada é: "+tarefaExecutando.getTempChegada()+", tempo de saída é: "+tarefaExecutando.getTempSaida()+" e prioridade: "+tarefaExecutando.getPrioridade()+" e dura: "+tarefaExecutando.getDuracao());
				//Quantum de 2 segundos
				timer += 2;
				Thread.sleep(2*1000); 
				System.out.println("Timer: "+timer);
						
//				if(tarefaExecutando.getTempSaida() <= timer){
//					System.out.println("Tarefa P"+tarefaExecutando.getId()+" removida da lista.");
//					filaDeTarefas.remove(tarefaExecutando);
////					tarefaExecutando = filaDeTarefas.get(0);
////					tarefaExecutando.setTempChegada(timer);
//				}
				
				Collections.sort(filaDeTarefas);
//				for(Tarefa tarefa : filaDeTarefas){
					if(filaDeTarefas.get(0).getTempChegada() <= timer){
//						if(tarefa.getPrioridade() > tarefaExecutando.getPrioridade()){
						if(tarefaExecutando.getTempSaida() > timer){
							tarefaExecutando.setDuracao(tarefaExecutando.getDuracao()-(tarefaExecutando.getTempSaida()-tarefaExecutando.getTempChegada()));
							filaDeTarefas.add(tarefaExecutando);
						}else{
							System.out.println("Tarefa P"+tarefaExecutando.getId()+" foi concluída.");
						}
							tarefaExecutando = filaDeTarefas.get(0);
							tarefaExecutando.setTempChegada(timer);
//						}
					}
//				}
				
				
				
								
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}finally{
				//verifico se minha tarefa terminou, se sim, removo da lista, se não, verifico quais tarefas estão na pilha durante esse tempo e verifio a prioridade (CRIAR LISTA ADICIONAL APENAS PARA ESSA EXECUÇÃO)
			}
		}
		
		System.out.println("-------------- Fim da Execução do RR ------------------------");
	}

}
