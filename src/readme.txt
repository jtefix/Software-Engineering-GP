SEG: Software Engineering Group,
Team 7:
Naser Salameh – ns4u18
Viktor Cholakov – vc3g18
Gopika Bejoy – gb1n17
Stefania Calnuschi – sc3u18
Nithish John Paull – njp1g18

Supervisor:
Sofia Kitromili – sk1n15

Ad Auction Dashboard.

Installation guide:
1) Have all three CSVs in the following directory:
"src/main/resources"

2) CRUCIAL: Ensure Java is JRE 11, all throughout project structure. with language level 11. with the Computer's environment variable %JAVE_HOME% directing to the JRE 11 directory.

3) CRUCIAL: run jar file with following command:

java  --module-path "PATH TO LIB" --add-modules=javafx.controls,javafx.fxml --add-open javafx.base/com.sun.javafx.runtime=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED --add opens javafx.base/com.sun.javafx.binding=ALL-UNNAMED --add-opens javafx.base/com.sun.javafx.event=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED     -jar "JARNAME"

WHERE PATH TO LIB is the local path to src/main/resources/javafx-sdk-11.0.2/lib
