package com.omnishore.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.omnishore.dao.CollaborateurDao;
import com.omnishore.dao.EntiteDao;
import com.omnishore.entities.Collaborateur;
import com.omnishore.entities.Compte;
import com.omnishore.entities.Entite;
import com.omnishore.entities.Fonction;

public class CollaborateurDaoImpl implements CollaborateurDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9143635338440557472L;

	public EntiteDao entiteDao = new EntiteDaoImpl();

	@Override
	public void createCollaborateur(Collaborateur collaborateur) {

		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			Entite entite = null;

			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();

			if (collaborateur.getIsAdmin() == null) {
				collaborateur.setIsAdmin(false);
			}
			if (collaborateur.getIsResponsable() == null) {
				collaborateur.setIsResponsable(false);
			}

			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(collaborateur);
			if (collaborateur.getIsResponsable() != null
					&& collaborateur.getIsResponsable()) {
				if (collaborateur.getEntite() != null) {
					entite = collaborateur.getEntite();
					entite.setResponsable(collaborateur);
					session.update(entite);
				}
			}
			session.getTransaction().commit();
			System.out.println("Collaborateur enregistré");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}

	}

	@Override
	public Collaborateur findByCIN(String CIN) {
		Collaborateur collaborateur = null;
		Session session = null;
		String hql;
		try {
			hql = "from Collaborateur where CIN = :CIN";
			SessionFactory sf = (new Configuration()).configure()
					.buildSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("CIN", CIN);
			List<Collaborateur> result = query.getResultList();
			if (!result.isEmpty()) {
				if (result.get(0) != null) {
					collaborateur = result.get(0);
				}
			}

			session.close();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return collaborateur;
	}

	@Override
	public Collaborateur findCollaborateur(int collaborateurID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Collaborateur collaborateur = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			collaborateur = session.get(Collaborateur.class, collaborateurID);
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return collaborateur;
	}

	@Override
	public List<Collaborateur> getResponsables() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Collaborateur> responsables = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Collaborateur where isResponsable is true";
			Query query = session.createQuery(hql);
			responsables = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return responsables;
	}

	@Override
	public List<Collaborateur> getCollaborateurs() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Collaborateur> collaborateurs = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Collaborateur";
			Query query = session.createQuery(hql);
			collaborateurs = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return collaborateurs;
	}

	@Override
	public void updateCollaborateur(Collaborateur collaborateur) {
		SessionFactory sessionFactory = null;
		Session session = null;

		try {

			/*
			 * 
			 * I must fetch the old entity and set its is_responsable to null
			 * before setting the new entity's id_responsable to collaborateur .
			 */

			entiteDao.setResponsableToNull(collaborateur.getIdCollaborateur());

			if (collaborateur.getActif() == false
					&& collaborateur.getEntite() != null) {
				collaborateur.setEntite(null);
			}

			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(collaborateur);

			if (collaborateur.getIsResponsable()) {
				if (collaborateur.getEntite() != null) {
					collaborateur.getEntite().setResponsable(collaborateur);
					session.update(collaborateur.getEntite());
				}
			}
			session.getTransaction().commit();
			System.out.println("reponsable (" + collaborateur.getNom() + " "
					+ collaborateur.getPrenom() + ") modifiée");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean deleteCollaborateur(int collaborateurID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		boolean deleted = false;
		String hql;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			hql = "from Compte where ID_COLLABORATEUR = :id";
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("id", collaborateurID);
			List<Compte> results = query.getResultList();
			if(results.isEmpty()){
				Collaborateur collaborateur = findCollaborateur(collaborateurID);
				session.delete(collaborateur);
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
	public List<Collaborateur> getAvailableResponsables() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Collaborateur> availableResponsables = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Collaborateur where isResponsable is true and id_entite is null";
			Query query = session.createQuery(hql);
			availableResponsables = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return availableResponsables;
	}

	@Override
	public Collaborateur findCollaborateur(String nomCollaborateur) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Collaborateur collaborateur = null;
		String hql;
		try {
			hql = "from Collaborateur where nom = :nomCollab";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomCollab", nomCollaborateur);
			List<Collaborateur> result = query.getResultList();
			session.getTransaction().commit();
			for (Collaborateur coll : result) {
				collaborateur = coll;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return collaborateur;

	}

	@Override
	public Collaborateur findCollaborateur(String prenomCollaborateur,
			String nomCollaborateur) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Collaborateur collaborateur = null;
		String hql;
		try {
			hql = "from Collaborateur where nom = :nomCollab and prenom = :prenomCollab";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomCollab", nomCollaborateur);
			query.setParameter("prenomCollab", prenomCollaborateur);
			List<Collaborateur> result = query.getResultList();
			session.getTransaction().commit();
			for (Collaborateur coll : result) {
				collaborateur = coll;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return collaborateur;

	}

	@Override
	public void setEntiteToNull(int idEntite) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Collaborateur collaborateur = null;
		String hql;
		try {
			hql = "from Collaborateur where isResponsable is true and ID_Entite = :idEntite";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("idEntite", idEntite);
			List<Collaborateur> result = query.getResultList();
			for (Collaborateur coll : result) {
				collaborateur = coll;
			}

			if (collaborateur != null) {
				collaborateur.setEntite(null);
				session.update(collaborateur);
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
	public List<Collaborateur> getCollaborateurOfEntite(Entite entite) {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Collaborateur> collaborateursOfEntite = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Collaborateur where id_entite = :id_entite";
			Query query = session.createQuery(hql);
			query.setParameter("id_entite", entite.getIdEntite());
			collaborateursOfEntite = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return collaborateursOfEntite;
	}

	@Override
	public List<Collaborateur> getOnlyCollaborateurOfEntite(Entite entite) {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Collaborateur> collaborateursOfEntite = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Collaborateur where isResponsable is false and id_entite = :id_entite";
			Query query = session.createQuery(hql);
			query.setParameter("id_entite", entite.getIdEntite());
			collaborateursOfEntite = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return collaborateursOfEntite;
	}

	@Override
	public HashMap<String, List<Collaborateur>> getMapCollaborateursEntites() {
		
		HashMap<String, List<Collaborateur>> collaborateurPerEntite = null;
		String hql = null;
		SessionFactory sessionFactory = null;
		Session session = null;
	
		try {
			collaborateurPerEntite = new HashMap<String, List<Collaborateur>>();
			List<Entite> entites = entiteDao.getEntites();
			
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			for (Entite entite : entites) {
				int  id_entite = entite.getIdEntite();
				hql = "from Collaborateur where isResponsable is false and id_entite = :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", id_entite);
				
				collaborateurPerEntite.put(entite.getNom(), query.getResultList());
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return collaborateurPerEntite;
	}

	@Override
	public List<Fonction> getListFonctions() {
		String hql = null;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Fonction> fonctions = null;
	
		try {			
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Fonction order by codeFonction";
			Query query = session.createQuery(hql);
			fonctions = query.getResultList();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return fonctions;
	}

	@Override
	public Fonction getFunctionByLabel(String value) {
		String hql = null;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Fonction> fonctions = null;
		Fonction fonction = null;
	
		try {			
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Fonction where libelleFonction =  :value";
			Query query = session.createQuery(hql);
			query.setParameter("value", value);
			fonctions = query.getResultList();
			if(!fonctions.isEmpty()){
				for(Fonction fonc : fonctions){
					fonction = fonc;
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return fonction;
	}
}
