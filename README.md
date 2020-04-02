# LogisticsDeliveryMovement
Logiostics Delivery of packages using train to different stations
* You are modelling a logistics system.
* You have number of train stations,
* stations can be connected with routes,
* each route has positive time cost (time that it takes to move from one station to other connected by
that route),
* each route is bidirectional if a train can move trough that route it can also return back,
* stations can have different packages/cargo in them which needs to be delivered to some other station,
* you also have trains, each train has max cargo capacity,
* trains can carry more than one package if the total cargo capacity does not exceed the limit,
* trains cannot deliver part of the package,
* loading and unloading packages has no time cost,
* multiple trains can be at the same station, route at the same time,
* trains cannot turn back while moving from station to station.
#### example input
3 // number of stations
A // station name
B // station name
C // station name
2 // number of routes
A,B,3 // route from A to B that takes 3 units of time
B,C,1 // route from B to C that takes 1 unit of time
1 // number of deliveries to be performed
D1,A,C,5 // delivery D1 with weight 5 located currently at station A that must be delivered to
station C
1 // number of trains
T1,B,6 // train T1 with capacity 6 located at station B

## To 
performs all the deliveries,
// example output
current time -> 0 | train T1 is moving B -> A (1/3)
current time -> 1 | train T1 is moving B -> A (2/3)
current time -> 2 | train T1 is moving B -> A (3/3)
current time -> 3 | train T1 arrived at A
current time -> 3 | train T1 loaded D1
current time -> 3 | train T1 is moving A -> B (1/3)
current time -> 4 | train T1 is moving A -> B (2/3)
current time -> 5 | train T1 is moving A -> B (3/3)
current time -> 6 | train T1 arrived at B
current time -> 6 | train T1 is moving B -> C (1/1)
current time -> 7 | train T1 arrived at C
current time -> 7 | train T1 unloaded D1
Collapse



Things to consider for solution
* Language used Java8
* Build tool Maven
* Test library : Junit
* There are test cases for multiple scenarios which you can directly run from the test section to test the entire flow. Name of the test class : MovementControllerTest.java
* The main function will also work which takes input from command line the way it is mentioned in the problem pdf
* Right now the solution process the request one-by-one for deliveries but it is easily extendable to run multiple trains at a time.
* In the entire network to go from source to destination I am calculating shortest path and based on that only I am moving the trains.
* For shortest path I have used Dijkstra shortest path algorithm.
* Calculating all the shortest path between all the stations beforehand and storing it into HashMap<String, HashMap<String, ArrayList<String>>>. We are constructing this Map just once and whenever we need to get the source to dest path we will get in just O(1) time which will save a lot of processing time
* have made the code very modular and testable at every leg so that we can unit test the code easily and will be able to extend the solution further.
