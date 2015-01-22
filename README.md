# BetaLoL
Example application for showing some of the features of [AndroidAnnotations](https://github.com/excilys/androidannotations "Android Annotations"). 
This application lets you search for League of Legends summoners and see statistics of their last matches.

##Configuration
To use this application, the only configuration needed is to change the League of Lengeds API Key in the [Utils.java](https://github.com/josescgar/BetaLoL/blob/master/app/src/main/java/com/escobeitor/betalol/config/Utils.java "Utils.java") class.
You can get one at the [League Of Legends Developer Console](https://developer.riotgames.com/ "LoL Developers").

```java
public class Utils {

    public static final String LOL_API_KEY = "XXX";
    
    ...
}
```
