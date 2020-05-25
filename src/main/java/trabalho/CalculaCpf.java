package trabalho;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class CalculaCpf {
	
	Logger LOGGER = Logger.getLogger(CalculaCpf.class.getName());
	static int cpfIncompleto[] = new int[11];
	int soma1, soma2, div1, div2;
	String cpf;
	
	// Método para cálculo dos dígitos verificadores do CPF
	public synchronized String calculaCpf(String cpfIncompleto) {
		System.out.println(cpfIncompleto.length());
		if(cpfIncompleto.length() == 9) {
			transferCpf(cpfIncompleto);
			soma1 = digito1();
			div1 = dividendo(soma1);
			this.cpfIncompleto[10] = div1;
			soma2 = digito2();
			div2 = dividendo(soma2);
			cpf = formataCpf(cpfIncompleto);
			System.out.println(cpfIncompleto);
			return cpf;
		}else {
			LOGGER.severe("CPF informado não está padrão esperado");
			return "ERRO";
		}
	}
	
	// Método auxiliar para somatório do primeiro dígito
	int digito1() {
		int somatorio = 0;
		int[] multiplicadores = {10, 9, 8, 7, 6, 5, 4, 3, 2};
		
		for(int i=0; i<multiplicadores.length; i++) {
			somatorio += (cpfIncompleto[i] * multiplicadores[i]);
		}		
		return somatorio;
	}
	
	// Método auxiliar para somatório do segundo dígito
	int digito2() {
		int somatorio = 0;
		int[] multiplicadores = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
		
		for(int i=0; i<multiplicadores.length; i++) {
			somatorio += (cpfIncompleto[i] * multiplicadores[i]);
		}
		return somatorio;
	}
	
	// Método auxiliar para dividendo
	int dividendo(int soma) {
		int resultado, resto;
		
		resto = soma % 11;
		resultado = 11 - resto;
		
		if(resultado >= 10) {
			return 0;
		}
		return resultado;
	}
	
	// Método auxiliar para leitura
	void transferCpf(String cpf) {
		for(short i=0; i<cpf.length(); i++) {
			System.out.println(cpf);
			cpfIncompleto[i] = Character.digit(cpf.charAt(i), 10);
		}
	}
	
	// Método auxiliar para formatar o cpf
	String formataCpf(String numero){
		String cpf;
		cpf = numero + div1 + div2;
		return cpf;
	}
}