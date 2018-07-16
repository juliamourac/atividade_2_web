/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc.ufscar.web.views;

import dc.ufscar.web.dao.HotelDAO;
import dc.ufscar.web.dao.SiteReservaDAO;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author juliamourac
 */
@Named
@SessionScoped
public class Login implements Serializable {

    @Inject
    HotelDAO hdao;
    @Inject
    SiteReservaDAO srdao;

    MensagemBootstrap mensagem;

    String usuario;
    String senha;

    public void Login() {
        hdao = new HotelDAO();
        srdao = new SiteReservaDAO();
        mensagem = new MensagemBootstrap();
        mensagem.setMensagem(true, "Preencha todos os campos.", MensagemBootstrap.TipoMensagem.TIPO_INFO);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public MensagemBootstrap getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagemBootstrap mensagem) {
        this.mensagem = mensagem;
    }

    public void logar() throws SQLException {
        try {
            if (usuario.equals("admin") && senha.equals("admin")) {
                //Loga como admin
            } else if (hdao.logar(usuario, senha)) {
                //loga como hotel
            } else if (srdao.logar(usuario, senha)) {
                //loga como site de reserva
            } else
                mensagem.setMensagem(true, "Login não encontrado", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
                
        } catch (SQLException e){
            mensagem.setMensagem(true, "Não foi possível conectar ao Banco de Dados", MensagemBootstrap.TipoMensagem.TIPO_ERRO);
        }
    }

}
