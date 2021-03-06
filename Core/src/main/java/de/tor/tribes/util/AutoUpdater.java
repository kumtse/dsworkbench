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
package de.tor.tribes.util;

import de.tor.tribes.util.interfaces.UpdateListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Torridity
 */
public class AutoUpdater {

  private static Logger logger = Logger.getLogger("AutoUpdater");
  private final static File UPDATES_DIR = new File("./lib/classes");
  //private final static File CORE_JAR = new File("./lib/core.jar");//new File("./lib/core.jar");

  //private final static File CORE_JAR = new File("./store/core.jar");//For testing only
  // private final static File CORE_JAR = new File("C:/Users/Torridity/AppData/Local/DSWorkbench/lib/core.jar");//new File("./lib/core.jar");
  static {
    logger.debug("Initializing AutoUpdater");
    initialize();
  }

  public static void initialize() {
    if (!UPDATES_DIR.exists()) {
      if (UPDATES_DIR.mkdir()) {
        logger.info("Update directory created");
      }
    } else {
      logger.debug("Update directory already exists");
    }
  }

  public static List<String> getUpdatedResources(UpdateListener pListener) throws IOException {
    //no updates available
    return new ArrayList<String>(); 
  }
  public static void getImages(File pFolder, List<File> files) {
    for (File f : pFolder.listFiles()) {
      if (f.getPath().indexOf(".svn") == -1 && f.getPath().indexOf("skins") == -1 && f.getPath().indexOf("world") == -1 && f.getPath().indexOf("tex") == -1 && f.getPath().indexOf("splash") == -1) {
        if (f.isDirectory()) {
          getImages(f, files);
        } else {
          if (f.getName().endsWith("gif") || f.getName().endsWith("png")) {
            files.add(f);
          }
        }
      }
    }
  }

  public static void getClasses(File pFolder, List<File> files) {
    for (File f : pFolder.listFiles()) {
      if (f.getPath().indexOf(".svn") == -1 && f.getPath().indexOf("res") == -1) {
        if (f.isDirectory()) {
          getClasses(f, files);
        } else {
          if (f.getName().endsWith("java")) {
            files.add(f);
          }
        }
      }
    }
  }
}
