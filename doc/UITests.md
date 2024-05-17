# UI Tesztek

## UI tesztek beüzemelése

A UI tesztek írásához az AssertJ Swing keretrendszert választottam mivel 
átlátható teszteket lehet készíteni és jól együttműködik a JUnit 
keretrendszerrel, amit a unit tesztelés során is használunk.

Ezek után írtam egy egyszerű UI tesztet, hogy megnézzem, hogy teszteljem
működik-e a kialakított környezet *Github Actions*-ben is. Mivel az 
*Action*-ökben nincs képernyő bekötve ezért ezt emulálni kell, erre azt a
megoldást találtam, hogy a UI tesztek futtatása a következő környezet alatt 
fut:

```yaml
- name: Build and Test
  uses: coactions/setup-xvfb@v1
  with:
    run: mvn -B package --file pom.xml --no-transfer-progress
```

## UI Tesztek írása

Először a játék kiindulási állapotát teszteltem, a kezdeti pontok 0-val
megegyeznek, az előre kialakított pályastruktúra minden eleme kiralyzolódik.
Ezek után funkcionálisabb jellegű teszteket írtam: lehet-e a csöveket mozgatni 
az egerünkkel, a játékosok tudnak-e lépni, csúszóssá vagy ragadóssá tudják-e 
tenni a csöveket, a szerelők meg tudják-e javítani a pumpákat és a megfelelő
pályaelemre állva fel tudnak-e venni pumpát és csövet.

## Összegzés

Sok olyan teszteset azért nem született meg mert a kódban túl nagy 
változtatások lennének szükségesek, hogy eredményesek legyenek, ezért ha 
előre tudjuk, hogy a megbízhatóság kedvéért UI teszteket fogunk írni érdemes 
ezt fejben tartani már az implementálás közben is, hogy ne löjük lábon 
magunkat.

