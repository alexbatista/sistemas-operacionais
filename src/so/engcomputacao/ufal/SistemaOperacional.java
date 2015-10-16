package so.engcomputacao.ufal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

	public void rr() {

		int t = 0;
		int p = 0;
		tempoTotal = tempoTotal(filaDeTarefas);

		printCabecalho(filaDeTarefas.size());

		Collections.sort(filaDeTarefas);

		while (tempoTotal > t) {

			alteraEstado(t);

			if (filaDeTarefas.get(p).getEstado() != Estado.CONCLUIDA) {

				if (filaDeTarefas.get(p).getTempoExecutado() < filaDeTarefas.get(p).getDuracao()) {

					filaDeTarefas.get(p).setEstado(Estado.EXECUTANDO);

					for (int i = 0; i < 2; i++) {
						filaDeTarefas.get(p).setTempoExecutado(1);
						t = t + 1;
						printTarefas(t, filaDeTarefas);
						alteraEstado(t);
						if (filaDeTarefas.get(p).getTempoExecutado() == filaDeTarefas.get(p).getDuracao()) {
							filaDeTarefas.get(p).setEstado(Estado.CONCLUIDA);
							break;

						}

					}

					if(filaDeTarefas.get(p).getEstado() != Estado.CONCLUIDA){
						filaDeTarefas.get(p).setEstado(Estado.PRONTA);
					}

				}
				// p = p % filaDeTarefas.size();
			}
			if (p < (filaDeTarefas.size() - 1)) {
				p++;
			} else {
				p = 0;
			}

		}

	}

	public void printCabecalho(int numTarefas) {
		OutputStream os;

		try {
			os = new FileOutputStream("output.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write("Tempo   ");
			for (int i = 1; i <= numTarefas; i++) {
				bw.write(("P" + i) + "  ");
			}
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void printTarefas(int timer, List<Tarefa> tarefas) {

		List<String> estados = new ArrayList<String>(Collections.nCopies(tarefas.size(), ""));

		for (Tarefa tarefa : tarefas) {

			if (tarefa.getEstado() == Estado.EXECUTANDO)
				estados.add(tarefa.getId(), " ## ");

			if (tarefa.getEstado() == Estado.PRONTA)
				estados.add(tarefa.getId(), " -- ");

			if (tarefa.getEstado() == Estado.CONCLUIDA)
				estados.add(tarefa.getId(), "    ");

			if (tarefa.getEstado() == Estado.NOVO)
				estados.add(tarefa.getId(), "    ");

			estados.remove((tarefa.getId() + 1));

		}

		escreverNaSaida(estados, timer);

		System.out.println("");
	}

	public int tempoTotal(List<Tarefa> tarefas) {

		for (Tarefa tarefa : tarefas) {
			tempoTotal += tarefa.getDuracao();
		}
		return tempoTotal;

	}

	/*************************************************************************************************/
	public void escreverNaSaida(List<String> str, int timer) {

		OutputStream os;

		String texto = null;

		if (timer < 10) {
			texto = " " + (timer - 1) + "-" + timer + "   ";
		} else if (timer == 10) {
			texto = " " + (timer - 1) + "-" + timer + "  ";
		} else {
			texto = " " + (timer - 1) + "-" + timer + " ";
		}

		try {
			os = new FileOutputStream("output.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			for (String string : str) {
				texto += string;
			}
			bw.write(texto);
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void alteraEstado(int t){
		
		for (Tarefa tarefa : filaDeTarefas) {
			if ((tarefa.getTempChegada() == (t - 1) || tarefa.getTempChegada() == t) && tarefa.getEstado() != Estado.EXECUTANDO) {
				tarefa.setEstado(Estado.PRONTA);
			}
		}
	}
	
	public static List<Tarefa> lerDoArquivo(String arquivo) {

		int i;
		int j = 0;
		String[] novaString = new String[3];
		String linha = null;
		List<Tarefa> listaDeTarefas = new ArrayList<Tarefa>();

		FileInputStream entrada;
		try {
			entrada = new FileInputStream(arquivo);
			InputStreamReader entradaFormatada = new InputStreamReader(entrada);
			BufferedReader br = new BufferedReader(entradaFormatada);

			linha = br.readLine();

			while (linha != null) {
				novaString = linha.split(" ");

				Tarefa tarefa = new Tarefa();
				tarefa.setId(j);
				tarefa.setTempChegada(Integer.parseInt(novaString[0]));
				tarefa.setDuracao(Integer.parseInt(novaString[1]));
				tarefa.setPrioridade(Integer.parseInt(novaString[2]));
				listaDeTarefas.add(tarefa);
				linha = br.readLine();
				j++;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Tarefa tarefa : listaDeTarefas) {
			System.out.println(tarefa.getId());
			System.out.println(tarefa.getTempChegada());
			System.out.println(tarefa.getDuracao());
			System.out.println(tarefa.getPrioridade());
		}

		return listaDeTarefas;

	}

}
