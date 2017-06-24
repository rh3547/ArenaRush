package com.binextechnologies.arenarush;

import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.player.Player;
import com.binextechnologies.arenarush.world.World;
import com.binextechnologies.arenarush.world.spawning.SpawnInfo;
import com.binextechnologies.arenarush.world.spawning.SpawnPointFinite;
import com.binextechnologies.arenarush.world.spawning.SpawnPointInfinite;

public class CreateWorld
{
	public static void main(String[] args)
	{
		World world = new World("TestWorld", -1, -1, 32, 32);
		
		SpawnPointInfinite sp = new SpawnPointInfinite(new Vector2f(400, 600), 100, 180);
		sp.addSpawnableObject(new SpawnInfo(0, 1));
		world.spawns.add(sp);
		
		SpawnPointFinite sp2 = new SpawnPointFinite(new Vector2f(1000, 300), 100, 180, 2);
		sp2.addSpawnableObject(new SpawnInfo(0, 1));
		world.spawns.add(sp2);
		
		Player player = new Player();
		player.setPosX(400);
		player.setPosY(400);
		world.players.add(player);
		
		world.generateWorldFile("res/worlds/", "testWorldRaw.world");
	}
}
