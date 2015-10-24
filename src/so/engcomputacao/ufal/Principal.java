package so.engcomputacao.ufal;

import java.util.List;

public class Principal {

	public static void main(String[] args) {

		List<Tarefa> tarefas = SistemaOperacional.lerDoArquivo(args[0]);
		SistemaOperacional so = new SistemaOperacional(tarefas);

		if (args[1].equals("fcfs"))
			so.escalonarTarefasFCFS();

		if (args[1].equals("rr"))
			so.roundRobin();
	}

}
