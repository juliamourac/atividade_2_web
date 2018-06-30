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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author juliamourac
 */
@Named
@SessionScoped
public class NovoHotel implements Serializable {

    @Inject
    HotelDAO hdao;

    Hotel dadosHotel;
    MensagemBootstrap mensagem;

    public NovoHotel() {
        hdao = new HotelDAO();
        dadosHotel = new Hotel();
        mensagem = new MensagemBootstrap();
        mensagem.setMensagem(true, "Preencha todos os campos.", MensagemBootstrap.TipoMensagem.TIPO_INFO);
    }

    public Hotel getDadosHotel() {
        return dadosHotel;
    }

    public void setDadosHotel(Hotel dadosHotel) {
        this.dadosHotel = dadosHotel;
    }

    public MensagemBootstrap getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemBootstrap mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean validarDados() {
        //Validação de CNPJ
        if (dadosHotel.getCnpj().equals("") || dadosHotel.getCnpj() == null) {
            mensagem.setMensagem(true, "O CNPJ DEVE SER PREENCHIDO!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
            return false;
        } else {
            //Verifica se o CNPJ é um número
            try {
                Long.parseLong(dadosHotel.getCnpj());

                //Verifica e exitência do CNPJ
                try {
                    if (hdao.buscaCNPJ(dadosHotel.getCnpj())) {
                        mensagem.setMensagem(true, "Já existe um hotel com este CNPJ!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                    }
                } catch (SQLException e) {
                    Logger.getLogger(NovoHotel.class.getName()).log(Level.SEVERE, null, e);
                    mensagem.setMensagem(true, "Ocorreu um problema!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                    return false;
                }

            } catch (NumberFormatException e) {
                mensagem.setMensagem(true, "O CNPJ deve ser um número!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                return false;
            }

            //validação do nome
            if (dadosHotel.getNome().equals("") || dadosHotel.getNome() == null) {
                mensagem.setMensagem(true, "O campo \"nome\" deve ser preenchido!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                return false;
            }

            //validação da senha
            if (dadosHotel.getSenha().equals("") || dadosHotel.getSenha() == null) {
                mensagem.setMensagem(true, "O campo \"senha\" deve ser preenchido!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                return false;
            }

            //Validação de cidade
            if (dadosHotel.getCidade().equals("") || dadosHotel.getCidade() == null) {
                mensagem.setMensagem(true, "O campo \"cidade\" deve ser preenchido!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                return false;
            }

            //se passou por tudo
            return true;
        }
    }

    public void cadastrar() throws SQLException {
        if (!(hdao.buscaCNPJ(dadosHotel.getCnpj())))  {
            if (validarDados()) {
                try {
                    hdao.gravarHotel(dadosHotel);
                    mensagem.setMensagem(true, "Informações gravadas com sucesso!", MensagemBootstrap.TipoMensagem.TIPO_SUCESSO);
                } catch (Exception e) {
                    mensagem.setMensagem(true, "Ocorreu um erro!\nNão foi possível gravar os dados do Hotel!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                }
            }
        }
        else
            mensagem.setMensagem(true, "Já há um hotel cadastrado como esse CNPJ!", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
    }    
}
