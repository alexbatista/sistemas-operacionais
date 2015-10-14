package so.engcomputacao.ufal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-seconds

public class SistemaOperacional {

	private int tempoTotal = 0;

	private List<Tarefa> filaDeTarefas = new ArrayList<Tarefa>();

	public SistemaOperacional(List<Tarefa> tarefas) {
		this.filaDeTarefas = tarefas;
	}

	public SistemaOperacional() {

	}

	public void escalonarTarefasFCFS() {
		Collections.sort(filaDeTarefas);

		printCabecalho(filaDeTarefas.size());

		int j = 0;
		int timer = 0;

		while (j < filaDeTarefas.size()) {

			for (Tarefa tarefa : filaDeTarefas) {
				if (tarefa.getTempChegada() == timer) {
					tarefa.setEstado(Estado.PRONTA);

				}
			}
			timer += 1;

			filaDeTarefas.get(j).setEstado(Estado.EXECUTANDO);
			filaDeTarefas.get(j).setTempoExecutado(1);
			printTarefas(timer, filaDeTarefas);

			if (filaDeTarefas.get(j).getTempoExecutado() == filaDeTarefas.get(j).getDuracao()) {
				filaDeTarefas.get(j).setEstado(Estado.CONCLUIDA);
				j++;
			}

		}

		System.out.println("----------- Fim da execução do FCFS -------------------");
	}

	public void rr() throws IOException {

		int t = 0;
		int p = 0;
		tempoTotal = tempoTotal(filaDeTarefas);

		printCabecalho(filaDeTarefas.size());

		Collections.sort(filaDeTarefas);

		while (tempoTotal > t) {

			for (Tarefa tarefa : filaDeTarefas) {
				if (tarefa.getTempChegada() == t) {
					tarefa.setEstado(Estado.PRONTA);
				}
			}

			if (filaDeTarefas.get(p).getEstado() != Estado.CONCLUIDA) {

				if (filaDeTarefas.get(p).getTempoExecutado() < filaDeTarefas.get(p).getDuracao()) {

					filaDeTarefas.get(p).setEstado(Estado.EXECUTANDO);
					for (int i = 0; i < 2; i++) {
						filaDeTarefas.get(p).setTempoExecutado(filaDeTarefas.get(p).getTempoExecutado() + 1);
						t = t + 1;
						printTarefas(t, filaDeTarefas);

					}
					if (filaDeTarefas.get(p).getTempoExecutado() == filaDeTarefas.get(p).getDuracao()) {
						filaDeTarefas.get(p).setEstado(Estado.CONCLUIDA);

					} else {
						filaDeTarefas.get(p).setEstado(Estado.PRONTA);
					}

				}
				p++;
				p = p % filaDeTarefas.size();
			}
		}

	}

	public void printCabecalho(int numTarefas) {
		System.out.print("tempo   ");
		for (int i = 1; i <= numTarefas; i++) {
			System.out.print("P" + i + "  ");
		}
		System.out.println("");
	}

	public void printTarefas(int timer, List<Tarefa> tarefas) {

		List<String> estados = new ArrayList<String>(Collections.nCopies(tarefas.size(), ""));

		if (timer < 10) {
			System.out.print(" " + (timer - 1) + "-" + timer + "   ");
		} else if (timer == 10) {
			System.out.print(" " + (timer - 1) + "-" + timer + "  ");
		} else {
			System.out.print(" " + (timer - 1) + "-" + timer + " ");
		}
		for (Tarefa tarefa : tarefas) {

			if (tarefa.getEstado() == Estado.EXECUTANDO)
				estados.add(tarefa.getId(), " ## ");

			if (tarefa.getEstado() == Estado.PRONTA)
				estados.add(tarefa.getId(), " -- ");

			if (tarefa.getEstado() == Estado.CONCLUIDA)
				estados.add(tarefa.getId(), "    ");

			if (tarefa.getEstado() == Estado.NOVO)
				estados.add(tarefa.getId(), "    ");

		}

		for (int i = 0; i < estados.size(); i++) {
			System.out.print(estados.get(i));
		}

		System.out.println("");
	}

	public int tempoTotal(List<Tarefa> tarefas) {

		for (Tarefa tarefa : tarefas) {
			tempoTotal += tarefa.getDuracao();
		}
		return tempoTotal;

	}

}
