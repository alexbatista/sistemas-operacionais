package so.engcomputacao.ufal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SistemaOperacional {


	private List<Tarefa> filaDeTarefas = new ArrayList<Tarefa>();
	private List<Tarefa> filaDeProntas = new ArrayList<Tarefa>();

	public SistemaOperacional(List<Tarefa> tarefas) {
		this.filaDeTarefas = tarefas;
	}

	public SistemaOperacional() {

	}

	public void escalonarTarefasFCFS() {
		/**
		 * Ordena a fila das tarefas de acordo com o tempo de chegada, caso for
		 * igual, ele compara a prioridade
		 */
		Collections.sort(filaDeTarefas);

		/*Função que escreve o cabeçalho no arquivo*/
		printCabecalho(filaDeTarefas.size());

		/*j representa o índice da tarefa que está sendo executada*/
		int j = 0;
		
		/*Representa a quantidade de tarefas com estado CONCLUIDA*/
		int tarefasConcluidas = 0;
		
		/*timer representa o tempo*/
		int timer = 0;

		/*Enquanto houver tarefas não concluídas, faça:*/
		while (tarefasConcluidas < filaDeTarefas.size()) {

			/*Verifico se no tempo atual, chegou alguma tarefa para ser executada, se sim, jogo na fila de tarefas prontas*/
			for (Tarefa tarefa : filaDeTarefas) {
				if (tarefa.getTempChegada() == timer) {
					tarefa.setEstado(Estado.PRONTA);

				}
			}
			
			if (filaDeTarefas.get(j).getTempChegada() <= timer) {
				filaDeTarefas.get(j).setEstado(Estado.EXECUTANDO);
				filaDeTarefas.get(j).setTempoExecutado(1);
			}
			timer += 1;
			printTarefas(timer, filaDeTarefas);

			/*Verifico se o tempo de duração da tarefa já foi concluído, se sim, seto o estado para CONCLUIDA*/
			if (filaDeTarefas.get(j).getTempoExecutado() >= filaDeTarefas
					.get(j).getDuracao()) {
				filaDeTarefas.get(j).setEstado(Estado.CONCLUIDA);
				j++;
				tarefasConcluidas++;
			}

		}

	}


	public void roundRobin() {
		
		/*representa o tempo*/
		int timer = 0;
		
		/*Quantum de 2s para cada tarefa*/
		int quantum = 0;
		
		/*Representa a quantidade de tarefas com estado CONCLUIDA*/
		int tarefasTerminadas = 0;

		/*Escreve o cabeçalho no arquivo*/
		printCabecalho(filaDeTarefas.size());

		
		/*
		 * Ordena a fila das tarefas de acordo com o tempo de chegada, caso for
		 * igual, ele compara a prioridade
		 */
		Collections.sort(filaDeTarefas);

		/*Enquanto a quantidade tarefas CONCLUIDAS for menor que a quantidade total de tarefas, faça: */
		while (tarefasTerminadas < filaDeTarefas.size()) {
			
			/*Se alguma tarefa está executando*/
			if (tarefaExecutando() > -1) {
				int index = tarefaExecutando();
				if (filaDeTarefas.get(index).getTempoExecutado() >= filaDeTarefas
						.get(index).getDuracao()) {
					filaDeTarefas.get(index).setEstado(Estado.CONCLUIDA);
					tarefasTerminadas++;
				} else {
					if (quantum == 2) {
						filaDeTarefas.get(index).setEstado(Estado.PRONTA);
						filaDeProntas.add(filaDeTarefas.get(index));
					}
				}

			}

			/*Verifico se alguma nova tarefa chegou, se sim, seto o estado PRONTA nesta tarefa*/
			alterarEstadoParaPronta(timer);

			/*Se não existe tarefa executando e existem tarefas na fila de prontas*/
			if (tarefaExecutando() == -1 && filaDeProntas.size() > 0) {
				quantum = 0;
				int i = filaDeTarefas.indexOf(filaDeProntas.get(0));
				filaDeTarefas.get(i).setEstado(Estado.EXECUTANDO);
				filaDeProntas.remove(0);
			}
			
			/*Se existe tarefa executando, retorno o índice da tarefa e incremento seu tempo de execução*/
			if (tarefaExecutando() > -1)
				filaDeTarefas.get(tarefaExecutando()).setTempoExecutado(1);

			quantum++;
			timer++;
			printTarefas(timer, filaDeTarefas);

		}
	}

	/** Função que retorna o índice na lista da tarefa com estado EXECUTANDO. Caso não haja tarefa executando, retorna -1*/
	public int tarefaExecutando() {
		for (int i = 0; i < filaDeTarefas.size(); i++) {
			if (filaDeTarefas.get(i).getEstado().equals(Estado.EXECUTANDO))
				return i;
		}
		return -1;
	}

	/** Função que retorna o índice na lista da tarefa com estado PRONTA. Caso não haja tarefa pronta, retorna -1 */
	public int tarefaPronta() {
		for (int j = 0; j < filaDeTarefas.size(); j++) {
			if (filaDeTarefas.get(j).getEstado().equals(Estado.PRONTA))
				return j;
		}
		return -1;
	}

	/**
	 * Imprime o cabeçalho do arquivo de saída(input.txt), ordenado por ordem de
	 * chegada.
	 */
	public void printCabecalho(int numTarefas) {
		OutputStream os;
		String cabecalho = "Tempo   ";
		try {
			os = new FileOutputStream("output.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			for (int i = 1; i <= numTarefas; i++) {
				cabecalho += " P" + i + "   ";
			}

			bw.write(cabecalho);
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Imprime a cada segundo o estado das tarefas, ou seja, imprime os
	 * caracteres(conforme definido na especificação do trabalho) indicando se a
	 * tarefa naquele instante está executando, pronta, concluida ou nova
	 */
	public void printTarefas(int timer, List<Tarefa> tarefas) {

		/**
		 * cria e inicializa a lista(com a string "") onde é guardada os
		 * caracteres que serão imprimidos no arquivo de saída.
		 */
		List<String> estados = new ArrayList<String>(Collections.nCopies(
				tarefas.size(), ""));

		for (int i = 0; i < tarefas.size(); i++) {

			if (tarefas.get(i).getEstado() == Estado.EXECUTANDO)
				estados.set(i, "  ##  ");

			if (tarefas.get(i).getEstado() == Estado.PRONTA)
				estados.set(i, "  --  ");

			if (tarefas.get(i).getEstado() == Estado.CONCLUIDA)
				estados.set(i, "      ");

			if (tarefas.get(i).getEstado() == Estado.NOVO)
				estados.set(i, "      ");

		}

		escreverNaSaida(estados, timer);

	}


	/**
	 * Escreve no arquivo de saída os caracteres contidos na lista(str) no
	 * instante "timer".
	 */
	public void escreverNaSaida(List<String> str, int timer) {

		OutputStream os;

		String texto = null;
		/**
		 * verificação feita com a finalidade de deixar o arquivo de saída bem
		 * formatado, ou seja, imprimir o caracter que indica o estado da tarefa
		 * na mesma coluna da tarefa.
		 */
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

	/**
	 * Faz a verificação a cada segundo se ingressou uma nova tarefa, caso
	 * positivo, muda o estado da tarefa para pronta.
	 **/
	public void alterarEstadoParaPronta(int t) {

		for (Tarefa tarefa : filaDeTarefas) {
			if ((tarefa.getTempChegada() == (t - 1) || tarefa.getTempChegada() == t)
					&& tarefa.getEstado() == Estado.NOVO) {
				tarefa.setEstado(Estado.PRONTA);
				filaDeProntas.add(tarefa);
			}
		}

	}

	/**
	 * Le do arquivo de entrada linha por linha, e cria as tarefas de acordo com
	 * os dados desse arquivo e coloca-os dentro de uma lista.
	 * 
	 */

	public static List<Tarefa> lerDoArquivo(String arquivo) {

		int j = 0;
		String[] novaString = new String[3];
		String linha = null;
		List<Tarefa> listaDeTarefas = new ArrayList<Tarefa>();

		FileInputStream entrada;
		try {
			entrada = new FileInputStream(arquivo);
			InputStreamReader entradaFormatada = new InputStreamReader(entrada);
			BufferedReader br = new BufferedReader(entradaFormatada);
			/**
			 * Lendo arquivo linha por linha, e usa os dados para criar as
			 * tarefas.
			 */
			linha = br.readLine();
			while (linha != null) {
				novaString = linha.split(" ");

				Tarefa tarefa = new Tarefa();
				tarefa.setId(j);
				tarefa.setTempChegada(Integer.parseInt(novaString[0]));
				tarefa.setDuracao(Integer.parseInt(novaString[1]));
				tarefa.setPrioridade(Integer.parseInt(novaString[2]));
				tarefa.setEstado(Estado.NOVO);
				listaDeTarefas.add(tarefa);
				linha = br.readLine();
				j++;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaDeTarefas;

	}

}
