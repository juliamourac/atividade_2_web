/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc.ufscar.web.views;

import dc.ufscar.web.beans.Hotel;
import dc.ufscar.web.dao.HotelDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author juliamourac
 */
@Named
@ViewScoped
public class ListaHoteis implements Serializable {
    
    @Inject
    HotelDAO hdao;
    
    private List<Hotel> hoteis;
    private List<Hotel> hoteisFiltrados;
    private List<String> cidades;
    
    @PostConstruct
    public void init(){
        try{
            hoteis = hdao.listarTodosHoteis();
            cidades = hdao.listarCidades();
        }catch(SQLException ex){
            Logger.getLogger(ListaHoteis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Hotel> getHoteis(){
        return hoteis;
    }
    
    public List<Hotel> getHoteisFiltrados(){
        return hoteisFiltrados;
    }
    
    public void setHoteisFiltrados(List<Hotel> hoteisFiltrados){
        this.hoteisFiltrados = hoteisFiltrados;
    }

    public List<String> getCidades() {
        return cidades;
    }
    
    
}
