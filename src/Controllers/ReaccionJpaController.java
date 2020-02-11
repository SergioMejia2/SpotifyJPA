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
import Entities.Reaccion;
import Entities.ReaccionPK;
import Entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sala BD
 */
public class ReaccionJpaController implements Serializable {

    public ReaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reaccion reaccion) throws PreexistingEntityException, Exception {
        if (reaccion.getReaccionPK() == null) {
            reaccion.setReaccionPK(new ReaccionPK());
        }
        reaccion.getReaccionPK().setCodCancion(reaccion.getCancion().getCodCancion());
        reaccion.getReaccionPK().setNickname(reaccion.getUsuario().getNickname());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion cancion = reaccion.getCancion();
            if (cancion != null) {
                cancion = em.getReference(cancion.getClass(), cancion.getCodCancion());
                reaccion.setCancion(cancion);
            }
            Usuario usuario = reaccion.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getNickname());
                reaccion.setUsuario(usuario);
            }
            em.persist(reaccion);
            if (cancion != null) {
                cancion.getReaccionList().add(reaccion);
                cancion = em.merge(cancion);
            }
            if (usuario != null) {
                usuario.getReaccionList().add(reaccion);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReaccion(reaccion.getReaccionPK()) != null) {
                throw new PreexistingEntityException("Reaccion " + reaccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reaccion reaccion) throws NonexistentEntityException, Exception {
        reaccion.getReaccionPK().setCodCancion(reaccion.getCancion().getCodCancion());
        reaccion.getReaccionPK().setNickname(reaccion.getUsuario().getNickname());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reaccion persistentReaccion = em.find(Reaccion.class, reaccion.getReaccionPK());
            Cancion cancionOld = persistentReaccion.getCancion();
            Cancion cancionNew = reaccion.getCancion();
            Usuario usuarioOld = persistentReaccion.getUsuario();
            Usuario usuarioNew = reaccion.getUsuario();
            if (cancionNew != null) {
                cancionNew = em.getReference(cancionNew.getClass(), cancionNew.getCodCancion());
                reaccion.setCancion(cancionNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getNickname());
                reaccion.setUsuario(usuarioNew);
            }
            reaccion = em.merge(reaccion);
            if (cancionOld != null && !cancionOld.equals(cancionNew)) {
                cancionOld.getReaccionList().remove(reaccion);
                cancionOld = em.merge(cancionOld);
            }
            if (cancionNew != null && !cancionNew.equals(cancionOld)) {
                cancionNew.getReaccionList().add(reaccion);
                cancionNew = em.merge(cancionNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getReaccionList().remove(reaccion);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getReaccionList().add(reaccion);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ReaccionPK id = reaccion.getReaccionPK();
                if (findReaccion(id) == null) {
                    throw new NonexistentEntityException("The reaccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ReaccionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reaccion reaccion;
            try {
                reaccion = em.getReference(Reaccion.class, id);
                reaccion.getReaccionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reaccion with id " + id + " no longer exists.", enfe);
            }
            Cancion cancion = reaccion.getCancion();
            if (cancion != null) {
                cancion.getReaccionList().remove(reaccion);
                cancion = em.merge(cancion);
            }
            Usuario usuario = reaccion.getUsuario();
            if (usuario != null) {
                usuario.getReaccionList().remove(reaccion);
                usuario = em.merge(usuario);
            }
            em.remove(reaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reaccion> findReaccionEntities() {
        return findReaccionEntities(true, -1, -1);
    }

    public List<Reaccion> findReaccionEntities(int maxResults, int firstResult) {
        return findReaccionEntities(false, maxResults, firstResult);
    }

    private List<Reaccion> findReaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reaccion.class));
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

    public Reaccion findReaccion(ReaccionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getReaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reaccion> rt = cq.from(Reaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
