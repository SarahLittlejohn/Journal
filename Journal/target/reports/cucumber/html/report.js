$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("resources.feature");
formatter.feature({
  "line": 1,
  "name": "View \u0026 create resources",
  "description": "",
  "id": "view-\u0026-create-resources",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 2,
  "name": "Get the list of resources",
  "description": "",
  "id": "view-\u0026-create-resources;get-the-list-of-resources",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 3,
  "name": "a get request is made to \u0027 \"/resources\" \u0027",
  "keyword": "When "
});
formatter.step({
  "line": 4,
  "name": "a response code of 200",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "/resources",
      "offset": 28
    }
  ],
  "location": "ResourceSteps.aGetRequestIsMadeToEndpoint(String)"
});
formatter.result({
  "duration": 97607102,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "200",
      "offset": 19
    }
  ],
  "location": "ResourceSteps.aResponseCodeOf(int)"
});
formatter.result({
  "duration": 2961479,
  "status": "passed"
});
});