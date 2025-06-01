# Presentation Notes 
## Unit Testing and TDD - Java 

_These notes go along with the slide deck, which is not included in this Github repo. Most of the subheadings in the notes correspond with the titles of slides. The notes for some of the slides are sparse; this document is not sufficient to prepare for the presentation._

_The notes provide a sense of what I intend to talk about during the workshop. You may prefer to focus on different things or make different points, or even express a conflicting point of view on the subject. You can expand some topics and omit others to meet the needs of your audience. Hopefully the notes will be useful._ 

This workshop is a basic, hands-on introduction to fundamental unit testing concepts and practices. This version of the workshop uses Java for the hands-on work.

The introduction is broad rather than deep. We'll touch on three kinds of executable tests that typically exercise code at the "unit" level, including example-based testing, property-based testing, and mutation testing. 

We'll practice writing unit tests for existing code, writing unit tests as executable specifications to drive out new code, and refactoring existing code and using mocks to enable isolated unit testing. We'll demonstrate property-based tests and mutation tests. We will not go into great depth in any of these topics. 

We'll clarify some terminology about testing that often causes confusion, as some terms are overloaded - that is, they have different meanings in different contexts. 

## Testing vs. Checking 

There are several ways to exercise software to discover what it does and verify whether it behaves as we want it to. People tend to refer to all the various activities as "testing," in a blanket way. It's unlikely this verbal habit will change. 

Yet, it's useful to keep in mind the distinction between repeatable, predictable activities and activities that require human creativity. Many professional software testers refer to the former as _checking_ the software, and reserve the word _testing_ for activities that require human creativity.

When we _check_ a software product, we're validating that it behaves as expected under controlled operating conditions and with predefined inputs. It's often feasible to automate software checks.

When we _test_ a software product, we're exploring its behavior to discover things about it that we don't already know, or for which we don't have predefined expectations to check against.

Unit testing is really a form of _checking_ software. It can be automated and included in a continuous integration / continuous delivery pipeline. 

## System Under Test (SUT) or Code Under Test (CUT) 

When we _test_ software we're usually working with the entire application. When we _check_ software we often exercise a subset of the application. We control the scope, the configuration, the execution environment, and the inputs passed to the portion of the application we're interested in checking. 

## Example-Based Testing 

Most of the executable test cases you will see and write are _example-based_ tests. Really, they are _checks_, but people say _tests_ anyway. Each case is an example of a _single interaction_ with a subset of the SUT. The entire _suite_ of tests may exercise the system pretty comprehensively. 

An example-based test case can exercise any subset of the SUT, large or small. Although there is no standard definition of a "unit" of software, when we write _unit tests_ we are usually trying to exercise the smallest subset of the SUT that can be run in isolation from the rest of the system. 

Just how small that is will depend on the characteristics of the programming language we're using. For Java, a unit test exercises one logical path through one method. 

## A check or test case or example 

With example-based testing, a single test case may be called a _test case_, an _example_, or a _check_. The most common term is _test case_.

## Test Automation 

Test automation is the practice of writing executable code that exercises the SUT, and setting it up to run automatically when a triggering event occurs. Typically, the triggering event is a commit to version control, which initiates the CI/CD pipeline. Automated checks are executed at various points in the pipeline to verify application behavior at different scopes. 

## A check can be automated if... 

People are often pretty casual about how they use the term _test automation_. Usually, all they mean is that the test cases are executable. That's fine for casual discussion, but there's more to test automation than that. 

In addition to being executable, the test case must not require any human intervention. If you have a script that prompts you for a userid or an IP address, that script can't be automated as-is. You'd have to modify it so that it provides any required inputs without human intervention. 

The test case also has to be _repeatable_. In the context of test automation, _repeatable_ means that every execution of the test case is identical. If you have to reset anything, change configuration settings, reload a database, or change input data between runs, then the test script is not repeatable. 

Finally, to be automated the test script has to start whenever appropriate without human intervention. Typically, the event that triggers execution is a commit to version control, and the thing that gets started is a CI/CD pipeline that includes any automated steps necessary to achieve the goal of the pipeline. This may include tests or checks, but also other things such as static code analysis, security scans, license checks, compiling source code, spinning up server instances, logging information for IT audits, and moving code between execution environments.

## Regression 

According to the American Heritage Dictionary, 5th edition, _to regress_ means "to return to a previous, usually worse or less developed state." 

People often say they want to "run regressions" or "do regressions" on a software product. I suspect that isn't quite what they really want to do. It's more likely that they want to _detect_ regressions before they find their way into production. 

_Regression tests_ help us do that. The _regressions_ as such are not what we want. 

## Regression Tests

Regression tests are _checks_. They compare the present behavior of the application with its previous (good) behavior. As such, they can usually be automated. Regression tests can be written at any level of abstraction, including the unit level. 

One benefit of having a comprehensive suite of unit tests is that they help us identify regressions early in the development process.

## Scope (and cost) of a check or test 

To check application functionality end-to-end may require considerable setup time, the check is susceptible to failure for a variety of reasons besides the functionality of the SUT, execution time may be long, and there may not be enough time available to exercise the application sufficiently - we may have to skip some of the checks. Yet it is important to check the entire application to a reasonable extent.

When we check functionality at a fine-grained level, we can fully control the runtime conditions, configuration, and input data for the SUT. These checks are small, fast, cheap to run, and easy to modify, but each individual check exercises only a very small part of the application. 

What we'd like to have is the appropriate mix of fine-grained, mid-level, and end-to-end checks to give us high confidence in the code and to detect regressions accurately and early.

The way to accomplish this is to check as much functionality as we can in small chunks, using test cases that are easy to set up, cheap to run, and fast. To check aspects of the system that can't be assessed in a meaningful way in very small chunks, we write test cases that exercise a larger portion of the application. 

Hopefully, we can avoid having to check much of the functionality by running the entire system, because those tests will be the most expensive, the slowest, and the most complicated to set up.

## Test Automation Pyramid 

The original test automation pyramid was built by the ancient Egyptians with the assistance of space aliens. Or not.

## It's a Triangle 

The test automation pyramid is really just a triangle. But pyramids are more impressive than triangles, so people like to call it a pyramid. No one visits Egypt to look at triangles.

## Many of these

The shape is meant to suggest that we want to write more fine-grained checks than large-scope ones, because they're fast, cheap, and point directly to the problem when they fail, so we don't have to spend much time in analysis to diagnose the problem. 

We want to cover as much functionality as we can at this level, so that we won't have to check that functionality with more-expensive and harder-to-maintain checks at higher levels. 

## Fewer of these

But not everything can be checked adequately at a fine-grained level. As we write checks of increasing scope, we want to continue to follow the philosophy of checking as much functionality as possible as low as possible on the pyramid...or triangle.

## Very few of these 

Ultimately we may need very few checks of large scope, like end-to-end tests. 

## Manual testing 

"Real" testing can't be automated, but is still important. We'll talk about a technique called Exploratory Testing later. As information about the behavior of the application is discovered through testing, we can often capture it in the form of executable checks once we understand what to expect and how to set up the conditions for the behavior in a repeatable way. 

## What's a "unit?" 

_group discussion_ 

A source of confusion about unit testing is the fact there's no generally-accepted or standard definition of "software unit." Let participants discuss this and see if different opinions emerge. Guide them toward the understanding that we usually think of a software unit as _small_.  

## Michael Feathers' rules for a unit test

If there's no standard definition for "unit" and no common understanding of the word "test," how can we define the characteristics of a "unit test?"

Michael Feathers, a well-respected software engineer and technical coach, came up with some rules of thumb for unit tests back in 2005. Many people in the industry still use this as the basic guideline for unit tests. 

The first three points are related to the fact we want the unit check to fail for one reason only - the actual behavior of the SUT does not match the expected behavior. We don't want it to fail because of a network timeout or incorrect data in a test database. That won't tell us anything useful about the state of the application. 

We also don't want the checks to depend on each other. The main reason is so we can run any one of them, or a subset that pertains to some particular aspect of the application, without having failures just because some other check wasn't executed first. 

Another reason is to enable us to run a large test suite on multiple threads or on multiple servers concurrently, in the interest of time. 

Finally, if we have to tweak the environment then the check is dependent on human intervention, and can't be automated. 

## Java Unit Testing 

Let's take a look at some code and see what unit checks, or unit tests, look like. You can fork and clone the repo shown on the slide and play with it to your heart's content. 

In directory ```src/main/java/age/```, take a look at the file named ```Age.java```. It contains a method named ```categorizeByAge()``` that takes an integer representing a person's age in years and returns a string description of the age range within which the person's age falls. So, if we pass in an age of 32, we'll get a description of "Adult." 

How would we write unit checks or unit tests for this code? In directory ```src/test/java/age/```, open file ```AgeTest.java```. 

Different unit test libraries, also called "frameworks," have different conventions for naming test methods and other details, but they generally perform the same functions. There will be a test runner that inspects the test code to locate and execute test cases. 

Here we're using a Java unit test library called JUnit. The test runner for JUnit looks for classes whose names end with "Test", and within those classes it looks for methods with the annotation @Test, as well as a few other annotations we'll look at later. It bypasses other methods. 

The method ```an_8yo_is_a_child()``` illustrates the _Arrange, Act, Assert_ pattern. This is common to all kinds of executable checks. First we need to set the preconditions for the test case. That's the _Arrange_ part. Next, we run the code under test. That's the _Act_ part. Finally, we assert the expected postconditions for the test case. That's the _Assert_ part. 

We don't normally include comments to mark the three parts of each test case. Method ```a_10_yo_is_an_adolescent()``` shows the way methods like this usually look. 

But it's also common to compress everything into as few lines as possible, since we'll probably have a very large number of these methods. Method ```a_21_yo_is_an_adult()``` shows how this style looks. 

Some general coding conventions are different between test code and production code. The most obvious one is probably the fact the test method names are written with _snake case_ instead of _camel case_, as normal Java method names are written, 

The reason for this is that test method names tend to be quite long compared with production method names, and many people find it easier to read long names in snake case. This is not a universal convention, and you will see many test method names in camel case. 

We don't usually write hard-coded literals in production code, but it's common to do so in test code because we want to see at a glance what values were input and what values are expected in each test case. 

We don't want to have to scroll up and down or look at a separate source file to find the values that were used in the test case, when we're trying to resolve a problem. It comes down to a difference in how we use test code, and how we organize production code for maintainability.

## Arrange-Act-Assert 

We've been referring to the the three-part pattern for test cases as Arrange-Act-Assert. The same pattern goes by other names, too. They all mean the same thing. 

## Preconditions-Action-Postconditions 

Another set of terms for the same thing.

## Prepare-Run-Expect 

Another set of terms for the same thing.

## Given-When-Then

Another set of terms for the same thing. This one is commonly used with a related approach called _Behavior-Driven Development_. It's the same idea - set up the preconditions, exercise the SUT, assert the desired or expected postconditions.

## Changing Existing Code and Tests 

Let's say we wanted to add logic to this application to recognize people between the ages of 13 and 17 as "teenagers". We'd have to make a couple of small changes to both the production code in ```Age.java``` and test code in file ```AgeTest.java```. 

There are a couple of approaches to this. Traditionally, people would first make the changes to the production code and then add or modify the relevant test cases in the test code. Some people call this approach "test-after" development, because you write the test cases after you've modified the production code. 

A more contemporary approach is first to adjust the test cases to reflect the behavior we want to see. When we've verified that the new or modified test cases fail because we haven't changed the production code yet, and not for some silly reason like a compilation error, we modify the production code so that the new test cases as well as the rest of them pass. 

This approach is called _Test-Driven Development_, or TDD, because you use the failing test cases to guide or "drive" the necessary logic in the production code. 

So, we want to see the result "Adolescent" for ages between 10 and 12, and the result "Teenager" for ages between 13 and 17. 

Let's try the traditional "test-after" approach first. Make the changes to ```Age.java``` and then modify the test cases in ```AgeTest.java```. 

You'll notice there are two sample test cases for _parameterized tests_. One uses the ```CsvSource``` option to provide values for the test case, and the other uses the ```MethodSource``` option.

JUnit support for parameterized tests is robust and flexible. Numerous options are available, which you can find in the documentation, by searching online for examples, or by asking your favorite LLM coding assistant to generate a sample.

_let participants work - assist as needed - discuss outcomes and how it felt_ 

## Shift Left Testing 

A long time ago, people came to realize that delaying testing until late in the delivery process leads to all kinds of trouble. Problems were not detected until most of the project schedule was used up and most of the budget spent. 

People started talking about shifting testing left, meaning starting testing activities earlier in the delivery cycle. This is assuming we read from left to right, of course. 

Before long, some people were shifting testing - or some of it, anyway - so far to the left that it happened before coding. Since there was no code for the tests to test, this changed the dynamic of software development in some ways. 

## Java TDD

Okay, now revert your changes. Get the code back into the state is was in when you started. 

Let's make the same changes, but this time modify the test cases first. 

One thing about the TDD approach is that we don't make all the changes at once. First change the test cases that verify the "Adolescent" age range. When those cases fail for the right reason, modify the production code just enough to make them pass. Make sure all the other existing test cases pass, too. 

Now examine the test code and the production code to look for any opportunities to simplify the design. There probably won't be any such opportunities in this example, but it's a good idea to take a moment to look for opportunities anyway, rather than just assuming.

Now that the application behaves the way we want it to for "Adolescents," repeat the same steps to add logic for the new "Teenager" category. 

_let participants work - assist as needed - discuss differences in the 2 approaches_ 

## Red-Green-Refactor Cycle 

What did we just do with the age class? It turns out there's a method to the madness of TDD. Mechanically, it's a repeating three-step process. Of course, there's more to TDD than just that, but those are the bare mechanics of it. 

The test-driven development process is often depicted as a circle with three steps labeled _red_, _green_, and _refactor_. The colors red and green are used in software development tools that display the results of executable test cases. As you might guess, red represents a failed test case and green represents a test case that passed - that is, its assertion was true. 

## Test-Driven Development Mechanics 

We want to repeat this cycle in very small iterations. That way, when something goes wrong we immediately know where to look for the problem in the code; it will be the very last change we made. Because of this, people who use TDD rarely use a debugger during development. 

But we don't blindly start writing just any old thing. The TDD cycle occurs as part of our overall development process. Before we start coding, we have to think about what small piece of functionality we'd like to create next. We might identify several test cases that will help us arrive at our goal. Then we pick the one we'd like to start with, and write just that single example. 

Red doesn't mean just any old shade of red. The test case has to fail for the right reason. What's the right reason? When the SUT runs, the result doesn't match our expectation, as expressed in the assertion in the test case. Other reasons for failure don't count. We have to work through those just to reach our starting point - a meaningful red. 

Then we write just enough code to make our new test case pass, along with all the previous ones. We don't write any more production code than that, even if it's obvious we haven't developed much of the necessary logic yet. We want all the logic to be driven out by test cases, one by one. 

And we want to keep the code clean little by little, as we go along, rather than building up a lot of design debt. 

Sounds simple, right? Well, as the old saying goes, the devil is in the details.

There are a lot of lessons learned over the years about how to get the best value from TDD. Those details are out of scope for this workshop. We're just getting a taste of various things related to unit-level functional checks. But if you want to give TDD a serious try, there's plenty of information about it.

_discuss slide content_ 

## If we write tests before production code...

_Facilitate a group discussion. We want the group to arrive at a couple of points: (1) during development, TDD is purely a design and development process; (2) once the code has been written, the unit test suite that results from TDD becomes a regression test suite. So, TDD is partly a design process and partly a testing process, depending on where we are in the development cycle and the reasons why we're writing a test case._ 

## Reverse Polish Notation 

Let's try test driving a complete application from scratch.

_describe what RPN is if any of the participants is unsure about it._ 

## Test-Driving a Reverse Polish Notation Calculator 

Create a subdirectory off the project root directory named ```rpn``` and cd into it. Under ```rpn/``` create two directory trees: ```src/main/java``` and ```src/test/java```. 

At this point you should have a directory structure like this:

```shell 
[project root]/
    rpn/ 
        src/main/java/
        src/test/java/
```        

In both ```src/main/java``` and ```src/test/java```, use the _new -> package_ feature of IntelliJ IDEA to create a package named ```com.neopragma.rpn```. Build your RPN solution there.

_let participants work - assist as needed - debrief_ 

# Refactoring 

We spend far more time working with existing code bases than we do writing greenfield code. We did a little of that when we modified the age categorization application to support "Teenagers." 

But in real life it isn't usually as simple as that example. We often have to change the structure or internal design of the code in order to make the changes we need, and keep the code in a form that's reasonable for unit testing. 

There's a buzzword for changing the internal structure or design of code while keeping its behavior the same - _refactoring_. 

You'll hear people using the word "refactoring" as a synonym for "change" - any kind of change. But it specifically means changing only the internal structure or design of code without changing the behavior of the code. 

Isn't it risky to make changes to code that might accidentally change its behavior? Changing behavior is the same as breaking functionality, right? How can we feel confident that our refactorings don't break something? 

_let the group answer. desired answer is the unit test suite is our safety net for refactoring_ 

Refactoring code tends to involve the same changes over and over. Martin Fowler developed a catalog of common refactorings. Later, Joshua Kerievsky published a book on refactoring to patterns - that is, the Design Patterns described in the Gang of Four book long ago. 

IDEs like IntelliJ IDEA, as well as smart editors, include some built-in support for basic refactorings. 

_demonstrate how to find refactorings in the IntelliJ IDEA UI._ 

## Unit Tests as a Safety Net for Refactoring 

In a unit test case you can make assertions about any aspect of the SUT. 

When the test cases are agnostic about the underlying implementation of the SUT, and they only verify the visible behavior of the SUT as seen through its public APIs, then tests that pass before refactoring the production code will still pass afterward. This provides a sort of "safety net" for refactoring. 

Refactoring is not supposed to change the behavior of the SUT as seen through its public APIs. So if an implementation-agnostic test case fails after refactoring, it's a signal that you've inadvertently "broken" some logic.

On the other hand, if a test case depends on the underlying implementation of the SUT to pass, then refactoring the SUT might change that implementation and cause the test case to fail. Then you can't be sure whether you've broken some logic that was working before you refactored. 

The rule of thumb is to write test cases such that they don't depend on or even know about the underlying implementation details of the SUT. Just make assertions about the values returned from methods you're checking. 

As with anything else in our field, this is not a universal rule. Check implementation details if you must, but try to avoid it as much as you can.

## Approaching a Code Base That Lacks Unit Tests 

An all-too-common situation is that we must make changes to an existing code base that doesn't have any executable tests to provide a safety net. 

Once we have a few tests in place, it becomes easier to work with the code. But initially, it may not be clear how we can _begin_ to work with the code. 

## Foodie: Legacy Code with Multiple Concerns 

Quite often, when unit tests are written after the production code - or when they're never written at all - the production code tends to violate one of the most fundamental software design principles of all - separation of concerns. Different concerns are jumbled together in the same source units. This leads to poor cohesion and tight coupling.

Code like this can be difficult to unit test unless we break out the different concerns and/or set up a lot of mocks and stubs to fake out the portions of the code that aren't of interest to each particular test case.

In the ```foodie``` module, take a look at the Java source files ```Foodie.java```, ```FoodStorage.java```, and ```FoodRun.java```. Think about how you might set up isolated unit tests for this code.

_Facilitate a discussion._

See if you can refactor the code to make it easier to unit test.

_Let participants work on this a while. They don't necessarily have to finish._

_Facilitate a discussion about how easy or hard they found it to get meaningful unit test cases around the code._

_Guide them toward thinking about the value of unit testing code like that, which consists mainly of interactions with external services - a network API and a database adapter. Bring Dude's Law into the picture and see if participants consider it worthwhile to write unit tests that only verify that the SUT made certain calls. Maybe it is, maybe it isn't. One thing to consider is that test cases like these are implementation-aware and therefore can't be relied on as a safety net for refactoring._

_Note: Your version of the repo has a module named ```foodie-solution``` that contains refactored foodie code and a sample unit test case for the API call. If they didn't get much done on their own, you can use this sample solution to support the discussion._ 

### Some ideas for refactoring foodie 

_These ideas are provided to help get things moving along in class, if necessary. You are free to approach the code in your own way. If you want to demonstrate the changes as they are documented here, you can copy the relevant versions of the files for each step from the ```copy-paste``` directory into ```src/main/java```, ```src/test/java```, ```src/main/resources```, and ```src/test/resources```; or you can make the changes live, or guide participants in doing so._ 

#### Step 1 - enable unit testing of the logic to create the HttpRequest object

The modified files for this step are in ```[project_root]/copy-paste/foodie-refactor-1/```. 

1. The constructor in ```Foodie.java``` creates the ```HttpRequest``` object to call the API. It is clumsy to isolate this logic to facilitate unit testing. In the sample solution, the logic is extracted into method ```createRequest()```, and the constructor calls that method. This is still suboptimal. We need a do-nothing constructor for ```Foodie``` with ```package``` visibility so that we can exercise the ```createRequest()``` method in test cases. 

1. Added unit test cases for the ```createRequest()``` method in ```Foodie```. 

#### Step 2 - externalize the URI for the food information API 

The modified files for this step are in ```[project_root]/copy-paste/foodie-refactor-2/```.

1. The hard-coded URI string in ```Foodie.java``` (named ```baseURLString```) could be externalized. The sample solution defines a Java property for this in ```src/[main|test]/resources/foodie.properties```, property name ```base.uri```. Method ```createRequest()``` reads this property. 



## Resources for Working with Existing Codebases 

There are a lot of resources online and in print to help us with this problem. Here are a couple of the best ones. 

Michael Feathers' book is on a short list of programming books that stands the test of time, because it doesn't go out of date as technologies evolve. Besides that, it's useful for working with older codebases whose source code _hasn't_ actually evolved. 

Emily Bache based the Gilded Rose refactoring kata on previous work by Bobby Johnson, and expanded it into what has become the single most popular exercise for practicing refactoring techniques. We're going to do that exercise in a minute. 

## Techniques for Working with Existing Codebases 

People have had to work with existing codebases for as long as software has existed. Some have come up with practical techniques that help us get started with it. 

I think Michael Feathers is the person who coined the term "seams" to refer to places in monolithic code where there's an obvious place to separate out some of the logic - for some definition of "obvious." 

_show some of the code from Gilded Rose and show how the if/else blocks seem to suggest places where we could pull out chunks of code_ 

Another way to discover secrets about the behavior of monolithic legacy code is to introduce so-called _sensing variables_ and then write unit test cases that assert bogus values for the variable, in order to generate test failures. We can then see what the value of certain elements are at specific points in the complex logic. 

The sensing variables and the test cases for them will not remain in the code, of course. They're temporary tools to help us understand what the code is doing. 

_demonstrate adding a sensing variable and writing a unit test cases for it - then delete that code_

In the YouTube video linked on the slide, Sandi Metz walks through the Gilded Rose refactoring kata using Ruby and demonstrates a technique she called the _squint test_. It means we can get some clues about how to restructure the code by paying attention to the visual shape of the source code as well as the color highlighting provided by our editor. 

The problematic code in the Gilded Rose exercise has a zig-zag shape due to the repetitious if/else blocks. This gives us some clues about where we might break up the code or add sensing variables to explore the behavior of the code. 

An aside: What I'm describing here is _testing_. We're _exploring_ the code to learn more about what it does. We're using _the same tools_ we would use to write automated checks. Whether we're _testing_ or _checking_ isn't a question of which tools we're using; it's a question of what we're doing and why.

Back to the topic...

The boundaries of if/else blocks are often _seams_ where we can extract chunks of code into smaller methods. Once we've learned what the code is doing, we don't necessarily keep all those smaller methods. Method extraction may be one step in a longer process of understanding and remediating the code. 

When refactorings appear to be feasible, one approach is to go ahead and do them even while reading through the code for the first time. Arlo Belshee calls it _read by refactoring_. You don't just _look_ at the code, you go ahead and rip it apart as you go. In some cases this can help you understand what's going on in a big chunk of monolithic code.

_extract one or two chunks of if/else code to demonstrate the technique - then revert_ 

If you'd rather not risk confusing yourself by making the code even harder to understand, you can leave it as-is and write an _approval test_ for it. An approval test is not a unit check. It's a higher-level test that captures output from the SUT, usually in a file. 

Going forward, as we work with the legacy code we can periodically run the approval test to see if we've changed the application's behavior. The approval test doesn't tell us whether the code is correct, but it does signal us when we inadvertently change its behavior. 

The captured output file is called the _golden master_. We can compare the output from the application against the golden master at any time while working with the legacy code to see if we've inadvertently broken any logic. 

_run the provided test to produce a golden master - no need to remove this unless you want to_

Another way to provide a sort of safety net for modifying the legacy code is to introduce an abstraction layer between client code and the legacy application. That way, you can gradually change the underlying legacy code without affecting the client code. 

In a minute you'll see that the rules for the Gilded Rose kata prohibit us from touching the Item class. When you examine the code, you'll quickly realize the bad design stems from the Item class. If we could refactor it, the code for the updateQuality method could be much simpler and clearer.

In a situation like this, we can borrow a page from the _strangler_ pattern for gradually replacing an old implementation with a new one. That is, we can add an abstraction layer between the Item class and the updateQuality method. We can modify updateQuality to refer to an Item wrapper instance, and handle the peculiarities of the Item class there instead of all over the place in updateQuality.

## The Gilded Rose Refactoring Kata 

So we have some ideas for addressing the legacy code provided in the Gilded Rose exercise. There's no right or wrong way to approach it. 

_walk through the instructions and code and let the participants work on it_ 

## Using an LLM assistant 

_Several LLM plugins are available for IntelliJ IDEA, but none is free. JetBrains AI Free comes bundled with the IDE, including the Community Edition, but the "free" part is only a 30-day trial. It comes with the Ultimate Edition under the name JetBrains AI Assistant, with all features enabled. Copilot is available as a plugin, but it requires a paid account through Google._ 

_A crude workaround to avoid having to pay for anything is to open VSCode and use the included version of Copilot, then paste code into IDEA._

_If you're doing this training for a corporate client, they may have (should have) obtained professional licenses for their employees, with LLM support included._ 

_Using whatever LLM option that's practical in your situation, demonstrate the process of defining the name formatter via copilot for English names_ 

## Modifying Existing Code Using an LLM assistant 

Now it's your turn. Your task is to enhance the solution to support Spanish names. Spanish names have a different format from English names. 

A full Spanish name consists of the primer nombre, segundo nombre, primer appellido, and segundo appellido. Sometimes people omit the segundo nombre and/or segundo appellido when they provide their names on forms. 

It's a common convention to write the name as primer nombre, initial of the segundo nombre, primer appellido, and initial of the segundo appellido. 

Use an AI coding assistant to enhance the previous solution per the instructions on the slide. 

## When Unit Testing Is Not Useful

When people are first introduced to ideas like executable unit tests and test-driven development, they often take a binary view of the subject - that is, they assume it must be _all or nothing_. That isn't really the case. 

Executable unit tests can provide value in some situations and not in others. Test-driven development, understood in the strict sense, provides value in a substantial subset of the same situations. 

I often meet teams and individuals who worry excessively about how to unit test user interfaces, front-end code, and "glue" code that connects libraries or frameworks with application logic. It's been my experience that these parts of an application may best be checked one level of abstraction above the "unit" level, using what most people refer to as _integration tests_. 

It's useful to consider Dude's Law: Value = Why / How. 

When the _How_ is large and the _Why_ is difficult to quantify, the effort to develop executable checks at the unit level may not be repaid. When we consider the fact the majority of production issues are related to configuration - either application or environment configuration - or problems that arise dynamically in the runtime environment, and that the majority of "bugs" pertain to integration between software components and not mis-coded "business logic," the relative value of these unit checks becomes harder to quantify.

### How Much Automated Unit Testing Is Enough?

Before jumping for joy at the prospect of avoiding unit testing altogether, bear this in mind: People who have little or no experience writing unit tests or test-driving their code tend to draw the line betweeen "valuable" and "not valuable" in a very different place than do more-experiened people. If you're new to this, give yourself a fair chance to build up a solid base of hands-on experience before making judgments without guidance. 

### Generated Code 

Source code that is automatically generated from a tool doesn't have to be test-driven or explicitly unit tested after the fact. 

Consider Oracle's Application Development Framework, or ADF. Using an Oracle database schema as input, ADF generates a complete, functional CRUD application in Java. It can produce either a webapp or a thick client app. There is no value in trying to test-drive the Java code, and no practical way to do so since it's all based on XML templates. 

With a basic CRUD app in place, we can write custom Java classes (POJOs - plain old Java objects) to provide functionality unique to our solution, and drop them into predifined spots in the request/response cycle where the ADF runtime will call them. Those POJOs can and should be test-driven. 

Another example of this sort of tool is CA-Telon, currently owned by Broadcom. Telon generates a boilerplate application that can be customized. The generated code doesn't have to be explicitly unit tested; only the custom code that we add to the solution. 

A smaller example of generated code is the ability of most IDEs to generate Java getters and setters, C# properties, and similar code elements for other languages. As long as we stick to the convention that a getter/setter contains no logic except to pass through to a non-public instance variable, and we don't hand-code the getter/setter, we can dispense with unit testing it. 

It's the hand-coding that raises the need to unit test getters and setters, not the complexity of the logic within them. As long as they are automatically generated, we needn't unit test them.

### Libraries, Frameworks, and External Resources  

Don't try to unit test the third-party libraries and frameworks your application uses, or the functionality of external resources such as network-hosted services or database management systems. Their owners are responsible for that. Your job is to support your application. 

When you need to unit test a piece of your own code that interacts with a library or framework, you can mock out the APIs to provide consistent replies to your unit tests. 

Avoid the practice of asserting that your code issues exactly _n_ calls to method _notYourCode()_ and the like. That makes your unit check implementation-aware and therefore unreliable as a safety net for refactoring. Instead, assert values that are returned from method/function calls.

### Command-Line User Interfaces 

For command-line user interfaces, it's usually feasible to capture stdout, stderr, and output files as well as to fake stdin and input files. Then we can make assertions about what the code writes to these targets when specific inputs are provided through stdin and/or files. 

In some cases, there may be some value in doing this. If we're diligent about applying the software engineering principle known as _separation of concerns_, then there will rarely be any interesting logic in the code that handles command-line interaction. Most issues will become apparent the moment we try to execute the application. 

That code will use language-specific or OS-specific calls to receive and send input over the standard streams, to pick up command-line arguments, and possibly to read and write files. It will invoke an entry point in the application, and handle program termination when the application exits. And that's all. 

The effort needed to setup and maintain unit tests for that kind of code may not return much value. 

### Web Front-Ends 

As of this writing, the majority of interactive applications that present a graphical user interface are Web-based applications, or webapps. The front-end for a webapp has to follow the rules for HTTP and ultimately the GUI widgets have to boil down to HTML, one way or another. 

That means all Webapp front-ends will have many characteristics in common. They are almost, but not quite, the same front-end over and over again - if you take a crude view of it and don't ask too many questions about details.

Conceptually, a Webapp front-end consists of the following: 

- structure 
- layout 
- look and feel 
- accessibility
- localization 
- security
- content 
- dynamic behavior 
- perceived response time

Structure, layout, and look and feel might sound like different terms for the same thing. Let's break them apart to see which of them may be unit testable, and whether it's worth doing so.

#### GUI Structure

By _structure_ I mean the component parts of each HTML document; things like the header, footer, sidebar, menu bar, and main content area. There may be a specific place where your company logo should appear. There may be a required favicon. There may be required meta tags.

When your company has standards for these things, it's possible to write unit checks that verify each HTML document contains the required elements, typically organized in the document body as DIV elements that have unique IDs or names. The elements may be defined in (or generated as) a single unit, or they may be assembled at runtime from different sources. 

The fact the structure of an HTML document _can_ be unit tested doesn't automatically mean it's _worth_ unit testing it. 

#### GUI Layout

By _layout_ I mean the absolute or relative positioning of the structural elements of an HTML document within the viewport. Some front-end designs have floating elements, which makes checking the layout more challenging. In my experience, it's nearly always unnecessary to try and unit test the layout. 

#### GUI Look-and-Feel

By _look and feel_ I mean the way the UI is perceived by the user. We can check that appropriate backgrounds, colors, and fonts are used, and that certain media filenames are referenced in the HTML tags, but we can't automatically check what all of that actually looks like to a human. 

#### GUI Accessibility

_Accessibility_ is closely related to _look and feel_. The term refers to HTML elements that are meant to assist users who have one or more difficulties using a computer, such as visual impairment, hearing impairment, and movement impairment. 

It's possible to verify that the HTML document and the elements it contains have the appropriate tags and values to support accessibility standards, but doing so has the same limitations as checking _look and feel_ generally - the presence of the appropriate HTML tag doesn't tell us anything about what a user actually experiences when using the application. This is usually verified more effectively during Exploratory Testing than through automated checks. 

#### GUI Localization 

A localized webapp will have its content generated dynamically, so there's nothing in the HTML document about which we can make assertions until the document has been rendered for a given locale. It's extremely tricky to write unit checks for this, and they will almost certainly be unreliable. Dude's Law steers us away from trying to unit test this aspect of the UI. 

Tests or checks around localization are more commonly written against the mid-tier or back-end code that handles localization. We can assert the code supports _internationalization_ without having to know the exact _localized_ values of text. Other aspects of localization, such as color schemes, choices of photos for display, and decorative visual elements such as national flags or small animations, bleed over into the realm of _look and feel_. 

In addition, localization is typically not within the scope of the software development team; it's handled by experts in the languages and cultures of the locales to be supported. The programmatic side of it is _internationalization_. 

#### GUI Security

Security for webapps usually comes down to configuration settings, for instance to disallow cross-site scripting. It's possible that JavaScript/TypeScript code running in the front-end will enable security exploits. That's _partially_ covered in our unit checks for functions in the scripts, but can't be adequately checked using automated tests. 

#### GUI Content

The _content_ of an HTML document can be checked at the unit test level. Be aware that content is subject to change more frequently than any other part of a user interface. Unit checks that depend on an element containing an exact text value will be fragile. 

#### GUI Dynamic Behavior

The _dynamic behavior_ of a front-end is usually coded in JavaScript or a language that can be transpiled to JavaScript. Given modular design and functions that follow generally-accepted software design principles such as _referential transparency_ and _avoiding hidden side-effects_ can be unit tested effectively. (Bear in mind we're not checking the results of a round-trip to the back end at the "unit" level.)

In my experience, there is value in doing this. However, if a JavaScript function only changes the appearance of an HTML element, and otherwise contains no "business logic," there's limited value in unit testing it. 

#### GUI Perceived Response Time

I guess it's pretty obvious that _perceived response time_ won't be testable at the "unit" level because in order to measure it we have to make round trips to the back end. In fact, we'd have to make a whole lot of round trips under different loads and operating conditions. Far out of scope for unit checks.

#### Thick-Client GUIs 

Thick-client GUIs have mostly the same general characteristics as webapp GUIs. For purposes of unit testing, the main difference is that thick-client GUIs are usually written in the same programming language as the rest of the application. That makes it easier to write unit checks against the GUI components. 

On the other hand, most thick-client GUIs are built using tools that generate GUI widgets and decorate them with the details we want, such as labels, conditional visibility, and response to user gestures. Most of the time, thick-client GUIs fall into the same category as other forms of automatically-generated code. Their functionality is more easily checked at the integration/functional level than the unit level.

#### Mobile Device GUIs 

If you use Android Studio (or similar) or iOS development tools to develop front-end apps for mobile devices, the tooling will generate boilerplate examples of unit tests and instrumentation tests. Unfortunately, most people ignore these and build applications that have no tests. 

## What About Unit Testing "Glue" Code? 

Many applications make use of various libraries and frameworks to handle common functionality that isn't part of the unique business logic of the solution. For instance, a Java webapp might use Java Server Faces; a Python webapp might use Django; a Ruby webapp might use Rails. 

A rule of thumb for designing such applications is to separate the "business logic" bits from the bits that interact with the framework. The bits that interact with the framework contain only enough logic to connect the dots between the framework and the application. Some people call that "glue" code; it glues the application to the framework.

Typically, there's no need to unit test glue code separately from the rest of the application. Any issues with it will pop up immediately during integration testing. The most we can do with it is to assert that our code issues the correct calls to the framework's APIs; that's an implementation-aware check and therefore fragile. 

### What About Solutions Based on "Cloud" Resources?

It's possible to build a wide range of useful solutions using Internet-based resources such as Microsoft Azure, without hand-coding most (if any) of the application in a conventional programming language. 

In many cases, the lowest level of the "pyramid" where it makes sense to build automated checks is somewhere above the unit level. It may be feasible to write unit checks for individual units of custom code that we integrate with the tool's built-in workflow or pipeline facility. However, a lot of that code ends up being "glue" code. Keep Dude's Law in mind when deciding how much of this code to unit test.

## What About Metrics Related to Unit Testing? 

It's not uncommon for people to get carried away with test-related metrics. The most popular metric for abuse is _code coverage_, especially line coverage. The value of this metric is already questionable, and the value of trying to achieve a coverage target doubly so. 

A clever programmer can easily game code coverage metrics. Spending time doing that instead of on developing meaningful unit tests is waste. 

One of the most egregious examples I've seen was in a Java codebase. Java enums are implemented such that the compiler generates a few lines of source code that are then compiled down to bytecodes. These lines are not written by application developers, but code coverage tools still count them as "source." 

To achieve 100% line coverage, a clever programmer wrote a fairly convoluted test class that would cause the generated source lines in Java enums to be counted as "covered" by the coverage tool. I'm sure it was an interesting puzzle to solve; but please don't do this sort of thing.

Another common issue with using test-related metrics is the idea that a certain percentage of failing tests is acceptable. Teams will set a standard that code can't be merged unless at least _X_% of unit tests are passing. 

In reality, something is wrong if you have _any_ failing test cases. You should have a look at your test suite to see why the tests fail. If they have been accepted by the team more than once, it's probably because they aren't valid or useful, and people have learned to ignore the failures. Do something about that instead of accepting a passing rate under 100%.

If we don't look at code coverage, how will we know whether we're improving? Improving the quality and usefulness of your executable checks will result in visible changes in delivery metrics. Cycle Time and Lead Time will decrease. Instances of back-flows from testers to programmers to fix issues prior to deployment will decline. 

An interesting metric supported in SonarQube is called Cognitive Complexity. It measures the relative difficulty of understanding and working with the code. If you see a downward trend over time in Cognitive Complexity, it's because you've been taking practical steps to improve your delivery effectiveness. 

Increasing your unit test coverage may be one of the beneficial things you've been doing that influences meaningful metrics, but avoid the trap of focusing on coverage metrics for their own sake.

## Continuous Delivery Pipeline 

Unit tests fit into a CI/CD pipeline near the beginning, after any forms of static and dynamic code analysis that might be part of the pipeline and before any executable tests of greater scope than a "unit." 

## Checkpoint 

Here's what you've learned so far. 

## Other Kinds of Checking/Testing 

So far, everything we've covered falls under the heading of _example-based tests_. Each test case is an example of a single interaction with the SUT. 

Of course, we need to run executable checks and perform creative testing activities on larger subsets of the solution than individual units of code. That's what the test automation pyramid illustrates. 

But there's more we can do at the unit level. 

### Property-Based Testing 

There's a kind of executable check or test called a _property-based test_. Property-based tests compare the behavior of the SUT with a definition of the characteristics, or properties, of the code, treated as invariants. 

A PBT tool works by generating a massive number of inputs to the SUT and comparing the outputs to invariants. For instance, if the SUT has an integer argument, the tool will generate a test case for every possible integer value. 

During each run, the tool narrows down the number of test cases necessary to exercise the code fully, until we have just enough. That way, we aren't running millions or billions of tests that only repeat the same results. 

A PBT tool can almost always discover edge cases we didn't think of when we wrote our example-based tests. 

##---------- RESUME HERE ----------

Take a look at the file named test underscore rpn underscore props dot py in directory property underscore tests. 

PBT tools for some languages can be pretty complicated to use. Fortunately, pytest supports PBT in a relatively straightforward way. 

_run the sample PBT tests_ 

### Mutation Testing

A mutation testing tool will modify conditional logic in the SUT to see if our unit test suite will detect the changes. The modifications are called _mutants_. 

Any mutant that isn't detected by the unit test suite is said to have _survived_. Our goal is to ensure our unit test suite kills all mutants. 

Mutation testing tools only work when the unit test suite is green - that is, all the test cases pass. 

_demonstrate mutation testing_

### Exploratory Testing 

Exploratory Testing is human testing at the level of the entire application. It is not a form of unit checking. 

You will find numerous online and print resources about Exploratory Testing. Unfortunately, most of them are incomplete, self-contradictory, describe an obsolete form of the technique, or are just plain wrong. 

Most descriptions of Exploratory Testing describe it along the lines of _unstructured_, _ad hoc_, _free-form_, or _lacking proper planning_. Most resources describe Exploratory Testing as if it were an isolated activity performed by "a tester" who reports their findings asynchronously. 

In contemporary practice, Exploratory Testing is performed on the basis of a _strategy_ or _theme_ rather than a step-by-step test plan. This may be the reason some people think of it as _unstructured_ or _unplanned_. The testers need freedom to follow their results to the next logical question; that doesn't mean there's no planning or structure at all. 

Besides that, Exploratory Testing isn't usually performed by just one person, "a tester," who works in isolation. 

Typically, a software team will allocate a set amount of time on a regular basis for Exploratory Testing. It may be done once a month, once a week, once per iteration, or on some other cadence that makes sense in context. It isn't an occasional or random or _ad hoc_ thing. The whole team participates. 

Someone who is relatively senior in software testing develops a theme for the Exploratory Testing session, and team members work in twos or threes to explore the behavior of the application around that theme. Each pair or group may have a different strategy to follow, or they may all follow the same strategy in any given session. 

Examples of _themes_ include internationalization, accessibilty, user experience, security, error-handling, or any other specific _concern_ of the application. 

A _strategy_ might be to follow the work flow of a user as they carry out a particular business function or end-user activity from start to finish, like transferring funds between bank accounts, registering a new user for an online service, or purchasing items from an online store.

## After Deployment, Are We Finished With Testing?

The combination of different kinds of checking and testing, at different levels of abstraction, at different points in the development/delivery process, helps us gain confidence that our solution will not crash and burn in production. 

But there are no guarantees! Such things as the number of processor cores in a given server, a memory leak in an application that shares a server with ours, gray failures in a cloud environment, intentional hacking, and timing issues in distributed applications can't be reliably replicated in a test environment prior to deployment. 

That's the reason we need observability tools in production, and a rational strategy for managing production server instances. Those topics are out of scope here, but the key point to take away is that some form of checking or testing occurs throughout the lifetime of the product.

Automated checks can catch many issues before they become problematic, but no form of testing or checking can catch every potential issue. The time never comes when we can afford to stop paying attention.

And this is not something that should be set aside in favor of rapidly cranking out just one more User Story before the end of a Sprint. What's the use of delivering that User Story if it's only going to break the application because it wasn't sufficiently checked and tested? It's false economy.








