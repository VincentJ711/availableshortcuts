package com.psquirrel.ijplugins.availableshortcuts;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.keymap.KeymapManager;
import com.intellij.openapi.keymap.KeymapUtil;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.KeyStroke;

public class ShowTakenShortcutsAction extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    Iterator<String> itr = KeymapManager.getInstance()
        .getActiveKeymap().getActionIdList().iterator();
    HashSet<KeyStroke> takenStrokes = new HashSet<>();
    HashSet<KeyStroke> availableStrokes = new HashSet<>();

    while (itr.hasNext()) {
      String actionId = itr.next();
      Shortcut[] cuts =
          KeymapUtil.getActiveKeymapShortcuts(actionId).getShortcuts();

      for (Shortcut cut : cuts) {
        if (cut instanceof KeyboardShortcut) {
          KeyboardShortcut sc = (KeyboardShortcut) cut;
          takenStrokes.add(sc.getFirstKeyStroke());

          if (sc.getSecondKeyStroke() != null) {
            takenStrokes.add(sc.getSecondKeyStroke());
          }
        }
      }
    }

    for (KeyStroke x : AllStrokes.values) {
      if (!takenStrokes.contains(x)) {
        availableStrokes.add(x);
      }
    }
// 359 770 545

    // TODO: insert action logic here
    Notifications.Bus.notify(new Notification(
        "hello",
        "listing taken shortcuts",
        takenStrokes.size() + " " + AllStrokes.values.size() +
            " " + availableStrokes.size(),
        NotificationType.INFORMATION
    ));
  }
}

/*
shift pressed DELETE, shift meta pressed DELETE, meta alt pressed N, shift meta pressed ENTER, shift ctrl pressed P, meta pressed 3, shift pressed PAGE_DOWN, pressed BACK_SPACE, meta pressed 4, meta alt pressed RIGHT, pressed ENTER, meta alt pressed F7, alt pressed F3, pressed TAB, ctrl pressed PERIOD, shift ctrl pressed 2, shift meta pressed A, meta pressed 5, shift pressed UP, meta alt pressed O, shift meta pressed W, ctrl pressed 0, meta pressed 6, shift meta alt pressed N, meta alt pressed DOWN, shift ctrl alt pressed UP, shift alt pressed A, shift meta pressed SUBTRACT, meta pressed MULTIPLY, shift meta pressed F9, ctrl pressed 2, meta pressed 7, shift ctrl pressed LEFT, meta alt pressed F8, meta pressed 8, meta alt pressed P, shift ctrl pressed 6, meta pressed ADD, shift meta alt pressed A, pressed ESCAPE, ctrl pressed 4, ctrl pressed TAB, shift meta pressed HOME, shift meta pressed SLASH, shift ctrl pressed G, pressed PAGE_DOWN, meta pressed 9, pressed PAGE_UP, shift pressed F1, ctrl pressed 6, pressed HOME, meta alt pressed F9, pressed END, shift meta pressed E, pressed UP, meta pressed SUBTRACT, pressed LEFT, meta alt pressed EQUALS, pressed DOWN, shift meta pressed OPEN_BRACKET, pressed RIGHT, shift meta alt pressed K, ctrl pressed 8, ctrl alt pressed UP, meta pressed BACK_SPACE, meta pressed SEMICOLON, shift meta pressed F2, alt pressed F7, shift meta alt pressed RIGHT, meta pressed DIVIDE, pressed 2, pressed 1, shift pressed F9, pressed 4, meta pressed F1, pressed 3, meta pressed ENTER, pressed 5, meta pressed EQUALS, shift meta pressed DOWN, shift pressed ESCAPE, alt pressed F8, meta pressed F2, shift meta pressed I, meta pressed F3, shift meta pressed T, shift meta alt pressed H, alt pressed F9, ctrl alt pressed DOWN, shift meta pressed MULTIPLY, shift meta alt pressed F7, shift ctrl pressed SPACE, meta pressed F4, shift alt pressed F10, shift meta pressed F6, pressed F, shift alt pressed UP, pressed E, shift pressed END, shift ctrl pressed 1, shift alt pressed S, alt pressed F10, meta pressed A, alt pressed HOME, shift ctrl pressed B, shift meta pressed PAGE_UP, meta alt pressed F12, meta pressed B, meta pressed F6, alt pressed BACK_SPACE, meta alt pressed T, pressed P, shift pressed RIGHT, meta pressed F7, shift pressed BACK_SPACE, pressed T, meta pressed C, shift meta pressed M, alt pressed LEFT, meta alt pressed MINUS, meta pressed D, shift ctrl pressed 5, meta pressed F8, ctrl pressed H, meta pressed QUOTE, alt pressed TAB, meta alt pressed A, meta pressed F9, shift ctrl pressed F, shift meta pressed F10, alt pressed F12, alt pressed UP, meta pressed E, shift alt pressed B, meta alt pressed U, ctrl pressed J, pressed NUMPAD2, meta pressed F, alt pressed ENTER, meta alt pressed PERIOD, pressed NUMPAD1, meta pressed F10, pressed NUMPAD4, shift meta pressed LEFT, pressed NUMPAD3, shift pressed F2, shift alt pressed I, ctrl pressed L, ctrl meta pressed SPACE, meta pressed F11, meta alt pressed B, pressed NUMPAD5, shift alt pressed F7, shift ctrl pressed 9, alt pressed RIGHT, shift ctrl pressed F10, meta pressed G, shift meta pressed SEMICOLON, meta alt pressed V, pressed MULTIPLY, shift alt pressed P, ctrl pressed N, shift ctrl pressed J, shift pressed F6, meta pressed F12, pressed ADD, meta alt pressed MULTIPLY, pressed SUBTRACT, ctrl meta pressed F, pressed F1, alt pressed DOWN, shift alt pressed W, pressed F3, ctrl pressed LEFT, pressed F2, meta pressed I, pressed F5, pressed F4, meta alt pressed C, shift pressed F10, pressed F7, meta pressed J, pressed F6, shift meta pressed BACK_SPACE, shift meta alt pressed L, pressed F9, ctrl pressed RIGHT, meta alt pressed W, pressed F8, pressed F11, shift ctrl pressed N, meta pressed K, pressed F12, meta alt pressed ADD, meta pressed L, meta pressed DELE

 */

/*
{ToggleBookmark8=[[shift ctrl pressed 8]], ToggleBookmark9=[[shift ctrl pressed 9]],
ClassNameCompletion=[[ctrl alt pressed SPACE]], ToggleBookmark6=[[shift ctrl pressed 6]],
 ToggleBookmark7=[[shift ctrl pressed 7]], ToggleReadOnlyAttribute=[],
 ToggleBookmark4=[[shift ctrl pressed 4]], Images.Thumbnails.ToggleRecursive=[[alt pressed MULTIPLY]],
 ToggleBookmark5=[[shift ctrl pressed 5]], Stop=[[meta pressed F2]], ToggleBookmark2=[[shift ctrl pressed 2]],
  ToggleBookmark3=[[shift ctrl pressed 3]], ToggleBookmark0=[[shift ctrl pressed 0]],
   ToggleBookmark1=[[shift ctrl pressed 1]], GotoRelated=[[meta alt pressed HOME]],
   EditorDuplicate=[[meta pressed D]], SearchEverywhere.NextTab=[[pressed TAB]],
   MaintenanceAction=[[shift meta alt pressed SLASH]], MaximizeToolWindow=[[shift meta pressed QUOTE]],
   PreviousOccurence=[[meta alt pressed UP]], ActivateRunToolWindow=[[meta pressed 4]],
   EditorUnSelectWord=[[shift meta pressed W]], SaveAll=[[meta pressed S]],
   ExpandRegionRecursively=[[meta alt pressed ADD], [meta alt pressed EQUALS]],
   EditorNextWordWithSelection=[[shift alt pressed RIGHT]],
   EditorAddRectangularSelectionOnMouseDrag=[button=1 clickCount=1 modifiers=832],
   ShowErrorDescription=[[meta pressed F1]], StructuralSearchPlugin.StructuralReplaceAction=[],
   ContextHelp=[[pressed F1]], FileChooser.GotoProject=[[meta pressed 2]],
   PreviousProjectWindow=[[meta alt pressed OPEN_BRACKET]],
   Git.Commit.And.Push.Executor=[[meta alt pressed K]], UnselectPreviousOccurrence=[[shift ctrl pressed G]],
   JumpToLastChange=[[shift meta pressed BACK_SPACE]], $SelectAll=[[meta pressed A]],
   Mvc.RunTarget=[[meta alt pressed G]], hg4idea.QFold=[[shift alt pressed D]], EditorContextInfo=[[shift ctrl pressed Q]], PopupHector=[[shift meta alt pressed H]], ConvertJavaToKotlin=[[shift meta alt pressed K]], XDebugger.SetValue=[[pressed F2]], VcsShowPrevChangeMarker=[[shift ctrl alt pressed UP]], HideActiveWindow=[[shift pressed ESCAPE]], CheckinProject=[[meta pressed K]], ActivateVersionControlToolWindow=[[meta pressed 9], [shift meta pressed 9]], hg4idea.QPushAction=[[shift alt pressed P]], FileChooser.GotoModule=[[meta pressed 3]], CollapseRegionRecursively=[[meta alt pressed SUBTRACT], [meta alt pressed MINUS]], Inline=[[meta alt pressed N]], EditorFocusGutter=[[shift alt pressed 6]+[pressed F]], EditorBackSpace=[[pressed BACK_SPACE], [shift pressed BACK_SPACE]], StructuralSearchPlugin.StructuralSearchAction=[], Vcs.Log.GoToParent=[[pressed RIGHT]], ChangesView.Move=[], Forward=[[meta alt pressed RIGHT], button=5 clickCount=1 modifiers=0], HippieCompletion=[[alt pressed SLASH]], ExpandAllRegions=[[shift meta pressed ADD], [shift meta pressed EQUALS]], PrevParameter=[[shift pressed TAB]], StopBackgroundProcesses=[[shift meta pressed F2]], DebugClass=[[shift ctrl pressed F9]], PreviousDiff=[[shift pressed F7]], EditorDeleteLine=[[meta pressed Y]], EditSourceInNewWindow=[[shift pressed F4]], Images.ShowThumbnails=[[shift meta pressed T]], ShowFilterPopup=[[ctrl alt pressed F]], GotoSymbol=[[shift meta alt pressed N]], EditorCreateRectangularSelectionOnMouseDrag=[button=1 clickCount=1 modifiers=512, button=2 clickCount=1 modifiers=0], CompareTwoFiles=[[meta pressed D]], EditorRightWithSelection=[[shift pressed RIGHT]], ActivateFindToolWindow=[[meta pressed 3]], EditorToggleInsertState=[], EditorPageDown=[[pressed PAGE_DOWN]], EditorCodeBlockEndWithSelection=[[shift meta pressed CLOSE_BRACKET]], EditorPageDownWithSelection=[[shift pressed PAGE_DOWN]], ChangesView.ShelveSilently=[[meta alt pressed H]], GuiDesigner.EditGroup=[[pressed F2]], Diff.FocusOppositePane=[[ctrl pressed TAB]], NextParameter=[[pressed TAB]], PasteMultiple=[[shift meta pressed V]], ShowUsages=[[meta alt pressed F7]], SurroundWith=[[meta alt pressed T]], $Delete=[[pressed DELETE], [pressed BACK_SPACE], [meta pressed BACK_SPACE]], MoveElementRight=[[shift meta alt pressed RIGHT]], ExpandTreeNode=[[pressed ADD]], ExtractMethod=[[meta alt pressed M]], ExpandRegion=[[meta pressed ADD], [meta pressed EQUALS]], EditSource=[[pressed F4]], ViewSource=[[meta pressed ENTER]], FileStructurePopup=[[meta pressed F12]], ShowIntentionActions=[[alt pressed ENTER]], PrevSplitter=[[shift alt pressed TAB]], RecentChanges=[[shift alt pressed C]], ForceRunToCursor=[[meta alt pressed F9]], QuickEvaluateExpression=[[meta alt pressed F8], button=1 clickCount=1 modifiers=512], ExternalJavaDoc=[[shift pressed F1]], QuickImplementations=[[shift meta pressed I]], ExpandAll=[[meta pressed ADD], [meta pressed EQUALS]], ChangesView.Rename=[[pressed F2], [shift pressed F6]], NextDiff=[[pressed F7]], DuplicatesForm.SendToRight=[[meta pressed 2]], hg4idea.QGotoFromPatches=[[shift alt pressed G]], EditorLineEndWithSelection=[[shift pressed END], [shift meta pressed RIGHT]], Diff.ApplyRightSide=[[shift ctrl pressed LEFT]], $Undo=[[meta pressed Z]], EditorChooseLookupItemDot=[[ctrl pressed PERIOD]], MoveLineUp=[[shift alt pressed UP]], SendEOF=[[meta pressed D]], RecentLocations=[[shift meta pressed E]], OpenElementInNewWindow=[[shift pressed ENTER]], CollapseBlock=[[shift meta pressed PERIOD]], Back=[[meta alt pressed LEFT], button=4 clickCount=1 modifiers=0], EditorMoveToPageTopWithSelection=[[shift meta pressed PAGE_UP]], GotoDeclaration=[[meta pressed B], button=1 clickCount=1 modifiers=256, button=2 clickCount=1 modifiers=0], StepOver=[[pressed F8]], DirDiffMenu.SynchronizeDiff=[[pressed ENTER]], Exit=[[meta pressed Q]], Console.History.Previous=[], IntroduceParameter=[[meta alt pressed P]], ShowPopupMenu=[], SwitchCoverage=[[meta alt pressed F6]], FindUsagesInFile=[[meta pressed F7]], EditorLeft=[[pressed LEFT]], Debugger.EditTypeSource=[[shift pressed F4]], ExtractFunctionToScope=[[shift meta alt pressed M]], Console.Execute=[[pressed ENTER]], QuickActionPopup=[[meta alt pressed ENTER]], EditorSplitLine=[[meta pressed ENTER]], RunDashboard.ShowConfigurations=[[shift meta pressed T]], ImplementMethods=[[meta pressed I]], Refresh=[[ctrl pressed F5]], EmmetPreviousEditPoint=[[ctrl alt pressed LEFT]], GuiDesigner.EditComponent=[[pressed F2]], CollapseTreeNode=[[pressed SUBTRACT]], FocusTracer=[[shift meta pressed F11]], ChooseRunConfiguration=[[shift alt pressed F10]], EditBreakpoint=[[shift meta pressed F8]], CodeInspection.OnEditor=[[shift alt pressed I]], ActivateStructureToolWindow=[[meta pressed 7]], EditorCodeBlockStartWithSelection=[[shift meta pressed OPEN_BRACKET]], ResizeToolWindowUp=[[shift meta pressed UP]], FindPrevious=[[shift pressed F3], [shift ctrl pressed L]], ReformatCode=[[meta alt pressed L]], GotoPrevElementUnderCaretUsage=[[ctrl alt pressed UP]], AutoIndentLines=[[meta alt pressed I]], EditorPreviousWordWithSelection=[[shift alt pressed LEFT]], EditorMatchBrace=[[shift meta pressed M]], Vcs.Push=[[shift meta pressed K]], DomCollectionControl.Add=[[pressed INSERT]], EditorAddOrRemoveCaret=[button=1 clickCount=1 modifiers=576], SelectIn=[[alt pressed F1]], ResizeToolWindowLeft=[[shift meta pressed LEFT]], XPathView.Actions.Evaluate=[[meta alt pressed X]+[pressed E]], CommentByBlockComment=[[shift ctrl pressed SLASH], [shift ctrl pressed DIVIDE], [shift meta pressed SLASH], [shift meta pressed DIVIDE]], context.save=[[shift alt pressed S]], EditorScrollDown=[[meta pressed DOWN]], Synchronize=[[meta alt pressed Y]], VcsHistory.ShowAllAffected=[[shift alt pressed A]], Refactorings.QuickListPopupAction=[[shift meta alt pressed T]], TestGestureAction=[Press, release and hold ⌘1], ExpandLiveTemplateByTab=[[pressed TAB]], Git.Reword.Commit=[[pressed F2], [shift pressed F6]], EditorLookupUp=[[ctrl pressed UP]], RunInspection=[[shift meta alt pressed I]], EditorPasteFromX11=[button=2 clickCount=1 modifiers=0], EditorStartNewLineBefore=[[meta alt pressed ENTER]], GotoChangedFile=[[meta pressed N]], PreviousEditorTab=[[shift ctrl pressed LEFT]], GotoCustomRegion=[[meta alt pressed PERIOD]], Console.Execute.Multiline=[[meta pressed ENTER]], SafeDelete=[[meta pressed DELETE]], ShowBookmarks=[[shift pressed F11]], InsertLiveTemplate=[[meta pressed J]], ToggleBookmark=[[pressed F11]], FullyExpandTreeNode=[[pressed MULTIPLY]], ForceStepOver=[[shift alt pressed F8]], FindInPath=[[shift ctrl pressed F]], MethodOverloadSwitchUp=[[meta pressed UP]], EditorChooseLookupItemReplace=[[pressed TAB]], Switcher=[[ctrl pressed TAB], [shift ctrl pressed TAB]], ShelvedChanges.Rename=[[pressed F2], [shift pressed F6]], EditorTextStartWithSelection=[[shift meta pressed HOME]], ActivateHierarchyToolWindow=[[meta pressed 8]], EditorDown=[[pressed DOWN]], DuplicatesForm.SendToLeft=[[meta pressed 1]], Compile=[[shift meta pressed F9]], RecentFiles=[[meta pressed E]], Unwrap=[[shift meta pressed DELETE]], EditorDelete=[[pressed DELETE]], TogglePopupHints=[], CloseContent=[[meta pressed F4]], Diff.NextChange=[[ctrl pressed RIGHT]], CompileDirty=[[meta pressed F9]], Debug=[[shift pressed F9]], ShowContent=[[ctrl pressed DOWN]], IntroduceTypeAlias=[[shift meta alt pressed A]], GotoAction=[[shift meta pressed A]], EditorLineStartWithSelection=[[shift pressed HOME], [shift meta pressed LEFT]], EditorCompleteStatement=[[shift meta pressed ENTER]], ShelveChanges.UnshelveWithDialog=[[shift meta pressed U]], ValidateXml=[], ActivateTODOToolWindow=[[meta pressed 6]], RunTargetAction=[[shift meta pressed F10]], ShowRecentTests=[[shift meta pressed SEMICOLON]], ToggleTemporaryLineBreakpoint=[[shift meta alt pressed F8]], Find=[[meta pressed F], [alt pressed F3]], EditorPageUp=[[pressed PAGE_UP]], NewElementSamePlace=[[ctrl alt pressed N]], EditorLeftWithSelection=[[shift pressed LEFT]], Resume=[[pressed F9]], MethodOverloadSwitchDown=[[meta pressed DOWN]], EditorShowGutterIconTooltip=[[shift alt pressed 6]+[pressed T]], ActivateProjectToolWindow=[[meta pressed 1]], CloseActiveTab=[[shift meta pressed F4]], TodoViewGroupByFlattenPackage=[[meta pressed F]], HighlightUsagesInFile=[[shift meta pressed F7]], CopyPaths=[[shift meta pressed C]], context.clear=[[shift alt pressed X]], TodoViewGroupByShowModules=[[meta pressed M]], MethodUp=[[ctrl ...
 */
