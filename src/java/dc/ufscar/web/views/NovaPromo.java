/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc.ufscar.web.views;

import dc.ufscar.web.beans.Hotel;
import dc.ufscar.web.beans.Promo;
import dc.ufscar.web.dao.HotelDAO;
import dc.ufscar.web.dao.PromoDAO;
import dc.ufscar.web.dao.SiteReservaDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 *
 * @author juliamourac
 */
@Named
@SessionScoped
public class NovaPromo implements Serializable {

    @Inject
    HotelDAO hdao;
    @Inject
    PromoDAO pdao;
    @Inject
    SiteReservaDAO rsdao;

    List<String> listaUrl;
    List<String> nomeHoteis;
    List<String> cnpjHoteis;
    String selectedHotelIndex;

    MensagemBootstrap mensagem;

    Promo dadosPromo;
    
    public void NovaPromo() {
        hdao = new HotelDAO();
        pdao = new PromoDAO();
        rsdao = new SiteReservaDAO();
        mensagem = new MensagemBootstrap();
        mensagem.setMensagem(true, "Preencha todos os campos.", MensagemBootstrap.TipoMensagem.TIPO_INFO);
        dadosPromo = new Promo();
    }

    public List<String> getListaUrl() throws SQLException {
        return listaUrl = rsdao.listaUrlSiteReservas();
    }

    public void setListaUrl(List<String> listaUrl) {
        this.listaUrl = listaUrl;
    }

    public MensagemBootstrap getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemBootstrap mensagem) {
        this.mensagem = mensagem;
    }

    public Promo getDadosPromo() {
        return dadosPromo;
    }

    public void setDadosPromo(Promo dadosPromo) {
        this.dadosPromo = dadosPromo;
    }

    public List<String> getNomeHoteis() throws SQLException {
        return nomeHoteis = hdao.listaNomeHoteis();
    }

    public void setNomeHoteis(List<String> nomeHoteis) {
        this.nomeHoteis = nomeHoteis;
    }

    public List<String> getCnpjHoteis() throws SQLException {
        return cnpjHoteis = hdao.listaCNPJ();
    }

    public void setCnpjHoteis(List<String> cnpjHoteis) {
        this.cnpjHoteis = cnpjHoteis;
    }

    public String getSelectedHotelIndex() {
        return selectedHotelIndex;
    }

    public void setSelectedHotelIndex(String selectedHotelIndex) {
        this.selectedHotelIndex = selectedHotelIndex;
    }
    
    public void cadastrar() throws SQLException{
        //Verifica URL
        if(dadosPromo.getUrl().equals(""))
            mensagem.setMensagem(true, "Escolha uma URL existente.", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
        else{
            //Verifica CNPJ
            if(this.getSelectedHotelIndex().equals("-1"))
                mensagem.setMensagem(true, "Escolha um Hotel existente.", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
            else{
                int n = Integer.parseInt(selectedHotelIndex);
                dadosPromo.setCnpj(getCnpjHoteis().get(n));
                
                //Verifica se DataInicio e DataFim são a mesma data
                if(dadosPromo.getDataInicio().equals(dadosPromo.getDataFim()))
                    mensagem.setMensagem(true, "A data de Início e de Fim devem ser diferentes.", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
            }
        }
    }
    
    
}
