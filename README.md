# Zero Tests
This inventory contains a suite of smoke tests of different microservices in the Salesforce zero environment posting results to a [Refocus](https://github.com/salesforce/refocus) instance hosted on Heroku. 

## Running Locally

Setup required environment variables: 
 - REFOCUS_ENDPOINT : URL of the Refocus instance we post results to
 - REFOCUS_ACCESS_TOKEN : Access token granted from refocus. See [Refocus Auth Doc](https://salesforce.github.io/refocus/docs/10-security.html#api-tokens)
 - ID_GENERATOR_URL : Endpoint of the id generator to test
 
 Once the environment is set, running all tests is a simple `mvn install` 
 
 
 If starting from a fresh DB, you want to initialize the UI by pushing the latest MultiTable, see directions in the refocus [docs](https://salesforce.github.io/refocus/docs/01-quickstart.html#step-4-install-a-lens)