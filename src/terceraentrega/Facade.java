/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terceraentrega;

import Controllers.AlbumJpaController;
import Controllers.CancionJpaController;
import Controllers.CancionxinterpreteJpaController;
import Controllers.CancionxplaylistJpaController;
import Controllers.DiscograficaJpaController;
import Controllers.FamiliarJpaController;
import Controllers.GeneroJpaController;
import Controllers.GratuitaJpaController;
import Controllers.IndividualJpaController;
import Controllers.InterpreteJpaController;
import Controllers.PaisJpaController;
import Controllers.PlaylistJpaController;
import Controllers.ReaccionJpaController;
import Controllers.RegistroJpaController;
import Controllers.SuscripcionJpaController;
import Controllers.UsuarioJpaController;
import Entities.Album;
import Entities.Cancion;
import Entities.Cancionxinterprete;
import Entities.CancionxinterpretePK;
import Entities.Cancionxplaylist;
import Entities.CancionxplaylistPK;
import Entities.Discografica;
import Entities.Familiar;
import Entities.Genero;
import Entities.GeneroPK;
import Entities.Gratuita;
import Entities.Individual;
import Entities.InfoCancion;
import Entities.Interprete;
import Entities.Pais;
import Entities.Playlist;
import Entities.Reaccion;
import Entities.ReaccionPK;
import Entities.Registro;
import Entities.RegistroPK;
import Entities.Suscripcion;
import Entities.Usuario;
import Vista.Menu;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author Sala BD
 */
public class Facade
{

    private EntityManagerFactory emf;
    private Menu menu;
    private Usuario usuario_Activo;
    private boolean isAdmin;

    public Facade()
    {
        usuario_Activo = null;
        isAdmin = false;
        emf = Persistence.createEntityManagerFactory("TerceraEntregaPU");

        PaisJpaController controPais = new PaisJpaController(emf);
        List<Pais> paises = controPais.findPaisEntities();
        DiscograficaJpaController controDisco = new DiscograficaJpaController(emf);
        List<Discografica> discos = controDisco.findDiscograficaEntities();
        GeneroJpaController controGenero = new GeneroJpaController(emf);
        List<Genero> generos = controGenero.findGeneroEntities();

        List<Genero> filtrado = new ArrayList<Genero>();
        for (Genero genero : generos)
        {
            if (genero.getGeneroPK().getCodIdioma() == BigInteger.ONE)
            {
                filtrado.add(genero);
            }
        }

        InterpreteJpaController controInter = new InterpreteJpaController(emf);
        List<Interprete> interpretes = controInter.findInterpreteEntities();

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });*/
        menu = new Menu(paises, discos, interpretes, filtrado, this);
        menu.setVisible(true);
    }

    public boolean AgregarArtista(String nombre_real, String nombre_artista, Pais pais)
    {
        try
        {
            InterpreteJpaController controlInter = new InterpreteJpaController(emf);

            if (pais != null)
            {
                if (!nombre_real.equals(""))
                {
                    Query q = controlInter.getEntityManager().createNativeQuery("SELECT codigo_Interprete.nextval from DUAL");
                    BigInteger result = ((BigDecimal) (q.getSingleResult())).toBigInteger();
                    Interprete inter;
                    if (!nombre_artista.equals(""))
                    {
                        inter = new Interprete(result, nombre_real, nombre_artista, pais);
                    } else
                    {
                        inter = new Interprete(result, nombre_real, null, pais);
                    }
                    controlInter.create(inter);
                    JOptionPane.showMessageDialog(null, "La base de datos (no de virus :v) ha sido actualizada", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
                } else
                {
                    throw new Exception("Ingrese nombre real");
                }
            } else
            {
                throw new Exception("El país no existe");
            }
            return true;
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public boolean AgregarAlbum(String nAlbum, String fecha, Discografica discografica, String tipo)
    {
        try
        {
            AlbumJpaController controAlbum = new AlbumJpaController(emf);            
            Query q = controAlbum.getEntityManager().createNativeQuery("SELECT codigo_Album.nextval from DUAL");
            BigInteger result = ((BigDecimal) (q.getSingleResult())).toBigInteger();

            Album al = new Album(result);
            al.setCodDiscografica(discografica);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ldfecha = LocalDate.parse(fecha, dtf);

            Date fechaLanzamiento = Date.from(ldfecha.atStartOfDay(ZoneId.systemDefault()).toInstant());

            al.setFechaLanzamiento(fechaLanzamiento);
            if (tipo.equals("EP"))
            {
                al.setIsep('S');
            } else
            {
                al.setIsep('N');
            }
            al.setTituloAlbum(nAlbum);
            controAlbum.create(al);
            JOptionPane.showMessageDialog(null, "La base de datos (no de virus :v) ha sido actualizada\nIngrese ahora las canciones", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public boolean Login(String username, String password)
    {
        if (username.equals("admin") && username.equals("admin"))
        {
            isAdmin = true;
            return true;
        } else
        {
            UsuarioJpaController controUsuario = new UsuarioJpaController(emf);
            Usuario user = controUsuario.findUsuario(username);
            if (user != null && user.getPassword().equals(password))
            {
                usuario_Activo = user;
                PlaylistJpaController playlistJpaController = new PlaylistJpaController(emf);
                List<Playlist> playlists = playlistJpaController.findPlaylistUsuario(user);
                menu.actualizarPlaylists(playlists);
                return true;
            } else
            {
                return false;
            }
        }
    }

    public void Suscribirse(String nombre, String apellido, String nickname, String password, Pais pais, String tipoSub, String numTarjeta)
    {
        try
        {
            SuscripcionJpaController controSub = new SuscripcionJpaController(emf);
            if (pais != null)
            {
                if (!nickname.equals(""))
                {
                    UsuarioJpaController controUsu = new UsuarioJpaController(emf);
                    if ((!tipoSub.equals("Gratuita") && !numTarjeta.equals("") && controUsu.validarTarjeta(numTarjeta)) || (tipoSub.equals("Gratuita")))
                    {
                        Usuario usu = controUsu.findUsuario(nickname);

                        if (usu == null)
                        {
                            usu = new Usuario(nickname, password);
                            usu.setNombre(nombre);
                            usu.setApellido(apellido);
                            usu.setCodPais(pais);
                            usu.setRol("Principal");

                            Suscripcion subs = new Suscripcion();
                            Query q = controSub.getEntityManager().createNativeQuery("SELECT codigo_Suscripcion.nextval from DUAL");
                            BigInteger result = ((BigDecimal) (q.getSingleResult())).toBigInteger();

                            subs.setCodSuscripcion(result);
                            List<Usuario> listaUsu = new ArrayList<Usuario>();
                            listaUsu.add(usu);
                            usu.setCodSuscripcion(subs);
                            controSub.create(subs);

                            controUsu.create(usu);
                            if (tipoSub.equals("Gratuita"))
                            {
                                GratuitaJpaController controGratuita = new GratuitaJpaController(emf);
                                Gratuita grat = new Gratuita(result);
                                grat.setNombre("Gratuita");
                                grat.setSuscripcion(subs);

                                controGratuita.create(grat);

                            } else if (tipoSub.equals("Individual"))
                            {
                                IndividualJpaController controIndividual = new IndividualJpaController(emf);
                                Individual indiv = new Individual(result);
                                indiv.setNombre("Individual");
                                indiv.setSuscripcion(subs);

                                LocalDate local = LocalDate.now();
                                Date fechaIn = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                indiv.setFechaInicio(fechaIn);

                                LocalDate localRenov = local.plusMonths(1);
                                Date fechafin = Date.from(localRenov.atStartOfDay(ZoneId.systemDefault()).toInstant());

                                indiv.setFechaFinal(fechafin);

                                controIndividual.create(indiv);

                            } else
                            {
                                FamiliarJpaController controFamiliar = new FamiliarJpaController(emf);
                                Familiar famil = new Familiar(result);
                                famil.setNombre("Familiar");
                                famil.setSuscripcion(subs);

                                LocalDate local = LocalDate.now();
                                Date fechaIn = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                famil.setFechaInicio(fechaIn);

                                LocalDate localRenov = local.plusMonths(1);
                                Date fechafin = Date.from(localRenov.atStartOfDay(ZoneId.systemDefault()).toInstant());

                                famil.setFechaFinal(fechafin);

                                controFamiliar.create(famil);

                            }

                            JOptionPane.showMessageDialog(null, "Usuario creado correctamente", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
                        } else
                        {
                            throw new Exception("Usuario ya existente");
                        }
                    } else
                    {
                        throw new Exception("Ingrese un número de tarjeta válido");
                    }
                } else
                {
                    throw new Exception("El país no existe");
                }

            }
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void AgregarCancion(String titulo, Genero genero, Interprete interprete, List<BigInteger> idsArtistas, int min, int sec)
    {
        try
        {
            if (idsArtistas.contains(interprete.getCodInterprete()))
            {
                throw new Exception("El artista principal no puede ser secundario");
            }

            CancionJpaController controCancion = new CancionJpaController(emf);
            Query q = controCancion.getEntityManager().createNativeQuery("SELECT codigo_Suscripcion.nextval from DUAL");
            BigInteger result = ((BigDecimal) (q.getSingleResult())).toBigInteger();

            AlbumJpaController controAlbum = new AlbumJpaController(emf);
            GeneroJpaController controGene = new GeneroJpaController(emf);
            Album al = controAlbum.getUltimaCancion();
            Cancion can = new Cancion(result, titulo, new BigInteger("" + min), new BigInteger("" + sec));
            can.setCodAlbum(al);
            can.setGenero(genero);
           
            controCancion.create(can);
            InterpreteJpaController controInter = new InterpreteJpaController(emf);

            CancionxinterpreteJpaController controCxi = new CancionxinterpreteJpaController(emf);

            CancionxinterpretePK cxiPK = new CancionxinterpretePK(interprete.getCodInterprete(), can.getCodCancion());
            Cancionxinterprete cxi = new Cancionxinterprete(interprete.getCodInterprete(), can.getCodCancion());
            cxi.setCancionxinterpretePK(cxiPK);
            cxi.setInterprete(interprete);
            cxi.setCancion(can);
            cxi.setRol("Principal");
            controCxi.getEntityManager().getTransaction().begin();
            controCxi.create(cxi);
            for (BigInteger idsArtista : idsArtistas)
            {
                Interprete inter = controInter.findInterprete(idsArtista);
                cxiPK = new CancionxinterpretePK(idsArtista, can.getCodCancion());
                cxi = new Cancionxinterprete(idsArtista, can.getCodCancion());
                cxi.setCancionxinterpretePK(cxiPK);
                cxi.setInterprete(inter);
                cxi.setCancion(can);
                cxi.setRol("Invitado");
                controCxi.create(cxi);
            }
            controCxi.getEntityManager().getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Canción añadida correctamente", "Confirmado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), "ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public List<InfoCancion> BuscarCancion(String busqueda)
    {
        CancionJpaController controCancion = new CancionJpaController(emf);
        List<InfoCancion> list_info = new ArrayList<InfoCancion>();
        list_info = controCancion.buscarCancion(busqueda);
        return list_info;
    }
    
    public InfoCancion BuscarCancion(BigInteger id_cancion)
    {
        CancionJpaController controCancion = new CancionJpaController(emf);
        InfoCancion list_info = controCancion.buscarCancion(id_cancion);
        return list_info;
    }

    public void AgregarAPlaylist(List<BigInteger> canciones, Playlist playlist)
    {
        CancionJpaController controCancion = new CancionJpaController(emf);
        PlaylistJpaController controPlaylist = new PlaylistJpaController(emf);
        CancionxplaylistJpaController controcxp = new CancionxplaylistJpaController(emf);
        List<String> agregadas = new ArrayList<String>();
        if (playlist != null)
        {
            for (BigInteger id_cancion : canciones)
            {
                try
                {
                    Cancion cancion = controCancion.findCancion(id_cancion);
                    CancionxplaylistPK cxpPK = new CancionxplaylistPK(id_cancion, playlist.getCodPlaylist());
                    Cancionxplaylist cxp = new Cancionxplaylist(cancion, playlist);
                    BigInteger orden = controcxp.findLastOrder(playlist);
                    cxp.setOrden(orden.add(new BigInteger("1")));
                    cxp.setCancionxplaylistPK(cxpPK);
                    controcxp.create(cxp);
                    agregadas.add(cancion.getTituloCancion());
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getStackTrace(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (!agregadas.isEmpty())
            {
                String agg = "";
                for (String agregada : agregadas)
                {
                    agg += (agregada + "\n");
                }
                JOptionPane.showMessageDialog(null, "Canciones agregadas:\n" + agg, "Confirmado", JOptionPane.INFORMATION_MESSAGE);
            }
            BigInteger numCanciones = new BigInteger(""+controcxp.getCancionxplaylistCount(playlist).size());
            BigInteger tiempoRepr = new BigInteger(""+controPlaylist.getDuracionPlaylist(playlist));
            
            float tiempoTotalEnSec = tiempoRepr.floatValue();
            
            float tiempoSec = tiempoTotalEnSec % 60;
            float tiempoTotalEnMin = (tiempoTotalEnSec-tiempoSec)/60;
            float tiempoMin = tiempoTotalEnMin % 60;
            float tiempoHor = (tiempoTotalEnMin-tiempoMin)/60;
            
            String tiempo = tiempoHor+"h "+tiempoMin+"m "+tiempoSec+"s";
            
            JOptionPane.showMessageDialog(null, "Tiempo de reproducción: "+tiempo+"\nNúmero de canciones: "+numCanciones, "Información de "+playlist.getNombrePlaylist(), JOptionPane.INFORMATION_MESSAGE);
            menu.cambiarPanel("Menu Principal");
        }
    }

    public List<Interprete> getInterpretes()
    {
        InterpreteJpaController controInter = new InterpreteJpaController(emf);
        List<Interprete> interpretes = controInter.findInterpreteEntities();
        return interpretes;
    }

    public Playlist AgregarPlaylist(String newPlaylist, List<BigInteger> canciones, String privacidad)
    {
        PlaylistJpaController controPlaylist = new PlaylistJpaController(emf);
        Query q = controPlaylist.getEntityManager().createNativeQuery("SELECT codigo_playlist.nextval from DUAL");
        BigInteger result = ((BigDecimal) (q.getSingleResult())).toBigInteger();

        Playlist playlist = new Playlist(result);
        playlist.setNombrePlaylist(newPlaylist);
        playlist.setNickname(usuario_Activo);
        playlist.setPrivacidad(privacidad);
        try
        {
            controPlaylist.create(playlist);
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);

        }
        AgregarAPlaylist(canciones, playlist);
        List<Playlist> playlists = controPlaylist.findPlaylistUsuario(usuario_Activo);
        menu.actualizarPlaylists(playlists);
        return playlist;

    }

    public boolean Logout()
    {
        if (usuario_Activo != null || isAdmin == true)
        {
            usuario_Activo = null;
            return true;
        }
        return false;
    }

    public List<Cancion> getCanciones(Playlist playlist)
    {
        CancionJpaController controCancion = new CancionJpaController(emf);
        List<Cancion> canciones = controCancion.buscarCanciones( playlist );

        return canciones;
    }
    
    public List<BigInteger> getOrdenes(Playlist playlist)
    {
        CancionxplaylistJpaController controcxp = new CancionxplaylistJpaController(emf);
        List<BigInteger> ordenes = controcxp.getOrdenes( playlist );
        return ordenes;
    }

    public void modificarPlaylist(HashMap<BigInteger, Cancion> mapa, String nombreP, Playlist playlist)
    {
        try
        {
            CancionxplaylistJpaController controcxp = new CancionxplaylistJpaController(emf);
            controcxp.cambiarOrden(mapa, playlist);
            PlaylistJpaController controPlaylist = new PlaylistJpaController(emf);
            controPlaylist.cambiarNombre(playlist, nombreP);
            JOptionPane.showMessageDialog(null, "Playlist modificada correctamente", "Información de "+playlist.getNombrePlaylist(), JOptionPane.INFORMATION_MESSAGE);
            menu.cambiarPanel("Menu Principal");
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Reaccion obtenerReaccion(BigInteger id_cancion)
    {
        ReaccionJpaController controReaccion = new ReaccionJpaController(emf);
        ReaccionPK rPK = new ReaccionPK(id_cancion, usuario_Activo.getNickname());
        Reaccion r = null;
        r = controReaccion.findReaccion(rPK);
        return r;
    }

    public void darReaccion(BigInteger codigo, boolean b)
    {
        ReaccionJpaController controReaccion = new ReaccionJpaController(emf);
        ReaccionPK rPK = new ReaccionPK(codigo, usuario_Activo.getNickname());
        Reaccion r = new Reaccion(rPK);
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        r.setFechaLike(in);
        
        CancionJpaController controCancion = new CancionJpaController(emf);
        
        r.setCancion(controCancion.findCancion(codigo));
        r.setUsuario(usuario_Activo);
        try
        {
            if(b == true)
            {
                controReaccion.create(r);
            }
            else
            {
                controReaccion.destroy(rPK);
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Usuario getUsuario_Activo()
    {
        return usuario_Activo;
    }

    public void registrarReproduccion(Registro registro)
    {
        RegistroJpaController controRegistro = new RegistroJpaController(emf);
        CancionJpaController controCancion = new CancionJpaController(emf);
        registro.setCancion(controCancion.findCancion(registro.getRegistroPK().getCodCancion()));
        registro.setUsuario(usuario_Activo);
        try
        {
            controRegistro.create(registro);
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

}
