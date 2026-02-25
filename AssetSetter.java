public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // ---------- KEYS ----------
        OBJ_Key key1 = new OBJ_Key();
        key1.worldX = gp.tileSize * 23;
        key1.worldY = gp.tileSize * 7;
        key1.type = ObjectType.KEY;          // 🔴 ENUM USED
        gp.objList.add(key1);                // 🔴 COLLECTION USED

        OBJ_Key key2 = new OBJ_Key();
        key2.worldX = gp.tileSize * 23;
        key2.worldY = gp.tileSize * 40;
        key2.type = ObjectType.KEY;
        gp.objList.add(key2);

        OBJ_Key key3 = new OBJ_Key();
        key3.worldX = gp.tileSize * 38;
        key3.worldY = gp.tileSize * 8;
        key3.type = ObjectType.KEY;
        gp.objList.add(key3);

        // ---------- DOORS ----------
        OBJ_Door door1 = new OBJ_Door();
        door1.worldX = gp.tileSize * 10;
        door1.worldY = gp.tileSize * 11;
        door1.type = ObjectType.DOOR;
        gp.objList.add(door1);

        OBJ_Door door2 = new OBJ_Door();
        door2.worldX = gp.tileSize * 8;
        door2.worldY = gp.tileSize * 28;
        door2.type = ObjectType.DOOR;
        gp.objList.add(door2);

        OBJ_Door door3 = new OBJ_Door();
        door3.worldX = gp.tileSize * 12;
        door3.worldY = gp.tileSize * 22;
        door3.type = ObjectType.DOOR;
        gp.objList.add(door3);

        // ---------- CHEAT ----------
        OBJ_Cheat cheat = new OBJ_Cheat();
        cheat.worldX = gp.tileSize * 10;
        cheat.worldY = gp.tileSize * 7;
        cheat.type = ObjectType.CHEAT;
        gp.objList.add(cheat);

        // ---------- BOOTS ----------
        OBJ_Boots boots = new OBJ_Boots();
        boots.worldX = gp.tileSize * 37;
        boots.worldY = gp.tileSize * 42;
        boots.type = ObjectType.BOOTS;
        gp.objList.add(boots);
    }
}