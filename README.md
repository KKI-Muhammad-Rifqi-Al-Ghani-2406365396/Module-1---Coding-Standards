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
