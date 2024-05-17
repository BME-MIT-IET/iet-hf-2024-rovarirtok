# SonarCloud implementálása

A projektünk fejlesztése során fontosnak tartottuk a kódbázis minőségének és biztonságának növelését. Ennek érdekében döntöttünk úgy, hogy bevezetjük a **SonarCloud**-ot a fejlesztési folyamatba.

A **SonarCloud** egy hatékony eszköz, amely lehetővé teszi a kódminőség ellenőrzését, hibák, code smellek és biztonsági kockázatok azonosítását, valamint a kódminőség folyamatos javítását.

Az implementálás során létrehoztam egy *GitHub Actions workflow-t*, amely automatizálja a kódanalízist és jelentéseket generál a kódminőségről. Ez a *workflow* futtatásra kerül minden *pull request* és *push* esemény alkalmával, így biztosítva, hogy a szoftverünk kódja mindig a megfelelő minőségű legyen. Mindezt a *Github repository-nak* a *Main* ágán csináltam, hogy a legfrissebb verziójú kódra egyből kapjak egy visszajelzést a SonarCloud webes felületén.

Ezt követően, alaposan átnéztem a visszajelzést a kódunk minőségéről, majd elkezdtem a legsúlyosabb hibák javításával. Néhány helyen, a *SonarCloud* által javasolt megoldás, a programunkat funkcionalitás szempontból rontotta, használhatatlanná tette, ezért azokat elfogadtam.

Sok helyen előfordult, hogy fölösleges kapcsos zárójeleket használtunk, amely jelentősen ront a kód átláthatóságán, ezeket töröltem. Hasonlóan, a nem használt, de beimportált könyvtárakat is töröltem.

A *Spring* és *Pump* osztályok implementáltak egy olyan interfészt, amelyet az ősosztályuk is implementált, amely kód redundanciához, olvashatatlansághoz és karbantartási problémákhoz vezet.

A *Window* és *MenuPanel* osztályok rendelkeztek olyan attribútummal, amelyek megegyeztek az ősosztályuk attribútumával, amely hibákhoz és olvashatósági problémákhoz vezet.

Mindezek javításával, a kódunk tisztább, biztonságosabb és olvashatóbb lett.

A kódunk továbbá az első lefutás után **184** *code smellel* rendelkezett, amelyeket a javítások és módosítások után **84**-re 
csökkent.