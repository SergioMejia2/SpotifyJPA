/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Familiar;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Suscripcion;
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
public class FamiliarJpaController implements Serializable {

    public FamiliarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Familiar familiar) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Suscripcion suscripcionOrphanCheck = familiar.getSuscripcion();
        if (suscripcionOrphanCheck != null) {
            Familiar oldFamiliarOfSuscripcion = suscripcionOrphanCheck.getFamiliar();
            if (oldFamiliarOfSuscripcion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Suscripcion " + suscripcionOrphanCheck + " already has an item of type Familiar whose suscripcion column cannot be null. Please make another selection for the suscripcion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suscripcion suscripcion = familiar.getSuscripcion();
            if (suscripcion != null) {
                suscripcion = em.getReference(suscripcion.getClass(), suscripcion.getCodSuscripcion());
                familiar.setSuscripcion(suscripcion);
            }
            em.persist(familiar);
            if (suscripcion != null) {
                suscripcion.setFamiliar(familiar);
                suscripcion = em.merge(suscripcion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFamiliar(familiar.getCodSuscripcion()) != null) {
                throw new PreexistingEntityException("Familiar " + familiar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Familiar familiar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familiar persistentFamiliar = em.find(Familiar.class, familiar.getCodSuscripcion());
            Suscripcion suscripcionOld = persistentFamiliar.getSuscripcion();
            Suscripcion suscripcionNew = familiar.getSuscripcion();
            List<String> illegalOrphanMessages = null;
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                Familiar oldFamiliarOfSuscripcion = suscripcionNew.getFamiliar();
                if (oldFamiliarOfSuscripcion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Suscripcion " + suscripcionNew + " already has an item of type Familiar whose suscripcion column cannot be null. Please make another selection for the suscripcion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (suscripcionNew != null) {
                suscripcionNew = em.getReference(suscripcionNew.getClass(), suscripcionNew.getCodSuscripcion());
                familiar.setSuscripcion(suscripcionNew);
            }
            familiar = em.merge(familiar);
            if (suscripcionOld != null && !suscripcionOld.equals(suscripcionNew)) {
                suscripcionOld.setFamiliar(null);
                suscripcionOld = em.merge(suscripcionOld);
            }
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                suscripcionNew.setFamiliar(familiar);
                suscripcionNew = em.merge(suscripcionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = familiar.getCodSuscripcion();
                if (findFamiliar(id) == null) {
                    throw new NonexistentEntityException("The familiar with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familiar familiar;
            try {
                familiar = em.getReference(Familiar.class, id);
                familiar.getCodSuscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The familiar with id " + id + " no longer exists.", enfe);
            }
            Suscripcion suscripcion = familiar.getSuscripcion();
            if (suscripcion != null) {
                suscripcion.setFamiliar(null);
                suscripcion = em.merge(suscripcion);
            }
            em.remove(familiar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Familiar> findFamiliarEntities() {
        return findFamiliarEntities(true, -1, -1);
    }

    public List<Familiar> findFamiliarEntities(int maxResults, int firstResult) {
        return findFamiliarEntities(false, maxResults, firstResult);
    }

    private List<Familiar> findFamiliarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Familiar.class));
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

    public Familiar findFamiliar(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Familiar.class, id);
        } finally {
            em.close();
        }
    }

    public int getFamiliarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Familiar> rt = cq.from(Familiar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
