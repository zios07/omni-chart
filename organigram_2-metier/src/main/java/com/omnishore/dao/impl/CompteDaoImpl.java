package com.omnishore.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.omnishore.dao.CompteDao;
import com.omnishore.entities.Collaborateur;
import com.omnishore.entities.Compte;

public class CompteDaoImpl implements CompteDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2017671193358043740L;

	@Override
	public void createVisitorAccount(Compte c) {
		Session session = null;
		try {
			String pass = c.getMotDePasse();

			ConfigurablePasswordEncryptor cpe = new ConfigurablePasswordEncryptor();
			cpe.setAlgorithm("SHA-256");
			cpe.setPlainDigest(false);

			String passCrypte = cpe.encryptPassword(pass);
			c.setMotDePasse(passCrypte);

			SessionFactory sf = (new Configuration()).configure()
					.buildSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			session.save(c);
			session.getTransaction().commit();
			session.close();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@Override
	public Compte trouverCompte(String login) {
		Session session = null;
		Compte compte = null;
		String hql;
		try {
			hql = "from Compte where login = :login";
			SessionFactory sf = (new Configuration()).configure()
					.buildSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("login", login);
			List<Compte> comptes = query.getResultList();
			if(!comptes.isEmpty()){
				compte = comptes.get(0);
			}
			session.getTransaction().commit();
			session.close();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return compte;
	}

	@Override
	public boolean verifierPass(Compte compte) {
		Session session = null;
		boolean correct = false;
		Compte compteBase = null;
		String hql;
		try {

			hql = "from Compte where login = :login";
			SessionFactory sf = (new Configuration()).configure()
					.buildSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("login", compte.getLogin());
			List<Compte> result = query.getResultList();

			if (!result.isEmpty()) {
				compteBase = result.get(0);
				ConfigurablePasswordEncryptor cpe = new ConfigurablePasswordEncryptor();
				cpe.setAlgorithm("SHA-256");
				cpe.setPlainDigest(false);
				if (cpe.checkPassword(compte.getMotDePasse(),
						compteBase.getMotDePasse())) {
					correct = true;
				}

			}

			session.close();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return correct;
	}

	
	
	@Override
	public void createCollaborateurAccount(Compte compte,
			Collaborateur collaborateur) {
		Session session = null;
		Session session2 = null;
		SessionFactory sessionFactory = null;
		String pass;
		String hql1;
		String hql2;
		try {
			pass = compte.getMotDePasse();
			ConfigurablePasswordEncryptor cpe = new ConfigurablePasswordEncryptor();
			cpe.setAlgorithm("SHA-256");
			cpe.setPlainDigest(false);
			String passCrypte = cpe.encryptPassword(pass);
			hql1 = "select idCompte from Compte where CIN = :cin";
			hql2 = "update Collaborateur set ID_COMPTE = :idCmp where idCollaborateur = :idColl";
			compte.setMotDePasse(passCrypte);
			compte.setCollaborateur(collaborateur);

//			collaborateur.setCompte(compte);

			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

//			session.update(collaborateur);
			session.save(compte);
						
			session.getTransaction().commit();

			// Recupération de l'ID du compte et attribution de cet ID au collaborateur (fix probleme photo)

			
			session2 = sessionFactory.openSession();
			session2.beginTransaction();
			Query query1 = session2.createQuery(hql1);
			query1.setParameter("cin", compte.getCIN());
			if(!query1.getResultList().isEmpty()){
				int idcmp = Integer.parseInt(query1.getResultList().get(0)+"");
				Query query2 = session2.createQuery(hql2);
				query2.setParameter("idCmp", idcmp);
				query2.setParameter("idColl", collaborateur.getIdCollaborateur());
				query2.executeUpdate();
			}
			session2.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

}
