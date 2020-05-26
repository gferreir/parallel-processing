package trabalho;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class CalculaCnpj {
	
	Logger LOGGER = Logger.getLogger(CalculaCpf.class.getName());
	static int cnpjIncompleto[] = new int[14];
	int soma1, soma2, div1, div2;
	String cnpj;
	
	// Método para cálculo dos dígitos verificadores do CNPJ
	public synchronized String calculaCnpj(String cnpjIncompleto) {
		if(cnpjIncompleto.length() == 12) {
			transferCnpj(cnpjIncompleto);
			soma1 = digito1();
			div1 = dividendo(soma1);
			soma2 = digito2(div1);
			div2 = dividendo(soma2);
			cnpj = formataCpf(cnpjIncompleto);
			return cnpj;
		}else {
			LOGGER.severe("CNPJ informado não está no padrão esperado");
			return "ERRO";
		}
	}
	
	// Método auxiliar para somatório do primeiro dígito
		int digito1() {
			int somatorio = 0;
			int[] multiplicadores = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
			
			for(int i=0; i<multiplicadores.length; i++) {
				somatorio += (cnpjIncompleto[i] * multiplicadores[i]);
			}
			return somatorio;
		}
		
		// Método auxiliar para somatório do segundo dígito
		int digito2(int dig1) {
			int somatorio = 0;
			int[] multiplicadores = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
			cnpjIncompleto[12] = dig1;
			for(int i=0; i<multiplicadores.length; i++) {
				somatorio += (cnpjIncompleto[i] * multiplicadores[i]);
			}
			return somatorio;
		}
		
		// Método auxiliar para dividendo
		int dividendo(int soma) {
			int resultado, resto;
			
			resto = soma % 11;
			resultado = 11 - resto;
			
			if(resto < 2) {
				return 0;
			}
			return resultado;
		}
		
		// Método auxiliar para leitura
		void transferCnpj(String cpf) {
			for(short i=0; i<cpf.length(); i++) {
				cnpjIncompleto[i] = Character.digit(cpf.charAt(i), 10);
			}
		}
		
		// Método auxiliar para formatar o cnpj
		String formataCpf(String numero){
			String cnpj;
			cnpj = numero + div1 + div2;
			return cnpj;
		}
}
