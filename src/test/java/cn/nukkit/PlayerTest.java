package cn.nukkit;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.dispenser.DispenseBehaviorRegister;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.biome.EnumBiome;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.level.format.anvil.Chunk;
import cn.nukkit.network.Network;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.LoginPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.protocol.types.NetworkInventoryAction;
import cn.nukkit.permission.BanList;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.potion.Effect;
import cn.nukkit.potion.Potion;
import cn.nukkit.resourcepacks.ResourcePackManager;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.timings.LevelTimings;
import cn.nukkit.utils.PlayerDataSerializer;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.util.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerTest {
    private final Long clientId = 32L;
    private final String clientIp = "1.2.3.4";
    private final int clientPort = 3232;
    
    /// Server Mocks ///
    @Mock
    PluginManager pluginManager;

    @Mock
    ServerScheduler scheduler;

    @Mock
    BanList banList;

    @Mock
    PlayerDataSerializer playerDataSerializer;

    @Mock
    ResourcePackManager resourcePackManager;

    @Mock
    Network network;

    @Mock
    DB db;

    File dataPath = FileUtils.createTempDir("powernukkit-player-test-data");

    @InjectMocks
    Server server = mock(Server.class, withSettings()
            .useConstructor(dataPath)
            .defaultAnswer(CALLS_REAL_METHODS));

    /// Level Mocks ///
    
    @Mock
    LevelProvider levelProvider;

    Level level;
    
    /// Player Mocks ///
    @Mock
    SourceInterface sourceInterface;

    Skin skin;
    
    Player player;
    
    @Test
    void armorDamage() {
        player.attack(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.FALL, 2));
    }

    @Test
    void dupeCommand() {
        Item stick = Item.get(ItemID.STICK);
        Item air = Item.getBlock(BlockID.AIR, 0, 0);
        
        player.getInventory().addItem(stick);
        List<NetworkInventoryAction> actions = new ArrayList<>();
        NetworkInventoryAction remove = new NetworkInventoryAction();
        remove.sourceType = NetworkInventoryAction.SOURCE_CONTAINER;
        remove.windowId = 0;
        remove.stackNetworkId = 1;
        remove.inventorySlot = 0;
        remove.oldItem = stick;
        remove.newItem = air;
        actions.add(remove);

        for (int slot = 1; slot < 35; slot++) {
            if (slot > 1) {
                actions.add(remove);
            }
            
            NetworkInventoryAction add = new NetworkInventoryAction();
            add.sourceType = NetworkInventoryAction.SOURCE_CONTAINER;
            add.windowId = 0;
            add.stackNetworkId = 1;
            add.inventorySlot = slot;
            add.oldItem = air;
            add.newItem = stick;
            
            actions.add(add);
        }

        InventoryTransactionPacket packet = new InventoryTransactionPacket();
        packet.actions = actions.toArray(new NetworkInventoryAction[0]);
        
        player.handleDataPacket(packet);

        int count = countItems(stick);
        assertEquals(1, count);
    }
    
    private int countItems(Item item) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            Item inv = player.getInventory().getItem(i);
            if (item.equals(inv)) {
                count += inv.getCount();
            }
        }
        return count;
    }

    @BeforeEach
    void setUp() {
        /// Setup Level ///
        doReturn("normal").when(levelProvider).getGenerator();
        doReturn("TestLevel").when(levelProvider).getName();
        level = mock(Level.class, withSettings()
                .defaultAnswer(CALLS_REAL_METHODS)
                .useConstructor(server, "DefaultLevel", new File(dataPath, "worlds/TestLevel"), true, levelProvider));
        doReturn(new Position(100,64,200,level)).when(level).getSafeSpawn();
        doReturn("TestLevel").when(level).getFolderName();
        doReturn("TestLevel").when(level).getName();
        doReturn(new Chunk(levelProvider)).when(level).getChunk(eq(100>>4), eq(200>>4), anyBoolean());
        level.timings = new LevelTimings(level);
        doReturn(level).when(levelProvider).getLevel();
        doReturn(server).when(level).getServer();
        
        /// Setup Server ///
        server.getLevels().put(0, level);
        server.setDefaultLevel(level);
        doNothing().when(server).updatePlayerListData(any(), anyLong(), anyString(), any(), anyString());
        
        /// Setup skin ///
        skin = new Skin();
        skin.setSkinId("test");
        skin.setSkinData(new BufferedImage(64, 32, BufferedImage.TYPE_INT_BGR));
        assertTrue(skin.isValid());
        
        /// Make player login ///
        player = new Player(sourceInterface, clientId, clientIp, clientPort);
        LoginPacket loginPacket = new LoginPacket();
        loginPacket.username = "TestPlayer";
        loginPacket.protocol = ProtocolInfo.CURRENT_PROTOCOL;
        loginPacket.clientId = 2L;
        loginPacket.clientUUID = new UUID(3, 3);
        loginPacket.skin = skin;
        loginPacket.putLInt(2);
        loginPacket.put("{}".getBytes());
        loginPacket.putLInt(0);
        player.handleDataPacket(loginPacket);
        player.completeLoginSequence();
        
        /// Make sure the player is online ///
        assertTrue(player.isOnline(), "Failed to make the fake player login");
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteRecursively(dataPath);
    }

    @BeforeAll
    static void beforeAll() {
        Block.init();
        Enchantment.init();
        Item.init();
        EnumBiome.values(); //load class, this also registers biomes
        Effect.init();
        Potion.init();
        Attribute.init();
        DispenseBehaviorRegister.init();
        GlobalBlockPalette.getOrCreateRuntimeId(0, 0); //Force it to load
    }
}
