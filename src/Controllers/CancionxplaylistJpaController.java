/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Cancion;
import Entities.Cancionxplaylist;
import Entities.CancionxplaylistPK;
import Entities.Playlist;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sala BD
 */
public class CancionxplaylistJpaController implements Serializable {

    public CancionxplaylistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancionxplaylist cancionxplaylist) throws PreexistingEntityException, Exception {
        if (cancionxplaylist.getCancionxplaylistPK() == null) {
            cancionxplaylist.setCancionxplaylistPK(new CancionxplaylistPK());
        }
        cancionxplaylist.getCancionxplaylistPK().setCodPlaylist(cancionxplaylist.getPlaylist().getCodPlaylist());
        cancionxplaylist.getCancionxplaylistPK().setCodCancion(cancionxplaylist.getCancion().getCodCancion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion cancion = cancionxplaylist.getCancion();
            if (cancion != null) {
                cancion = em.getReference(cancion.getClass(), cancion.getCodCancion());
                cancionxplaylist.setCancion(cancion);
            }
            Playlist playlist = cancionxplaylist.getPlaylist();
            if (playlist != null) {
                playlist = em.getReference(playlist.getClass(), playlist.getCodPlaylist());
                cancionxplaylist.setPlaylist(playlist);
            }
            em.persist(cancionxplaylist);
            if (cancion != null) {
                cancion.getCancionxplaylistList().add(cancionxplaylist);
                cancion = em.merge(cancion);
            }
            if (playlist != null) {
                playlist.getCancionxplaylistList().add(cancionxplaylist);
                playlist = em.merge(playlist);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCancionxplaylist(cancionxplaylist.getCancionxplaylistPK()) != null) {
                throw new PreexistingEntityException("Cancionxplaylist " + cancionxplaylist + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cancionxplaylist cancionxplaylist) throws NonexistentEntityException, Exception {
        cancionxplaylist.getCancionxplaylistPK().setCodPlaylist(cancionxplaylist.getPlaylist().getCodPlaylist());
        cancionxplaylist.getCancionxplaylistPK().setCodCancion(cancionxplaylist.getCancion().getCodCancion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancionxplaylist persistentCancionxplaylist = em.find(Cancionxplaylist.class, cancionxplaylist.getCancionxplaylistPK());
            Cancion cancionOld = persistentCancionxplaylist.getCancion();
            Cancion cancionNew = cancionxplaylist.getCancion();
            Playlist playlistOld = persistentCancionxplaylist.getPlaylist();
            Playlist playlistNew = cancionxplaylist.getPlaylist();
            if (cancionNew != null) {
                cancionNew = em.getReference(cancionNew.getClass(), cancionNew.getCodCancion());
                cancionxplaylist.setCancion(cancionNew);
            }
            if (playlistNew != null) {
                playlistNew = em.getReference(playlistNew.getClass(), playlistNew.getCodPlaylist());
                cancionxplaylist.setPlaylist(playlistNew);
            }
            cancionxplaylist = em.merge(cancionxplaylist);
            if (cancionOld != null && !cancionOld.equals(cancionNew)) {
                cancionOld.getCancionxplaylistList().remove(cancionxplaylist);
                cancionOld = em.merge(cancionOld);
            }
            if (cancionNew != null && !cancionNew.equals(cancionOld)) {
                cancionNew.getCancionxplaylistList().add(cancionxplaylist);
                cancionNew = em.merge(cancionNew);
            }
            if (playlistOld != null && !playlistOld.equals(playlistNew)) {
                playlistOld.getCancionxplaylistList().remove(cancionxplaylist);
                playlistOld = em.merge(playlistOld);
            }
            if (playlistNew != null && !playlistNew.equals(playlistOld)) {
                playlistNew.getCancionxplaylistList().add(cancionxplaylist);
                playlistNew = em.merge(playlistNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CancionxplaylistPK id = cancionxplaylist.getCancionxplaylistPK();
                if (findCancionxplaylist(id) == null) {
                    throw new NonexistentEntityException("The cancionxplaylist with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CancionxplaylistPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancionxplaylist cancionxplaylist;
            try {
                cancionxplaylist = em.getReference(Cancionxplaylist.class, id);
                cancionxplaylist.getCancionxplaylistPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cancionxplaylist with id " + id + " no longer exists.", enfe);
            }
            Cancion cancion = cancionxplaylist.getCancion();
            if (cancion != null) {
                cancion.getCancionxplaylistList().remove(cancionxplaylist);
                cancion = em.merge(cancion);
            }
            Playlist playlist = cancionxplaylist.getPlaylist();
            if (playlist != null) {
                playlist.getCancionxplaylistList().remove(cancionxplaylist);
                playlist = em.merge(playlist);
            }
            em.remove(cancionxplaylist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cancionxplaylist> findCancionxplaylistEntities() {
        return findCancionxplaylistEntities(true, -1, -1);
    }

    public List<Cancionxplaylist> findCancionxplaylistEntities(int maxResults, int firstResult) {
        return findCancionxplaylistEntities(false, maxResults, firstResult);
    }

    private List<Cancionxplaylist> findCancionxplaylistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cancionxplaylist.class));
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

    public Cancionxplaylist findCancionxplaylist(CancionxplaylistPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cancionxplaylist.class, id);
        } finally {
            em.close();
        }
    }

    public int getCancionxplaylistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cancionxplaylist> rt = cq.from(Cancionxplaylist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public BigInteger findLastOrder(Playlist playlist)
    {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT nvl(MAX(orden),0) FROM cancionxplaylist WHERE cod_playlist = ?");
        query.setParameter(1, playlist.getCodPlaylist());
        BigInteger order = ((BigDecimal)query.getSingleResult()).toBigInteger();
        return order;
    }

    public List<Cancionxplaylist> getCancionxplaylistCount(Playlist playlist)
    {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT cxp.* FROM cancionxplaylist cxp WHERE cod_playlist = ?", Cancionxplaylist.class);
        query.setParameter(1, playlist.getCodPlaylist());
        List<Cancionxplaylist> lista_cxp = query.getResultList();
        return lista_cxp;
    }

    
    public List<BigInteger> getOrdenes(Playlist playlist)
    {
        System.out.println(playlist.getCodPlaylist());
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT orden FROM cancionxplaylist cxp WHERE cod_playlist = ? ORDER BY orden");
        q.setParameter(1, playlist.getCodPlaylist());
        List<BigDecimal> result = q.getResultList();
        List<BigInteger> retorno = new ArrayList<BigInteger>();

        for (int i = 0; i < result.size(); i++)
        {
            BigInteger bi = result.get(i).toBigInteger();
            retorno.add(bi);
        }
        return retorno;
    }

    public void cambiarOrden(HashMap<BigInteger, Cancion> mapa, Playlist playlist) throws Exception
    {
      EntityManager em = getEntityManager();
        for (Map.Entry<BigInteger, Cancion> entry : mapa.entrySet())
        {
            BigInteger key = entry.getKey();
            Cancion value = entry.getValue();
            CancionxplaylistPK cxpPk = new CancionxplaylistPK(value.getCodCancion(), playlist.getCodPlaylist());
            Cancionxplaylist cancion = em.find(Cancionxplaylist.class, cxpPk);
            if(cancion != null)
            {
                cancion.setOrden(key);
                edit(cancion);
            }
        }
      
    }
    
}
