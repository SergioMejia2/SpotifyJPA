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
import Entities.Familiar;
import Entities.Gratuita;
import Entities.Individual;
import Entities.Suscripcion;
import Entities.Usuario;
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
public class SuscripcionJpaController implements Serializable {

    public SuscripcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Suscripcion suscripcion) throws PreexistingEntityException, Exception {
        if (suscripcion.getUsuarioList() == null) {
            suscripcion.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familiar familiar = suscripcion.getFamiliar();
            if (familiar != null) {
                familiar = em.getReference(familiar.getClass(), familiar.getCodSuscripcion());
                suscripcion.setFamiliar(familiar);
            }
            Gratuita gratuita = suscripcion.getGratuita();
            if (gratuita != null) {
                gratuita = em.getReference(gratuita.getClass(), gratuita.getCodSuscripcion());
                suscripcion.setGratuita(gratuita);
            }
            Individual individual = suscripcion.getIndividual();
            if (individual != null) {
                individual = em.getReference(individual.getClass(), individual.getCodSuscripcion());
                suscripcion.setIndividual(individual);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : suscripcion.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getNickname());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            suscripcion.setUsuarioList(attachedUsuarioList);
            em.persist(suscripcion);
            if (familiar != null) {
                Suscripcion oldSuscripcionOfFamiliar = familiar.getSuscripcion();
                if (oldSuscripcionOfFamiliar != null) {
                    oldSuscripcionOfFamiliar.setFamiliar(null);
                    oldSuscripcionOfFamiliar = em.merge(oldSuscripcionOfFamiliar);
                }
                familiar.setSuscripcion(suscripcion);
                familiar = em.merge(familiar);
            }
            if (gratuita != null) {
                Suscripcion oldSuscripcionOfGratuita = gratuita.getSuscripcion();
                if (oldSuscripcionOfGratuita != null) {
                    oldSuscripcionOfGratuita.setGratuita(null);
                    oldSuscripcionOfGratuita = em.merge(oldSuscripcionOfGratuita);
                }
                gratuita.setSuscripcion(suscripcion);
                gratuita = em.merge(gratuita);
            }
            if (individual != null) {
                Suscripcion oldSuscripcionOfIndividual = individual.getSuscripcion();
                if (oldSuscripcionOfIndividual != null) {
                    oldSuscripcionOfIndividual.setIndividual(null);
                    oldSuscripcionOfIndividual = em.merge(oldSuscripcionOfIndividual);
                }
                individual.setSuscripcion(suscripcion);
                individual = em.merge(individual);
            }
            for (Usuario usuarioListUsuario : suscripcion.getUsuarioList()) {
                Suscripcion oldCodSuscripcionOfUsuarioListUsuario = usuarioListUsuario.getCodSuscripcion();
                usuarioListUsuario.setCodSuscripcion(suscripcion);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldCodSuscripcionOfUsuarioListUsuario != null) {
                    oldCodSuscripcionOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldCodSuscripcionOfUsuarioListUsuario = em.merge(oldCodSuscripcionOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuscripcion(suscripcion.getCodSuscripcion()) != null) {
                throw new PreexistingEntityException("Suscripcion " + suscripcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Suscripcion suscripcion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suscripcion persistentSuscripcion = em.find(Suscripcion.class, suscripcion.getCodSuscripcion());
            Familiar familiarOld = persistentSuscripcion.getFamiliar();
            Familiar familiarNew = suscripcion.getFamiliar();
            Gratuita gratuitaOld = persistentSuscripcion.getGratuita();
            Gratuita gratuitaNew = suscripcion.getGratuita();
            Individual individualOld = persistentSuscripcion.getIndividual();
            Individual individualNew = suscripcion.getIndividual();
            List<Usuario> usuarioListOld = persistentSuscripcion.getUsuarioList();
            List<Usuario> usuarioListNew = suscripcion.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            if (familiarOld != null && !familiarOld.equals(familiarNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Familiar " + familiarOld + " since its suscripcion field is not nullable.");
            }
            if (gratuitaOld != null && !gratuitaOld.equals(gratuitaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Gratuita " + gratuitaOld + " since its suscripcion field is not nullable.");
            }
            if (individualOld != null && !individualOld.equals(individualNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Individual " + individualOld + " since its suscripcion field is not nullable.");
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its codSuscripcion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (familiarNew != null) {
                familiarNew = em.getReference(familiarNew.getClass(), familiarNew.getCodSuscripcion());
                suscripcion.setFamiliar(familiarNew);
            }
            if (gratuitaNew != null) {
                gratuitaNew = em.getReference(gratuitaNew.getClass(), gratuitaNew.getCodSuscripcion());
                suscripcion.setGratuita(gratuitaNew);
            }
            if (individualNew != null) {
                individualNew = em.getReference(individualNew.getClass(), individualNew.getCodSuscripcion());
                suscripcion.setIndividual(individualNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getNickname());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            suscripcion.setUsuarioList(usuarioListNew);
            suscripcion = em.merge(suscripcion);
            if (familiarNew != null && !familiarNew.equals(familiarOld)) {
                Suscripcion oldSuscripcionOfFamiliar = familiarNew.getSuscripcion();
                if (oldSuscripcionOfFamiliar != null) {
                    oldSuscripcionOfFamiliar.setFamiliar(null);
                    oldSuscripcionOfFamiliar = em.merge(oldSuscripcionOfFamiliar);
                }
                familiarNew.setSuscripcion(suscripcion);
                familiarNew = em.merge(familiarNew);
            }
            if (gratuitaNew != null && !gratuitaNew.equals(gratuitaOld)) {
                Suscripcion oldSuscripcionOfGratuita = gratuitaNew.getSuscripcion();
                if (oldSuscripcionOfGratuita != null) {
                    oldSuscripcionOfGratuita.setGratuita(null);
                    oldSuscripcionOfGratuita = em.merge(oldSuscripcionOfGratuita);
                }
                gratuitaNew.setSuscripcion(suscripcion);
                gratuitaNew = em.merge(gratuitaNew);
            }
            if (individualNew != null && !individualNew.equals(individualOld)) {
                Suscripcion oldSuscripcionOfIndividual = individualNew.getSuscripcion();
                if (oldSuscripcionOfIndividual != null) {
                    oldSuscripcionOfIndividual.setIndividual(null);
                    oldSuscripcionOfIndividual = em.merge(oldSuscripcionOfIndividual);
                }
                individualNew.setSuscripcion(suscripcion);
                individualNew = em.merge(individualNew);
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Suscripcion oldCodSuscripcionOfUsuarioListNewUsuario = usuarioListNewUsuario.getCodSuscripcion();
                    usuarioListNewUsuario.setCodSuscripcion(suscripcion);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldCodSuscripcionOfUsuarioListNewUsuario != null && !oldCodSuscripcionOfUsuarioListNewUsuario.equals(suscripcion)) {
                        oldCodSuscripcionOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldCodSuscripcionOfUsuarioListNewUsuario = em.merge(oldCodSuscripcionOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = suscripcion.getCodSuscripcion();
                if (findSuscripcion(id) == null) {
                    throw new NonexistentEntityException("The suscripcion with id " + id + " no longer exists.");
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
            Suscripcion suscripcion;
            try {
                suscripcion = em.getReference(Suscripcion.class, id);
                suscripcion.getCodSuscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suscripcion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Familiar familiarOrphanCheck = suscripcion.getFamiliar();
            if (familiarOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Suscripcion (" + suscripcion + ") cannot be destroyed since the Familiar " + familiarOrphanCheck + " in its familiar field has a non-nullable suscripcion field.");
            }
            Gratuita gratuitaOrphanCheck = suscripcion.getGratuita();
            if (gratuitaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Suscripcion (" + suscripcion + ") cannot be destroyed since the Gratuita " + gratuitaOrphanCheck + " in its gratuita field has a non-nullable suscripcion field.");
            }
            Individual individualOrphanCheck = suscripcion.getIndividual();
            if (individualOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Suscripcion (" + suscripcion + ") cannot be destroyed since the Individual " + individualOrphanCheck + " in its individual field has a non-nullable suscripcion field.");
            }
            List<Usuario> usuarioListOrphanCheck = suscripcion.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Suscripcion (" + suscripcion + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable codSuscripcion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(suscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Suscripcion> findSuscripcionEntities() {
        return findSuscripcionEntities(true, -1, -1);
    }

    public List<Suscripcion> findSuscripcionEntities(int maxResults, int firstResult) {
        return findSuscripcionEntities(false, maxResults, firstResult);
    }

    private List<Suscripcion> findSuscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Suscripcion.class));
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

    public Suscripcion findSuscripcion(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Suscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Suscripcion> rt = cq.from(Suscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
