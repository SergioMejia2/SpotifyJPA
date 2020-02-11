/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Pais;
import Entities.Suscripcion;
import Entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import Entities.Registro;
import Entities.Reaccion;
import Entities.Playlist;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Sala BD
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getUsuarioList() == null) {
            usuario.setUsuarioList(new ArrayList<Usuario>());
        }
        if (usuario.getUsuarioList1() == null) {
            usuario.setUsuarioList1(new ArrayList<Usuario>());
        }
        if (usuario.getRegistroList() == null) {
            usuario.setRegistroList(new ArrayList<Registro>());
        }
        if (usuario.getReaccionList() == null) {
            usuario.setReaccionList(new ArrayList<Reaccion>());
        }
        if (usuario.getPlaylistList() == null) {
            usuario.setPlaylistList(new ArrayList<Playlist>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais codPais = usuario.getCodPais();
            if (codPais != null) {
                codPais = em.getReference(codPais.getClass(), codPais.getCodPais());
                usuario.setCodPais(codPais);
            }
            Suscripcion codSuscripcion = usuario.getCodSuscripcion();
            if (codSuscripcion != null) {
                codSuscripcion = em.getReference(codSuscripcion.getClass(), codSuscripcion.getCodSuscripcion());
                usuario.setCodSuscripcion(codSuscripcion);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : usuario.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getNickname());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            usuario.setUsuarioList(attachedUsuarioList);
            List<Usuario> attachedUsuarioList1 = new ArrayList<Usuario>();
            for (Usuario usuarioList1UsuarioToAttach : usuario.getUsuarioList1()) {
                usuarioList1UsuarioToAttach = em.getReference(usuarioList1UsuarioToAttach.getClass(), usuarioList1UsuarioToAttach.getNickname());
                attachedUsuarioList1.add(usuarioList1UsuarioToAttach);
            }
            usuario.setUsuarioList1(attachedUsuarioList1);
            List<Registro> attachedRegistroList = new ArrayList<Registro>();
            for (Registro registroListRegistroToAttach : usuario.getRegistroList()) {
                registroListRegistroToAttach = em.getReference(registroListRegistroToAttach.getClass(), registroListRegistroToAttach.getRegistroPK());
                attachedRegistroList.add(registroListRegistroToAttach);
            }
            usuario.setRegistroList(attachedRegistroList);
            List<Reaccion> attachedReaccionList = new ArrayList<Reaccion>();
            for (Reaccion reaccionListReaccionToAttach : usuario.getReaccionList()) {
                reaccionListReaccionToAttach = em.getReference(reaccionListReaccionToAttach.getClass(), reaccionListReaccionToAttach.getReaccionPK());
                attachedReaccionList.add(reaccionListReaccionToAttach);
            }
            usuario.setReaccionList(attachedReaccionList);
            List<Playlist> attachedPlaylistList = new ArrayList<Playlist>();
            for (Playlist playlistListPlaylistToAttach : usuario.getPlaylistList()) {
                playlistListPlaylistToAttach = em.getReference(playlistListPlaylistToAttach.getClass(), playlistListPlaylistToAttach.getCodPlaylist());
                attachedPlaylistList.add(playlistListPlaylistToAttach);
            }
            usuario.setPlaylistList(attachedPlaylistList);
            em.persist(usuario);
            if (codPais != null) {
                codPais.getUsuarioList().add(usuario);
                codPais = em.merge(codPais);
            }
            if (codSuscripcion != null) {
                codSuscripcion.getUsuarioList().add(usuario);
                codSuscripcion = em.merge(codSuscripcion);
            }
            for (Usuario usuarioListUsuario : usuario.getUsuarioList()) {
                usuarioListUsuario.getUsuarioList().add(usuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            for (Usuario usuarioList1Usuario : usuario.getUsuarioList1()) {
                usuarioList1Usuario.getUsuarioList().add(usuario);
                usuarioList1Usuario = em.merge(usuarioList1Usuario);
            }
            for (Registro registroListRegistro : usuario.getRegistroList()) {
                Usuario oldUsuarioOfRegistroListRegistro = registroListRegistro.getUsuario();
                registroListRegistro.setUsuario(usuario);
                registroListRegistro = em.merge(registroListRegistro);
                if (oldUsuarioOfRegistroListRegistro != null) {
                    oldUsuarioOfRegistroListRegistro.getRegistroList().remove(registroListRegistro);
                    oldUsuarioOfRegistroListRegistro = em.merge(oldUsuarioOfRegistroListRegistro);
                }
            }
            for (Reaccion reaccionListReaccion : usuario.getReaccionList()) {
                Usuario oldUsuarioOfReaccionListReaccion = reaccionListReaccion.getUsuario();
                reaccionListReaccion.setUsuario(usuario);
                reaccionListReaccion = em.merge(reaccionListReaccion);
                if (oldUsuarioOfReaccionListReaccion != null) {
                    oldUsuarioOfReaccionListReaccion.getReaccionList().remove(reaccionListReaccion);
                    oldUsuarioOfReaccionListReaccion = em.merge(oldUsuarioOfReaccionListReaccion);
                }
            }
            for (Playlist playlistListPlaylist : usuario.getPlaylistList()) {
                Usuario oldNicknameOfPlaylistListPlaylist = playlistListPlaylist.getNickname();
                playlistListPlaylist.setNickname(usuario);
                playlistListPlaylist = em.merge(playlistListPlaylist);
                if (oldNicknameOfPlaylistListPlaylist != null) {
                    oldNicknameOfPlaylistListPlaylist.getPlaylistList().remove(playlistListPlaylist);
                    oldNicknameOfPlaylistListPlaylist = em.merge(oldNicknameOfPlaylistListPlaylist);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getNickname()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getNickname());
            Pais codPaisOld = persistentUsuario.getCodPais();
            Pais codPaisNew = usuario.getCodPais();
            Suscripcion codSuscripcionOld = persistentUsuario.getCodSuscripcion();
            Suscripcion codSuscripcionNew = usuario.getCodSuscripcion();
            List<Usuario> usuarioListOld = persistentUsuario.getUsuarioList();
            List<Usuario> usuarioListNew = usuario.getUsuarioList();
            List<Usuario> usuarioList1Old = persistentUsuario.getUsuarioList1();
            List<Usuario> usuarioList1New = usuario.getUsuarioList1();
            List<Registro> registroListOld = persistentUsuario.getRegistroList();
            List<Registro> registroListNew = usuario.getRegistroList();
            List<Reaccion> reaccionListOld = persistentUsuario.getReaccionList();
            List<Reaccion> reaccionListNew = usuario.getReaccionList();
            List<Playlist> playlistListOld = persistentUsuario.getPlaylistList();
            List<Playlist> playlistListNew = usuario.getPlaylistList();
            List<String> illegalOrphanMessages = null;
            for (Registro registroListOldRegistro : registroListOld) {
                if (!registroListNew.contains(registroListOldRegistro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registro " + registroListOldRegistro + " since its usuario field is not nullable.");
                }
            }
            for (Reaccion reaccionListOldReaccion : reaccionListOld) {
                if (!reaccionListNew.contains(reaccionListOldReaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reaccion " + reaccionListOldReaccion + " since its usuario field is not nullable.");
                }
            }
            for (Playlist playlistListOldPlaylist : playlistListOld) {
                if (!playlistListNew.contains(playlistListOldPlaylist)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Playlist " + playlistListOldPlaylist + " since its nickname field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codPaisNew != null) {
                codPaisNew = em.getReference(codPaisNew.getClass(), codPaisNew.getCodPais());
                usuario.setCodPais(codPaisNew);
            }
            if (codSuscripcionNew != null) {
                codSuscripcionNew = em.getReference(codSuscripcionNew.getClass(), codSuscripcionNew.getCodSuscripcion());
                usuario.setCodSuscripcion(codSuscripcionNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getNickname());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            usuario.setUsuarioList(usuarioListNew);
            List<Usuario> attachedUsuarioList1New = new ArrayList<Usuario>();
            for (Usuario usuarioList1NewUsuarioToAttach : usuarioList1New) {
                usuarioList1NewUsuarioToAttach = em.getReference(usuarioList1NewUsuarioToAttach.getClass(), usuarioList1NewUsuarioToAttach.getNickname());
                attachedUsuarioList1New.add(usuarioList1NewUsuarioToAttach);
            }
            usuarioList1New = attachedUsuarioList1New;
            usuario.setUsuarioList1(usuarioList1New);
            List<Registro> attachedRegistroListNew = new ArrayList<Registro>();
            for (Registro registroListNewRegistroToAttach : registroListNew) {
                registroListNewRegistroToAttach = em.getReference(registroListNewRegistroToAttach.getClass(), registroListNewRegistroToAttach.getRegistroPK());
                attachedRegistroListNew.add(registroListNewRegistroToAttach);
            }
            registroListNew = attachedRegistroListNew;
            usuario.setRegistroList(registroListNew);
            List<Reaccion> attachedReaccionListNew = new ArrayList<Reaccion>();
            for (Reaccion reaccionListNewReaccionToAttach : reaccionListNew) {
                reaccionListNewReaccionToAttach = em.getReference(reaccionListNewReaccionToAttach.getClass(), reaccionListNewReaccionToAttach.getReaccionPK());
                attachedReaccionListNew.add(reaccionListNewReaccionToAttach);
            }
            reaccionListNew = attachedReaccionListNew;
            usuario.setReaccionList(reaccionListNew);
            List<Playlist> attachedPlaylistListNew = new ArrayList<Playlist>();
            for (Playlist playlistListNewPlaylistToAttach : playlistListNew) {
                playlistListNewPlaylistToAttach = em.getReference(playlistListNewPlaylistToAttach.getClass(), playlistListNewPlaylistToAttach.getCodPlaylist());
                attachedPlaylistListNew.add(playlistListNewPlaylistToAttach);
            }
            playlistListNew = attachedPlaylistListNew;
            usuario.setPlaylistList(playlistListNew);
            usuario = em.merge(usuario);
            if (codPaisOld != null && !codPaisOld.equals(codPaisNew)) {
                codPaisOld.getUsuarioList().remove(usuario);
                codPaisOld = em.merge(codPaisOld);
            }
            if (codPaisNew != null && !codPaisNew.equals(codPaisOld)) {
                codPaisNew.getUsuarioList().add(usuario);
                codPaisNew = em.merge(codPaisNew);
            }
            if (codSuscripcionOld != null && !codSuscripcionOld.equals(codSuscripcionNew)) {
                codSuscripcionOld.getUsuarioList().remove(usuario);
                codSuscripcionOld = em.merge(codSuscripcionOld);
            }
            if (codSuscripcionNew != null && !codSuscripcionNew.equals(codSuscripcionOld)) {
                codSuscripcionNew.getUsuarioList().add(usuario);
                codSuscripcionNew = em.merge(codSuscripcionNew);
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getUsuarioList().remove(usuario);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getUsuarioList().add(usuario);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
            }
            for (Usuario usuarioList1OldUsuario : usuarioList1Old) {
                if (!usuarioList1New.contains(usuarioList1OldUsuario)) {
                    usuarioList1OldUsuario.getUsuarioList().remove(usuario);
                    usuarioList1OldUsuario = em.merge(usuarioList1OldUsuario);
                }
            }
            for (Usuario usuarioList1NewUsuario : usuarioList1New) {
                if (!usuarioList1Old.contains(usuarioList1NewUsuario)) {
                    usuarioList1NewUsuario.getUsuarioList().add(usuario);
                    usuarioList1NewUsuario = em.merge(usuarioList1NewUsuario);
                }
            }
            for (Registro registroListNewRegistro : registroListNew) {
                if (!registroListOld.contains(registroListNewRegistro)) {
                    Usuario oldUsuarioOfRegistroListNewRegistro = registroListNewRegistro.getUsuario();
                    registroListNewRegistro.setUsuario(usuario);
                    registroListNewRegistro = em.merge(registroListNewRegistro);
                    if (oldUsuarioOfRegistroListNewRegistro != null && !oldUsuarioOfRegistroListNewRegistro.equals(usuario)) {
                        oldUsuarioOfRegistroListNewRegistro.getRegistroList().remove(registroListNewRegistro);
                        oldUsuarioOfRegistroListNewRegistro = em.merge(oldUsuarioOfRegistroListNewRegistro);
                    }
                }
            }
            for (Reaccion reaccionListNewReaccion : reaccionListNew) {
                if (!reaccionListOld.contains(reaccionListNewReaccion)) {
                    Usuario oldUsuarioOfReaccionListNewReaccion = reaccionListNewReaccion.getUsuario();
                    reaccionListNewReaccion.setUsuario(usuario);
                    reaccionListNewReaccion = em.merge(reaccionListNewReaccion);
                    if (oldUsuarioOfReaccionListNewReaccion != null && !oldUsuarioOfReaccionListNewReaccion.equals(usuario)) {
                        oldUsuarioOfReaccionListNewReaccion.getReaccionList().remove(reaccionListNewReaccion);
                        oldUsuarioOfReaccionListNewReaccion = em.merge(oldUsuarioOfReaccionListNewReaccion);
                    }
                }
            }
            for (Playlist playlistListNewPlaylist : playlistListNew) {
                if (!playlistListOld.contains(playlistListNewPlaylist)) {
                    Usuario oldNicknameOfPlaylistListNewPlaylist = playlistListNewPlaylist.getNickname();
                    playlistListNewPlaylist.setNickname(usuario);
                    playlistListNewPlaylist = em.merge(playlistListNewPlaylist);
                    if (oldNicknameOfPlaylistListNewPlaylist != null && !oldNicknameOfPlaylistListNewPlaylist.equals(usuario)) {
                        oldNicknameOfPlaylistListNewPlaylist.getPlaylistList().remove(playlistListNewPlaylist);
                        oldNicknameOfPlaylistListNewPlaylist = em.merge(oldNicknameOfPlaylistListNewPlaylist);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getNickname();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getNickname();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Registro> registroListOrphanCheck = usuario.getRegistroList();
            for (Registro registroListOrphanCheckRegistro : registroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Registro " + registroListOrphanCheckRegistro + " in its registroList field has a non-nullable usuario field.");
            }
            List<Reaccion> reaccionListOrphanCheck = usuario.getReaccionList();
            for (Reaccion reaccionListOrphanCheckReaccion : reaccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Reaccion " + reaccionListOrphanCheckReaccion + " in its reaccionList field has a non-nullable usuario field.");
            }
            List<Playlist> playlistListOrphanCheck = usuario.getPlaylistList();
            for (Playlist playlistListOrphanCheckPlaylist : playlistListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Playlist " + playlistListOrphanCheckPlaylist + " in its playlistList field has a non-nullable nickname field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais codPais = usuario.getCodPais();
            if (codPais != null) {
                codPais.getUsuarioList().remove(usuario);
                codPais = em.merge(codPais);
            }
            Suscripcion codSuscripcion = usuario.getCodSuscripcion();
            if (codSuscripcion != null) {
                codSuscripcion.getUsuarioList().remove(usuario);
                codSuscripcion = em.merge(codSuscripcion);
            }
            List<Usuario> usuarioList = usuario.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getUsuarioList().remove(usuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            List<Usuario> usuarioList1 = usuario.getUsuarioList1();
            for (Usuario usuarioList1Usuario : usuarioList1) {
                usuarioList1Usuario.getUsuarioList().remove(usuario);
                usuarioList1Usuario = em.merge(usuarioList1Usuario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public boolean validarTarjeta(String tarjeta)
    {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT validarTarjeta(?) FROM DUAL");
        query.setParameter(1, tarjeta);
        String ret = (String) query.getSingleResult();
        if(ret.equals("S"))
        {
            return true;
        }
        else return false;
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
