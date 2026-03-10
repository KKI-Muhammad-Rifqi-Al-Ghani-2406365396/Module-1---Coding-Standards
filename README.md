# Module-1---Coding-Standards

## Reflection 1

- I Have Consistently applied the meaninfull names for naming the function  
- making the function small and focused,  
- on matter of consistency, I have created a slight error when naming things such as ProductID and ProductId causing failures on run  

---

## Reflection 2

- Unit tests are testing/verifying the logic,in a repository and service methods  
- Functional test are testing user interactions  

### 1.
After Checking Unit tests, i feel more concerned , because getting 100% test,it would not mean anything.  
as for how many , it should be as much as we can cover every possible interaction  

### 2.
At first i used the tutorial provided template  
While this way its easier to initialize, it introduce a problem  

- Code Duplication: if there is an update, i need to take care of 2 file instead of one  
- it will reduce the Code Quality,  
- I Should make this Focused and Independent  

---

## Exercise 2

### 1.
- Updates could set invalid values , i enforced simple validation rules  
  This prevents bad data regardless of whether the request comes from the web UI, tests, or another API call in the future.  
- Tests often repeat the same URL strings or product field names. extracted repeated values into constants inside tests  

### 2.
i supposed it meets the definition of Continuous Integration, every push triggers an automated pipeline that builds the project and runs the test suite.  
the pipeline includes automated checks like code coverage and PMD analysis.  
as for Continous Deployement, it still need a manual run from the heroku it partially fill the condition .


## Reflection 3
### 1.

- SRP is implemented by seperating car controller and product controller
- LSP is implemented by CarController extends ProductController
- ISP Car and Product Service are small and focused interfaced
- DIP the Controller depends on the Interface of the service 

### 2.
- SRP makes easier maintainance and lesser accidental changes
- LSP removes wrong inheritance and avoid unexpected behaviours


### 3. 
- Without SRP , Files will have comically long lines of code and a single function can do many task 
- WIthout LSP , There will be behaviour that is not expected for the subclass
- Without DIP , a high level code that is tied directly to the low level will do unexpected behaviours


## Reflection 4

## 1.
- In the red phase, I first write tests based on requirements. This forces me to understand what the system is supposed to do before writing implementation.
- In the green phase, I only write enough code to make the tests pass. This helps me avoid overengineering.
- In the refactor phase, I improve the design after behavior is already protected by tests. For example, replacing hardcoded status strings with OrderStatus enum makes the code cleaner and easier to maintain.


So yes it is Pretty Useful

## 2.
- Fast , yes most test run are near instant
- Independent, mostly yes. some tests reuse shared setup structures like orders and products.
- Repeatable, Yes. They all should produce the exact same value on each test run 
- Self Validating, Yes, the Usage of AsserEquals/AssertNull/AssertTrue it will check by its self without manual checking
- Timely, Yes,The tests were written before or alongside implementation in the TDD cycle
