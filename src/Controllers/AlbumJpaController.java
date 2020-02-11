/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Album;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Discografica;
import Entities.Cancion;
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
public class AlbumJpaController implements Serializable {

    public AlbumJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Album album) throws PreexistingEntityException, Exception {
        if (album.getCancionList() == null) {
            album.setCancionList(new ArrayList<Cancion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discografica codDiscografica = album.getCodDiscografica();
            if (codDiscografica != null) {
                codDiscografica = em.getReference(codDiscografica.getClass(), codDiscografica.getCodDiscografica());
                album.setCodDiscografica(codDiscografica);
            }
            List<Cancion> attachedCancionList = new ArrayList<Cancion>();
            for (Cancion cancionListCancionToAttach : album.getCancionList()) {
                cancionListCancionToAttach = em.getReference(cancionListCancionToAttach.getClass(), cancionListCancionToAttach.getCodCancion());
                attachedCancionList.add(cancionListCancionToAttach);
            }
            album.setCancionList(attachedCancionList);
            em.persist(album);
            if (codDiscografica != null) {
                codDiscografica.getAlbumList().add(album);
                codDiscografica = em.merge(codDiscografica);
            }
            for (Cancion cancionListCancion : album.getCancionList()) {
                Album oldCodAlbumOfCancionListCancion = cancionListCancion.getCodAlbum();
                cancionListCancion.setCodAlbum(album);
                cancionListCancion = em.merge(cancionListCancion);
                if (oldCodAlbumOfCancionListCancion != null) {
                    oldCodAlbumOfCancionListCancion.getCancionList().remove(cancionListCancion);
                    oldCodAlbumOfCancionListCancion = em.merge(oldCodAlbumOfCancionListCancion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlbum(album.getCodAlbum()) != null) {
                throw new PreexistingEntityException("Album " + album + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Album album) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album persistentAlbum = em.find(Album.class, album.getCodAlbum());
            Discografica codDiscograficaOld = persistentAlbum.getCodDiscografica();
            Discografica codDiscograficaNew = album.getCodDiscografica();
            List<Cancion> cancionListOld = persistentAlbum.getCancionList();
            List<Cancion> cancionListNew = album.getCancionList();
            List<String> illegalOrphanMessages = null;
            for (Cancion cancionListOldCancion : cancionListOld) {
                if (!cancionListNew.contains(cancionListOldCancion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancion " + cancionListOldCancion + " since its codAlbum field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codDiscograficaNew != null) {
                codDiscograficaNew = em.getReference(codDiscograficaNew.getClass(), codDiscograficaNew.getCodDiscografica());
                album.setCodDiscografica(codDiscograficaNew);
            }
            List<Cancion> attachedCancionListNew = new ArrayList<Cancion>();
            for (Cancion cancionListNewCancionToAttach : cancionListNew) {
                cancionListNewCancionToAttach = em.getReference(cancionListNewCancionToAttach.getClass(), cancionListNewCancionToAttach.getCodCancion());
                attachedCancionListNew.add(cancionListNewCancionToAttach);
            }
            cancionListNew = attachedCancionListNew;
            album.setCancionList(cancionListNew);
            album = em.merge(album);
            if (codDiscograficaOld != null && !codDiscograficaOld.equals(codDiscograficaNew)) {
                codDiscograficaOld.getAlbumList().remove(album);
                codDiscograficaOld = em.merge(codDiscograficaOld);
            }
            if (codDiscograficaNew != null && !codDiscograficaNew.equals(codDiscograficaOld)) {
                codDiscograficaNew.getAlbumList().add(album);
                codDiscograficaNew = em.merge(codDiscograficaNew);
            }
            for (Cancion cancionListNewCancion : cancionListNew) {
                if (!cancionListOld.contains(cancionListNewCancion)) {
                    Album oldCodAlbumOfCancionListNewCancion = cancionListNewCancion.getCodAlbum();
                    cancionListNewCancion.setCodAlbum(album);
                    cancionListNewCancion = em.merge(cancionListNewCancion);
                    if (oldCodAlbumOfCancionListNewCancion != null && !oldCodAlbumOfCancionListNewCancion.equals(album)) {
                        oldCodAlbumOfCancionListNewCancion.getCancionList().remove(cancionListNewCancion);
                        oldCodAlbumOfCancionListNewCancion = em.merge(oldCodAlbumOfCancionListNewCancion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = album.getCodAlbum();
                if (findAlbum(id) == null) {
                    throw new NonexistentEntityException("The album with id " + id + " no longer exists.");
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
            Album album;
            try {
                album = em.getReference(Album.class, id);
                album.getCodAlbum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The album with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancion> cancionListOrphanCheck = album.getCancionList();
            for (Cancion cancionListOrphanCheckCancion : cancionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Album (" + album + ") cannot be destroyed since the Cancion " + cancionListOrphanCheckCancion + " in its cancionList field has a non-nullable codAlbum field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Discografica codDiscografica = album.getCodDiscografica();
            if (codDiscografica != null) {
                codDiscografica.getAlbumList().remove(album);
                codDiscografica = em.merge(codDiscografica);
            }
            em.remove(album);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Album> findAlbumEntities() {
        return findAlbumEntities(true, -1, -1);
    }

    public List<Album> findAlbumEntities(int maxResults, int firstResult) {
        return findAlbumEntities(false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Album.class));
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

    public Album findAlbum(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Album.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlbumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Album> rt = cq.from(Album.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Album getUltimaCancion()
    {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT max (a.cod_album) FROM Album a");
        BigInteger ret = ((BigDecimal) (q.getSingleResult())).toBigInteger();
        System.out.println("!!!!!!!!!!!!!!!!!!"+ret);
        Album al = findAlbum(ret);
        em.close();
        return al;
    }
    
}
