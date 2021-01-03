 # Projet_BigData


## Question Infrastructure
a) Les données que nous utilisons pour l’instant ne sont pas complètes car il manque des tweets. Combien de jours complets de collecte de données pouvons-nous stocker sur notre  infrastructure de test (votre salle de TP) ?
> Réponse: 1 Tweet = 5 ko et il y a 504 millions de tweet par jour. Donc on a: 504 000 000 x 5 ko = 2.52 To. 
> Si on a 20 machines avec 1 To (je sais pas la capacité) ca fait: 20To / 2.52To = 7.93 jours donc 7 jours entiers.


b) Pour ce nombre de jour de collecte complet, combien de blocs de données seront disponibles sur chaque machine en moyenne?
> Réponse:

c) Afin de planifier nos achats de matériels futurs, calculez le nombre de machines total que nous devrons avoir dans notre cluster pour stocker 5 ans de tweets ?
> Réponse: 


## Exemple d’analyse des hashtags

- [x] a) Permettre de récupérer pour un jour donné la liste des khashtags les plus utilisés ainsi que leur nombre d’apparition (k entre 1 et 10000).
- [ ] b) Permettre de récupérer les k hashtags les plus utilisés (k entre 1 et 10000) sur toutes les données.
- [ ] c) Permettre de récupérer le nombre d’apparition d’un hashtag donné.
- [x] d) Récupérer tous les utilisateurs qui ont utilisé un hashtag.

## Exemple d’analyse des users

- [ ] a) Permettre de récupérer pour un utilisateur la liste de ses hashtags sans doublon.
- [ ] b) Permettre de d’avoir le nombre de tweet d’un utilisateur.
- [ ] c) Nombre de tweet par pays ou par langue.

## Exemple d’analyse des influencers

- [ ] a) Récupérer tous les triplets de hashtags ainsi que les utilisateurs qui les ont utilisés.  
- [ ] b) Donner k triplets de hashtags les plus utilisés (k entre 1 et 1000)
- [ ] c) Trouver les influencers c.a.d les personnes avec le plus grand nombre de tweets dans les triplets que l’on a trouvé. 
- [ ] d) Trouver les faux influencer, personnes avec beaucoup de followers dont les tweets ne sont jamais retweeté.
- [ ] e) Trouvez les sujets (hashtag) qui permettent d’avoir le plus de followers. 
- [ ] f) Retagger les tweet en cherchant les hashtags connus dans le texte des tweets. 




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
spark-submit target/TPSpark-0.0.1.jar
```

Pour lancer spark sur le cluster
```
spark-submit --master yarn target/TPSpark-0.0.1.jar
```
