Account Aggregation by WiseEconomy

Getting Started With Perfios Sandbox Access:

As we were trying to simplify user’s banking experience, we wanted a common platform where users can see and have access to all of their banking needs across multiple bank accounts through a smooth and hassle free consent based mechanism and perfios provides just that. Linking multiple bank accounts of the user, just by verifying his/her phone number would provide such a great user experience and easy accessibility across multiple accounts. 

User Journey:

	We wanted our users to register on our app with an otp and mobile number from which all of their accounts would be fetched. We have our backend hosted on AWS with registration apis doing the registration at our end and user’s data stored securely in our db and then we are initiating a call to perfios to get their consent to fetch the accounts linked with the mobile. This api gave us a perfios url at which user would be redirected to and he will be able to see all of this bank accounts and a consent specifying his acceptance for our WiseEconomy to fetch them and a period till which this consent is valid. Once we have registered the user we get a callback of all the accounts that are related to the user mapped with the txn id used for consent approval. Once we had the accounts of the url, We had an api to initiate the data fetch of his transactions across all his accounts. Once this has been completed, we check the status of the data-fetch by a CronJob running at periodic intervals. Once we have the data-fetch for the accounts ready we call the rawReport api providing the account statements of the user and have stored it in our DB. 

Tech Side Of Things:

	We have used JAVA Spring Boot for our whole backend service and Java scheduler for the cron job. We have hosted our service on EC2 instance in our AWS account and an encrypted RDS (DB) storing all the user’s information. We have coded our backend in a modular way that it can be compatible with multiple other AAs, enabling us to scale even in cases where a particular AA is down. We have hosted our service in VPC and no APIs are publicly accessible. We have planned to whitelist only Perfios IPs to hit our callback ensuring that there are no MIM (Man in the Middle) attacks possible. Regarding scalability, we have currently 1 machine running and AutoScalingGroup configured to spawn x number of machines in case the load exceeds a given threshold and ELB in front of all these machines. We have used codeBuild,CodeDeploy for our CI/CD to our EC2 instances. 
