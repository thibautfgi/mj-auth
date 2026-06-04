HTTP Request → Controller → Service → Repository → BDD
                                      ↕
                            JwtService


DataBase with postgresql on the docker. (use pgAdmin for manage it).


) on project mj-auth: Compilation failure: Compilation failure:
[ERROR] /C:/Users/tibof/Desktop/Projet/Project M1/mountain-journey/mj-auth/src/main/java/com/mountain_journey/mj_auth/service/JwtService.java:[24,43] cannot find symbol
[ERROR]   symbol:   variable StandardCharsets
[ERROR]   location: class com.mountain_journey.mj_auth.service.JwtService
[ERROR] /C:/Users/tibof/Desktop/Projet/Project M1/mountain-journey/mj-auth/src/main/java/com/mountain_journey/mj_auth/security/SecurityConfig.java:[33,23] cannot find symbol
[ERROR]   symbol:   variable AbstractHttpConfigurer
[ERROR]   location: class com.mountain_journey.mj_auth.security.SecurityConfig
[ERROR] /C:/Users/tibof/Desktop/Projet/Project M1/mountain-journey/mj-auth/src/main/java/com/mountain_journey/mj_auth/security/SecurityConfig.java:[35,48] cannot find symbol
[ERROR]   symbol:   variable SessionCreationPolicy
[ERROR]   location: class com.mountain_journey.mj_auth.security.SecurityConfig
[ERROR] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException

