/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Gratuita;
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
public class GratuitaJpaController implements Serializable {

    public GratuitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gratuita gratuita) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Suscripcion suscripcionOrphanCheck = gratuita.getSuscripcion();
        if (suscripcionOrphanCheck != null) {
            Gratuita oldGratuitaOfSuscripcion = suscripcionOrphanCheck.getGratuita();
            if (oldGratuitaOfSuscripcion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Suscripcion " + suscripcionOrphanCheck + " already has an item of type Gratuita whose suscripcion column cannot be null. Please make another selection for the suscripcion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suscripcion suscripcion = gratuita.getSuscripcion();
            if (suscripcion != null) {
                suscripcion = em.getReference(suscripcion.getClass(), suscripcion.getCodSuscripcion());
                gratuita.setSuscripcion(suscripcion);
            }
            em.persist(gratuita);
            if (suscripcion != null) {
                suscripcion.setGratuita(gratuita);
                suscripcion = em.merge(suscripcion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGratuita(gratuita.getCodSuscripcion()) != null) {
                throw new PreexistingEntityException("Gratuita " + gratuita + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gratuita gratuita) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gratuita persistentGratuita = em.find(Gratuita.class, gratuita.getCodSuscripcion());
            Suscripcion suscripcionOld = persistentGratuita.getSuscripcion();
            Suscripcion suscripcionNew = gratuita.getSuscripcion();
            List<String> illegalOrphanMessages = null;
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                Gratuita oldGratuitaOfSuscripcion = suscripcionNew.getGratuita();
                if (oldGratuitaOfSuscripcion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Suscripcion " + suscripcionNew + " already has an item of type Gratuita whose suscripcion column cannot be null. Please make another selection for the suscripcion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (suscripcionNew != null) {
                suscripcionNew = em.getReference(suscripcionNew.getClass(), suscripcionNew.getCodSuscripcion());
                gratuita.setSuscripcion(suscripcionNew);
            }
            gratuita = em.merge(gratuita);
            if (suscripcionOld != null && !suscripcionOld.equals(suscripcionNew)) {
                suscripcionOld.setGratuita(null);
                suscripcionOld = em.merge(suscripcionOld);
            }
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                suscripcionNew.setGratuita(gratuita);
                suscripcionNew = em.merge(suscripcionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = gratuita.getCodSuscripcion();
                if (findGratuita(id) == null) {
                    throw new NonexistentEntityException("The gratuita with id " + id + " no longer exists.");
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
            Gratuita gratuita;
            try {
                gratuita = em.getReference(Gratuita.class, id);
                gratuita.getCodSuscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gratuita with id " + id + " no longer exists.", enfe);
            }
            Suscripcion suscripcion = gratuita.getSuscripcion();
            if (suscripcion != null) {
                suscripcion.setGratuita(null);
                suscripcion = em.merge(suscripcion);
            }
            em.remove(gratuita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gratuita> findGratuitaEntities() {
        return findGratuitaEntities(true, -1, -1);
    }

    public List<Gratuita> findGratuitaEntities(int maxResults, int firstResult) {
        return findGratuitaEntities(false, maxResults, firstResult);
    }

    private List<Gratuita> findGratuitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gratuita.class));
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

    public Gratuita findGratuita(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gratuita.class, id);
        } finally {
            em.close();
        }
    }

    public int getGratuitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gratuita> rt = cq.from(Gratuita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
