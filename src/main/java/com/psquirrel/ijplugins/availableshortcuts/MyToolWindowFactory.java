package com.psquirrel.ijplugins.availableshortcuts;

import com.intellij.openapi.keymap.KeymapManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class MyToolWindowFactory implements ToolWindowFactory {
  // Create the tool window content.
  public void createToolWindowContent(@NotNull Project project,
      @NotNull ToolWindow toolWindow) {
    MyToolWindow myToolWindow = new MyToolWindow();
    // MyToolWindow myToolWindow = new MyToolWindow(toolWindow);
    ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
    Content content =
        contentFactory.createContent(myToolWindow.getContent(), "", false);
    toolWindow.getContentManager().addContent(content);
    project.getMessageBus().connect().subscribe(KeymapManagerListener.TOPIC,
        myToolWindow);
  }
}
