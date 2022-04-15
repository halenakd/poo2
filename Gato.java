package reflexao;

import java.lang.annotation.Annotation;

public class Gato {

	// -------------Atributos-------------
	private String nome;
	private String raca;
	private double idadeHumana; /* em anos */
	
	// -------------Construtores-------------
	// Construtor
	public Gato(String nome, String raca, double idadeHumana) {
		this.nome = nome;
		this.raca = raca;
		this.idadeHumana = idadeHumana;
	}
	
	// Construtor vazio
	public Gato() {}
	
	// -------------Métodos-------------
	// Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public double getIdadeHumana() {
		return idadeHumana;
	}

	public void setIdadeHumana(double idadeHumana) {
		this.idadeHumana = idadeHumana;
	}
	
	// calcular idade de anos humanos em anos felinos
	@Parametro(nomeParametro = "idadeHumana")
	public double calcIdadeFelina(double idadeHumana) {
		System.out.println("\n----------Conversao de Anos Humanos em Anos Felinos----------");
		double idadeFelina;
		if(idadeHumana <= 1 && idadeHumana > 0) {
		    idadeFelina = idadeHumana * 15;
		}
		else if(idadeHumana > 1 && idadeHumana <= 2) {
			idadeFelina = 1 * 15 + (idadeHumana - 1) * 9;
		}
		else {
		    idadeFelina = 1 * 15 + 1 * 9 + (idadeHumana - 2) * 4;
		}
		System.out.println("Idade felina: " + idadeFelina);
		return idadeFelina;
	}
	
	// imprimir atributos
	public void imprimir() {
		System.out.println("\n----------Imprimir da Classe Gato----------");
		System.out.println( "Nome do gato: " + nome + "\n" +
				"Raça: " + raca + "\n" +
				"Idade humana: " + idadeHumana + " anos\n");
		//System.out.println("Idade felina: " + calcIdadeFelina(idadeHumana) + " anos\n");
	}
}
