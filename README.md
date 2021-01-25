# Projet_BigData



|                         Description                          | Front | API  | HBase | Spark | Globale |
| :----------------------------------------------------------: | :---: | :--: | :---: | :---: | :-----: |
| Permettre de récupérer les k hashtags les plus utilisés (k entre 1 et 1000) sur toutes les données. | DONE  | DONE | DONE  | DONE  |   100%   |
| Permettre de récupérer le nombre d’apparition d’un hashtag donné. | DONE  | DONE | DONE  | DONE  |   100%   |
|           Nombre de tweet par pays ou par langue.            | DONE  | DONE | DONE  | DONE  |   100%   |
| Donner k triplets de hashtags les plus utilisés (k entre 1 et 1000). | DONE  | DONE | DONE  | DONE  |   100%   |
| Permettre de récupérer pour un utilisateur la liste de ses hashtags sans doublon. | DONE  | DONE | DONE  | DONE  |   100%   |
| Trouver les influencers c.a.d les personnes avec le plus grand nombre de tweets dans les triplets que l’on a trouvé. | DONE  | DONE | DONE  | DONE  |   100%    |
| Retagger un tweet en cherchant les hashtags connus dans le texte des tweets. | DONE  | DONE | DONE  | DONE  |   100%    |
| Avoir le pourcentage de tweet ou il y a 0 hashtags, entre 1 et 3, entre 4 et 10, et 10+. | DONE  | DONE | DONE  | DONE  |   100%   |
|             Avoir le nombre de tweets par jour.              | DONE  | DONE | DONE  | DONE  |   100%   |
|            Avoir le nombre de hashtags par jour.             | DONE  | DONE | DONE  | DONE  |   100%   |
| Top K Users par nb followers | DONE  | DONE | DONE  | DONE  |   100%   |
| Pour un hashtag, avoir le nb d'utilisation par jour | DONE  | DONE | DONE  | DONE  |   100%   |
| Permettre de récupérer pour un jour donné la liste des khashtags les plus utilisés ainsi que leur nombre d’apparition (k entre 1 et 1000). | DONE  | TODO | TODO  | DONE  |   50%   |
| Récupérer tous les utilisateurs qui ont utilisé un hashtag.  | TODO  | TODO | TODO  | DONE  |   25%   |
| Récupérer tous les triplets de hashtags ainsi que les utilisateurs qui les ont utilisés. | TODO  | TODO | TODO  | DONE  |   25%   |
| Trouvez les sujets (hashtag) qui permettent d’avoir le plus de followers. | TODO  | TODO | TODO  | TODO  |   0%    |
| Trouver les faux influencer, personnes avec beaucoup de followers dont les tweets ne sont jamais retweeté. | TODO  | TODO | TODO  | TODO  |   0%    |

**TABLES DANS HBASE**

- augeard-tarmil-top-hashtag                     <String, Long>     
- augeard-tarmil-tweet-by-day                   <String, Long>
- augeard-tarmil-tweet-by-hashtag-nb     <String, Long>
- augeard-tarmil-tweet-by-language         <String, Long>
- augeard-tarmil-top-followed-user          <String, Long>
- augeard-tarmil-top-tweeting-user          <String,Long>
- augeard-tarmil-influencers                      <String, Long>
- augeard-tarmil-triplet-hashtags              <Triplet, Long>
- augeard-tarmil-user-hashtag                   <User, Set<'Hashtag>>
- augeard-tarmil-top-hashtag-by-day        <String, Set<Tuple2<String,Long>>>
- augeard-tarmil-hashtag-by-day                <String,Long>



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
- [x] f) Retagger les tweet en cherchant les hashtags connus dans le texte des tweets. 
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
spark-submit --master yarn --num-executors 10 target/ProjetTwitter-0.0.1.jar fct file save
```
