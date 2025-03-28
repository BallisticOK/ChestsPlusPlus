package com.jamesdpeters.minecraft.chests.party;

import com.jamesdpeters.minecraft.chests.lang.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PartyInvite {

    private final OfflinePlayer owner;
    private final OfflinePlayer player;
    private final PlayerParty party;
    private boolean pending;

    public PartyInvite(OfflinePlayer owner, OfflinePlayer player, PlayerParty party) {
        this.owner = owner;
        this.player = player;
        this.party = party;
    }

    public void sendInvite() {
        Player onlinePlayer = player.getPlayer();
        if (onlinePlayer != null) {
            onlinePlayer.sendMessage(ChatColor.GREEN + Message.PARTY_INVITE.getString(
                ChatColor.WHITE + owner.getName() + ChatColor.GREEN, 
                ChatColor.WHITE + party.getPartyName() + ChatColor.GREEN));

            String tellraw = "tellraw " + onlinePlayer.getName() + 
                " {\"text\":\"" + Message.PARTY_ACCEPT_INVITE.getString() + 
                "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/c++ party view-invites\"}}";

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), tellraw);
        }

        Player onlineOwner = owner.getPlayer();
        if (onlineOwner != null) {
            onlineOwner.sendMessage(ChatColor.GREEN + Message.PARTY_INVITE_OWNER.getString(
                ChatColor.WHITE + player.getName() + ChatColor.GREEN, 
                ChatColor.WHITE + party.getPartyName() + ChatColor.GREEN));
        }

        pending = true;
    }

    public void acceptInvite() {
        if (pending) {
            party.addMember(player);
            Player onlinePlayer = player.getPlayer();
            if (onlinePlayer != null) {
                onlinePlayer.sendMessage(ChatColor.GREEN + Message.PARTY_JOINED.getString(
                    ChatColor.WHITE + owner.getName() + ChatColor.GREEN, 
                    ChatColor.WHITE + party.getPartyName() + ChatColor.GREEN));
            }
            pending = false;
        }
    }

    public void rejectInvite() {
        if (pending) {
            pending = false;
        }
    }

    public PlayerParty getParty() {
        return party;
    }
}
