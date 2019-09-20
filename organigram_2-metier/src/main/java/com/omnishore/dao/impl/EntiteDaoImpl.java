package com.omnishore.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.omnishore.dao.EntiteDao;
import com.omnishore.entities.Collaborateur;
import com.omnishore.entities.Entite;

public class EntiteDaoImpl implements EntiteDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2273685288135114253L;

	@Override
	public boolean enregistrerEntite(Entite entite) {
		Session session = null;
		SessionFactory sessionFactory = null;
		boolean saved = false;
		try {
			
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(entite);
			if(entite.getResponsable()!=null){
				Collaborateur responsable = entite.getResponsable();
				responsable.setEntite(entite);
				session.update(responsable);
			}
			session.getTransaction().commit();
			saved = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return saved;
	}

	@Override
	public Entite trouverEntite(int entiteID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Entite entite = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			entite = session.get(Entite.class, entiteID);
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return entite;
	}

	@Override
	public List<Entite> getEntites() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Entite> entites = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Entite";
			Query query = session.createQuery(hql);
			entites = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return entites;
	}

	@Override
	public void modifierEntite(Entite entite) {

		SessionFactory sessionFactory = null;
		Session session = null;
		Entite ent = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(entite);
			session.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally{
			session.close();
			sessionFactory.close();
		}
	}

	@Override
	public boolean supprimerEntite(int EntiteID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		boolean deleted = false;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			if(!estEntiteMere(EntiteID)){
				Entite entite = trouverEntite(EntiteID);
				session.delete(entite);
				session.getTransaction().commit();
				deleted = true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return deleted;
	}
	

	@Override
	public boolean verifierNom(String nomEntite) {
		boolean estUnique = true;
		String hql;
		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			hql = "from Entite where nom =:nomNouvelleEntite";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomNouvelleEntite", nomEntite);
			List result = query.getResultList();
			if (!result.isEmpty()) {
				estUnique = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}

		return estUnique;
	}

	@Override
	public Entite trouverEntite(String nomEntite) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Entite entite = null;
		String hql;
		try {
			hql = "from Entite where nom = :nomE";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomE", nomEntite);
			List<Entite> result = query.getResultList();
			session.getTransaction().commit();
			for(Entite e : result){
				entite = e;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return entite;

	}

	@Override
	public List<Entite> getEntitesSansResponsable() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Entite> entiteSansResponsable = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Entite where id_responsable is null";
			Query query = session.createQuery(hql);
			entiteSansResponsable = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return entiteSansResponsable;
	}

	@Override
	public void setResponsableToNull(int responsableID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Entite entite = null;
		String hql;
		try {
			hql = "from Entite where ID_Responsable = :responsableID";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("responsableID", responsableID);
			List<Entite> result = query.getResultList();
			for(Entite e : result){
				entite = e;
			}
			
			if(entite!=null){
				entite.setResponsable(null);
				session.update(entite);
			}
			session.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		
	}

	@Override
	public List<Entite> getEntiteSansEntiteMere() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Entite> entiteSansResponsable = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Entite where entiteMere is null";
			Query query = session.createQuery(hql);
			entiteSansResponsable = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return entiteSansResponsable;
	}

	@Override
	public List<Entite> getSubEntities(Entite entite) {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Entite> subEntities = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Entite where entiteMere = :entite";
			Query query = session.createQuery(hql);
			query.setParameter("entite", entite);
			subEntities = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return subEntities;
	}

	@Override
	public List<Entite> getEntitesFilles() {
		
			String hql;
			SessionFactory sessionFactory = null;
			Session session = null;
			List<Entite> entiteSansResponsable = null;
			try {
				sessionFactory = (new Configuration()).configure()
						.buildSessionFactory();
				session = sessionFactory.openSession();
				hql = "from Entite where entiteMere is not null";
				Query query = session.createQuery(hql);
				entiteSansResponsable = query.getResultList();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				sessionFactory.close();
				session.close();
			}
			return entiteSansResponsable;
		
	}

	@Override
	public Entite getEntiteByNom(String nomEntite) {
		String hql;
		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			hql = "from Entite where nom =:nomNouvelleEntite";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomNouvelleEntite", nomEntite);
			List<Entite> result = query.getResultList();
			if(!result.isEmpty()){
				return result.get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return null;
	}

	@Override
	public Entite getRaindomEntity() {
		String hql;
		Session session = null;
		SessionFactory sessionFactory = null;
		Entite entite = null;
		try {
			hql = "from Entite";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			List<Entite> result = query.getResultList();
			if(!result.isEmpty()){
				return result.get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		return null;
	}

	@Override
	public boolean estEntiteMere(int idEntite) {
		boolean estEntiteMere = false;
		String hql;
		Session session = null;
		SessionFactory sessionFactory = null;
		List<Entite> result = null;
		try {
			hql = "from Entite where ID_Entite_Mere = :id";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("id", idEntite);
			result = query.getResultList();
			if(!result.isEmpty()){
				estEntiteMere = true;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		return estEntiteMere;
	}

}
