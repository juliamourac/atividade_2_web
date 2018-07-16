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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
    List<Hotel> listaHoteis;

    MensagemBootstrap mensagem;

    Promo dadosPromo;
    
    public NovaPromo() {
        hdao = new HotelDAO();
        pdao = new PromoDAO();
        rsdao = new SiteReservaDAO();
        dadosPromo = new Promo();
        mensagem = new MensagemBootstrap();
        mensagem.setMensagem(true, "Preencha todos os campos.", MensagemBootstrap.TipoMensagem.TIPO_INFO);        
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

    public List<Hotel> getListaHoteis() throws SQLException {
        return listaHoteis = hdao.listarTodosHoteis();
    }

    public void setListaHoteis(List<Hotel> listaHoteis) {
        this.listaHoteis = listaHoteis;
    }

    public void cadastrar() throws SQLException {
        //Verifica URL
        if (dadosPromo.getUrl().equals("-1")) {
            mensagem.setMensagem(true, "Escolha uma URL existente.", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
        } else {
            //verifica CNPJ
            if (dadosPromo.getCnpj().equals("-1")) {
                mensagem.setMensagem(true, "Escolha um CNPJ existente.", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
            } else {
                //verifica preço
                try {
                    Float.parseFloat(dadosPromo.getPreco());
                } catch (NumberFormatException e) {
                    mensagem.setMensagem(true, "O preço deve ser um número", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                }

                //Verificação de data
                try {
                    SimpleDateFormat formaData = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataInicio = formaData.parse(dadosPromo.getDataInicio());
                    Date dataFim = formaData.parse(dadosPromo.getDataFim());

                    if (dataInicio.after(dataFim)) {
                        mensagem.setMensagem(true, "A data final deve ser maior que a data de início da promoção.", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                    }

                } catch (ParseException e) {
                    mensagem.setMensagem(true, "Escolha uma data válida", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                }
            }
        }
        
        try{
            pdao.gravarPromo(dadosPromo);
            mensagem.setMensagem(true, "Promoção cadastrada com sucesso!", MensagemBootstrap.TipoMensagem.TIPO_SUCESSO);            
        }catch(Exception e){
            mensagem.setMensagem(true, "Não foi possível cadastrar a promoção", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
            e.printStackTrace();
        }
    }

}
