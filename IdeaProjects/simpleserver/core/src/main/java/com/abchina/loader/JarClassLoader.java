package com.abchina.loader;

import java.net.URLClassLoader;

import static com.abchina.utils.FileUtils.parseSinglePathToUrls;

public class JarClassLoader extends URLClassLoader {

    public JarClassLoader(String jarPath) throws Exception {
        super(parseSinglePathToUrls(jarPath));
    }

}
