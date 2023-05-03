# Nombre magique : Application

## Description
Jeu multi-joueurs, le but est de trouver un nombre magique.
Ce nombre est généré par le serveur, entre 0 et 100.

Nosu utilisons ici la librairie Jade, pour mettre en pratique les notions de programmation des systèmes multi-agents.

## Demonstration 

https://user-images.githubusercontent.com/106016869/235977932-56c3c770-4010-441a-9080-50eaba02c7f0.mp4

## Containers
Les containers sont des environnements d'exécution pour les agents.
On a ici un container `MainContainer`
```java
public static void main(String[] args) throws ControllerException {
    Runtime runtime=Runtime.instance();
    ProfileImpl profile=new ProfileImpl();
    profile.setParameter("gui","true");
    AgentContainer agentContainer=runtime.createAgentContainer(profile);
    agentContainer.start();
}
```


## Agents
- **AgentServerGui** : Agent qui permet de lancer l'interface graphique du serveur.
- **AgentServer** : Agent qui permet de lancer le serveur.
- **AgentClientGui** : Agent qui permet de lancer l'interface graphique du client.
- **AgentClient** : Agent qui permet de lancer le client.

Nous avons aussi la possibilité de lancer plusieurs instances du client, pour jouer en multi-joueurs.

## Communication

Ici, nous utilisons la librairie Jade pour la communication entre les agents. Le protocole de communication est assuré par `ACLMessage`.

```java
//Exemple de l'envoi de la réponse du serveur
ACLMessage responseMSG=new ACLMessage(ACLMessage.INFORM);
responseMSG.addReceiver(new AID(clientName,AID.ISLOCALNAME));
responseMSG.setContent(response);
send(responseMSG);
agentServerGui.showMessage("==>>"+response);
```

## AgentServer

L'agent serveur est un agent qui permet de générer un nombre aléatoire entre 0 et 100. Il permet aussi de recevoir les réponses des clients et de les comparer avec le nombre magique.

```java
//Méthode de la génération du nombre magique
protected int magicNumber(){
        return (int)(Math.random()*100);
    }
```

Quand le client envoi un nombre, on envoie le message et l'identifiant du client à la méthode pour la comparaison.

```java
//Méthode de la comparaison du nombre magique et du nombre envoyé par le client
protected String guessNumber(Integer number,String clientName){
        if(number==magicNumber){
            magicNumber=magicNumber();
            sendToAll(clientName);
            return "Bravo\nRénitialisation du nombre!";
        }else if(number<magicNumber){
            return "Plus grand";
        }else{
            return "Plus petit";
        }
    }
```

Si le nombre est trouvé, on envoie un message à tous les clients pour les informer que le nombre a été trouvé et qui l'a trouvé.

```java
//Méthode d'envoi du message à tous les clients
protected void sendToAll(String clientName){
    ACLMessage responseMSG=new ACLMessage(ACLMessage.INFORM);
    responseMSG.setContent("Le nombre a été trouvé par "+clientName);
    for (String client:clients) {
        responseMSG.addReceiver(new AID(client,AID.ISLOCALNAME));
    }
    send(responseMSG);
}
```

---


