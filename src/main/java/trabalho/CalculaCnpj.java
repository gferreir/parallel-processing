package trabalho;

import org.springframework.stereotype.Component;

@Component
public class CalculaCnpj {
	
	// Método para cálculo dos dígitos verificadores do CNPJ
	public synchronized String calculaCnpj(String cnpj) {

		return cnpj;
	}
}
