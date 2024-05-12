# Rovarírtók

A projekt célja, hogy egy átfogó fejlesztési környezetet biztosítson a csapat számára, amely lehetővé teszi a hatékony és minőségi kódfejlesztést. Ennek érdekében kiválasztottuk és beüzemeljük a következő funkciókat.

Az első lépésben a **Maven build** keretrendszert vezetjük be a projektben. A Maven segítségével könnyedén kezelhető a projekt build folyamata, optimalizálva ezzel a fejlesztési folyamatot és biztosítva a konzisztens környezetet a fejlesztők számára.

A **Continuous Integration (CI)** rendszer kiépítése következik a projektben, melyet a GitHub Actions és Azure platform segítségével valósítunk meg. Ez lehetővé teszi a folyamatos integrációt és tesztelést a fejlesztési folyamat során, biztosítva a gyors és hatékony visszajelzéseket a fejlesztők számára.

A kód minőségének folyamatos ellenőrzése érdekében bevezetjük a **Sonarcloud** statikus analízis eszközt. Ennek segítségével folyamatosan figyelemmel kísérhetjük a kód minőségét, és javaslatokat tehetünk a javításra, ezáltal növelve a kód stabilitását és olvashatóságát.

A **Webswing** alkalmazást integráltuk a projekthez, ami lehetővé teszi a Java alkalmazások futtatását böngészőből. Ez jelentősen megkönnyíti a fejlesztési és tesztelési folyamatokat, valamint egyszerűsíti a környezetek közötti átállást és telepítést.

A **Docker** segítségével támogatjuk a projekt deployment folyamatát, lehetővé téve a könnyű és egységes telepítést és futtatást különböző környezetekben, így biztosítva a konzisztens működést a fejlesztés és üzemeltetés során.

Az egységtesztek készítése során az **xUnit** keretrendszert alkalmazzuk, amely segítségével biztosítjuk a kód stabilitását és funkcionalitását. Ezáltal könnyen és hatékonyan tesztelhetővé válnak a különböző komponensek és funkciók.

A tesztek kódlefedettségének folyamatos mérése alapján bővítjük a tesztkészletet, hogy minél teljesebb legyen a kód lefedettsége. Ez segít abban, hogy a fejlesztett funkciók és komponensek minőségi szintje folyamatosan magas legyen, minimalizálva a hibalehetőségeket.

Az automatizált **UI tesztek** készítése biztosítja a felhasználói felület megfelelő működését és stabilitását, elősegítve ezzel a felhasználói élmény javítását és a hibák korai felfedezését a fejlesztési folyamat során.

A **Behavior Driven Development (BDD)** alapú tesztek létrehozása segít abban, hogy a funkcionális követelmények teljesítését ellenőrizzük és dokumentáljuk. Ezáltal biztosítjuk, hogy a fejlesztett funkciók megfeleljenek az üzleti elvárásoknak és a felhasználói igényeknek.