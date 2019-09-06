package com.idimabr.lightskywars.util;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class FlatFile extends YamlConfiguration {

    private File bruteFile;

    public FlatFile(String name, Plugin plugin) {
        this.bruteFile = new File(plugin.getDataFolder(), name.matches(".*(?i).yml$") ? name : name.concat(".yml"));
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            if (!this.bruteFile.exists()) {
                this.bruteFile.createNewFile();
            }
            this.load(this.bruteFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e2) {
            e2.printStackTrace();
        }
    }

    public void clearSection(String section) {
        this.set(section, (Object) null);
        this.createSection(section);
    }

    public Object getSomething(String path) {
        return super.get(path);
    }

    public void setLocation(String arg0, Location arg1) {
        super.set(arg0, (Object) (String.valueOf(arg1.getWorld().getName()) + ";" + arg1.getBlockX() + ";"
                + arg1.getBlockY() + ";" + arg1.getBlockZ() + ";" + arg1.getYaw() + ";" + arg1.getPitch()));
    }

    public Location getLocation(String arg0) {
        if (super.isSet(arg0)) {
            String lc = super.getString(arg0);
            World world = Bukkit.getWorld(lc.split(";")[0]);
            double x = Double.parseDouble(lc.split(";")[1]);
            double y = Double.parseDouble(lc.split(";")[2]);
            double z = Double.parseDouble(lc.split(";")[3]);
            float yaw = Float.parseFloat(lc.split(";")[4]);
            float pitch = Float.parseFloat(lc.split(";")[5]);
            return new Location(world, x, y, z, yaw, pitch);
        }
        return null;
    }

    public void save() {
        try {
            super.save(this.bruteFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            this.load(this.bruteFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e2) {
            e2.printStackTrace();
        }
    }

    public void save(File file) throws IOException {
        Validate.notNull((Object) file, "File cannot be null");
        Files.createParentDirs(file);
        String data = this.saveToString();
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
        try {
            writer.write(data);
        } finally {
            writer.close();
        }
        writer.close();
    }

    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull((Object) file, "File cannot be null");
        this.load((Reader) new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
    }

    @Deprecated
    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
        Validate.notNull((Object) stream, "Stream cannot be null");
        this.load((Reader) new InputStreamReader(stream, Charsets.UTF_8));
    }

}