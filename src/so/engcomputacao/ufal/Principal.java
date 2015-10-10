package so.engcomputacao.ufal;

import java.util.ArrayList;
import java.util.List;

public class Principal {

	public static void main(String[] args) {
		

		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
//		for(int i = 0; i < 10; i++){
//			Tarefa t = new Tarefa();
//			t.setId(i);
//			t.setDuracao((i+1)*5/2);
//			t.setPrioridade(10 - i);
//			t.setTempChegada(i - i/2);
//			tarefas.add(t);
//		}
		
		
		Tarefa t1 = new Tarefa();
		t1.setId(1);
		t1.setDuracao(13);
		t1.setPrioridade(10);
		t1.setTempChegada(0);
		
		Tarefa t2 = new Tarefa();
		t2.setId(2);
		t2.setDuracao(4);
		t2.setPrioridade(prioridade)
		
		System.out.println("Tarefas adicionadas ao SO: "+tarefas.size());
		
		SistemaOperacional so = new SistemaOperacional(tarefas);
		
//		so.escalonarTarefasFCFS();
		so.escalonarTarefasRR();
		
	}

}
