package so.engcomputacao.ufal;

import java.util.List;

public class Principal {

	public static void main(String[] args) {

		List<Tarefa> tarefas = SistemaOperacional.lerDoArquivo("input.txt");
		SistemaOperacional so = new SistemaOperacional(tarefas);
		// if (args[1].equals("fcfs")) {
		//so.escalonarTarefasFCFS();
		// } else if (args[1].equals("rr")) {
		so.rr();
		// }

	}

}
