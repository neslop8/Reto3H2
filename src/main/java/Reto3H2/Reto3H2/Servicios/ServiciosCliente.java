
package Reto3H2.Reto3H2.Servicios;

import Reto3H2.Reto3H2.Repositorio.RepositorioCliente;
import Reto3H2.Reto3H2.Modelos.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service      
public class ServiciosCliente {
    
   @Autowired
   private RepositorioCliente mCrCliente;
   
   public List<Client> getAll(){
       return mCrCliente.getAll();
   }
   
   public Optional <Client> getClient(int idClient){
       return mCrCliente.getClient(idClient);
   }
   
   public Client save(Client client){
    if (client.getIdClient()==null){
        return mCrCliente.save(client);
    }else{
        Optional<Client> evt = mCrCliente.getClient(client.getIdClient());
        if (evt.isEmpty()){
            return mCrCliente.save(client);
        }else {
            return client;
        }
    }
  }

    public Client update(Client client) {
    
        if(client.getIdClient()!=null){
            Optional<Client> e= mCrCliente.getClient(client.getIdClient());
            if(!e.isEmpty()){
                if(client.getName()!=null){
                    e.get().setName(client.getName());
                }
                if(client.getAge()!=null){
                    e.get().setAge(client.getAge());
                }
                if(client.getPassword()!=null){
                    e.get().setPassword(client.getPassword());
                }
                mCrCliente.save(e.get());
                return e.get();
            }else{
                return client;
            }
        }else{
            return client;
        }
        
    }

    public boolean deleteClient(int idClient) {
        
        Boolean aBoolean = getClient(idClient).map(client -> {
            mCrCliente.delete(client);
            return true;
        }).orElse(false);
        return aBoolean;
    }

}
