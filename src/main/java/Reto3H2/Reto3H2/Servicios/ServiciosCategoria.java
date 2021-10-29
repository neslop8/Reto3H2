
package Reto3H2.Reto3H2.Servicios;

import Reto3H2.Reto3H2.Repositorio.RepositorioCategoria;
import Reto3H2.Reto3H2.Modelos.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiciosCategoria {
   
   @Autowired
   private RepositorioCategoria mCrCategoria;
   
   public List<Category> getAll(){
       return mCrCategoria.getAll();
   }
   
   public Optional <Category> getCategory(int idCategory){
       return mCrCategoria.getCategory(idCategory);
   }
   
   public Category save(Category category){
    if (category.getId()==null){
        return mCrCategoria.save(category);
    }else{
        Optional<Category> evt = mCrCategoria.getCategory(category.getId());
        if (evt.isEmpty()){
            return mCrCategoria.save(category);
        }else {
            return category;
        }
    }
  }

    public Category update(Category category) {
        if(category.getId()!=null){
            Optional<Category>evt=mCrCategoria.getCategory(category.getId());
            if(!evt.isEmpty()){
                if(category.getDescription()!=null){
                    evt.get().setDescription(category.getDescription());
                }
                if(category.getName()!=null){
                    evt.get().setName(category.getName());
                }
                return mCrCategoria.save(evt.get());
            }
        }
        return category;
    }

    public boolean deletecategoria(int id) {
        
        Boolean d=getCategory(id).map(category -> {
            mCrCategoria.delete(category);
            return true;
        }).orElse(false);
        return d;
    }
}
    
