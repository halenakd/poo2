package reflexao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Array; 
import java.util.HashMap;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class GatoReflexao {

		public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException, NoSuchFieldException {
			
			// criando e instanciando objeto da classe Gato COM reflexao
			
			// pedindo o package e o nome da classe
			// no caso seria reflexao.Gato
			System.out.println("Insira o package e o nome da classe (package.Class): ");
			Scanner lerNameClass = new Scanner(System.in);
			String nameClass = lerNameClass.next();
			
			// criando o objeto
			Class<?> classe = Class.forName(nameClass);
			// instanciando o objeto
			Object objeto = classe.getConstructor().newInstance();
			
			
			// abrindo arquivo com dados para preencher atributos
			String path = "/home/halena/eclipse-workspace/POO2av1/src/reflexao/Dados";
			try(BufferedReader br = new BufferedReader(new FileReader(path))) {
				
				// cria uma lista string com as linhas do arquivo
				String line = br.readLine();
				List<String> lines = new ArrayList<String>();
				while(line != null) {
					lines.add(line);
					line = br.readLine();
				}

				// separa a linha, deixando cada atributo com seu valor
				String[] atribEValJunt = lines.get(0).split(",");
				
				// percorre os atributos da classe
				for(Field F: classe.getDeclaredFields()) {
					
					// torna os atributos acessiveis
					F.setAccessible(true);
						
					// percorre as partes da linha
					for(int i = 0; i < atribEValJunt.length; i++) {
							
						// separa o atributo(0) de seu valor(1)
						String[] atribEValSep = atribEValJunt[i].split("=");
							
						// se o nome do atributo da classe for igual ao nome do atributo encontrado na linha
						if(F.getName().intern() == atribEValSep[0].intern()) {
								
							// se o tipo do atributo for primitivo
							if(F.getType().isPrimitive()) {
									
								// converte o tipo string do valor encontrado na linha para double
								double convertido = Double.parseDouble(atribEValSep[1]);
								
								// seta o valor do atributo como o valor encontrado na linha
								F.set(objeto,  convertido);
							}
								
							// se o tipo do atributo não for primitivo
							else {
									
								// seta o valor do atributo como o valor encontrado na linha
								F.set(objeto,  atribEValSep[1]);
							}
						}	
					}
				}
			}
			
			// tratamento de erros
			catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			System.out.println("\n----------Teste dos Metodos----------");
			// pega os metodos declarados na classe
			for (Method m: classe.getDeclaredMethods()) {
				// se o metodo não for um get ou um set
				if (!isGetterOrSetter(m)) {
					// nome do metodo
					String propriedade = m.getName();
					// se houver uma anotacao "parametro" no metodo, ele tem parametros
					if(m.isAnnotationPresent(Parametro.class))
					{
						// pega a anotacao e o valor dentro da anotacao (contem os parametros do metodo)
						Parametro anotacaoAtributo = m.getAnnotation(Parametro.class);
						String atributo = anotacaoAtributo.nomeParametro();
						
						// percorre os atributos declarados na classe
						for(Field F: classe.getDeclaredFields()) {
							/* se o nome de um atributo da classe for igual 
							/ ao nome do atributo encontrado dentro da anotacao (parametros do metodo)*/
							if(F.getName().intern() == atributo.intern()) {
								Class[] parametros = new Class[1];
								// testa o metodo de acordo com o tipo do parametro
								F.setAccessible(true);
								if(F.getType() == double.class) {
									parametros[0] = double.class;
								}
								else if(F.getType() == String.class){
									parametros[0] = String.class;
								}
								else if(F.getType() == Integer.class){
									parametros[0] = Integer.class;
								}
								m = objeto.getClass().getDeclaredMethod(propriedade, parametros);
								m.invoke(objeto, F.get(objeto));
							}
						}
					}
					// se nao houver anotacao ele nao tem parametros
					else {
						// testa o metodo
						m = objeto.getClass().getDeclaredMethod(propriedade);
						m.invoke(objeto);
					}
				}
			}
			
			// imprime informacoes sobre a classe, atributos, metodos e pode trocar valores de atributos
			Reflexao.refletir(objeto, objeto.getClass());
		}
		
		// função para checar se um método é um get
		private static boolean isGetterOrSetter(Method m) {
			return (m.getName().startsWith("get") &&
					m.getReturnType() != void.class &&
					m.getParameterTypes().length == 0) || 
					(m.getName().startsWith("set") && 
					m.getParameterTypes().length == 1);
		}
		
}
