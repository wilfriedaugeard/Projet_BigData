# Projet_BigData

## Installation et lancement de l'app
Installer les dépendances du dossier app:
```
cd app/; npm install
```
Lancer hbase:
```
hbase rest start
```
Lancer l'API:
```
npm run app
```
Ensuite se diriger sur le port `3001` du navigateur.


## Principales commandes de développement
Pour upload mvn, spark et hbase
```
source /espace/Auber_PLE-203/user-env.sh 
```

Pour compiler le code source côté data:
```
mvn package
```

Pour lancer spark en local
```
spark-submit target/ProjetTwitter-0.0.1.jar fct file save

Avec pour fct :
	- top-hashtag : topK des hashtags utilisés et le nombre d'utilisation
	- top-hashtag-by-day : topK des hashtags utilisés et le nombre d'utilisation par jours
	- user-hashtag : classement des user par quantité d'hashtag utilisé et la liste des hashtags correspondant 
	- triplet-hashtag : topK des triplets de hashtag utilisé et leur nombre d'utilisation
	- tweet-by-hashtag-nb : nombre de tweet ayant 0, 1-3, 4-7 et 8+ hashtag
	- tweet-by-language : classement des languages les plus utilisés et le nombre de tweets associés
	- hatweet-by-day : nombre de tweet pour un jour donné
	- top-followed-user : topK des users par nombre de followers
	- top-retweeted-user : topK des users par nombre de RT total
	- top-tweeting-user : topK des users par nombre de tweets total
	- influencers : topK des users ayant utilisé le plus de triplet d'hashtag
	- fake-influencers : topK des users avec le plus de followers et le nombre de RT moyen le plus bas
	
Avec pour file :
	- small : petit fichier de 10k tweets
	- one : fichier contenant les données d'un jour de tweets
	- all : tous les tweets sur la période annalysée

Avec pour save :
	- true : sauvegarde valeurs du RDD dans Hbase et affiche les DISPLAY premier résultats
	- false : calcul le RDD et se contente d'afficher les DISPLAY premiers résultats
```

Pour lancer spark sur le cluster

```
spark-submit --master yarn --num-executors 20 target/ProjetTwitter-0.0.1.jar fct file save
```
