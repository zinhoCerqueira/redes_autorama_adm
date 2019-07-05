package controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.Request;
import model.Carro;
import model.Classificacao;
import model.Equipe;
import model.Piloto;
import model.Pista;
import model.VoltaPiloto;


public class ClientController {
    private String serverIp;
    private int serverPort;
    
    public ClientController(String serverIp, int serverPort){
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }
    
    // Cadastros essenciais -------------------------------------------------------------------------------------- //
    // ----------------------------------------------------------------------------------------------------------- //
    
    public String dadosClassificacao(String pista, String p1, String p2, String p3, String p4) throws IOException {
        ArrayList dadosCorrida = new ArrayList();
        dadosCorrida.add(pista);
        dadosCorrida.add(p1);
        dadosCorrida.add(p2);
        dadosCorrida.add(p3);
        dadosCorrida.add(p4);
        return this.enviarRequisicao(new Request("dadosCorrida", dadosCorrida));
    }
    
    //Métodos de Cadastro de Equipe
    public String cadEquipe(String nome) throws IOException{
        Equipe x = new Equipe(nome);
        return this.enviarRequisicao(new Request("equipe", x));        
    }
    
    //Método que cadastra pilotos.
    public String cadPiloto(String nomePiloto, String num, String nomeEquipe, String codCarro) throws IOException {
        Equipe equipe = new Equipe(nomeEquipe);
        Piloto x = new Piloto(nomePiloto, equipe, num);
        Carro carro = new Carro(codCarro);
        x.setCarro(carro);
        return this.enviarRequisicao(new Request("piloto", x));
    }
    
    //Método que cadastra pistas no servidor.
    public String cadPista(String nomePista) throws IOException {
        Pista pista = new Pista(nomePista);
        return this.enviarRequisicao(new Request("pista", pista));
    }

    // Métodos que retornam listas dos dados cadastrados no servidor --------------------------------------------- //
    // ----------------------------------------------------------------------------------------------------------- //
    
    //Método que retorna a lista de equipes já cadastradas no servidor.
    public ArrayList returnListEquipe() throws IOException, ClassNotFoundException {
        Request request = new Request("listaEquipe");
        
        Socket socket = new Socket(serverIp, serverPort);
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.write(this.serializeRequest(request));
            
        ObjectInputStream in= new ObjectInputStream(socket.getInputStream());
        List<Equipe> lista=(ArrayList<Equipe>) in.readObject();
        socket.close();
        
        return (ArrayList) lista;        
    }
    
    //Método que retorna a lista de pistas já cadastradas no servidor.
    public ArrayList returnListPistas() throws IOException, ClassNotFoundException {
        Request request = new Request("listaPista");
        Socket socket = new Socket(serverIp, serverPort);
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.write(this.serializeRequest(request));
            
        ObjectInputStream in= new ObjectInputStream(socket.getInputStream());
        List<Pista> lista=(ArrayList<Pista>) in.readObject();
        socket.close();
        
        return (ArrayList) lista;
        
    }
    
    public ArrayList returnPiloto() throws IOException, ClassNotFoundException {
        Request request = new Request("listaPiloto");
        Socket socket = new Socket(serverIp, serverPort);
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.write(this.serializeRequest(request));
            
        ObjectInputStream in= new ObjectInputStream(socket.getInputStream());
        List<Piloto> lista=(ArrayList<Piloto>) in.readObject();
        socket.close();
        
        return (ArrayList) lista;
    }
    
    //Métodos que verificam se um obj se encontra em uma respectiva lista.------------------------------//
    //---------------------------------------------------------------------------------------------------//
    
    //Método que verifica se uma equipe ja está cadastrada ou não.
    public boolean verificaEquipe(String equipe, ArrayList listEquipes) {
        for(Object obj : listEquipes){
            Equipe x = (Equipe) obj;
            if(equipe.equals(x.getNome())){
                return true;
            }
        }
        return false;
    }
    
    public boolean verificaPista(String nomePista, ArrayList listPistas) {
        for(Object obj: listPistas){
            Pista x = (Pista) obj;
            if(nomePista.equals(x.getNome())){
                return true;
            }
        }
        return false;
    }
    
    public boolean verificaPiloto(String nomePiloto, ArrayList listPilotos){
        for(Object obj: listPilotos){
            Piloto x = (Piloto) obj;
            if(nomePiloto.equals(x.getNome())){
                return true;
            }
        }
        return false;
    }
    
    //Outros métodos -------------------------------------------------------------------------------//
    //----------------------------------------------------------------------------------------------//
    
    
    //Método que verifica se todos os pilotos estão presentes para serem cadastrados em uma corrida.
    public boolean testeNomePiloto(String p1, String p2, String p3, String p4) throws IOException, ClassNotFoundException {
        ArrayList pilotos = returnPiloto();
        if(verificaPiloto(p1, pilotos) || verificaPiloto(p2, pilotos)){
            if(verificaPiloto(p3, pilotos) || verificaPiloto(p2, pilotos)){
                return true;
            }
        }else{
            return false;
        }
        return false;
    }
    
    
    //Método que pega a classificação pra fazer uma corrida.
    public VoltaPiloto returnClas() throws IOException, ClassNotFoundException {
        Request request = new Request("clas");
        Socket socket = new Socket(serverIp, serverPort);
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.write(this.serializeRequest(request));
            
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        VoltaPiloto clas =(VoltaPiloto) in.readObject();
        socket.close();
        
        return  clas;
    }
    
    public String returnCodCarro() throws IOException, ClassNotFoundException {
        Request request = new Request("pegarCodCarro");
        Socket socket = new Socket(serverIp, serverPort);
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.write(this.serializeRequest(request));
        
        ObjectInputStream in= new ObjectInputStream(socket.getInputStream());
        String codCarro= (String) in.readObject();
        socket.close();
        return codCarro;
    }
    
    //Começa a corrida que deve enviar os dados pros clientes que visualizam os dados da corrida.
    public void startRun(Classificacao clas) throws IOException {
        Request request = new Request("corrida", clas);
        Socket socket = new Socket(serverIp, serverPort);
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.write(this.serializeRequest(request));
    }
    
    public void enviarTempoClassificacao(long tempo) throws IOException {
        this.enviarRequisicao(new Request("tempoClassificacao", tempo)); 
    }
    
    public void finalizaClassificacaoPiloto() throws IOException {
        this.enviarRequisicao(new Request("finalizaClassificacaoPiloto")); 
    }
    
    public void resetaClassificacao() throws IOException {
        this.enviarRequisicao(new Request("resetaClassificacao"));
    }



    


    //Método que manda um objeto pro server.
    public String enviarRequisicao(Request request) throws IOException{
        try{
            Socket socket = new Socket(serverIp, serverPort);
            DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
            saida.write(this.serializeRequest(request));           
            saida.flush();
            //Parte que pega a resposta do servidor.
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = buffer.readLine();
            socket.close();
            return response;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public byte[] serializeRequest(Request request) throws IOException{
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try{
            ObjectOutput out = new ObjectOutputStream(b);
            out.writeObject(request);
            out.flush();
            return b.toByteArray();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

























}
