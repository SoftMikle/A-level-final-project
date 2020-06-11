<img src="icons/e-library.png" align="right" width="122" height="190" />

# README 

To have a look and test my API click on Swagger icon.
Also the app could say hello on start page. Testing of that could be done by clicking Heroku icon.
Both of actions could take some time while app will wake up after standby.

[<img src="https://help.apiary.io/images/swagger-logo.png" width="100" height="100" />](https://app.swaggerhub.com/apis-docs/SoftMikle/FirstTest/0.0.2) 
[<img src="https://cdn.iconscout.com/icon/free/png-512/heroku-225989.png" width="100" height="100" />](https://a-level-library.herokuapp.com/) 

##API testing

For logging in as Admin use next credentials:

`{
 "login": "SuperDetective",
 "password": "elementaryWatson!"
 }`
 
###Tiny guide how to use Swagger OpenApi
 
- Let's have a look how to authorize in the app. First of all choose Post method with endpoint "/auth/login":
 
 <img src="swagger-cheat-sheet/1.png" align="center" />
 
- Then click "Try it out" button:
 
 <img src="swagger-cheat-sheet/2.png" align="center" />
 
- The third step is to copy the credentials above into the field that become active and press "Execute" button:

 <img src="swagger-cheat-sheet/3.png" align="center" />
 
- Perhaps waiting of the response will take some time. After getting response copy to clipboard token (value of "token" field in response"):
 
 <img src="swagger-cheat-sheet/4.png" align="center" />
  
 - Then press "Authorize" button on the top of the page:
  
  <img src="swagger-cheat-sheet/5.png" align="center" />
    
 - The last step is to paste token in authorization field and clicking "Authorize" button  :
  
  <img src="swagger-cheat-sheet/6.png" align="center" />
  
 - Well done, you are logged in!