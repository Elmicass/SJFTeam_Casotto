# Progetto Casotto

E' un progetto realizzato in Java mediante il framework SpringBoot per il corso di laurea Informatica presso l'università di Camerino per l'esame di Ingegneria del Software (IDS)



## 🗃️ Contenuti

- Di cosa si occupa il software
- Processo di sviluppo
- Tecnologie applicate
- Autori


## ❓ Di cosa si occupa il software
L'obiettivo finale è la realizzazione di uno Chalet smart al fine di garantire, attraverso l'uso di un software, la gestione della spiaggia con prenotazione di ombrellone (dotati di sdraio e/o lettini), servizio bar e attività.
### Prenotazione degli ombrelloni
La prenotazione si riferisce a svariate fasce orarie (mezza giornata / giornata intera / piu giorni), ovviamente il prezzo della prenotazione varierà in base al tempo prenotato e da politiche che il gestore dello chalet potrà imporre.
### Servizio bar
Oltre alla prenotazione di ombrelloni come scritto sopra lo Chalet smart fornisce poi un servizio bar per i clienti che hanno prenotato gli ombrelloni. Il cliente potrà dunque accedervi potendo selezionare prodotti i quali saranno poi gestiti e portati dai dipendenti dello chalet.
### Organizzazione e prenotazione di attività
Lo chalet infine prevede anche l'organizzazione di attività dedicate agli ospiti che possono essere di natura ludico/sportiva. Per partecipare alle suddette attività gli ospiti dovranno prenotarsi, alcune di queste saranno con posti limitati e sarà quindi necessario gestire il numero masimo di partecipanti.

### Gestione dello Chalet Smart
Il sistema prevede ovviamente funzionalità accessibili al gestore al fine di definire le caratteristiche dello Chalet stesso quali la spiaggia, le attrezzature, le attività e tutto il necessario al fine di garantire il corretto funzionamento del sistema
## 🕑 Processo di sviluppo
Per sviluppare l'applicativo è stato scelto di seguire il processo standardizzato Unified Process (UP), processo iterativo incrementale, utilizzando come strumento di lavoro Visual Paradigm basato sul Unified Modeling Language (UML).

Sono state svolte in totale 4 iterazioni dove è stato possibile effettuare l'analisi dei requisiti, la progettazione del sistema, l'implementazione e la fase di testing.

Le varie iterazioni hanno dato origine ai seguenti artefatti:

- Diagramma dei casi d'uso: raccolta e specifica dei requisiti e funzionalità del sistema.
- Diagramma classi di analisi: identificano i concetti che è necessario il sistema rappresenti e sia capace di manipolare.
- Diagrammi di sequenza: descrivono come le classi di analisi interagiscono tra di loro per realizzare il comportamento definito nei casi d'uso.
- Diagramma classi di progetto: per derivare le classi di progetto partendo dalle classi di analisi, il diagramma verrà utilizzato per le attività di implementazione.
- Code Base (attività implementativa)
- Package Diagram generato dal codice automaticamente in modo retroattivo
## 🧪 Tecnologie applicate
Dal lato backend è stato utilizzato il linguaggio Java in accoppiata all'uso del Framework Spring Boot. Nella parte di sviluppo non è stata trascurata la fase di testing al quale è stato fatto affidamento al framework JUnit. Come strumento di building automatizzato del sistema è stato usato Gradle.

Per quanto concerne la persistenza delle informazioni processate a livello di back end si è deciso di sfruttare i servizi offerti dal DBMS relazionale MySQL e dal relativa libreria di Spring Boot.

Per questioni di tempo il lato frontend è stato usato un approccio Console View, quindi gli output e gli input richiesti e ottenuti all'utente sono tutti tramite console.
## ✒️ Autori
- [Micarelli Simone](https://github.com/Elmicass)
- [Nowak Leon](https://github.com/Chitarrina)
- [Mengascini Michele](https://github.com/menga28)
