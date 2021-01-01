# Projet_BigData

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
