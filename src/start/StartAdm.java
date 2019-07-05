package start;

import controller.ClientController;
import controller.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import model.Classificacao;
import model.Equipe;
import model.Piloto;
import model.Pista;
import model.VoltaPiloto;

public class StartAdm {
    private static String serverIp = "localhost";
    private static int serverPort = 5555;
    
   
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        ClientController client = new ClientController(serverIp, serverPort);
        Classificacao clas;
        VoltaPiloto voltaPiloto = null;
        int escolha;  
        while(true){
            System.out.println("Menu");
            System.out.println("");
            System.out.println("1 --- Cadastrar Piloto");
            System.out.println("2 --- Cadastrar Equipe");
            System.out.println("3 --- Cadastrar Circuito"); 
            System.out.println("4 --- Iniciar Classificação"); 
            System.out.println("5 --- Encerrar Classificação"); 
            System.out.println("6 --- Iniciar Corrida"); 
            escolha = Console.readInt();
            
            switch(escolha){
                case 0:
                    System.exit(0);                                     
                break;
                case 1:
                    cadPiloto(client);
                break;
                case 2:
                    cadEquipe(client);
                break;
                case 3:
                    cadPista(client);
                break;
                case 4:
                    startClas(client, voltaPiloto);
                break;
                case 5:

                break;
                case 6:
                    //startRun(client);
                break;
                
                
            }
        }
    }

    //Método para cadastro de piltos. *Para cadastrar um piloto é necessário uma equipe cadastrada antes.
    private static void cadPiloto(ClientController client) throws IOException, ClassNotFoundException {
        System.out.println("Informe o nome do piloto.");
        String nomePiloto = Console.readString();
        System.out.println("Informe o número do piloto");
        String num = Console.readString();  
        
        //Método que espera o sensor mandar uma tag de um carro e vincular com um piloto.
        String codCarro = cadastroCarroSensor(client);       
        System.out.println(codCarro);
                
        //Método que cadastra uma equipe existente ao piloto.
        String equipe = "AVADA KEDAVRA";
        ArrayList listEquipes = client.returnListEquipe();
        while(!client.verificaEquipe(equipe, listEquipes)){
            equipe = escolherEquipe(client);
        } 
        String resp = client.cadPiloto(nomePiloto, num, equipe, codCarro);
        System.out.println(resp);     
    }
    
    //Método auxiliar para o cadastro de piloto. *Método Anterior
    private static String escolherEquipe(ClientController client) throws IOException, ClassNotFoundException {
        ArrayList listEquipes = client.returnListEquipe();
        System.out.println("Equipes Cadastradas.");
        for(Object i: listEquipes){
            if(i instanceof Equipe){
                System.out.print("Equipe :");
                System.out.println(((Equipe) i).getNome());
            }     
        }
        System.out.println("Informe sua equipe.");
        String equipe = Console.readString();
        
        if(client.verificaEquipe(equipe, listEquipes) == false){
            System.out.println("Nome Incorreto / Equipe Não Cadastrada");
            return "AVADA KEDAVRA";
        }else{          
            return equipe;
        }
    }

    //Método para cadastrar uma equipe.
    private static void cadEquipe(ClientController client) throws IOException {
        String nomeEquipe;
        System.out.println("Informe o nome da equipe.");
        nomeEquipe = Console.readString();
        String resp = client.cadEquipe(nomeEquipe);
        System.out.println(resp);
    }

    //Método para cadastrar uma pista.
    private static void cadPista(ClientController client) throws IOException, ClassNotFoundException {
        String nomePista;
        System.out.println("Informe o nome da Pista.");
        nomePista = Console.readString();
        ArrayList listPistas = client.returnListPistas();
        if(!client.verificaPista(nomePista, listPistas) == false){
            System.out.println("Pista Já Cadastrada.");
            cadPista(client);
        }
        String resp = client.cadPista(nomePista);
        System.out.println(resp);
    }
    
    
    //***************************************************************BOTAR ESSES METODOS PARA BAIXO DA CLASSIFICACAO
    //Esse método auxilia na classificação ajudando a escolher a pista.
    private static String escolherPista(ClientController client) throws IOException, ClassNotFoundException{
        ArrayList listPistas = client.returnListPistas();
        System.out.println("Pistas Cadastradas.");
        for(Object i: listPistas){
            System.out.print("Pista :");
            System.out.println(((Pista) i).getNome());
        } 
        System.out.println("Informe sua pista.");
        String pista = Console.readString();
        
        if(client.verificaPista(pista, listPistas) == false){
            System.out.println("Nome Incorreto / Pista Não Cadastrada");
            return "CRUCIATUS";
        }else{          
            return pista;
        }
    }
    
    //Esse Método auxilia na classificação ajudando a escolher os pilotos que disputarão as corridas.
    private static String escolherPiloto(ClientController client) throws IOException, ClassNotFoundException{
        ArrayList listPiloto = client.returnPiloto();
        System.out.println("Pilotos Cadastradas.");
        for(Object i: listPiloto){
            System.out.print("Piloto :");
            System.out.println(((Piloto) i).getNome());
        } 
        System.out.println("Informe o Nome do Piloto.");
        String piloto = Console.readString();
        
        if(client.verificaPiloto(piloto, listPiloto) == false){
            System.out.println("Nome Incorreto / Piloto Não Cadastrada");
            return "IMPERIUS";
        }else{          
            return piloto;
        }
    }

    //O método de classificação pega os dados como a pista, os pilotos e o tempo em que cada piloto terá
    //para decidir qual a sua colocação na largada da corrida.
    private static void startClas(ClientController client, VoltaPiloto voltaPiloto) throws IOException, ClassNotFoundException, InterruptedException {
        
        String pista = "CRUCIATUS";
        ArrayList listPistas = client.returnListPistas();
        String piloto1 = "IMPERIUS";
        String piloto2 = "EXPECTO PATRONO";
        String piloto3 = "EXPELIARMUS";
        String piloto4 = "INCENDIO";
        ArrayList listPiloto = client.returnPiloto();

        //Esse while ajuda a escolher uma pista para a classificação.
        while(!client.verificaPista(pista, listPistas)){
            pista = escolherPista(client);
        }
        
        //Esse while ajuda a escolher os pilotos para a classificacao.
        while(!client.verificaPiloto(piloto1, listPiloto)){
            piloto1 = escolherPiloto(client);
        }
        
        while(!client.verificaPiloto(piloto2, listPiloto)){
            piloto2 = escolherPiloto(client);
        }
        
        while(!client.verificaPiloto(piloto3, listPiloto)){
            piloto3 = escolherPiloto(client);
        }
        
        while(!client.verificaPiloto(piloto4, listPiloto)){
            piloto4 = escolherPiloto(client);
        }
        
        System.out.println(pista);
        System.out.println(piloto1);
        System.out.println(piloto2);
        System.out.println(piloto3);
        System.out.println(piloto4);
            
        System.out.println("Informe O Tempo De Classificação.");
        System.out.println("*** Em Segundos ***");
        int tempoClassificacao = Console.readInt();
        long tempo = (1000 * tempoClassificacao);
            
        
        System.out.println("Iniciando Fase de Classificação.");
        //ERRO NESSE MÈTODO AQUI
        String msg = client.dadosClassificacao(pista, piloto1, piloto2, piloto3, piloto4);
        client.enviarTempoClassificacao(tempo);
                
            // Aqui o sistema tem todos os dados p/ ocorre a classificação.
            // Seguinte, ele chama um método para o começo da classificação.
        
        client.resetaClassificacao();
        System.out.println("Prepare-se para a classificação.");
        System.out.println("Prepare Qualquer Carro.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        
        //ESTA AQUI NESSE SCARNER, DAQUI VOU RELER O FOR E REENTENDER A CLASSIFICAÇÂO
        //ESTA AQUI NESSE SCARNER, DAQUI VOU RELER O FOR E REENTENDER A CLASSIFICAÇÂO
        //ESTA AQUI NESSE SCARNER, DAQUI VOU RELER O FOR E REENTENDER A CLASSIFICAÇÂO
        
                
        for(int j = 0; j<4; j++){
            voltaPiloto = null;
            for(int i=0; i<tempo/3 ; i++){
                Thread.currentThread().sleep(3000);
                voltaPiloto = client.returnClas();
                System.out.print("Piloto --> " );
                System.out.println(voltaPiloto.getPiloto().getNome());
                System.out.print("Tempos --> " );
                Iterator it = voltaPiloto.getVoltas().iterator();
                while(it.hasNext()){
                    System.out.print((double) it.next());
                }
            }
            if(j<3){
                client.finalizaClassificacaoPiloto();
                System.out.println("Prepare Mais um Carro.");
                scanner.nextLine(); 
            }
        }
    }
                          
    private static String cadastroCarroSensor(ClientController client) throws IOException, ClassNotFoundException {
        System.out.println("Cadastro do Carro");
        System.out.println("Por Favor, Aproxime o Carro do Sensor.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        return client.returnCodCarro(); 
    }





}

//Fazer com que o endClas faça parar de receber novas mensagens do sensor.
//Fazer com que o startClasscomece uma corrida.

//Como fazer com que os metodos de controle peguem os dados do sensor, e parem de pegar, no momento desejado.
//Ajeitar os dados de melhor tempo.