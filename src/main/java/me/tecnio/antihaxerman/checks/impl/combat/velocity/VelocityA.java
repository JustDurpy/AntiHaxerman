package me.tecnio.antihaxerman.checks.impl.combat.velocity;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.tecnio.antihaxerman.checks.Check;
import me.tecnio.antihaxerman.checks.CheckInfo;
import me.tecnio.antihaxerman.playerdata.PlayerData;

@CheckInfo(name = "Velocity", type = "A")
public final class VelocityA extends Check {
    public VelocityA(PlayerData data) {
        super(data);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        if (isFlyingPacket(e)) {
            if (data.getVelocityTicks() == 1) {
                final double velTaken = data.getDeltaY();
                final double velExpected = data.getLastVelocity().getY() * 0.998F;
                final double percentage = (velTaken * 100) / velExpected;

                if (velTaken <= velExpected
                        && data.liquidTicks() > 20
                        && !data.isOnClimbableBlock()
                        && data.underBlockTicks() > 20
                        && !data.isNearWall()) {
                    if (++preVL > 1) {
                        flag(data, "took less velocity than expected. p: " + percentage);
                    }
                }else preVL = 0;
            }
        }
    }
}
