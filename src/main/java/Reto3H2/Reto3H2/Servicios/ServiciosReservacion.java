
package Reto3H2.Reto3H2.Servicios;

import Reportes.ContadorClientes;
import Reportes.StatusReservas;
import Reto3H2.Reto3H2.Repositorio.RepositorioReservacion;
import Reto3H2.Reto3H2.Modelos.Reservation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiciosReservacion {
    
   @Autowired
   private RepositorioReservacion mCrReservation;
   
   public List<Reservation> getAll(){
       return mCrReservation.getAll();
   }
   
   public Optional <Reservation> getReservation(int idReservation){
       return mCrReservation.getReservation(idReservation);
   }
   
   public Reservation save(Reservation reservation){
    if (reservation.getIdReservation()==null){
        return mCrReservation.save(reservation);
    }else{
        Optional<Reservation> evt =mCrReservation.getReservation(reservation.getIdReservation());
        if (evt.isEmpty()){
            return mCrReservation.save(reservation);
        }else {
            return reservation;
        }
    }
  }

    public Reservation update(Reservation reservation) {
       
        if(reservation.getIdReservation()!=null){
            Optional<Reservation> e= mCrReservation.getReservation(reservation.getIdReservation());
            if(!e.isEmpty()){

                if(reservation.getStartDate()!=null){
                    e.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate()!=null){
                    e.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus()!=null){
                    e.get().setStatus(reservation.getStatus());
                }
                mCrReservation.save(e.get());
                return e.get();
            }else{
                return reservation;
            }
        }else{
            return reservation;
        }
    }

    public boolean deleteReservation(int idReservation) {
        
        Boolean aBoolean = getReservation(idReservation).map(reservation -> {
            mCrReservation.delete(reservation);
            return true;
        }).orElse(false);
        return aBoolean;
    }
    
    public StatusReservas getReservationsStatusReport(){
        List<Reservation>completed=mCrReservation.getReservationByStatus("completed");
        List<Reservation>cancelled=mCrReservation.getReservationByStatus("cancelled");
    return new StatusReservas(completed.size(), cancelled.size());
    }
    
    public List<Reservation> getReservationPeriod(String datoA, String datoB){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = new Date();
        Date fechaFin = new Date();
        
       try {
           fechaInicio = parser.parse(datoA);
           fechaFin = parser.parse(datoB);
       }catch(ParseException evt){
           evt.printStackTrace();
       }if(fechaInicio.before(fechaFin)){
           return mCrReservation.getReservationPeriod(fechaInicio, fechaFin);
       }else{
           return new ArrayList<>();
       } 
    
    }
    
    public List<ContadorClientes> getTopClients(){
        return mCrReservation.getTopClients();
    }

    
}