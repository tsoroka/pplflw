How to start application on localhost:
1. Run *mvn clean install*
1. Run *docker-compose up --build*
1. Open Swagger via "http://localhost:8080/pplflw/swagger-ui/"

**Answers to Second Part:**

Definition of ready and definition of done should be defined for user stories.
Also regular status check should be performed.

**Production-readiness criteria should be defuned in definition of done for user stories:**

1. The user story implementation meets all the acceptance criteria
1. The Product Owner approved the user story
1. The unit tests were written, executed and passed
1. Every acceptance criteria have at least a test case associated
1. The team wrote, completed unit tests and they passed
1. The Technical Documentation is uploaded by the team on Confluence in needed
1. The performance is under X
1. There is no regression in the automation testing suite
1. The user story has been peer-reviewed
1. Integration testing performed and compiles
1. The manual configurations that need to be performed after the deployment to production are marked in the user story
1. The deployment to the production environment of the story has release notes
1. End-user documentation is available
1. The user interface is according to the design
1. The code refactoring is completed

**Definition if ready for user story:**
1. User Story is clear
1. User Story is testable
1. User Story is feasible
1. User Story defined
1. User Story Acceptance Criteria defined
1. User Story dependencies identified
1. User Story sized by Development Team
1. Scrum Team accepts User Experience artefacts
1. Performance criteria identified, where appropriate
1. Scalability criteria identified, where appropriate
1. Security criteria identified, where appropriate
1. Person who will accept the User Story is identified
1. Team has a good idea what it will mean to Demo the User Story

**Answers to Third Part:**
To implement statistic endpoints, I would use REST endpoints, added to Controller layers.
The overall architecture will fit current architecture: Rest Controller -> Service -> Repository

