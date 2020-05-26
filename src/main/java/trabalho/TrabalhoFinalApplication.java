package trabalho;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrabalhoFinalApplication implements CommandLineRunner{
	
	private static final List<String> cpfList = Collections.synchronizedList(new ArrayList<>());
	private static final List<String> cnpjList = Collections.synchronizedList(new ArrayList<>());
	
	@Override
	public void run(String... args) throws Exception {
		
		// leitura do arquivo
		ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("BASEPROJETO.txt").getFile());
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		// abertura de arquivo para gravação
		FileWriter fw = new FileWriter("RESULTADO.txt");
		PrintWriter pw = new PrintWriter(fw);
		
		// vars
		int count = 0;
		
		// varredura do arquivo
		while(count < 1200000) {
			 String linha = buffReader.readLine();
			 if(linha.charAt(0) >= '0' && linha.charAt(0) <= '9') {
				 cnpjList.add(linha);
			 }else {
				 String newStr = linha.replace(" ", "");
				 cpfList.add(newStr);
			 }
			 count++;
		}
		buffReader.close();
		
		// instâncias
		CalculaCpf calculaCpf = new CalculaCpf();
		CalculaCnpj calculaCnpj = new CalculaCnpj();
		
		// início contador de tempo
		long start = System.currentTimeMillis();
		
		// thread 1 processa 1/4 das listas de cpf e cnpj
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<150000; i++) {
					String cpfIn = cpfList.get(i);
					String cnpjIn = cnpjList.get(i);
					String cpfOut;
					String cnpjOut;
					cpfOut = calculaCpf.calculaCpf(cpfIn);
					cnpjOut = calculaCnpj.calculaCnpj(cnpjIn);
					cpfList.set(i, cpfOut);
					cnpjList.set(i, cnpjOut);
				}
			}
		});
		
		// thread 2 processa 1/4 das listas de cpf e cnpj
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {	
				for(int i=150000; i<300000; i++) {
					String cpfIn = cpfList.get(i);
					String cnpjIn = cnpjList.get(i);
					String cpfOut;
					String cnpjOut;
					cpfOut = calculaCpf.calculaCpf(cpfIn);
					cnpjOut = calculaCnpj.calculaCnpj(cnpjIn);
					cpfList.set(i, cpfOut);
					cnpjList.set(i, cnpjOut);
				}
			}
		});
		
		// thread 3 processa 1/4 das listas de cpf e cnpj
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=300000; i<450000; i++) {
					String cpfIn = cpfList.get(i);
					String cnpjIn = cnpjList.get(i);
					String cpfOut;
					String cnpjOut;
					cpfOut = calculaCpf.calculaCpf(cpfIn);
					cnpjOut = calculaCnpj.calculaCnpj(cnpjIn);
					cpfList.set(i, cpfOut);
					cnpjList.set(i, cnpjOut);
				}
			}
		});	
		
		// thread 4 processa 1/4 das listas de cpf e cnpj
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {	
				for(int i=450000; i<600000; i++) {
					String cpfIn = cpfList.get(i);
					String cnpjIn = cnpjList.get(i);
					String cpfOut;
					String cnpjOut;
					cpfOut = calculaCpf.calculaCpf(cpfIn);
					cnpjOut = calculaCnpj.calculaCnpj(cnpjIn);
					cpfList.set(i, cpfOut);
					cnpjList.set(i, cnpjOut);
				}
			}
		});
		
		// start das threads
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		
		// contador final de tempo
		long finalT = System.currentTimeMillis();
		System.out.printf("Tempo: %.2f",(finalT - start)/1000d);
		
		pw.flush();
		
		// gravação das listas no arquivo
		for(int i = 0; i < cpfList.size(); i++) {
			pw.println(cpfList.get(i));
		}
		for(int i = 0; i < cnpjList.size(); i++) {
			pw.println(cnpjList.get(i));
		}
		pw.close();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TrabalhoFinalApplication.class, args);
	}
}
