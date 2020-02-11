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
import Entities.Interprete;
import Entities.Pais;
import java.util.ArrayList;
import java.util.List;
import Entities.Usuario;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sala BD
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) throws PreexistingEntityException, Exception {
        if (pais.getInterpreteList() == null) {
            pais.setInterpreteList(new ArrayList<Interprete>());
        }
        if (pais.getUsuarioList() == null) {
            pais.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Interprete> attachedInterpreteList = new ArrayList<Interprete>();
            for (Interprete interpreteListInterpreteToAttach : pais.getInterpreteList()) {
                interpreteListInterpreteToAttach = em.getReference(interpreteListInterpreteToAttach.getClass(), interpreteListInterpreteToAttach.getCodInterprete());
                attachedInterpreteList.add(interpreteListInterpreteToAttach);
            }
            pais.setInterpreteList(attachedInterpreteList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : pais.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getNickname());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            pais.setUsuarioList(attachedUsuarioList);
            em.persist(pais);
            for (Interprete interpreteListInterprete : pais.getInterpreteList()) {
                Pais oldCodPaisOfInterpreteListInterprete = interpreteListInterprete.getCodPais();
                interpreteListInterprete.setCodPais(pais);
                interpreteListInterprete = em.merge(interpreteListInterprete);
                if (oldCodPaisOfInterpreteListInterprete != null) {
                    oldCodPaisOfInterpreteListInterprete.getInterpreteList().remove(interpreteListInterprete);
                    oldCodPaisOfInterpreteListInterprete = em.merge(oldCodPaisOfInterpreteListInterprete);
                }
            }
            for (Usuario usuarioListUsuario : pais.getUsuarioList()) {
                Pais oldCodPaisOfUsuarioListUsuario = usuarioListUsuario.getCodPais();
                usuarioListUsuario.setCodPais(pais);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldCodPaisOfUsuarioListUsuario != null) {
                    oldCodPaisOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldCodPaisOfUsuarioListUsuario = em.merge(oldCodPaisOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPais(pais.getCodPais()) != null) {
                throw new PreexistingEntityException("Pais " + pais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getCodPais());
            List<Interprete> interpreteListOld = persistentPais.getInterpreteList();
            List<Interprete> interpreteListNew = pais.getInterpreteList();
            List<Usuario> usuarioListOld = persistentPais.getUsuarioList();
            List<Usuario> usuarioListNew = pais.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Interprete interpreteListOldInterprete : interpreteListOld) {
                if (!interpreteListNew.contains(interpreteListOldInterprete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Interprete " + interpreteListOldInterprete + " since its codPais field is not nullable.");
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its codPais field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Interprete> attachedInterpreteListNew = new ArrayList<Interprete>();
            for (Interprete interpreteListNewInterpreteToAttach : interpreteListNew) {
                interpreteListNewInterpreteToAttach = em.getReference(interpreteListNewInterpreteToAttach.getClass(), interpreteListNewInterpreteToAttach.getCodInterprete());
                attachedInterpreteListNew.add(interpreteListNewInterpreteToAttach);
            }
            interpreteListNew = attachedInterpreteListNew;
            pais.setInterpreteList(interpreteListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getNickname());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            pais.setUsuarioList(usuarioListNew);
            pais = em.merge(pais);
            for (Interprete interpreteListNewInterprete : interpreteListNew) {
                if (!interpreteListOld.contains(interpreteListNewInterprete)) {
                    Pais oldCodPaisOfInterpreteListNewInterprete = interpreteListNewInterprete.getCodPais();
                    interpreteListNewInterprete.setCodPais(pais);
                    interpreteListNewInterprete = em.merge(interpreteListNewInterprete);
                    if (oldCodPaisOfInterpreteListNewInterprete != null && !oldCodPaisOfInterpreteListNewInterprete.equals(pais)) {
                        oldCodPaisOfInterpreteListNewInterprete.getInterpreteList().remove(interpreteListNewInterprete);
                        oldCodPaisOfInterpreteListNewInterprete = em.merge(oldCodPaisOfInterpreteListNewInterprete);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Pais oldCodPaisOfUsuarioListNewUsuario = usuarioListNewUsuario.getCodPais();
                    usuarioListNewUsuario.setCodPais(pais);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldCodPaisOfUsuarioListNewUsuario != null && !oldCodPaisOfUsuarioListNewUsuario.equals(pais)) {
                        oldCodPaisOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldCodPaisOfUsuarioListNewUsuario = em.merge(oldCodPaisOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pais.getCodPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getCodPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Interprete> interpreteListOrphanCheck = pais.getInterpreteList();
            for (Interprete interpreteListOrphanCheckInterprete : interpreteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Interprete " + interpreteListOrphanCheckInterprete + " in its interpreteList field has a non-nullable codPais field.");
            }
            List<Usuario> usuarioListOrphanCheck = pais.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable codPais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
