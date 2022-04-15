package reflexao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Reflexao {

	public static void refletir(Object objeto, Class<?> classe) throws IllegalArgumentException, IllegalAccessException {
		
		System.out.println("\n----------REFLEXAO----------");
		System.out.println("\n----------Classe----------");
		
		// mostra o nome, o nome simples e o nome do tipo da classe
		System.out.println("Nome da classe: " + classe.getName());
		System.out.println("Nome simples da classe: " + classe.getSimpleName());
		System.out.println("Nome do tipo da classe: " + classe.getTypeName());
		
		System.out.println("\n----------Atributos----------");
		
		// lista dos atributos declarados na classe e for para percorre-la
		Field[] atributos = classe.getDeclaredFields();
		for(Field F: atributos) {
			
			// torna os atributos acessiveis
			F.setAccessible(true);		

			// seta os valores para 0 e null, dependendo do tipo em questao
			/*if (F.getType().isPrimitive())
				F.set(objeto, 0);
			else
				F.set(objeto, null);*/
						
			// mostra os nomes dos atributos e seus valores
			System.out.println(F.getName() + ": " + F.get(objeto));
		}
		
		System.out.println("\n----------Metodos----------");
		
		// lista dos metodos declarados na classe e for para percorre-la
		Method[] metodos = classe.getDeclaredMethods();
		for(Method M: metodos) {
			// mostra os nomes dos metodos, os tipos dos seus parametros e dos seus retornos
			System.out.println("* " + M.getName() + "\nparametros: " + Arrays.toString(M.getParameterTypes()) +
					" retorno: " + M.getReturnType().getSimpleName());
		}
	}
}
