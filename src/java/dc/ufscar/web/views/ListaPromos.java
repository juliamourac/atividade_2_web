/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc.ufscar.web.views;

import dc.ufscar.web.beans.Promo;
import dc.ufscar.web.dao.PromoDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author juliamourac
 */

@Named
@RequestScoped
public class ListaPromos implements Serializable {
    @Inject
    PromoDAO pdao;
    
    private List<Promo> promos;
    private List<Promo> promosFiltradas;
    
    @PostConstruct
    public void init(){
        try{
            promos = pdao.listarTodosHoteis();
        }catch(SQLException e){
            Logger.getLogger(ListaHoteis.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public List<Promo> getPromos(){
        return promos;
    }
    
    public List<Promo> getPromosFiltradas(){
        return promosFiltradas;
    }
    
    public void setPromosFiltradas(List<Promo> promosFiltradas){
            this.promosFiltradas = promosFiltradas;
    }
    
}
