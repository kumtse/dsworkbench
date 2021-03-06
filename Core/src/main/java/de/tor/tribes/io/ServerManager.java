/* 
 * Copyright 2015 Torridity.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tor.tribes.io;

import de.tor.tribes.util.Constants;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.lorecraft.phparser.SerializedPhpParser;

/**
 * @author Charon
 */
public class ServerManager {

    private static Logger logger = Logger.getLogger("ServerManager");
    // private static Hashtable<String, URL> SERVER_LIST = null;
    // private static List<DatabaseServerEntry> SERVERS = null;
    private static LinkedHashMap<String, String> SERVERS = null;

    private static boolean SERVERS_UPDATED = false;

    static {
        SERVERS = new LinkedHashMap<>();
        File serverDir = new File(Constants.SERVER_DIR);
        if (!serverDir.exists()) {
            serverDir.mkdir();
        }
    }

    public static void loadServerList(Proxy pProxy) throws Exception {
        SERVERS = new LinkedHashMap<>();
        logger.debug("Loading servers for die-staemme.de");
        LinkedHashMap<String, String> serversDe = loadServerList("https://www.die-staemme.de", pProxy);
        logger.debug("Loading servers for staemme.ch");
        LinkedHashMap<String, String> serversCh = loadServerList("https://www.staemme.ch", pProxy);
        for (String id : serversDe.keySet()) {
            logger.debug("Adding server with id " + id + " and URL " + serversDe.get(id));
            SERVERS.put(id, serversDe.get(id));
        }
        for (String id : serversCh.keySet()) {
            logger.debug("Adding server with id " + id + " and URL " + serversCh.get(id));
            SERVERS.put(id, serversCh.get(id));
        }

        logger.debug("REading custom servers from servers.txt");

        BufferedReader r = null;
        try {
            if (new File("servers.txt").exists()) {
                r = new BufferedReader(new FileReader("servers.txt"));
                int cnt = 0;
                String line;
                while ((line = r.readLine()) != null) {
                    if (!line.startsWith("#")) {//not commented line
                        String[] split = line.split(";");
                        logger.debug("Adding server with id " + split[0] + " and URL " + split[1]);
                        SERVERS.put(split[0], split[1]);
                        cnt++;
                    }
                }
                logger.info("Read " + cnt + " external servers");
            }
        } catch (Throwable e) {
            logger.warn("Failed to read external servers. Skipping it.");
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    public static LinkedHashMap<String, String> loadServerList(String pServerBaseUrl, Proxy pProxy) throws Exception {
        URLConnection con;
        if (pProxy == null) {
            con = new URL(pServerBaseUrl + "/backend/get_servers.php").openConnection();
        } else {
            con = new URL(pServerBaseUrl + "/backend/get_servers.php").openConnection(pProxy);
        }
        InputStream isr = con.getInputStream();
        int bytes = 0;
        byte[] data = new byte[1024];
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int sum = 0;
        while (bytes != -1) {
            if (bytes != -1) {
                result.write(data, 0, bytes);
            }

            bytes = isr.read(data);
            sum += bytes;
            if (sum % 500 == 0) {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                }
            }
        }
        SerializedPhpParser serializedPhpParser = new SerializedPhpParser(result.toString());
        return (LinkedHashMap<String, String>) serializedPhpParser.parse();
    }

    /**
     * Get the listof locally stored servers
     */
    public static String[] getLocalServers() {
        List<String> servers = new LinkedList<String>();
        for (File serverDir : new File(Constants.SERVER_DIR).listFiles()) {
            if (serverDir.isDirectory()) {
                servers.add(serverDir.getName());
            }
        }
        return servers.toArray(new String[0]);
    }

    public static String[] getServerIDs() {
        String[] servers = SERVERS.keySet().toArray(new String[]{});
        Arrays.sort(servers, String.CASE_INSENSITIVE_ORDER);
        return servers;
    }

    public static String getServerURL(String pServerID) {
        return SERVERS.get(pServerID);
    }
}
