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
import Entities.Usuario;
import Entities.Cancionxplaylist;
import Entities.Playlist;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sala BD
 */
public class PlaylistJpaController implements Serializable {

    public PlaylistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Playlist playlist) throws PreexistingEntityException, Exception {
        if (playlist.getCancionxplaylistList() == null) {
            playlist.setCancionxplaylistList(new ArrayList<Cancionxplaylist>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario nickname = playlist.getNickname();
            if (nickname != null) {
                nickname = em.getReference(nickname.getClass(), nickname.getNickname());
                playlist.setNickname(nickname);
            }
            List<Cancionxplaylist> attachedCancionxplaylistList = new ArrayList<Cancionxplaylist>();
            for (Cancionxplaylist cancionxplaylistListCancionxplaylistToAttach : playlist.getCancionxplaylistList()) {
                cancionxplaylistListCancionxplaylistToAttach = em.getReference(cancionxplaylistListCancionxplaylistToAttach.getClass(), cancionxplaylistListCancionxplaylistToAttach.getCancionxplaylistPK());
                attachedCancionxplaylistList.add(cancionxplaylistListCancionxplaylistToAttach);
            }
            playlist.setCancionxplaylistList(attachedCancionxplaylistList);
            em.persist(playlist);
            if (nickname != null) {
                nickname.getPlaylistList().add(playlist);
                nickname = em.merge(nickname);
            }
            for (Cancionxplaylist cancionxplaylistListCancionxplaylist : playlist.getCancionxplaylistList()) {
                Playlist oldPlaylistOfCancionxplaylistListCancionxplaylist = cancionxplaylistListCancionxplaylist.getPlaylist();
                cancionxplaylistListCancionxplaylist.setPlaylist(playlist);
                cancionxplaylistListCancionxplaylist = em.merge(cancionxplaylistListCancionxplaylist);
                if (oldPlaylistOfCancionxplaylistListCancionxplaylist != null) {
                    oldPlaylistOfCancionxplaylistListCancionxplaylist.getCancionxplaylistList().remove(cancionxplaylistListCancionxplaylist);
                    oldPlaylistOfCancionxplaylistListCancionxplaylist = em.merge(oldPlaylistOfCancionxplaylistListCancionxplaylist);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlaylist(playlist.getCodPlaylist()) != null) {
                throw new PreexistingEntityException("Playlist " + playlist + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Playlist playlist) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Playlist persistentPlaylist = em.find(Playlist.class, playlist.getCodPlaylist());
            Usuario nicknameOld = persistentPlaylist.getNickname();
            Usuario nicknameNew = playlist.getNickname();
            List<Cancionxplaylist> cancionxplaylistListOld = persistentPlaylist.getCancionxplaylistList();
            List<Cancionxplaylist> cancionxplaylistListNew = playlist.getCancionxplaylistList();
            List<String> illegalOrphanMessages = null;
            for (Cancionxplaylist cancionxplaylistListOldCancionxplaylist : cancionxplaylistListOld) {
                if (!cancionxplaylistListNew.contains(cancionxplaylistListOldCancionxplaylist)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxplaylist " + cancionxplaylistListOldCancionxplaylist + " since its playlist field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (nicknameNew != null) {
                nicknameNew = em.getReference(nicknameNew.getClass(), nicknameNew.getNickname());
                playlist.setNickname(nicknameNew);
            }
            List<Cancionxplaylist> attachedCancionxplaylistListNew = new ArrayList<Cancionxplaylist>();
            for (Cancionxplaylist cancionxplaylistListNewCancionxplaylistToAttach : cancionxplaylistListNew) {
                cancionxplaylistListNewCancionxplaylistToAttach = em.getReference(cancionxplaylistListNewCancionxplaylistToAttach.getClass(), cancionxplaylistListNewCancionxplaylistToAttach.getCancionxplaylistPK());
                attachedCancionxplaylistListNew.add(cancionxplaylistListNewCancionxplaylistToAttach);
            }
            cancionxplaylistListNew = attachedCancionxplaylistListNew;
            playlist.setCancionxplaylistList(cancionxplaylistListNew);
            playlist = em.merge(playlist);
            if (nicknameOld != null && !nicknameOld.equals(nicknameNew)) {
                nicknameOld.getPlaylistList().remove(playlist);
                nicknameOld = em.merge(nicknameOld);
            }
            if (nicknameNew != null && !nicknameNew.equals(nicknameOld)) {
                nicknameNew.getPlaylistList().add(playlist);
                nicknameNew = em.merge(nicknameNew);
            }
            for (Cancionxplaylist cancionxplaylistListNewCancionxplaylist : cancionxplaylistListNew) {
                if (!cancionxplaylistListOld.contains(cancionxplaylistListNewCancionxplaylist)) {
                    Playlist oldPlaylistOfCancionxplaylistListNewCancionxplaylist = cancionxplaylistListNewCancionxplaylist.getPlaylist();
                    cancionxplaylistListNewCancionxplaylist.setPlaylist(playlist);
                    cancionxplaylistListNewCancionxplaylist = em.merge(cancionxplaylistListNewCancionxplaylist);
                    if (oldPlaylistOfCancionxplaylistListNewCancionxplaylist != null && !oldPlaylistOfCancionxplaylistListNewCancionxplaylist.equals(playlist)) {
                        oldPlaylistOfCancionxplaylistListNewCancionxplaylist.getCancionxplaylistList().remove(cancionxplaylistListNewCancionxplaylist);
                        oldPlaylistOfCancionxplaylistListNewCancionxplaylist = em.merge(oldPlaylistOfCancionxplaylistListNewCancionxplaylist);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = playlist.getCodPlaylist();
                if (findPlaylist(id) == null) {
                    throw new NonexistentEntityException("The playlist with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Playlist playlist;
            try {
                playlist = em.getReference(Playlist.class, id);
                playlist.getCodPlaylist();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The playlist with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancionxplaylist> cancionxplaylistListOrphanCheck = playlist.getCancionxplaylistList();
            for (Cancionxplaylist cancionxplaylistListOrphanCheckCancionxplaylist : cancionxplaylistListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Playlist (" + playlist + ") cannot be destroyed since the Cancionxplaylist " + cancionxplaylistListOrphanCheckCancionxplaylist + " in its cancionxplaylistList field has a non-nullable playlist field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario nickname = playlist.getNickname();
            if (nickname != null) {
                nickname.getPlaylistList().remove(playlist);
                nickname = em.merge(nickname);
            }
            em.remove(playlist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Playlist> findPlaylistEntities() {
        return findPlaylistEntities(true, -1, -1);
    }

    public List<Playlist> findPlaylistEntities(int maxResults, int firstResult) {
        return findPlaylistEntities(false, maxResults, firstResult);
    }

    private List<Playlist> findPlaylistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Playlist.class));
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

    public Playlist findPlaylist(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Playlist.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlaylistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Playlist> rt = cq.from(Playlist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Playlist> findPlaylistUsuario(Usuario user)
    {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT P.* FROM Playlist P where nickname = ?", Playlist.class);
        q.setParameter(1, user.getNickname());
        List<Playlist> playlists = q.getResultList();
        return playlists;
    }

    public BigInteger getDuracionPlaylist(Playlist playlist)
    {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT SUM(tiempo_ReprM)*60 + SUM(tiempo_ReprS) "
                + "                     FROM ((Playlist natural join CancionXPlaylist) natural join Cancion)"
                + "                     where cod_playlist = ?");
        q.setParameter(1, playlist.getCodPlaylist());
        BigInteger tiempoEnSec = ((BigDecimal)q.getSingleResult()).toBigInteger();
        return tiempoEnSec;
    }

    public void cambiarNombre(Playlist playlist, String nombreP) throws Exception
    {
        EntityManager em = getEntityManager();
        Playlist p = em.find(Playlist.class, playlist.getCodPlaylist());
        if (p != null)
        {
            p.setNombrePlaylist(nombreP);
            playlist.setNombrePlaylist(nombreP);
            edit(p);
        }
    }
    
}
