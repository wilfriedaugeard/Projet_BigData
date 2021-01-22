# Projet_BigData


## Question Infrastructure
a) Les données que nous utilisons pour l’instant ne sont pas complètes car il manque des tweets. Combien de jours complets de collecte de données pouvons-nous stocker sur notre  infrastructure de test (votre salle de TP) ?
> Réponse: 1 Tweet = 5 ko et il y a 504 millions de tweet par jour. Donc on a: 504 000 000 x 5 ko = 2.52 To. 
> Si on a 20 machines avec 1 To (je sais pas la capacité) ca fait: 20To / 2.52To = 7.93 jours donc 7 jours entiers. Mais comme on a une réplication de 3 ca fait 7.93 / 3 = 2.64 jours (c'est pas beaucoup...) donc 2 jours complets.


b) Pour ce nombre de jour de collecte complet, combien de blocs de données seront disponibles sur chaque machine en moyenne?
> Réponse: 2 jours complets (avec réplication) = 2 * 3 * 2.52 = 15.12To / 128Mo = 118 125 blocs.

c) Afin de planifier nos achats de matériels futurs, calculez le nombre de machines total que nous devrons avoir dans notre cluster pour stocker 5 ans de tweets ?
> Réponse: 1 jour de tweets = 2.52 To. Donc 5 * 365 * 2.52 = 4 599 To. Avec la réplication on doit donc stocker 4 599 * 3 = 13 797 To (13.797 Po). Pour 1 marchine ayant 1 To il faudrait 13 777 machines supplémentaires pour 5 ans (On en a deja 20 donc 20 To de disponible). 



|                         Description                          | Front | API  | HBase | Spark | Globale |
| :----------------------------------------------------------: | :---: | :--: | :---: | :---: | :-----: |
| Permettre de récupérer pour un jour donné la liste des khashtags les plus utilisés ainsi que leur nombre d’apparition (k entre 1 et 1000). | DONE  | TODO | TODO  | TODO |   50%   |
| Permettre de récupérer les k hashtags les plus utilisés (k entre 1 et 1000) sur toutes les données. | DONE  | DONE | DONE  | DONE  |   100%   |
| Permettre de récupérer le nombre d’apparition d’un hashtag donné. | DONE  | TODO | TODO  | DONE  |   50%   |
| Récupérer tous les utilisateurs qui ont utilisé un hashtag.  | TODO  | TODO | TODO  | DONE  |   25%   |
| Permettre de récupérer pour un utilisateur la liste de ses hashtags sans doublon. | TODO  | TODO | TODO  | DONE  |   25%   |
|           Nombre de tweet par pays ou par langue.            | DONE  | TODO | TODO  | DONE  |   50%   |
| Récupérer tous les triplets de hashtags ainsi que les utilisateurs qui les ont utilisés. | TODO  | TODO | TODO  | DONE  |   25%   |
| Donner k triplets de hashtags les plus utilisés (k entre 1 et 1000). | DONE  | TODO | TODO  | DONE  |   50%   |
| Trouver les influencers c.a.d les personnes avec le plus grand nombre de tweets dans les triplets que l’on a trouvé. | TODO  | TODO | TODO  | DONE |   25%   |
| Trouver les faux influencer, personnes avec beaucoup de followers dont les tweets ne sont jamais retweeté. | TODO  | TODO | TODO  | DONE |   25%   |
| Trouvez les sujets (hashtag) qui permettent d’avoir le plus de followers. | TODO  | TODO | TODO  | TODO  |   0%    |
| Avoir le pourcentage de tweet ou il y a 0 hashtags, entre 1 et 3, entre 4 et 10, et 10+. | DONE  | TODO | TODO  | DONE |   50%   |
|             Avoir le nombre de tweets par jour.              | DONE  | TODO | TODO  | DONE |   50%   |
|            Avoir le nombre de hashtags par jour.             | DONE  | TODO | TODO  | TODO  |   25%   |
| Top K Users par nb followers. (on peut surement regrouper topk_tweet et topk_follower en regranpant les infos (1 seul rdd de deux tuples)) | DONE  | TODO | TODO  | TODO  |   25%   |
| Pour un hashtag, avoir le nb d'utilisation par jour (mais ca parait gros a stocker) | TODO  | TODO | TODO  | TODO  |   0%    |



## Exemple d’analyse des hashtags

- [x] a) Permettre de récupérer pour un jour donné la liste des khashtags les plus utilisés ainsi que leur nombre d’apparition (k entre 1 et 10000).
- [x] b) Permettre de récupérer les k hashtags les plus utilisés (k entre 1 et 10000) sur toutes les données.
- [x] c) Permettre de récupérer le nombre d’apparition d’un hashtag donné.
- [x] d) Récupérer tous les utilisateurs qui ont utilisé un hashtag.

## Exemple d’analyse des users

- [x] a) Permettre de récupérer pour un utilisateur la liste de ses hashtags sans doublon.
- [x] b) Permettre de savoir le nombre de tweet d’un utilisateur.
- [x] c) Nombre de tweet par pays ou par langue.

## Exemple d’analyse des influencers

- [x] a) Récupérer tous les triplets de hashtags ainsi que les utilisateurs qui les ont utilisés.  
- [x] b) Donner k triplets de hashtags les plus utilisés (k entre 1 et 1000)
- [x] c) Trouver les influencers c.a.d les personnes avec le plus grand nombre de tweets dans les triplets que l’on a trouvé. 
- [x] d) Trouver les faux influencer, personnes avec beaucoup de followers dont les tweets ne sont jamais retweeté.
- [ ] e) Trouvez les sujets (hashtag) qui permettent d’avoir le plus de followers. 
- [ ] f) Retagger les tweet en cherchant les hashtags connus dans le texte des tweets. 
- [x] g) Trouver les k users les plus Retweetés (k entre 1 et 1000)




## Principales commandes
Pour upload mvn et spark
```
source /espace/Auber_PLE-203/user-env.sh 
```

Pour compiler:
```
mvn package
```

Pour lancer spark en local
```
spark-submit target/ProjetTwitter-0.0.1.jar fct file save

Avec pour fct :
	- top-hashtag : topK des hashtags utilisés et le nombre d'utilisation
	- user-hashtag : classement des user par quantité d'hashtag utilisé et la liste des hashtags correspondant 
	- triplet-hashtag : topK des triplets de hashtag utilisé et leur nombre d'utilisation
	- tweet-by-hashtag-nb : nombre de tweet ayant 0, 1, 2, 3, 4-7 et 8+ hashtag
	- tweet-by-language : classement des languages les plus utilisés et le nombre de tweets associés
	-tweet-by-day : nombre de tweet pour un jour donné
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
spark-submit --master yarn --num-executors 10 target/ProjetTwitter-0.0.1.jar fct file save
```
