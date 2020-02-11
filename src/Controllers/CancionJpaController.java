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
import Entities.Cancion;
import Entities.Genero;
import Entities.Cancionxinterprete;
import java.util.ArrayList;
import java.util.List;
import Entities.Registro;
import Entities.Reaccion;
import Entities.Cancionxplaylist;
import Entities.InfoCancion;
import Entities.Playlist;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sala BD
 */
public class CancionJpaController implements Serializable {

    public CancionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancion cancion) throws PreexistingEntityException, Exception {
        if (cancion.getCancionxinterpreteList() == null) {
            cancion.setCancionxinterpreteList(new ArrayList<Cancionxinterprete>());
        }
        if (cancion.getRegistroList() == null) {
            cancion.setRegistroList(new ArrayList<Registro>());
        }
        if (cancion.getReaccionList() == null) {
            cancion.setReaccionList(new ArrayList<Reaccion>());
        }
        if (cancion.getCancionxplaylistList() == null) {
            cancion.setCancionxplaylistList(new ArrayList<Cancionxplaylist>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album codAlbum = cancion.getCodAlbum();
            if (codAlbum != null) {
                codAlbum = em.getReference(codAlbum.getClass(), codAlbum.getCodAlbum());
                cancion.setCodAlbum(codAlbum);
            }
            Genero genero = cancion.getGenero();
            if (genero != null) {
                genero = em.getReference(genero.getClass(), genero.getGeneroPK());
                cancion.setGenero(genero);
            }
            List<Cancionxinterprete> attachedCancionxinterpreteList = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteListCancionxinterpreteToAttach : cancion.getCancionxinterpreteList()) {
                cancionxinterpreteListCancionxinterpreteToAttach = em.getReference(cancionxinterpreteListCancionxinterpreteToAttach.getClass(), cancionxinterpreteListCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteList.add(cancionxinterpreteListCancionxinterpreteToAttach);
            }
            cancion.setCancionxinterpreteList(attachedCancionxinterpreteList);
            List<Registro> attachedRegistroList = new ArrayList<Registro>();
            for (Registro registroListRegistroToAttach : cancion.getRegistroList()) {
                registroListRegistroToAttach = em.getReference(registroListRegistroToAttach.getClass(), registroListRegistroToAttach.getRegistroPK());
                attachedRegistroList.add(registroListRegistroToAttach);
            }
            cancion.setRegistroList(attachedRegistroList);
            List<Reaccion> attachedReaccionList = new ArrayList<Reaccion>();
            for (Reaccion reaccionListReaccionToAttach : cancion.getReaccionList()) {
                reaccionListReaccionToAttach = em.getReference(reaccionListReaccionToAttach.getClass(), reaccionListReaccionToAttach.getReaccionPK());
                attachedReaccionList.add(reaccionListReaccionToAttach);
            }
            cancion.setReaccionList(attachedReaccionList);
            List<Cancionxplaylist> attachedCancionxplaylistList = new ArrayList<Cancionxplaylist>();
            for (Cancionxplaylist cancionxplaylistListCancionxplaylistToAttach : cancion.getCancionxplaylistList()) {
                cancionxplaylistListCancionxplaylistToAttach = em.getReference(cancionxplaylistListCancionxplaylistToAttach.getClass(), cancionxplaylistListCancionxplaylistToAttach.getCancionxplaylistPK());
                attachedCancionxplaylistList.add(cancionxplaylistListCancionxplaylistToAttach);
            }
            cancion.setCancionxplaylistList(attachedCancionxplaylistList);
            em.persist(cancion);
            if (codAlbum != null) {
                codAlbum.getCancionList().add(cancion);
                codAlbum = em.merge(codAlbum);
            }
            if (genero != null) {
                genero.getCancionList().add(cancion);
                genero = em.merge(genero);
            }
            for (Cancionxinterprete cancionxinterpreteListCancionxinterprete : cancion.getCancionxinterpreteList()) {
                Cancion oldCancionOfCancionxinterpreteListCancionxinterprete = cancionxinterpreteListCancionxinterprete.getCancion();
                cancionxinterpreteListCancionxinterprete.setCancion(cancion);
                cancionxinterpreteListCancionxinterprete = em.merge(cancionxinterpreteListCancionxinterprete);
                if (oldCancionOfCancionxinterpreteListCancionxinterprete != null) {
                    oldCancionOfCancionxinterpreteListCancionxinterprete.getCancionxinterpreteList().remove(cancionxinterpreteListCancionxinterprete);
                    oldCancionOfCancionxinterpreteListCancionxinterprete = em.merge(oldCancionOfCancionxinterpreteListCancionxinterprete);
                }
            }
            for (Registro registroListRegistro : cancion.getRegistroList()) {
                Cancion oldCancionOfRegistroListRegistro = registroListRegistro.getCancion();
                registroListRegistro.setCancion(cancion);
                registroListRegistro = em.merge(registroListRegistro);
                if (oldCancionOfRegistroListRegistro != null) {
                    oldCancionOfRegistroListRegistro.getRegistroList().remove(registroListRegistro);
                    oldCancionOfRegistroListRegistro = em.merge(oldCancionOfRegistroListRegistro);
                }
            }
            for (Reaccion reaccionListReaccion : cancion.getReaccionList()) {
                Cancion oldCancionOfReaccionListReaccion = reaccionListReaccion.getCancion();
                reaccionListReaccion.setCancion(cancion);
                reaccionListReaccion = em.merge(reaccionListReaccion);
                if (oldCancionOfReaccionListReaccion != null) {
                    oldCancionOfReaccionListReaccion.getReaccionList().remove(reaccionListReaccion);
                    oldCancionOfReaccionListReaccion = em.merge(oldCancionOfReaccionListReaccion);
                }
            }
            for (Cancionxplaylist cancionxplaylistListCancionxplaylist : cancion.getCancionxplaylistList()) {
                Cancion oldCancionOfCancionxplaylistListCancionxplaylist = cancionxplaylistListCancionxplaylist.getCancion();
                cancionxplaylistListCancionxplaylist.setCancion(cancion);
                cancionxplaylistListCancionxplaylist = em.merge(cancionxplaylistListCancionxplaylist);
                if (oldCancionOfCancionxplaylistListCancionxplaylist != null) {
                    oldCancionOfCancionxplaylistListCancionxplaylist.getCancionxplaylistList().remove(cancionxplaylistListCancionxplaylist);
                    oldCancionOfCancionxplaylistListCancionxplaylist = em.merge(oldCancionOfCancionxplaylistListCancionxplaylist);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCancion(cancion.getCodCancion()) != null) {
                throw new PreexistingEntityException("Cancion " + cancion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cancion cancion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion persistentCancion = em.find(Cancion.class, cancion.getCodCancion());
            Album codAlbumOld = persistentCancion.getCodAlbum();
            Album codAlbumNew = cancion.getCodAlbum();
            Genero generoOld = persistentCancion.getGenero();
            Genero generoNew = cancion.getGenero();
            List<Cancionxinterprete> cancionxinterpreteListOld = persistentCancion.getCancionxinterpreteList();
            List<Cancionxinterprete> cancionxinterpreteListNew = cancion.getCancionxinterpreteList();
            List<Registro> registroListOld = persistentCancion.getRegistroList();
            List<Registro> registroListNew = cancion.getRegistroList();
            List<Reaccion> reaccionListOld = persistentCancion.getReaccionList();
            List<Reaccion> reaccionListNew = cancion.getReaccionList();
            List<Cancionxplaylist> cancionxplaylistListOld = persistentCancion.getCancionxplaylistList();
            List<Cancionxplaylist> cancionxplaylistListNew = cancion.getCancionxplaylistList();
            List<String> illegalOrphanMessages = null;
            for (Cancionxinterprete cancionxinterpreteListOldCancionxinterprete : cancionxinterpreteListOld) {
                if (!cancionxinterpreteListNew.contains(cancionxinterpreteListOldCancionxinterprete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxinterprete " + cancionxinterpreteListOldCancionxinterprete + " since its cancion field is not nullable.");
                }
            }
            for (Registro registroListOldRegistro : registroListOld) {
                if (!registroListNew.contains(registroListOldRegistro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registro " + registroListOldRegistro + " since its cancion field is not nullable.");
                }
            }
            for (Reaccion reaccionListOldReaccion : reaccionListOld) {
                if (!reaccionListNew.contains(reaccionListOldReaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reaccion " + reaccionListOldReaccion + " since its cancion field is not nullable.");
                }
            }
            for (Cancionxplaylist cancionxplaylistListOldCancionxplaylist : cancionxplaylistListOld) {
                if (!cancionxplaylistListNew.contains(cancionxplaylistListOldCancionxplaylist)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxplaylist " + cancionxplaylistListOldCancionxplaylist + " since its cancion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codAlbumNew != null) {
                codAlbumNew = em.getReference(codAlbumNew.getClass(), codAlbumNew.getCodAlbum());
                cancion.setCodAlbum(codAlbumNew);
            }
            if (generoNew != null) {
                generoNew = em.getReference(generoNew.getClass(), generoNew.getGeneroPK());
                cancion.setGenero(generoNew);
            }
            List<Cancionxinterprete> attachedCancionxinterpreteListNew = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteListNewCancionxinterpreteToAttach : cancionxinterpreteListNew) {
                cancionxinterpreteListNewCancionxinterpreteToAttach = em.getReference(cancionxinterpreteListNewCancionxinterpreteToAttach.getClass(), cancionxinterpreteListNewCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteListNew.add(cancionxinterpreteListNewCancionxinterpreteToAttach);
            }
            cancionxinterpreteListNew = attachedCancionxinterpreteListNew;
            cancion.setCancionxinterpreteList(cancionxinterpreteListNew);
            List<Registro> attachedRegistroListNew = new ArrayList<Registro>();
            for (Registro registroListNewRegistroToAttach : registroListNew) {
                registroListNewRegistroToAttach = em.getReference(registroListNewRegistroToAttach.getClass(), registroListNewRegistroToAttach.getRegistroPK());
                attachedRegistroListNew.add(registroListNewRegistroToAttach);
            }
            registroListNew = attachedRegistroListNew;
            cancion.setRegistroList(registroListNew);
            List<Reaccion> attachedReaccionListNew = new ArrayList<Reaccion>();
            for (Reaccion reaccionListNewReaccionToAttach : reaccionListNew) {
                reaccionListNewReaccionToAttach = em.getReference(reaccionListNewReaccionToAttach.getClass(), reaccionListNewReaccionToAttach.getReaccionPK());
                attachedReaccionListNew.add(reaccionListNewReaccionToAttach);
            }
            reaccionListNew = attachedReaccionListNew;
            cancion.setReaccionList(reaccionListNew);
            List<Cancionxplaylist> attachedCancionxplaylistListNew = new ArrayList<Cancionxplaylist>();
            for (Cancionxplaylist cancionxplaylistListNewCancionxplaylistToAttach : cancionxplaylistListNew) {
                cancionxplaylistListNewCancionxplaylistToAttach = em.getReference(cancionxplaylistListNewCancionxplaylistToAttach.getClass(), cancionxplaylistListNewCancionxplaylistToAttach.getCancionxplaylistPK());
                attachedCancionxplaylistListNew.add(cancionxplaylistListNewCancionxplaylistToAttach);
            }
            cancionxplaylistListNew = attachedCancionxplaylistListNew;
            cancion.setCancionxplaylistList(cancionxplaylistListNew);
            cancion = em.merge(cancion);
            if (codAlbumOld != null && !codAlbumOld.equals(codAlbumNew)) {
                codAlbumOld.getCancionList().remove(cancion);
                codAlbumOld = em.merge(codAlbumOld);
            }
            if (codAlbumNew != null && !codAlbumNew.equals(codAlbumOld)) {
                codAlbumNew.getCancionList().add(cancion);
                codAlbumNew = em.merge(codAlbumNew);
            }
            if (generoOld != null && !generoOld.equals(generoNew)) {
                generoOld.getCancionList().remove(cancion);
                generoOld = em.merge(generoOld);
            }
            if (generoNew != null && !generoNew.equals(generoOld)) {
                generoNew.getCancionList().add(cancion);
                generoNew = em.merge(generoNew);
            }
            for (Cancionxinterprete cancionxinterpreteListNewCancionxinterprete : cancionxinterpreteListNew) {
                if (!cancionxinterpreteListOld.contains(cancionxinterpreteListNewCancionxinterprete)) {
                    Cancion oldCancionOfCancionxinterpreteListNewCancionxinterprete = cancionxinterpreteListNewCancionxinterprete.getCancion();
                    cancionxinterpreteListNewCancionxinterprete.setCancion(cancion);
                    cancionxinterpreteListNewCancionxinterprete = em.merge(cancionxinterpreteListNewCancionxinterprete);
                    if (oldCancionOfCancionxinterpreteListNewCancionxinterprete != null && !oldCancionOfCancionxinterpreteListNewCancionxinterprete.equals(cancion)) {
                        oldCancionOfCancionxinterpreteListNewCancionxinterprete.getCancionxinterpreteList().remove(cancionxinterpreteListNewCancionxinterprete);
                        oldCancionOfCancionxinterpreteListNewCancionxinterprete = em.merge(oldCancionOfCancionxinterpreteListNewCancionxinterprete);
                    }
                }
            }
            for (Registro registroListNewRegistro : registroListNew) {
                if (!registroListOld.contains(registroListNewRegistro)) {
                    Cancion oldCancionOfRegistroListNewRegistro = registroListNewRegistro.getCancion();
                    registroListNewRegistro.setCancion(cancion);
                    registroListNewRegistro = em.merge(registroListNewRegistro);
                    if (oldCancionOfRegistroListNewRegistro != null && !oldCancionOfRegistroListNewRegistro.equals(cancion)) {
                        oldCancionOfRegistroListNewRegistro.getRegistroList().remove(registroListNewRegistro);
                        oldCancionOfRegistroListNewRegistro = em.merge(oldCancionOfRegistroListNewRegistro);
                    }
                }
            }
            for (Reaccion reaccionListNewReaccion : reaccionListNew) {
                if (!reaccionListOld.contains(reaccionListNewReaccion)) {
                    Cancion oldCancionOfReaccionListNewReaccion = reaccionListNewReaccion.getCancion();
                    reaccionListNewReaccion.setCancion(cancion);
                    reaccionListNewReaccion = em.merge(reaccionListNewReaccion);
                    if (oldCancionOfReaccionListNewReaccion != null && !oldCancionOfReaccionListNewReaccion.equals(cancion)) {
                        oldCancionOfReaccionListNewReaccion.getReaccionList().remove(reaccionListNewReaccion);
                        oldCancionOfReaccionListNewReaccion = em.merge(oldCancionOfReaccionListNewReaccion);
                    }
                }
            }
            for (Cancionxplaylist cancionxplaylistListNewCancionxplaylist : cancionxplaylistListNew) {
                if (!cancionxplaylistListOld.contains(cancionxplaylistListNewCancionxplaylist)) {
                    Cancion oldCancionOfCancionxplaylistListNewCancionxplaylist = cancionxplaylistListNewCancionxplaylist.getCancion();
                    cancionxplaylistListNewCancionxplaylist.setCancion(cancion);
                    cancionxplaylistListNewCancionxplaylist = em.merge(cancionxplaylistListNewCancionxplaylist);
                    if (oldCancionOfCancionxplaylistListNewCancionxplaylist != null && !oldCancionOfCancionxplaylistListNewCancionxplaylist.equals(cancion)) {
                        oldCancionOfCancionxplaylistListNewCancionxplaylist.getCancionxplaylistList().remove(cancionxplaylistListNewCancionxplaylist);
                        oldCancionOfCancionxplaylistListNewCancionxplaylist = em.merge(oldCancionOfCancionxplaylistListNewCancionxplaylist);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = cancion.getCodCancion();
                if (findCancion(id) == null) {
                    throw new NonexistentEntityException("The cancion with id " + id + " no longer exists.");
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
            Cancion cancion;
            try {
                cancion = em.getReference(Cancion.class, id);
                cancion.getCodCancion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cancion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancionxinterprete> cancionxinterpreteListOrphanCheck = cancion.getCancionxinterpreteList();
            for (Cancionxinterprete cancionxinterpreteListOrphanCheckCancionxinterprete : cancionxinterpreteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Cancionxinterprete " + cancionxinterpreteListOrphanCheckCancionxinterprete + " in its cancionxinterpreteList field has a non-nullable cancion field.");
            }
            List<Registro> registroListOrphanCheck = cancion.getRegistroList();
            for (Registro registroListOrphanCheckRegistro : registroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Registro " + registroListOrphanCheckRegistro + " in its registroList field has a non-nullable cancion field.");
            }
            List<Reaccion> reaccionListOrphanCheck = cancion.getReaccionList();
            for (Reaccion reaccionListOrphanCheckReaccion : reaccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Reaccion " + reaccionListOrphanCheckReaccion + " in its reaccionList field has a non-nullable cancion field.");
            }
            List<Cancionxplaylist> cancionxplaylistListOrphanCheck = cancion.getCancionxplaylistList();
            for (Cancionxplaylist cancionxplaylistListOrphanCheckCancionxplaylist : cancionxplaylistListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Cancionxplaylist " + cancionxplaylistListOrphanCheckCancionxplaylist + " in its cancionxplaylistList field has a non-nullable cancion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Album codAlbum = cancion.getCodAlbum();
            if (codAlbum != null) {
                codAlbum.getCancionList().remove(cancion);
                codAlbum = em.merge(codAlbum);
            }
            Genero genero = cancion.getGenero();
            if (genero != null) {
                genero.getCancionList().remove(cancion);
                genero = em.merge(genero);
            }
            em.remove(cancion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cancion> findCancionEntities() {
        return findCancionEntities(true, -1, -1);
    }

    public List<Cancion> findCancionEntities(int maxResults, int firstResult) {
        return findCancionEntities(false, maxResults, firstResult);
    }

    private List<Cancion> findCancionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cancion.class));
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

    public Cancion findCancion(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cancion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCancionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cancion> rt = cq.from(Cancion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<InfoCancion> buscarCancion(String busqueda)
    {
        List<InfoCancion> info_cancion = new ArrayList<InfoCancion>();
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT distinct cod_cancion, (titulo_cancion), nvl(nombre_artista, nombre_real), titulo_album, tiempo_reprm, tiempo_reprs \n" +
"                                       FROM (((cancion natural join cancionxinterprete) natural join interprete) natural join album) \n" +
"                                       WHERE (UPPER(titulo_cancion) like UPPER(?) or UPPER(nombre_artista) like UPPER(?)) and rol like 'Principal'");
        q.setParameter(1, "%"+busqueda+"%");
        q.setParameter(2, "%"+busqueda+"%");
        
        List<Object[]> results = q.getResultList();
        for (Object[] result : results)
        {
           
            InfoCancion info = new InfoCancion(((BigDecimal)result[0]).toBigInteger(), (String)result[1], (String)result[2],(String)result[3], ((BigDecimal)result[4]).toBigInteger(), ((BigDecimal)result[5]).toBigInteger());
            info_cancion.add(info);
        }
        em.close();
        return info_cancion;
    }

    public InfoCancion buscarCancion(BigInteger id_cancion)
    {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT distinct cod_cancion, (titulo_cancion), nvl(nombre_artista, nombre_real), titulo_album, tiempo_reprm, tiempo_reprs \n" +
"                                       FROM (((cancion natural join cancionxinterprete) natural join interprete) natural join album) \n" +
"                                       WHERE (cod_cancion = ?)");
        q.setParameter(1, id_cancion);
        List<Object[]> results = q.getResultList();
        Object[] result = results.get(0);
        InfoCancion info = new InfoCancion(((BigDecimal)result[0]).toBigInteger(), (String)result[1], (String)result[2],(String)result[3], ((BigDecimal)result[4]).toBigInteger(), ((BigDecimal)result[5]).toBigInteger());

        em.close();
        return info;
    }

    
    public List<Cancion> buscarCanciones(Playlist playlist)
    {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT C.* from ((playlist P inner join cancionxplaylist X on (P.cod_playlist=X.cod_playlist) ) inner join cancion C on (X.cod_cancion=C.cod_cancion))"
                                      +"WHERE p.cod_playlist = ? ORDER BY X.orden",Cancion.class);
        q.setParameter(1, playlist.getCodPlaylist());
        List<Cancion> canciones = q.getResultList();
        return canciones;
    }
    
}
