package so.engcomputacao.ufal;

import java.util.ArrayList;
import java.util.List;

public class Principal {

	public static void main(String[] args) {
		

		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		//teste para o FCFS
		for(int i = 0; i < 10; i++){
			Tarefa t = new Tarefa();
			t.setId(i);
			t.setDuracao((i+1)*5/2);
			t.setPrioridade(10 - i);
			t.setTempChegada(i - i/2);
			tarefas.add(t);
		}
		
		
//		Tarefa t1 = new Tarefa();
//		t1.setId(1);
//		t1.setDuracao(13);
//		t1.setPrioridade(10);
//		t1.setTempChegada(0);
//		
//		Tarefa t2 = new Tarefa();
//		t2.setId(2);
//		t2.setDuracao(4);
//		t2.setPrioridade(20);
//		t2.setTempChegada(5);
//		
//		Tarefa t3 = new Tarefa();
//		t3.setId(3);
//		t3.setDuracao(4);
//		t3.setPrioridade(5);
//		t3.setTempChegada(6);
//
//		Tarefa t4 = new Tarefa();
//		t4.setId(4);
//		t4.setDuracao(4);
//		t4.setPrioridade(8);
//		t4.setTempChegada(6);
//		
//		
//		tarefas.add(t1);
//		tarefas.add(t2);
//		tarefas.add(t3);
//		tarefas.add(t4);
		
		System.out.println("Tarefas adicionadas ao SO: "+tarefas.size());
		
		SistemaOperacional so = new SistemaOperacional(tarefas);
		
		so.escalonarTarefasFCFS();
//		so.escalonarTarefasRR();
		
	}

}
