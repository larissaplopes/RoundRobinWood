import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      LinkedList<Processo> listaRR = new LinkedList<Processo>();
	      
	      int nProcessos = 0;
	      
	      try {
	         // TODO code application logic here
	         BufferedReader leitura = new BufferedReader(new FileReader("C:/Programa/Fernando/processos1.txt"));
	         
	         String linha = leitura.readLine();
	         String[] str = linha.split(" ");
	         
	         nProcessos++;
	         listaRR.add(new Processo(Integer.parseInt(str[0]), Integer.parseInt(str[1]), nProcessos));
	         
	         while(leitura.ready()){
	            linha = leitura.readLine();
	            str = linha.split(" ");
	            nProcessos++;
	            listaRR.add(new Processo(Integer.parseInt(str[0]), Integer.parseInt(str[1]), nProcessos));
	            
	         }
	         leitura.close();
	         RRobin(listaRR, nProcessos);
	      } catch (FileNotFoundException ex) {
	         JOptionPane.showMessageDialog(null, "Erro na abertura do arquivo");
	         System.exit(1);
	      } catch (IOException ex) {
	         JOptionPane.showMessageDialog(null, "Problema na leitura do arquivo");
	         System.exit(1);
	      }

	}

	   public static void RRobin(List<Processo> listaFifo, int n){
		      LinkedList<Processo> filaPronto = new LinkedList<>();
		      LinkedList<Processo> filaEncerrado = new LinkedList<>();
		      LinkedList<Processo> filaTemp = new LinkedList<Processo>();
		      
		      int tempo = 0,
		          contagem = 0,
		          quantum = 1;
		      double mediaRetorno = 0,
		             mediaResposta = 0,
		             mediaEspera = 0;
		      Processo cpu, vazio = new Processo();
		      DecimalFormat df = new DecimalFormat("0.#");    
		      cpu = vazio;
		      
		      for(tempo = 0; ;tempo++){
		          if(contagem == n){
		             break;
		          }
		          /* processos chegam na fila de pronto */
		          for(Processo p : listaFifo){
		             if(p.getEntrada() == tempo){
		                p.setEntradaPronto(tempo);
		                filaPronto.add(p);
		             }else if(p.getEntrada() > tempo){
		                break;
		             }
		          }
		          
		          if(quantum == 1 && !filaPronto.isEmpty()){
		        	  cpu = filaPronto.remove();
			           if(cpu.primeiraVez()){
				               cpu.setEntradaCPU(tempo);
				       }
			           cpu.setEmCPU();
			           quantum++;
		          }else if(quantum == 2){
		        	  cpu.setEmCPU();
		        	  filaTemp.add(cpu);
		        	  cpu = vazio;
		        	  quantum--;
		          }
		          if(!filaPronto.isEmpty()){
		        	  for(Processo p : filaPronto){
		        		  p.setEspera();
		          	  }
		          }
		          if(!filaTemp.isEmpty() && filaTemp.getFirst().nTerminou()){
		        	  filaPronto.addAll(filaTemp);
		        	  filaTemp.remove();
		          }else if(!filaTemp.isEmpty()){
		        	  filaEncerrado.addAll(filaTemp);
		        	  filaTemp.remove();
		        	  contagem++;
		          }
		          
		       }
		      for(Processo p : filaEncerrado){
		     	 System.out.println("Fim ID: " + p.getID());
		          mediaRetorno += p.getRetorno();
		          mediaEspera += p.getEspera();
		          mediaResposta += p.getEntradaCPU();
		       }
		       
		   
		  
	   }//rr		      
		      

}
