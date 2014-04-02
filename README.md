[CSCI3465-Assignment3](https://github.com/Glavin001/CSCI3465-Assignment3/)
====================

> Develop the "fridge magnet" client/server application.

## Download

Please see the [Releases](https://github.com/Glavin001/CSCI3465-Assignment3/releases) for precompiled builds.


## Grading

See assignment at http://cs.smu.ca/~jdeveaux/csci/3465/2014/assign3.html

### Features

- [&#x2713;] Fridge (server) contains letters placed randomly on the "door".
- [&#x2713;] Real-time syncing between all Kids (clients) observing the Fridge (server).
- [&#x2713;] When a kid (client) leaves (disconnects from) the fridge (server), the fridge gracefully removes that client's connection.
- [&#x2713;] Client/Server style as a variation of the Observer pattern as demonstrated in the ChatClient/ChatServer programs.
- [&#x2713;] Must implement this application using Sockets to establish the client/server connection.
- [&#x2713;] Include your name in the corner of your frame.
- [&#x2713;] [Detailed instructions on how to get the client and server to communicate to each other. See documentation below.](https://github.com/Glavin001/CSCI3465-Assignment3#usage-instructions)

### Full Details

> Develop one (or more) versions of the "fridge magnet" client/server application.
Your fridge (server) contains letters placed randomly on the door.
Your kids (clients) will approach the fridge and be allowed to move some of the letters.
Multiple kids can move letters on the fridge (although not necessarily the same letter) simultaneously
When one kid moves a letter, all of the other kids will see that letter move, in real time
When a kid leaves the fridge, the fridge doesn't fall to pieces (graceful connection termination)

> This assignment is meant to be written in a client/server style as a variation of the Observer pattern as demonstrated in the ChatClient/ChatServer programs.

> At the very least, you must implement this application using Sockets to establish the client/server connection.
Bonus marks will be awarded for including a version where the client and server communicate with each other using RMI or SocketChannels

> Provide DETAILED INSTRUCTIONS on how to get the client and server to communicate to each other:
> - On the same machine
> - On separate machines

> Be sure to include your name in the corner of your frame! 

> Notes:
> Your assignments will be marked based on the effectiveness of your solution, as well as the way your make use of classes and interfaces in your solution.


## Documentation

### JavaDoc 

See http://glavin001.github.io/CSCI3465-Assignment3/Assignment3/doc/

### Usage Instructions

#### Server [CLI](http://en.wikipedia.org/wiki/Command-line_interface) (Fridge)

```bash
java -jar FridgeServer.jar <port>
```

##### Optional Arguments

- `port` - Default is `6666`.

##### Basic Example Usage

Open and execute the `FridgeServer.jar` file or run the following command:

```bash
java -jar FridgeServer.jar
```

##### Advanced Example Usage

```bash
java -jar FridgeServer.jar 6666
```

#### Client [CLI](http://en.wikipedia.org/wiki/Command-line_interface) (Kid)

```bash
java -jar KidClient.jar <hostname> <port>
```

##### Optional Arguments

- `hostname` - Default is "`localhost`".
- `port` - Default is `6666`.

##### Basic Example Usage

Open and execute the `KidClient.jar` file or run the following command:

```bash
java -jar KidClient.jar
```

##### Advanced Example Usage

```bash
java -jar KidClient.jar localhost 6666
```

#### Same Machine

1. Start the Fridge (Server) .jar executible.
2. Start the Kid (Client) .jar executible.
3. Repeat step 2 for as many clients as desired.

#### Separate Machines


