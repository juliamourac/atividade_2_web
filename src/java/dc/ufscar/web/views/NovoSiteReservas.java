/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc.ufscar.web.views;

import dc.ufscar.web.beans.SiteReservas;
import dc.ufscar.web.dao.SiteReservaDAO;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

/**
 *
 * @author juliamourac
 */
@Named
@RequestScoped
public class NovoSiteReservas implements Serializable {

    @Inject
    SiteReservaDAO rsdao;

    SiteReservas dadosSiteReserva;
    MensagemBootstrap mensagem;

    public NovoSiteReservas() {
        rsdao = new SiteReservaDAO();
        dadosSiteReserva = new SiteReservas();
        mensagem = new MensagemBootstrap();
        mensagem.setMensagem(true, "Preencha todos os campos.", MensagemBootstrap.TipoMensagem.TIPO_INFO);
    }

    public SiteReservas getDadosSiteReserva() {
        return dadosSiteReserva;
    }

    public void setDadosSiteReserva(SiteReservas dadosSiteReserva) {
        this.dadosSiteReserva = dadosSiteReserva;
    }

    public MensagemBootstrap getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemBootstrap mensagem) {
        this.mensagem = mensagem;
    }

    public void verificaURL() throws SQLException {
        if (!(rsdao.verificaURL(dadosSiteReserva.getUrl()))) {
            mensagem.setMensagem(true, "Um site com esse URL já existe!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
        }
    }

    public void cadastrar() throws SQLException, NamingException {
        if (!(rsdao.verificaURL(dadosSiteReserva.getUrl()))) {
            //verifica campo telefone
            if (dadosSiteReserva.getTelefone().equals(" ")) {
                mensagem.setMensagem(true, "O campo telefone deve ser preenchido !", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
            } else {
                try {
                    Long.parseLong(dadosSiteReserva.getTelefone());

                    rsdao.gravaSiteReservas(dadosSiteReserva);
                } catch (NumberFormatException e) {
                    mensagem.setMensagem(true, "O campo telefone deve ser preenchido somente com números!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                }
            }
        } else {
            mensagem.setMensagem(true, "Um site com esse URL já existe!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
        }
    }
}
