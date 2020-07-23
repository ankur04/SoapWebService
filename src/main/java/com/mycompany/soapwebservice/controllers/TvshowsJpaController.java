/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soapwebservice.controllers;

import com.mycompany.soapwebservice.controllers.exceptions.NonexistentEntityException;
import com.mycompany.soapwebservice.controllers.exceptions.PreexistingEntityException;
import com.mycompany.soapwebservice.models.Tvshows;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ankur
 */
public class TvshowsJpaController implements Serializable {

    public TvshowsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tvshows tvshows) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tvshows);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTvshows(tvshows.getId()) != null) {
                throw new PreexistingEntityException("Tvshows " + tvshows + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tvshows tvshows) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tvshows = em.merge(tvshows);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tvshows.getId();
                if (findTvshows(id) == null) {
                    throw new NonexistentEntityException("The tvshows with id " + id + " no longer exists.");
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
            Tvshows tvshows;
            try {
                tvshows = em.getReference(Tvshows.class, id);
                tvshows.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tvshows with id " + id + " no longer exists.", enfe);
            }
            em.remove(tvshows);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tvshows> findTvshowsEntities() {
        return findTvshowsEntities(true, -1, -1);
    }

    public List<Tvshows> findTvshowsEntities(int maxResults, int firstResult) {
        return findTvshowsEntities(false, maxResults, firstResult);
    }

    private List<Tvshows> findTvshowsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tvshows.class));
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

    public Tvshows findTvshows(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tvshows.class, id);
        } finally {
            em.close();
        }
    }

    public int getTvshowsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tvshows> rt = cq.from(Tvshows.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
