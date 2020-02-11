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
import Entities.Cancionxinterprete;
import Entities.Interprete;
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
public class InterpreteJpaController implements Serializable {

    public InterpreteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Interprete interprete) throws PreexistingEntityException, Exception {
        if (interprete.getCancionxinterpreteList() == null) {
            interprete.setCancionxinterpreteList(new ArrayList<Cancionxinterprete>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais codPais = interprete.getCodPais();
            if (codPais != null) {
                codPais = em.getReference(codPais.getClass(), codPais.getCodPais());
                interprete.setCodPais(codPais);
            }
            List<Cancionxinterprete> attachedCancionxinterpreteList = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteListCancionxinterpreteToAttach : interprete.getCancionxinterpreteList()) {
                cancionxinterpreteListCancionxinterpreteToAttach = em.getReference(cancionxinterpreteListCancionxinterpreteToAttach.getClass(), cancionxinterpreteListCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteList.add(cancionxinterpreteListCancionxinterpreteToAttach);
            }
            interprete.setCancionxinterpreteList(attachedCancionxinterpreteList);
            em.persist(interprete);
            if (codPais != null) {
                codPais.getInterpreteList().add(interprete);
                codPais = em.merge(codPais);
            }
            for (Cancionxinterprete cancionxinterpreteListCancionxinterprete : interprete.getCancionxinterpreteList()) {
                Interprete oldInterpreteOfCancionxinterpreteListCancionxinterprete = cancionxinterpreteListCancionxinterprete.getInterprete();
                cancionxinterpreteListCancionxinterprete.setInterprete(interprete);
                cancionxinterpreteListCancionxinterprete = em.merge(cancionxinterpreteListCancionxinterprete);
                if (oldInterpreteOfCancionxinterpreteListCancionxinterprete != null) {
                    oldInterpreteOfCancionxinterpreteListCancionxinterprete.getCancionxinterpreteList().remove(cancionxinterpreteListCancionxinterprete);
                    oldInterpreteOfCancionxinterpreteListCancionxinterprete = em.merge(oldInterpreteOfCancionxinterpreteListCancionxinterprete);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInterprete(interprete.getCodInterprete()) != null) {
                throw new PreexistingEntityException("Interprete " + interprete + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Interprete interprete) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interprete persistentInterprete = em.find(Interprete.class, interprete.getCodInterprete());
            Pais codPaisOld = persistentInterprete.getCodPais();
            Pais codPaisNew = interprete.getCodPais();
            List<Cancionxinterprete> cancionxinterpreteListOld = persistentInterprete.getCancionxinterpreteList();
            List<Cancionxinterprete> cancionxinterpreteListNew = interprete.getCancionxinterpreteList();
            List<String> illegalOrphanMessages = null;
            for (Cancionxinterprete cancionxinterpreteListOldCancionxinterprete : cancionxinterpreteListOld) {
                if (!cancionxinterpreteListNew.contains(cancionxinterpreteListOldCancionxinterprete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxinterprete " + cancionxinterpreteListOldCancionxinterprete + " since its interprete field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codPaisNew != null) {
                codPaisNew = em.getReference(codPaisNew.getClass(), codPaisNew.getCodPais());
                interprete.setCodPais(codPaisNew);
            }
            List<Cancionxinterprete> attachedCancionxinterpreteListNew = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteListNewCancionxinterpreteToAttach : cancionxinterpreteListNew) {
                cancionxinterpreteListNewCancionxinterpreteToAttach = em.getReference(cancionxinterpreteListNewCancionxinterpreteToAttach.getClass(), cancionxinterpreteListNewCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteListNew.add(cancionxinterpreteListNewCancionxinterpreteToAttach);
            }
            cancionxinterpreteListNew = attachedCancionxinterpreteListNew;
            interprete.setCancionxinterpreteList(cancionxinterpreteListNew);
            interprete = em.merge(interprete);
            if (codPaisOld != null && !codPaisOld.equals(codPaisNew)) {
                codPaisOld.getInterpreteList().remove(interprete);
                codPaisOld = em.merge(codPaisOld);
            }
            if (codPaisNew != null && !codPaisNew.equals(codPaisOld)) {
                codPaisNew.getInterpreteList().add(interprete);
                codPaisNew = em.merge(codPaisNew);
            }
            for (Cancionxinterprete cancionxinterpreteListNewCancionxinterprete : cancionxinterpreteListNew) {
                if (!cancionxinterpreteListOld.contains(cancionxinterpreteListNewCancionxinterprete)) {
                    Interprete oldInterpreteOfCancionxinterpreteListNewCancionxinterprete = cancionxinterpreteListNewCancionxinterprete.getInterprete();
                    cancionxinterpreteListNewCancionxinterprete.setInterprete(interprete);
                    cancionxinterpreteListNewCancionxinterprete = em.merge(cancionxinterpreteListNewCancionxinterprete);
                    if (oldInterpreteOfCancionxinterpreteListNewCancionxinterprete != null && !oldInterpreteOfCancionxinterpreteListNewCancionxinterprete.equals(interprete)) {
                        oldInterpreteOfCancionxinterpreteListNewCancionxinterprete.getCancionxinterpreteList().remove(cancionxinterpreteListNewCancionxinterprete);
                        oldInterpreteOfCancionxinterpreteListNewCancionxinterprete = em.merge(oldInterpreteOfCancionxinterpreteListNewCancionxinterprete);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = interprete.getCodInterprete();
                if (findInterprete(id) == null) {
                    throw new NonexistentEntityException("The interprete with id " + id + " no longer exists.");
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
            Interprete interprete;
            try {
                interprete = em.getReference(Interprete.class, id);
                interprete.getCodInterprete();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interprete with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancionxinterprete> cancionxinterpreteListOrphanCheck = interprete.getCancionxinterpreteList();
            for (Cancionxinterprete cancionxinterpreteListOrphanCheckCancionxinterprete : cancionxinterpreteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Interprete (" + interprete + ") cannot be destroyed since the Cancionxinterprete " + cancionxinterpreteListOrphanCheckCancionxinterprete + " in its cancionxinterpreteList field has a non-nullable interprete field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais codPais = interprete.getCodPais();
            if (codPais != null) {
                codPais.getInterpreteList().remove(interprete);
                codPais = em.merge(codPais);
            }
            em.remove(interprete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Interprete> findInterpreteEntities() {
        return findInterpreteEntities(true, -1, -1);
    }

    public List<Interprete> findInterpreteEntities(int maxResults, int firstResult) {
        return findInterpreteEntities(false, maxResults, firstResult);
    }

    private List<Interprete> findInterpreteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Interprete.class));
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

    public Interprete findInterprete(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Interprete.class, id);
        } finally {
            em.close();
        }
    }

    public int getInterpreteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Interprete> rt = cq.from(Interprete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
