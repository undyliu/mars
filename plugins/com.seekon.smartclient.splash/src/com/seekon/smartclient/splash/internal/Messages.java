package com.seekon.smartclient.splash.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.seekon.smartclient.splash.internal.messages"; //$NON-NLS-1$
  public static String SplashPlugin_initialization;
  public static String SplashPlugin_loading;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

}
