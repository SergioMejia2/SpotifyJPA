/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.math.BigInteger;

/**
 *
 * @author Sala BD
 */
public class InfoCancion
{
    private String titulo;
    private String nombre_artista;
    private String nombre_album;
    private BigInteger codigo;
    private BigInteger minutos;
    private BigInteger segundos;
    
    public InfoCancion( BigInteger codigo, String titulo, String nombre_artista, String nombre_album, BigInteger minutos, BigInteger segundos )
    {
        this.codigo = codigo;
        this.titulo = titulo;
        this.nombre_artista = nombre_artista;
        this.nombre_album = nombre_album;
        this.minutos = minutos;
        this.segundos = segundos;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public String getNombre_artista()
    {
        return nombre_artista;
    }

    public String getNombre_album()
    {
        return nombre_album;
    }

    public BigInteger getCodigo()
    {
        return codigo;
    }
    
    public String getTiempo()
    {
        String retorno = ""+this.minutos+":";
        if(this.segundos.intValue() < 10)
            retorno+="0";
        retorno+=this.segundos;
        return retorno;
    }
    
    public long getDuracion()
    {
        long tiempo = minutos.longValue()*60 + segundos.longValue();
        return tiempo;
    }
    
    
}
