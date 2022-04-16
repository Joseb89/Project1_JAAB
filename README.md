# Project1_JAAB
This is a project I created as part of my training with Revature. It is an Employee Reimbursement Program which allows employees to submit reimbursement requests to
their supervisors, who can then approve or deny them. Once a request is submmited or approved, an email is sent to the respective user.

## Description
This program was written with two microservices: a Main Api where most of the functionality takes place (services, security, REST configuration), and an Email Api which
is responsible for generating an email once a request is submitted by the employee or approved or denied by the supervisor.

## Purpose
The purpose of this program was to increase my knowledge of the Spring Framework and introduce training in Docker containerization and DevOps.

## Technologies Used
- Java 11
- Spring 5
- Spring Boot 2
- HTML5
- CSS3
- Docker

## How To Run
### IDE
1. Clone this repository to your computer and open it in your prefered IDE.
2. You will need to configure the application.yaml file in email_api to connect to the mail server of your choice
3. Start main_api first, then start emai_api
4. Go to http://localhost:8080 and create a new ADMIN user and EMPLOYEE user by going to the 'New User?' link. Set the ADMIN user as the EMPLOYEE's supervisor.
6. Login as the EMPLOYEE and you will be directed to a page where you can complete the reimbusement request.
7. Once submitted, use your prefered mail API to verify if an email was sent to the supervisor's email.
8. Logout and login as the ADMIN.
9. Select the EMPLOYEE's link on the next page and select any form link on the next page to be directed to the submitted form.
10. In the drop-down list on the bottom of the page, select APPROVED or DENIED and submit the request.
11. Verify that an email was sent to the EMPLOYEE.

### Docker
1. Pull the following images from Docker Hub: joseb89/project1jaab-mainaapi and joseb89/project1jaab-emailapi.
2. Create a Docker network with the bridge driver
3. Run the containers mentioned above and add them to the newtwork you created.
4. Repeat steps 4 - 11 mentioned above.


## Status
Completed
