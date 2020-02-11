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
import Entities.Album;
import Entities.Discografica;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sala BD
 */
public class DiscograficaJpaController implements Serializable {

    public DiscograficaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Discografica discografica) throws PreexistingEntityException, Exception {
        if (discografica.getAlbumList() == null) {
            discografica.setAlbumList(new ArrayList<Album>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Album> attachedAlbumList = new ArrayList<Album>();
            for (Album albumListAlbumToAttach : discografica.getAlbumList()) {
                albumListAlbumToAttach = em.getReference(albumListAlbumToAttach.getClass(), albumListAlbumToAttach.getCodAlbum());
                attachedAlbumList.add(albumListAlbumToAttach);
            }
            discografica.setAlbumList(attachedAlbumList);
            em.persist(discografica);
            for (Album albumListAlbum : discografica.getAlbumList()) {
                Discografica oldCodDiscograficaOfAlbumListAlbum = albumListAlbum.getCodDiscografica();
                albumListAlbum.setCodDiscografica(discografica);
                albumListAlbum = em.merge(albumListAlbum);
                if (oldCodDiscograficaOfAlbumListAlbum != null) {
                    oldCodDiscograficaOfAlbumListAlbum.getAlbumList().remove(albumListAlbum);
                    oldCodDiscograficaOfAlbumListAlbum = em.merge(oldCodDiscograficaOfAlbumListAlbum);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDiscografica(discografica.getCodDiscografica()) != null) {
                throw new PreexistingEntityException("Discografica " + discografica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Discografica discografica) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discografica persistentDiscografica = em.find(Discografica.class, discografica.getCodDiscografica());
            List<Album> albumListOld = persistentDiscografica.getAlbumList();
            List<Album> albumListNew = discografica.getAlbumList();
            List<String> illegalOrphanMessages = null;
            for (Album albumListOldAlbum : albumListOld) {
                if (!albumListNew.contains(albumListOldAlbum)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Album " + albumListOldAlbum + " since its codDiscografica field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Album> attachedAlbumListNew = new ArrayList<Album>();
            for (Album albumListNewAlbumToAttach : albumListNew) {
                albumListNewAlbumToAttach = em.getReference(albumListNewAlbumToAttach.getClass(), albumListNewAlbumToAttach.getCodAlbum());
                attachedAlbumListNew.add(albumListNewAlbumToAttach);
            }
            albumListNew = attachedAlbumListNew;
            discografica.setAlbumList(albumListNew);
            discografica = em.merge(discografica);
            for (Album albumListNewAlbum : albumListNew) {
                if (!albumListOld.contains(albumListNewAlbum)) {
                    Discografica oldCodDiscograficaOfAlbumListNewAlbum = albumListNewAlbum.getCodDiscografica();
                    albumListNewAlbum.setCodDiscografica(discografica);
                    albumListNewAlbum = em.merge(albumListNewAlbum);
                    if (oldCodDiscograficaOfAlbumListNewAlbum != null && !oldCodDiscograficaOfAlbumListNewAlbum.equals(discografica)) {
                        oldCodDiscograficaOfAlbumListNewAlbum.getAlbumList().remove(albumListNewAlbum);
                        oldCodDiscograficaOfAlbumListNewAlbum = em.merge(oldCodDiscograficaOfAlbumListNewAlbum);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = discografica.getCodDiscografica();
                if (findDiscografica(id) == null) {
                    throw new NonexistentEntityException("The discografica with id " + id + " no longer exists.");
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
            Discografica discografica;
            try {
                discografica = em.getReference(Discografica.class, id);
                discografica.getCodDiscografica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discografica with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Album> albumListOrphanCheck = discografica.getAlbumList();
            for (Album albumListOrphanCheckAlbum : albumListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Discografica (" + discografica + ") cannot be destroyed since the Album " + albumListOrphanCheckAlbum + " in its albumList field has a non-nullable codDiscografica field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(discografica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Discografica> findDiscograficaEntities() {
        return findDiscograficaEntities(true, -1, -1);
    }

    public List<Discografica> findDiscograficaEntities(int maxResults, int firstResult) {
        return findDiscograficaEntities(false, maxResults, firstResult);
    }

    private List<Discografica> findDiscograficaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Discografica.class));
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

    public Discografica findDiscografica(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Discografica.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiscograficaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Discografica> rt = cq.from(Discografica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
