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

public class SistemaOperacional {

	/**
	 * Tempo total de execução de todas as tarefas
	 */
	private int tempoTotal = 0;

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

	}

	public void rr() {

		int t = 0;
		int p = 0;
		int tarefasTerminadas = 0;
		tempoTotal = tempoTotal(filaDeTarefas);

		printCabecalho(filaDeTarefas.size());

		Collections.sort(filaDeTarefas);

		while (tarefasTerminadas < filaDeTarefas.size()) {

			alteraEstado(t);

			if (filaDeTarefas.get(p).getEstado() == Estado.PRONTA) {

				/** O quantum de 2 segundos é simulado por esse for */
				for (int i = 0; i < 2; i++) {
					filaDeTarefas.get(p).setEstado(Estado.EXECUTANDO);
					filaDeTarefas.get(p).setTempoExecutado(1);
					t = t + 1;
					printTarefas(t, filaDeTarefas);
					alteraEstado(t);
					/**
					 * Verifica se a tarefa terminou a execução, caso positivo
					 * muda o estado dela para concluido
					 */
					if (filaDeTarefas.get(p).getTempoExecutado() >= filaDeTarefas.get(p).getDuracao()) {
						filaDeTarefas.get(p).setEstado(Estado.CONCLUIDA);
						tarefasTerminadas++;
						break;
					} else {
						filaDeTarefas.get(p).setEstado(Estado.PRONTA);
					}

				}

				/**
				 * P é o valor usado para percorrer a lista que contem as
				 * tarefas, e esta verificação é feita para evitar o acesso a um
				 * indice maior que o da lista
				 */
				if (p < (filaDeTarefas.size() - 1)) {
					p++;
				} else {
					p = 0;
				}

			} else {
				t = t + 1;
				printTarefas(t, filaDeTarefas);
			}
		}

	}

	public void rr2() {

		int t = 0;
		int p = 0;
		int tarefasTerminadas = 0;
		tempoTotal = tempoTotal(filaDeTarefas);

		printCabecalho(filaDeTarefas.size());

		Collections.sort(filaDeTarefas);

		while (tarefasTerminadas < filaDeTarefas.size()) {

			alteraEstado(t);
			
			if(!filaDeProntas.isEmpty()){
				
				if(filaDeProntas.get(p).getEstado().equals(Estado.PRONTA)){

				for (int i = 0; i < 2; i++) {
					filaDeProntas.get(p).setEstado(Estado.EXECUTANDO);
					filaDeProntas.get(p).setTempoExecutado(1);
					t = t + 1;
					printTarefas(t, filaDeProntas);
					alteraEstado(t);
	
					if (filaDeProntas.get(p).getTempoExecutado() >= filaDeProntas.get(p).getDuracao()) {
						filaDeProntas.get(p).setEstado(Estado.CONCLUIDA);
						filaDeProntas.remove(p);
						tarefasTerminadas++;
						break;
					} else {
						filaDeTarefas.get(p).setEstado(Estado.PRONTA);
						filaDeProntas.add(filaDeProntas.get(p));
						filaDeProntas.remove(p);
					}

				}
				}
			}

			} else {
				t = t + 1;
				printTarefas(t, filaDeTarefas);
			}
		}

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
				cabecalho += "P" + i + "  ";
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
		List<String> estados = new ArrayList<String>(Collections.nCopies(tarefas.size(), ""));

		for (int i = 0; i < tarefas.size(); i++) {

			if (tarefas.get(i).getEstado() == Estado.EXECUTANDO)
				estados.set(i, " ## ");

			if (tarefas.get(i).getEstado() == Estado.PRONTA)
				estados.set(i, " -- ");

			if (tarefas.get(i).getEstado() == Estado.CONCLUIDA)
				estados.set(i, "    ");

			if (tarefas.get(i).getEstado() == Estado.NOVO)
				estados.set(i, "    ");

		}

		escreverNaSaida(estados, timer);

	}

	/**
	 * Calcula e retorna a soma do tempo de execução das tarefas.
	 */
	public int tempoTotal(List<Tarefa> tarefas) {

		for (Tarefa tarefa : tarefas) {
			tempoTotal += tarefa.getDuracao();
		}
		return tempoTotal;

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
	public void alteraEstado(int t) {

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
