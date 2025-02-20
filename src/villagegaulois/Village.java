package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		this.villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(vendeur.getNom()).append(" cherche un endroit pour vendre ")
			.append(nbProduit).append(" ").append(produit).append(".\n");
		
		int indiceEtal = marche.trouverEtalLibre();
		if (indiceEtal == -1) {
			builder.append("Tous les étals sont occupés!\n");
			return builder.toString();
		}
		marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
		// TODO
		return builder.toString();
	}
	
	class Marche {
		private Etal[] etals;
		private int nbEtals;
		
		public Marche(int nbEtals) {
			this.nbEtals = nbEtals;
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		public Etal getEtal(int indiceEtal) {
			return etals[indiceEtal];
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int i = 0; i < nbEtals; i++) {
				if (!this.etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private int compterEtalsAvecProduit(String produit) {
			int n = 0;
			for (int i = 0; i < nbEtals; i++) {
				if (this.etals[i].contientProduit(produit)) {
					n += 1;
				}
			}
			return n;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalsAvecProduit = compterEtalsAvecProduit(produit);
			Etal[] etalsTrouves = new Etal[nbEtalsAvecProduit];

			int index = 0;
			for (int i = 0; i < nbEtals; i++) {
				Etal etal = etals[i];
		        if (etal.contientProduit(produit)) {
		            etalsTrouves[index] = etal;
		            index++;
		        }
		    }

		    return etalsTrouves;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < nbEtals; i++) {
				Etal etal = etals[i];
		        if (etal.getVendeur() != null && etal.getVendeur().equals(gaulois)) {
		            return etal;
		        }
		    }
		    return null;
		}
		
		public String afficherMarche() {
		    StringBuilder builder = new StringBuilder();
		    int nbEtalVide = 0;

		    for (int i = 0; i < nbEtals; i++) {
				Etal etal = etals[i];
				
	        	if (etal.isEtalOccupe()) {
		        	builder.append(etal.afficherEtal()).append("\n");
		        } else {
		        	nbEtalVide += 1;
		        }
		    }

		    if (nbEtalVide > 0) {
		    	builder.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
		    }

		    return builder.toString();
		}
	}
}