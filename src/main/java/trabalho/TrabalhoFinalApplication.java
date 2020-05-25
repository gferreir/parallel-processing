package trabalho;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
		// vars
		int count = 0;
		
		// varredura do arquivo
		while(count < 1200000) {
			 String linha = buffReader.readLine();
			 if(linha.charAt(0) >= '0' && linha.charAt(0) <= '9') {
				 cnpjList.add(linha+"\n");
			 }else {
				 String newStr = linha.replace(" ", "");
				 cpfList.add(newStr);
			 }
			 count++;
		}
		buffReader.close();
		CalculaCpf calculaCpf = new CalculaCpf();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<150000; i++) {
					String cpfIn = cpfList.get(i);
					String cpfOut;
					cpfOut = calculaCpf.calculaCpf(cpfIn);
					cpfList.set(i, cpfOut);
				}
			}
		});	
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {	
				for(int i=150000; i<300000; i++) {
					String cpfIn = cpfList.get(i);
					String cpfOut;
					cpfOut = calculaCpf.calculaCpf(cpfIn);
					cpfList.set(i, cpfOut);
				}
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=300000; i<450000; i++) {
					String cpfIn = cpfList.get(i);
					String cpfOut;
					cpfOut = calculaCpf.calculaCpf(cpfIn);
					cpfList.set(i, cpfOut);
				}
			}
		});	
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {	
				for(int i=450000; i<600000; i++) {
					String cpfIn = cpfList.get(i);
					String cpfOut;
					cpfOut = calculaCpf.calculaCpf(cpfIn);
					cpfList.set(i, cpfOut);
				}
			}
		});
		t1.start();
		//t2.start();
		//t3.start();
		//t4.start();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TrabalhoFinalApplication.class, args);
	}
}
