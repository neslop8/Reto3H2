
package Reto3H2.Reto3H2.Servicios;

import Reto3H2.Reto3H2.Repositorio.RepositorioMensaje;
import Reto3H2.Reto3H2.Modelos.Message;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiciosMensaje {
    
   @Autowired
   private RepositorioMensaje mCrMensaje;
   
   public List<Message> getAll(){
       return mCrMensaje.getAll();
   }
   
   public Optional <Message> getMessage(int idMessage){
       return mCrMensaje.getMessage(idMessage);
   }
   
   public Message save(Message message){
    if (message.getIdMessage()==null){
        return mCrMensaje.save(message);
    }else{
        Optional<Message> evt =mCrMensaje.getMessage(message.getIdMessage());
        if (evt.isEmpty()){
            return mCrMensaje.save(message);
        }else {
            return message;
        }
    }
  }

    public Message update(Message message) {
        if(message.getIdMessage()!=null){
            Optional<Message> e= mCrMensaje.getMessage(message.getIdMessage());
            if(!e.isEmpty()){
                if(message.getMessageText()!=null){
                    e.get().setMessageText(message.getMessageText());
                }
                mCrMensaje.save(e.get());
                return e.get();
            }else{
                return message;
            }
        }else{
            return message;
        }


    }

    public boolean deleteMessage(int idMessage) {
    
        Boolean aBoolean = getMessage(idMessage).map(message -> {
            mCrMensaje.delete(message);
            return true;
        }).orElse(false);
        return aBoolean;
    }
 }
