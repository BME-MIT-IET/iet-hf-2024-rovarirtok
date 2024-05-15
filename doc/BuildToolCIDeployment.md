# Build-tool beüzemelése, CI pipeline beüzemelése, deployment kialakítása Docker segítségével Azure-ra

## Maven Build Tool Beüzemelése

A Maven eszköz beüzemelése a projektbe egy kulcsfontosságú lépés volt, amely lehetővé teszi a projekt függőségeinek kezelését, a build folyamat automatizálását, és a tesztek egységes futtatását. A Maven `pom.xml` fájl konfigurálásával definiáltam, hogy a projekt buildje során egy `.jar` állomány jöjjön létre, amely tartalmazza az alkalmazás összes függőségét. A struktúra változása miatt szükséges volt a forrásfájlok és erőforrások (például képek) újraorganizálása és újraimportálása a Maven szabványai szerint, amit a `39d9b6a` commit tükröz.

## Előkészítő lépések Azure Portálon
A projekt során az Azure szolgáltatásait használva egy integrált környezetet hoztunk létre, amely támogatja a Docker konténerek kezelését és futtatását. Az előkészítő lépések magukban foglalták egy erőforráscsoport (resource group) létrehozását és konfigurálását, amely tartalmaz egy Container Registry-t és egy App Service-t. Ezek a lépések alapvetően szükségesek voltak a projekt infrastruktúrájának felépítéséhez és a későbbi CI/CD folyamatok támogatásához.

## GitHub Actions Alapú CI Pipeline Kialakítása

A Continuous Integration (CI) pipeline létrehozása GitHub Actions segítségével több lépést foglal magában:

1. **CI Workflow Definíció**: A `.github/workflows` könyvtárban definiáltam egy YAML fájlt, amely leírja a CI folyamatot. Ez a fájl tartalmazza a különböző lépéseket, mint például a Java környezet beállítása, a Maven segítségével történő buildelés, és a tesztek futtatása.

2. **Automatikus Triggerelés**: A pipeline úgy van beállítva, hogy minden `push` műveletre a `build-tool_CI` ágon, és minden `workflow_dispatch` eseményre automatikusan lefut.

3. **Java környezet beállítása**

4. **Projekt buildelése**

5. **Bejelentkezés Azure-ra**: a repository szinten felvett Azure secretek segítségével bejelentkezünk Azure-ra, annak érdekében, hogy majd ide pusholjuk a Docker image-et, illetve azután feldeployoljuk az alkalamzást

6. **Docker image buildelése és publisholása**: buildelünk egy Docker image-et a repository gyökérmappájában lévő Dockerfile alapján egy imaget, majd az image-et felpusholjuk a már előre létrehozott Azure Container Registry-be

7. **Alkalmazás deploy elvégzése**: az Azure Container Regisrty-ben tárolt Docker Image-et kiválasztjuk commit hash alapján, és a megelelő image-ből létrehozuk egy containert és deployoljuk Azure App Service-ként

A CI folyamat biztosítja, hogy minden kódváltoztatás esetén automatikusan leforduljon az alkalmazás, lefussanak a tesztek, és az eredmények közvetlenül láthatóak legyenek a fejlesztők számára.

## Docker Alapú Környezet Kialakítása és Deployment Azure-ra

A Docker segítségével készítettünk egy izolált környezetet, amely tartalmazza a Java Swing alapú webalkalmazást. A `Dockerfile` létrehozásának lépései a következők:

0. **Saját alap image elkészítése**: Egy JDK 21-et tartalmazó image-ből kiindulva felmásoltuk a Webswing webszervert tartalmazó ZIP-et, majd ez publisholtuk DockerHub-ra

1. **Alap Image Beállítása**: A `norbertkurcsi/webswing:24.1` Docker image-et használtuk alapként, amely a Java 21-et tartalmazza, illetve a Webswing-et egy zip-be csomagolva .

2. **Függőségek Telepítése**: Telepítettünk szükséges csomagokat, mint például a `wget`, `unzip`, `xvfb` (virtuális framebuffer), és grafikai könyvtárakat a Docker image-be.

3. **WebSwing Konfiguráció**: A WebSwing webszerver konfigurációs fájlját (`webswing.config`) módosítottuk az alkalmazás specifikus követelményei szerint, és beállítottuk a szükséges környezeti változókat.

4. **Alkalmazás és Webszerver Indítása**: A Docker image konfigurálása után az alkalmazás `.jar` fájlt és a szükséges scripteket a megfelelő helyre másoltuk, majd beállítottuk azok futtatását.

Az elkészült Docker image-et feltöltöttük egy Azure Container Registry-be, és a `webapps-deploy` GitHub Action segítségével deployoltuk egy Azure App Service által futtatott containerbe. Ez a lépés biztosítja az alkalmazás folyamatos elérhetőségét és skálázhatóságát az Azure felhő infrastruktúráján.

## Összegzés

A fenti lépések részletesen mutatják be, hogy miként valósult meg az alkalmazás fejlesztése, tesztelése, containerizálása és deploymentje egy modern és robosztus felhő alapú környezetbe. Az implementált CI/CD folyamatok és a Docker technológia alkalmazása lehetővé teszi a gyors iterációkat és biztosítja az alkalmazás magas rendelkezésre állását, miközben a fejlesztési és üzemeltetési költségeket optimalizálja.