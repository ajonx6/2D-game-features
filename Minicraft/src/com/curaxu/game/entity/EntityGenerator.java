package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.level.Level;

public class EntityGenerator {
    public static Entity createPlayer(Vector pos) {
        Entity player = new Entity(pos, "player");
        player.addComponent(new SpriteList(player, new Sprite(
                SpriteSheets.non_tiles_sheet.getSprite(4, 0, 1, 1),
                0xFF331D1A, 0XFF456D9E, 0XFFFFD8EF, 0), new Sprite(
                SpriteSheets.non_tiles_sheet.getSprite(5, 0, 1, 1),
                0XFF331D1A, 0XFF456D9E, 0XFFFFD8EF, 0XFF93D2F9)));
        player.addComponent(new AABBBox(player, player.getWorldPos(), new Vector(4, 2), 24, 28));
        player.addComponent(new CanMove(player, 150));
        player.addComponent(new CanSwim(player, 80));
        player.addComponent(new PlayerControl(player));
        player.addComponent(new Camera(player, true));
        return player;
    }

    public static Entity createNPC(Vector pos) {
        int[] shirtcols = new int[] {0XFF456D9E, 0xFFA02945, 0XFF597238, 0XFFE4A55A};
        int c = shirtcols[Game.getInstance().getRandom().nextInt(shirtcols.length)];
        Entity npc = new Entity(pos, "npc");
        npc.addComponent(new SpriteList(npc, new Sprite(
               SpriteSheets.non_tiles_sheet.getSprite(4, 0, 1, 1),
            0xFF331D1A, c, 0XFFFFD8EF, 0), new Sprite(
               SpriteSheets.non_tiles_sheet.getSprite(5, 0, 1, 1),
            0xFF331D1A, c, 0XFFFFD8EF, 0XFF93D2F9)));
        npc.addComponent(new AABBBox(npc, npc.getWorldPos(), new Vector(4, 2), 24, 28));
        npc.addComponent(new CanMove(npc, 120));
        npc.addComponent(new CanSwim(npc, 60));
        npc.addComponent(new Camera(npc, false));
        npc.addComponent(new RandomWalk(npc));
        // test.addComponent(new MoveTowards(test, player));
        return npc;
    }

    public static Entity createTree(Vector pos) {
        Entity tree = new Entity(pos, "tree");
        tree.addComponent(new SpriteList(tree, new Sprite(SpriteSheets.non_tiles_sheet.getSprite(3, 0, 1, 1), 0xFF704629, 0xFF438759, 0xFF54A86E, 0)));
        tree.addComponent(new AABBBox(tree, tree.getWorldPos(), new Vector(4, 0), 26, 30));
        return tree;
    }

    public static Entity createCandle(Vector pos) {
        Entity candle = new Entity(pos, "candle");
        candle.addComponent(new SpriteList(candle, new Sprite(SpriteSheets.non_tiles_sheet.getSprite(6, 0, 1, 1), 0xFFD9DDBC, 0xFF9B9E87, 0xFFE08443, 0)));
        candle.addComponent(new AABBBox(candle, candle.getWorldPos(), new Vector(12, 15), 6, 14));
        return candle;
    }
}
