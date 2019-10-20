# MyERP

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application


## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)


### Lancement

    cd docker/dev
    docker-compose up

### Arrêt

    cd docker/dev
    docker-compose stop

### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up

## Corréctifs

    - Méthode getTotalDebit() de la classe Ecriture comptable était un
     copier de la méthode getTotalCredit().
     
    - Méthode isEquilibree() de la classe Ecriture comptable, remplacement
     du equals par un compareTo car c'est deux BigDecimal à comparer.
     
    - Requete "SQLinsertListLigneEcritureComptable", il manquait une virgule.
    
    - Méthode updateEcritureComptable dans la classe ComptabliliteManagerImpl, 
    ajout de la méthode this.checkEcritureComptable() pour consulter si l'écriture est valide 
    avant de la sauvegarder dans la base de données.

## Modifications éffectués

    - Implémentation des tests unitaires pour les couches consumer,business,model et techical.
    - Implémentation des tests d'intégration pour la couche consumer et business.
    - Modification de certains constructeurs pour tester les méthodes de la classe.
    - Ajout d'un fichier travis.yml pour automatiserez les tests.
    - Intégration de la méthode addReference dans la classe ComptabiliteManagerImpl.
    - Ajout de deux méthodes insertSequenceEcritureComptable et deleteEcritueComptable
     ainsi que leurs requetes SQL dans le fichier sqlContext.xml.
    - Implémentation de la  RG_Compta_5 dans la classe ComptabiliteManagerImpl.
    - Passage de JUnit 4 vers JUnit 5/Jupiter.
    - Ajout de CORRECTED à la place des TODO.
    - Mise en commantires de deux lignes dans le fichier ste.xml qui générait une erreur pendant
    le mvn site site:stage.
   
    