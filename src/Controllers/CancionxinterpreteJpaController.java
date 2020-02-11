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
import Entities.Cancionxinterprete;
import Entities.CancionxinterpretePK;
import Entities.Interprete;
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
public class CancionxinterpreteJpaController implements Serializable {

    public CancionxinterpreteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancionxinterprete cancionxinterprete) throws PreexistingEntityException, Exception {
        if (cancionxinterprete.getCancionxinterpretePK() == null) {
            cancionxinterprete.setCancionxinterpretePK(new CancionxinterpretePK());
        }
        cancionxinterprete.getCancionxinterpretePK().setCodInterprete(cancionxinterprete.getInterprete().getCodInterprete());
        cancionxinterprete.getCancionxinterpretePK().setCodCancion(cancionxinterprete.getCancion().getCodCancion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion cancion = cancionxinterprete.getCancion();
            if (cancion != null) {
                cancion = em.getReference(cancion.getClass(), cancion.getCodCancion());
                cancionxinterprete.setCancion(cancion);
            }
            Interprete interprete = cancionxinterprete.getInterprete();
            if (interprete != null) {
                interprete = em.getReference(interprete.getClass(), interprete.getCodInterprete());
                cancionxinterprete.setInterprete(interprete);
            }
            em.persist(cancionxinterprete);
            if (cancion != null) {
                cancion.getCancionxinterpreteList().add(cancionxinterprete);
                cancion = em.merge(cancion);
            }
            if (interprete != null) {
                interprete.getCancionxinterpreteList().add(cancionxinterprete);
                interprete = em.merge(interprete);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCancionxinterprete(cancionxinterprete.getCancionxinterpretePK()) != null) {
                throw new PreexistingEntityException("Cancionxinterprete " + cancionxinterprete + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cancionxinterprete cancionxinterprete) throws NonexistentEntityException, Exception {
        cancionxinterprete.getCancionxinterpretePK().setCodInterprete(cancionxinterprete.getInterprete().getCodInterprete());
        cancionxinterprete.getCancionxinterpretePK().setCodCancion(cancionxinterprete.getCancion().getCodCancion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancionxinterprete persistentCancionxinterprete = em.find(Cancionxinterprete.class, cancionxinterprete.getCancionxinterpretePK());
            Cancion cancionOld = persistentCancionxinterprete.getCancion();
            Cancion cancionNew = cancionxinterprete.getCancion();
            Interprete interpreteOld = persistentCancionxinterprete.getInterprete();
            Interprete interpreteNew = cancionxinterprete.getInterprete();
            if (cancionNew != null) {
                cancionNew = em.getReference(cancionNew.getClass(), cancionNew.getCodCancion());
                cancionxinterprete.setCancion(cancionNew);
            }
            if (interpreteNew != null) {
                interpreteNew = em.getReference(interpreteNew.getClass(), interpreteNew.getCodInterprete());
                cancionxinterprete.setInterprete(interpreteNew);
            }
            cancionxinterprete = em.merge(cancionxinterprete);
            if (cancionOld != null && !cancionOld.equals(cancionNew)) {
                cancionOld.getCancionxinterpreteList().remove(cancionxinterprete);
                cancionOld = em.merge(cancionOld);
            }
            if (cancionNew != null && !cancionNew.equals(cancionOld)) {
                cancionNew.getCancionxinterpreteList().add(cancionxinterprete);
                cancionNew = em.merge(cancionNew);
            }
            if (interpreteOld != null && !interpreteOld.equals(interpreteNew)) {
                interpreteOld.getCancionxinterpreteList().remove(cancionxinterprete);
                interpreteOld = em.merge(interpreteOld);
            }
            if (interpreteNew != null && !interpreteNew.equals(interpreteOld)) {
                interpreteNew.getCancionxinterpreteList().add(cancionxinterprete);
                interpreteNew = em.merge(interpreteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CancionxinterpretePK id = cancionxinterprete.getCancionxinterpretePK();
                if (findCancionxinterprete(id) == null) {
                    throw new NonexistentEntityException("The cancionxinterprete with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CancionxinterpretePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancionxinterprete cancionxinterprete;
            try {
                cancionxinterprete = em.getReference(Cancionxinterprete.class, id);
                cancionxinterprete.getCancionxinterpretePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cancionxinterprete with id " + id + " no longer exists.", enfe);
            }
            Cancion cancion = cancionxinterprete.getCancion();
            if (cancion != null) {
                cancion.getCancionxinterpreteList().remove(cancionxinterprete);
                cancion = em.merge(cancion);
            }
            Interprete interprete = cancionxinterprete.getInterprete();
            if (interprete != null) {
                interprete.getCancionxinterpreteList().remove(cancionxinterprete);
                interprete = em.merge(interprete);
            }
            em.remove(cancionxinterprete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cancionxinterprete> findCancionxinterpreteEntities() {
        return findCancionxinterpreteEntities(true, -1, -1);
    }

    public List<Cancionxinterprete> findCancionxinterpreteEntities(int maxResults, int firstResult) {
        return findCancionxinterpreteEntities(false, maxResults, firstResult);
    }

    private List<Cancionxinterprete> findCancionxinterpreteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cancionxinterprete.class));
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

    public Cancionxinterprete findCancionxinterprete(CancionxinterpretePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cancionxinterprete.class, id);
        } finally {
            em.close();
        }
    }

    public int getCancionxinterpreteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cancionxinterprete> rt = cq.from(Cancionxinterprete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
}
