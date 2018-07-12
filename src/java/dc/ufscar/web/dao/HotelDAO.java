/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc.ufscar.web.dao;

import dc.ufscar.web.beans.Hotel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

/**
 *
 * @author juliamourac
 */

@RequestScoped
public class HotelDAO {

    @Resource(name = "jdbc/ReservaHotelDBLocal")
    DataSource dataSource;
    
    private final String CRIAR_HOTEL_SQL = "INSERT INTO Hotel values (?,?,?,?)";
    private final String BUSCAR_HOTEL_SQL = "SELECT cnpj, nome, cidade FROM Hotel";
    private final String BUSCAR_HOTEL_SQL_CIDADE = "SELECT nome, cidade FROM Hotel WHERE cidade = ?";
    private final String BUSCA_LOGIN = "SELECT cnpj, senha FROM Hotel WHERE cnpj=? AND senha=?";
    private final String BUSCA_CNPJ = "SELECT cnpj FROM Hotel WHERE cnpj=?";
    private final String LISTA_CIDADES = "SELECT cidade FROM Hotel";
    private final String LISTA_CNPJ = "SELECT cnpj FROM Hotel";

    public Hotel gravarHotel(Hotel h) throws SQLException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(CRIAR_HOTEL_SQL);) {
            ps.setString(1, h.getCnpj());
            ps.setString(2, h.getNome());
            ps.setString(3, h.getCidade());
            ps.setString(4, h.getSenha());
            ps.execute();
            con.close();
        }
        return h;
    }

    public List<Hotel> listarTodosHoteis() throws SQLException {
        List<Hotel> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCAR_HOTEL_SQL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Hotel h = new Hotel();
                    h.setCnpj(rs.getString("cnpj"));
                    h.setNome(rs.getString("nome"));
                    h.setCidade(rs.getString("cidade"));
                    ret.add(h);
                }
            }
        }
        return ret;
    }

    public List<Hotel> listarHoteisPorCidade(String cidade) throws SQLException {
        List<Hotel> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCAR_HOTEL_SQL_CIDADE)) {
            ps.setString(1, cidade);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Hotel h = new Hotel();
                    h.setNome(rs.getString("nome"));
                    h.setCidade(rs.getString("cidade"));
                    ret.add(h);
                }
            }
            return ret;
        }
    }

    public Boolean logar(String cnpj, String senha) throws SQLException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCA_LOGIN);) {
            ps.setString(1, cnpj);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    return true;
                else
                    return false;
            }
        }
    }
    
    public Boolean buscaCNPJ(String _cnpj) throws SQLException{
        try(Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTA_CIDADES);){
         
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return true;
                else
                    return false;
            }
        }
    }
    
    public List<String> listarCidades() throws SQLException{
        List<String> lista = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTA_CIDADES);){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    if(!(lista.contains(rs.getString("cidade"))))
                        lista.add(rs.getString("cidade"));
                }
                return lista;
            }
        }
    }

    public List<String> listaCNPJ()throws SQLException{
        List<String> lista = new ArrayList<>();
        
        try(Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTA_CNPJ);){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    lista.add(rs.getString("cnpj"));
                }
            }
        }
        
        return lista;       
    }
    
    public List<String> listaNomeHoteis()throws SQLException{
        List<String> lista = new ArrayList<>();
        
        try(Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCAR_HOTEL_SQL);){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    lista.add(rs.getString("nome"));
                }
            }
        }
        
        return lista;       
    }        
}
