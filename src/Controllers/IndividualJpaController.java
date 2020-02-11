/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Individual;
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
public class IndividualJpaController implements Serializable {

    public IndividualJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Individual individual) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Suscripcion suscripcionOrphanCheck = individual.getSuscripcion();
        if (suscripcionOrphanCheck != null) {
            Individual oldIndividualOfSuscripcion = suscripcionOrphanCheck.getIndividual();
            if (oldIndividualOfSuscripcion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Suscripcion " + suscripcionOrphanCheck + " already has an item of type Individual whose suscripcion column cannot be null. Please make another selection for the suscripcion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suscripcion suscripcion = individual.getSuscripcion();
            if (suscripcion != null) {
                suscripcion = em.getReference(suscripcion.getClass(), suscripcion.getCodSuscripcion());
                individual.setSuscripcion(suscripcion);
            }
            em.persist(individual);
            if (suscripcion != null) {
                suscripcion.setIndividual(individual);
                suscripcion = em.merge(suscripcion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIndividual(individual.getCodSuscripcion()) != null) {
                throw new PreexistingEntityException("Individual " + individual + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Individual individual) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Individual persistentIndividual = em.find(Individual.class, individual.getCodSuscripcion());
            Suscripcion suscripcionOld = persistentIndividual.getSuscripcion();
            Suscripcion suscripcionNew = individual.getSuscripcion();
            List<String> illegalOrphanMessages = null;
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                Individual oldIndividualOfSuscripcion = suscripcionNew.getIndividual();
                if (oldIndividualOfSuscripcion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Suscripcion " + suscripcionNew + " already has an item of type Individual whose suscripcion column cannot be null. Please make another selection for the suscripcion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (suscripcionNew != null) {
                suscripcionNew = em.getReference(suscripcionNew.getClass(), suscripcionNew.getCodSuscripcion());
                individual.setSuscripcion(suscripcionNew);
            }
            individual = em.merge(individual);
            if (suscripcionOld != null && !suscripcionOld.equals(suscripcionNew)) {
                suscripcionOld.setIndividual(null);
                suscripcionOld = em.merge(suscripcionOld);
            }
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                suscripcionNew.setIndividual(individual);
                suscripcionNew = em.merge(suscripcionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = individual.getCodSuscripcion();
                if (findIndividual(id) == null) {
                    throw new NonexistentEntityException("The individual with id " + id + " no longer exists.");
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
            Individual individual;
            try {
                individual = em.getReference(Individual.class, id);
                individual.getCodSuscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The individual with id " + id + " no longer exists.", enfe);
            }
            Suscripcion suscripcion = individual.getSuscripcion();
            if (suscripcion != null) {
                suscripcion.setIndividual(null);
                suscripcion = em.merge(suscripcion);
            }
            em.remove(individual);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Individual> findIndividualEntities() {
        return findIndividualEntities(true, -1, -1);
    }

    public List<Individual> findIndividualEntities(int maxResults, int firstResult) {
        return findIndividualEntities(false, maxResults, firstResult);
    }

    private List<Individual> findIndividualEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Individual.class));
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

    public Individual findIndividual(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Individual.class, id);
        } finally {
            em.close();
        }
    }

    public int getIndividualCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Individual> rt = cq.from(Individual.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
