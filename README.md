# Mule ESB Devops Application
Must add username and encrypted password in the api.properties file.  Encryption algorithm is AES.  The encrytionkey is passed in as a Argument of 16 characters -Dencryptionkey=****************.
# Hybrid Deployment
To deploy a hybrid application it will do the following steps;
- Get the Access Token
- Get the Organization ID
- Get the Environment ID based on the name passed in through the query parameter
- Get the Target ID based on the target type [SERVER, CLUSTER, GROUP] and the target name passed in the payload
- Create the multipart/form-data
- If the file is a URL (i.e. from Jenkins or Artifactory) it will read the bytes from the URL and attach them
- If the file is on the file system it will read the bytes from the file system and attach them 
- Check if the application is deployed
- If the application is not deployed it will deploy a new application
- If the application is deployed it will update the existing application
## Hybrid POST Example
curl -i -X POST \
   -H "Content-Type:application/json" \
   -d \
'{
  "targetType": "CLUSTER",
  "targetName": "cluster",
  "artifactId": "devops",
  "file": "C:/devops-1.0.0-SNAPSHOT.zip"
}' \
 'http://localhost:8081/api/devops/deploy/hybrid/v1?env=DEV'
# Cloud Deployment
To deploy a cloudhub application it will do the following steps;
- Get the Access Token
- Get the Organization ID
- Get the Environment ID based on the name passed in through the query parameter
- Create the multipart/form-data
- If the file is a URL (i.e. from Jenkins or Artifactory) it will read the bytes from the URL and attach them
- If the file is on the file system it will read the bytes from the file system and attach them 
- Check if the application is deployed
- If the application is not deployed it will deploy a new application
- If the application is deployed it will update the existing application
Please note that due to access restrictions the cloudhub deployment could not be properly tested.  Any feedback/ or potential contributors to make this complete is welcome.
## Cloud POST Example
curl -i -X POST \
   -H "Content-Type:application/json" \
   -d \
'{
  "file": "C:/devops-1.0.0-SNAPSHOT.zip",
  "workers": {
    "amount": "1",
    "name": "Medium",
    "weight": "0.1",
    "cpu": "0.1 vCores",
    "memory": "500 MB memory"
  },
  "properties" : {
    "encryptionkey" : "1234567890123456"
  }
}' \
 'http://localhost:8081/api/devops/deploy/cloud/v1?env=DEV'
