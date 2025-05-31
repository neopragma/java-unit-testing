# Unit testing and test-driven development in Java

## Walkthrough 

This repo supports a presentation and workshop. Some notes about running the examples are provided below. This is not complete documentation for self-guided learning. The live session has the following general agenda:

1. (Talk) Testing vs. checking 
1. (Talk) Test automation pyramid 
1. (Code) Writing unit tests for existing code 
1. (Code) Guiding new development and modifications from unit tests
1. (Code) Unit test suite as a safety net for refactoring
1. (Code) Refactoring 
1. (Code) Mocking dependencies 
1. (Code) Using an LLM assistant 
1. (Talk) When Unit Testing Is Not Useful
1. (Talk) Libraries, Frameworks, and External Resources
1. (Talk) What about unit testing user interfaces?
1. (Talk) What about testing "glue" code?
1. (Talk) What about metrics related to unit testing?
1. (Talk) Where do unit tests fit in a CI/CD pipeline?
1. (Demo) Property-based testing 
1. (Demo) Mutation testing 
1. (Talk) Exploratory testing 
1. (Talk) Testing after deployment

## Running the examples 

Some commands are saved under ```snippets.txt``` so you can copy/paste them into the command line if you wish.

### Run the unit tests for the Age module

```shell 
cd age
mvn test 
```

### Run the unit tests for the RPN module

```shell 
cd rpn
mvn test 
```

### Prepare the rpn jar for re-use  in the front-end samples 

The ```mvn package``` command will build a jar and place it in the ```target``` subdirectory of the ```rpn``` module. 

The ```mvn deploy``` command will copy the jar into a local subdirectory specified as a "repository" in the top-level ```pom```. 

At that point the console and GUI sample front-ends can pick up the ```rpn``` jar when they build.

```shell
cd rpn 
mvn package 
mvn deploy:deploy-file \
    -DgroupId=com.neopragma \
    -DartifactId=rpn \
    -Dversion=1.0.0 \
    -Durl=file:../local-maven-repo/ \
    -DrepositoryId=local-maven-repo \
    -DupdateReleaseInfo=true \
    -Dfile=target/rpn-1.0.0.jar
```

#### Warning 

_The above steps "should" work, but I've been unable to get Maven to recognize a local repository at all when building a module that has a dependency on a jar built from another module. I used the crude workaround of copying ```RPN.java``` from the ```rpn``` module to the ```rpnconsole``` module and compiling them together. No doubt a brain fart on my part. If you can fix it, please share the solution._ 

### Run the calculator with the console front-end 

```shell 
cd rpnconsole 
java -jar target/rpnconsole-1.0.0.jar
```

### Run the calculator with the GUI front-end 

```shell 
cd rpnconsole 
java -jar target/rpngui-1.0.0.jar
```


###---------- resume here ----------


## Mocking dependencies, refactoring

The sample code calls a public API and uses the result to insert data into a local sqlite3 database. 

### Setup the database

If necessary, create the database. 

```shell
> cd [project root]/foodie
> sqlite3 data/food4thot 
sqlite> .databases 
``` 

Expect to see food4thot as the current (main) database, with read/write access enabled, like this:

```shell 
main: [local-absolute-path-to-project-root]/foodie/data/food4thot r/w
```

Enter .quit to exit the REPL. 

```shell 
sqlite> .quit 
``` 

To initialize the test database:

```shell 
cd [project-root]/foodie
sqlite3 food4thot < data/init_db.sql 
``` 

##-------- tbd ----------

### Run the starter code for foodie 

```shell 
cd [project-root]/foodie/starter 

python3 food_run.py '0 44300 00012 4'
```

If the UPC code is no longer valid, look for another one here: https://www.upcitemdb.com/upc/44300000124. The sample code doesn't depend on any particular food item, provided the description matches the format used by the API, https://world.openfoodfacts.org/api/v0/product/.

Examine ```food_run.py```, ```foodie.py```, and ```food_storage.py``` and think about how you might isolate different parts of the code for automated checking ("testing"). You will have to do some refactoring to enable this. A sample solution is provided under ```foodie/solution```. 

### Create a "golden master" for Approval Testing

If you want to use the Approval Testing approach when refactoring the 'foodie' code, you can run this script:

```shell 
cd [project-root/foodie/starter/]
/run.sh > golden_master.txt 
```

### Run the property-based tests 

```shell
python3 -m pip install hypothesis
``` 

Sample property-based test:

```shell 
pytest property_tests
```

### Run the mutation tests 

```shell
python3 -m pip install mutmut 
``` 

Run mutation tests

```shell
mutmut run

mutmut browse 

q
``` 

## Some resources

## Java jars used in this workshop 
